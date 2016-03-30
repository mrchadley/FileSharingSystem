package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread
{
    private Socket clientSocket;

    private BufferedReader input;
    private PrintWriter output;

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
            output = new PrintWriter(clientSocket.getOutputStream(), true);


            String line = input.readLine();
            int index = line.indexOf(" ");
            String command;
            String filename;

            if(index == -1)
            {
                command = line;
                filename = "";
            }
            else
            {
                command = line.substring(0, index);
                filename = line.substring(index + 1, line.length());
            }

            if (command.intern() == "DIR")
            {
                displayDirectory();
            }
            else if (command.intern() == "UPLOAD")
            {
                uploadFile(filename);
            }
            else if (command.intern() == "DOWNLOAD")
            {
                downloadFile(filename);
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }

    }


    private void displayDirectory() throws IOException, InterruptedException
    {

        for(File file : serverDirectory.listFiles())
        {
            if(file.isFile() && file.getName().endsWith(".txt"))
            {
                output.println(file.getName());
            }
        }
        output.close();
        input.close();
        clientSocket.close();
        this.interrupt();
    }
    private void uploadFile(String filename) throws IOException, InterruptedException
    {
        File file = new File(serverDirectory + "/" + filename);
        if(!file.exists())
        {
            file.createNewFile();
        }

        output = new PrintWriter(file);

        String line = null;

        while((line = input.readLine()) != null)
        {
            output.println(line);
        }
        input.close();
        output.close();
        clientSocket.close();
        this.interrupt();

    }
    private void downloadFile(String filename)throws IOException, InterruptedException
    {
        File file = new File(serverDirectory + "/" + filename);

        Scanner scan = new Scanner(file);

        while(scan.hasNextLine())
        {
            output.println(scan.nextLine());
        }

        output.close();
        scan.close();
        clientSocket.close();
        this.interrupt();
    }
}
