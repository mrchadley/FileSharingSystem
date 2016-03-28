package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable
{
    private ServerSocket serverSocket = null;
    private Socket connectionBuffer = null;
    private boolean running = true;
    private File sharedFolder;

    public Server(int port, String folderName)
    {
        try
        {
            serverSocket = new ServerSocket(port);

            sharedFolder = new File(folderName);

            if(!sharedFolder.exists())
                sharedFolder.mkdir();

            System.out.println(sharedFolder);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while(running)
        {
            waitForConnections();
        }
    }


    private void waitForConnections()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting for Connections");
                connectionBuffer = serverSocket.accept();

                System.out.println("Connection Established");
                ClientThread newConnection = new ClientThread(connectionBuffer, sharedFolder);
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server(8080, "C:\\Server-Shared");
        server.run();
    }
}


