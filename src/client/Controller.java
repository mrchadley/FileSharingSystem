package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    private File serverDir = new File(".");

    Socket socket;

    //Socket socket = new Socket();
    //@FXML private Button upload;


    public void upload(ActionEvent actionEvent) throws IOException
    {
        try {
            socket = new Socket("127.0.0.1", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        String selectedFile = clientView.getSelectionModel().getSelectedItem();
        out.println("UPLOAD " + selectedFile);

        Scanner scan = new Scanner(clientMap.get(selectedFile));
        while(scan.hasNextLine())
        {
            out.println(scan.nextLine());
        }
        out.close();
    }


    public void download(ActionEvent actionEvent){

    }

    public void initialize()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the client directory...");
        chooser.setInitialDirectory(new File("."));
        clientDir = chooser.showDialog(null);

        System.out.println(clientDir.toString());

        DirectoryChooser chooser2 = new DirectoryChooser();
        chooser2.setTitle("Select the server directory...");
        chooser2.setInitialDirectory(new File("."));
        serverDir = chooser2.showDialog(null);

        System.out.println(serverDir.toString());

        for(File file : clientDir.listFiles())
        {
            if (file.getName().endsWith(".txt")) {
                clientMap.put(file.getName(), file);
                clientView.getItems().add(file.getName());
            }
        }


        for(File file : serverDir.listFiles())
        {
            if (file.getName().endsWith(".txt")) {
                serverMap.put(file.getName(), file);
                serverView.getItems().add(file.getName());
            }
        }

    }
}