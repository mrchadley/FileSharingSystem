package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Controller
{
    @FXML private ListView<String> clientView;
    @FXML private ListView<String> serverView;

    HashMap<String, File> clientMap = new HashMap<>();
    HashMap<String, File> serverMap = new HashMap<>();

    private File clientDir = new File(".");

    private String serverIP = "10.10.123.136";

    Socket socket;

    InputStream is;
    OutputStream os;

    //Socket socket = new Socket();
    //@FXML private Button upload;

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
        socket = new Socket(serverIP, 8080);
        is = socket.getInputStream();
        os = socket.getOutputStream();

        PrintWriter out = new PrintWriter(os, true);
        String selectedFile = clientView.getSelectionModel().getSelectedItem();
        out.println("UPLOAD " + selectedFile);

        Scanner scan = new Scanner(clientMap.get(selectedFile));
        while(scan.hasNextLine())
        {
            out.println(scan.nextLine());
        }
        out.close();
        refresh();
    }


    public void download(ActionEvent actionEvent) throws IOException
    {
        String selectedFile = serverView.getSelectionModel().getSelectedItem();
        //if(selectedFile != null) {
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
        //}
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
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the client directory...");
        chooser.setInitialDirectory(new File("."));
        clientDir = chooser.showDialog(null);

        System.out.println(clientDir.toString());

        refresh();
    }
}