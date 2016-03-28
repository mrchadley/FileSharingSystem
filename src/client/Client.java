package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String hostname = null;
    private int port = 0;


    public Client(String ip, int port) {
        this.hostname = ip;
        this.port = port;
    }

    public void upload(String txt)
    throws IOException{

        Socket socket = new Socket(this.hostname, this.port);

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Scanner scan = new Scanner(new File("test.txt"));

        while(scan.hasNextLine())
        {
            out.println(scan.nextLine());
        }

        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8080);
        try {
            client.upload("text.txt");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
