package server;

import java.io.*;
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

            String line = null;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while((line = in.readLine()) != null)
            {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            clientSocket.close();

        }
        catch( IOException e)
        {
            e.printStackTrace();
        }
    }
}