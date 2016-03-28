package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server
{
    public static final String DIR = "dir";
    public static final String UPLOAD = "upload";
    public static final int UPLOAD_PORT = 8081;
    public static final String DOWNLOAD = "download";

    private ServerSocket serverSocket = null;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRequests() {
        System.out.println("Server Listening");

        try {
            File file = new File("test_uploaded.txt");
            file.createNewFile();

            String line = null;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    )
            );
/*
            String line = null;
            String filename = "text.txt";

            while ((line = in.readLine()) != null) {
                String[] text = line.split(" ");
                break;
            }

            // copy the data from the file to the socket
            FileInputStream fileIn = new FileInputStream(filename);
            OutputStream out = clientSocket.getOutputStream();
            //uploadFile(filename, out);
            copyAllText(fileIn, out);

            // close everything down
            fileIn.close();
            in.close();
            out.close();
            clientSocket.close();*/


            while((line = in.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void copyAllText(FileInputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int numBytes = -1;
        while ((numBytes = in.read(buffer)) > 0) {
            out.write(buffer);
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server(8080);
        server.handleRequests();

    }


    public static void displayDirectory()
    {

    }
    public static void uploadFile(String filename, OutputStream out)
    {

    }
    public static void downloadFile(String filename)
    {

    }
}

//main/listener thread

//display directory thread

//upload thread

//download thread



