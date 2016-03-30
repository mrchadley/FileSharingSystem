package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.DirectoryChooser;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;


public class Controller
{
    @FXML private ListView<String> clientView;
    @FXML private ListView<String> serverView;

    HashMap<String, File> clientMap = new HashMap<>();

    private File clientDir = new File(".");

    private String serverIP = "127.0.0.1";

    Socket socket;

    InputStream is;
    OutputStream os;

    public void refresh()
    {
        clientView.getItems().clear();
        for(File file : clientDir.listFiles())
        {
            if (file.getName().endsWith(".txt")) {
                clientMap.put(file.getName(), file);
                clientView.getItems().add(file.getName());
            }
        }
        getDir();
    }

    public void upload(ActionEvent actionEvent) throws IOException
    {
        String selectedFile = clientView.getSelectionModel().getSelectedItem();
        if(selectedFile != null) {
            socket = new Socket(serverIP, 8080);
            is = socket.getInputStream();
            os = socket.getOutputStream();

            PrintWriter out = new PrintWriter(os, true);

            out.println("UPLOAD " + selectedFile);

            Scanner scan = new Scanner(clientMap.get(selectedFile));
            while (scan.hasNextLine()) {
                out.println(scan.nextLine());
            }
            out.close();
        }
        refresh();
    }


    public void download(ActionEvent actionEvent) throws IOException
    {
        String selectedFile = serverView.getSelectionModel().getSelectedItem();
        if(selectedFile != null) {
            socket = new Socket(serverIP, 8080);
            is = socket.getInputStream();
            os = socket.getOutputStream();

            PrintWriter out = new PrintWriter(os, true);

            out.println("DOWNLOAD " + selectedFile);


            File file = new File(clientDir + "/" + selectedFile);

            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter fileOut = new PrintWriter(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while ((line = in.readLine()) != null) {
                fileOut.println(line);
            }

            in.close();
            fileOut.close();
            out.close();
        }
        refresh();
    }

    public void getDir()
    {
        try {
            socket = new Socket(serverIP, 8080);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            pw.println("DIR");

            serverView.getItems().clear();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line = br.readLine()) != null)
            {
                System.out.println(line);
                serverView.getItems().add(line);
            }

            pw.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize()
    {
        TextInputDialog ipDialog = new TextInputDialog("127.0.0.1");
        ipDialog.initStyle(StageStyle.UNIFIED);
        ipDialog.setTitle("Server IP Address");
        ipDialog.setHeaderText("");
        ipDialog.setContentText("Please enter the Server's IP Address: ");

        Optional<String> ip = ipDialog.showAndWait();
        if(ip.isPresent())
            serverIP = ip.get();

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Please select the Client directory...");
        chooser.setInitialDirectory(new File("."));
        clientDir = chooser.showDialog(null);

        System.out.println(clientDir.toString());

        refresh();
    }
}