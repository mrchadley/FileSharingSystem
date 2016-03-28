package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread
{
    private Socket clientSocket;
    private BufferedReader input;
    private File serverDirectory;

    public ClientThread(Socket connection, File serverDirectory)
    {
        super(connection.toString());
        clientSocket = connection;
        this.serverDirectory = serverDirectory;
    }

    @Override public void run()
    {
        handleCommand();
    }

    private void handleCommand()
    {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line = input.readLine();
            String[] args = line.split(" ");

            if (args[0].intern() == "DIR")
            {
                displayDirectory();
            }
            else if (args[0].intern() == "UPLOAD")
            {
                uploadFile(args[1]);
            }
            else if (args[0].intern() == "DOWNLOAD")
            {
                downloadFile(args[1]);
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();;
        }

    }


    private void displayDirectory()
    {
        try
        {
            input.close();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            for(File file : serverDirectory.listFiles())
            {
                if(file.isFile() && file.getName().endsWith(".txt"));
                out.println(file.getName());
            }
            out.close();
            clientSocket.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    private void uploadFile(String filename)
    {
        System.out.println("uploading file");
        try {
            File file = new File(serverDirectory + "\\" + filename);
            if(!file.exists())
            {
                file.createNewFile();
            }
            String line = null;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            while((line = input.readLine()) != null)
            {
                writer.write(line);
                writer.newLine();
            }
            input.close();
            writer.close();
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private void downloadFile(String filename)
    {
        try {
            input.close();
            File file = new File(serverDirectory + "\\" + filename);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine())
            {
                out.println(scan.nextLine());
            }

            out.close();
            scan.close();
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
