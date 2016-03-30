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
        System.out.println("running");

        handleCommand();
    }

    private void handleCommand()
    {
        System.out.println("handling");
        try {

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);


            String line = input.readLine();

            System.out.println(line);
            String[] args = line.split(" ");

            String filename = "";
            for(int i = 1; i < args.length; i++)
                filename += " " + args[i];

            if (args[0].intern() == "DIR")
            {
                displayDirectory();
            }
            else if (args[0].intern() == "UPLOAD")
            {
                uploadFile(filename);
            }
            else if (args[0].intern() == "DOWNLOAD")
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
        System.out.println("displaying directory");

        for(File file : serverDirectory.listFiles())
        {
            if(file.isFile() && file.getName().endsWith(".txt"))
            {
                output.println(file.getName());
                System.out.println(file.getName());
            }
        }
        output.close();
        input.close();
        clientSocket.close();
        this.interrupt();
    }
    private void uploadFile(String filename) throws IOException, InterruptedException
    {
        System.out.println("uploading file");

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
