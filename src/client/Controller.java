package client;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.LinkedList;

public class Controller
{
    @FXML private TableView<File> clientView;
    @FXML private TableView<File> serverView;

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


        clientView.getItems().addAll(clientDir.listFiles());
        serverView.getItems().addAll(serverDir.listFiles());
    }
}