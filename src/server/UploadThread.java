package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UploadThread extends Thread
{
    private ServerSocket uploadSocket;
    private File file;

    UploadThread(String name, String filename, int port)
    {
        super(name);
        try {
            file = new File(filename);
            file.createNewFile();
            uploadSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void run()
    {
        try {
            Socket clientSocket = uploadSocket.accept();


        }catch( IOException e)
        {
            e.printStackTrace();
        }
    }
}