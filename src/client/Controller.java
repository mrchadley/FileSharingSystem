package client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;



public class Controller
{
    @FXML private ListView<String> clientView;
    @FXML private ListView<String> serverView;

    HashMap<File, String> clientMap = new HashMap<>();
    HashMap<File, String> serverMap = new HashMap<>();

    private File clientDir = new File(".");
    private File serverDir = new File(".");

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

        for(File file : clientDir.listFiles())
        {
            if (file.getName().endsWith(".txt")) {
                clientMap.put(file, file.getName());
                clientView.getItems().add(file.getName());
            }
        }


        for(File file : serverDir.listFiles())
        {
            if (file.getName().endsWith(".txt")) {
                serverMap.put(file, file.getName());
                serverView.getItems().add(file.getName());
            }
        }

    }
}