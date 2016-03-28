package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class DownloadThread extends Thread
{
    private ServerSocket downloadSocket;
    private File file;

    DownloadThread(String name, String filename)
    {
        super(name);
        try {
            file = new File(filename);
            file.createNewFile();
            downloadSocket = new ServerSocket(Server.DOWNLOAD_PORT);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void run()
    {
        try {
            Socket clientSocket = downloadSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine())
            {
                out.println(scan.nextLine());
            }

            out.close();
            clientSocket.close();
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }
    }
}