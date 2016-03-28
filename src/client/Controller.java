package client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.LinkedList;

public class Controller
{
    @FXML private ListView<String> clientView;
    @FXML private ListView<String> serverView;


    private File clientDir = new File(".");
    private File serverDir = new File(".");
    public String filenameClient;
    public String filenameServer;

    public void initialize()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File("."));
        clientDir = chooser.showDialog(null);

        System.out.println(clientDir.toString());

        DirectoryChooser chooser2 = new DirectoryChooser();
        chooser2.setInitialDirectory(new File("."));
        serverDir = chooser2.showDialog(null);

        System.out.println(serverDir.toString());


        File[] filesC = clientDir.listFiles();
        for (File file : filesC) {
            if ( (file.isDirectory() == false) && (file.getAbsolutePath().endsWith(".txt") )) {
                filenameClient = file.toString().substring(filenameClient.lastIndexOf("/") + 1);
                clientView.getItems().addAll(filenameClient);
            }
        }

        File[] filesS = serverDir.listFiles();
        for (File file : filesS) {
            if ( (file.isDirectory() == false) && (file.getAbsolutePath().endsWith(".txt") )) {
                filenameServer = file.toString().substring(filenameServer.lastIndexOf("/") + 1);
                serverView.getItems().addAll(filenameServer);
            }
        }

    }
}