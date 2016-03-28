package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private String hostname = null;
    private int port = 0;


    public Client(String ip, int port) {
        this.hostname = ip;
        this.port = port;
    }

    public void upload(String txt) {
        try {
            Socket socket = new Socket(this.hostname, this.port);

            BufferedWriter bufOut = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()) );

            BufferedReader bufIn = new BufferedReader( new InputStreamReader(socket.getInputStream()) );

            FileWriter outFile = new FileWriter("test.txt");
                try {
                    String line = null;
                    while ((line = bufIn.readLine()) != null) {
                        System.out.println(line);
                        outFile.write(line);
                    }
                    //bufOut.flush();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }




/*
            FileWriter out = new FileWriter(socket.getOutputStream());
            out.println("Upload " + txt + "\r\n");
            out.println("Host: " + this.hostname + "\r\n\r\n");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //FileWriter outFile = new FileWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                out.write(line);
            }

            out.close();
            in.close();
            socket.close();
*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8080);
        client.upload("text.txt");
    }

}
