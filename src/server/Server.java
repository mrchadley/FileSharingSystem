package server;

public class Server
{
    public static final String DIR = "dir";
    public static final String UPLOAD = "upload";
    public static final String DOWNLOAD = "download";

    public static void main(String[] args)
    {
        String operation = args[0].toLowerCase();
        String filename = "";
        if(args.length == 2)
                filename = args[1];

        if(operation == DIR)
        {
            displayDirectory();
        }
        else if(operation == UPLOAD)
        {
            uploadFile(filename);
        }
        else if(operation == DOWNLOAD)
        {
            downloadFile(filename);
        }
    }

    public static void displayDirectory()
    {

    }
    public static void uploadFile(String filename)
    {

    }
    public static void downloadFile(String filename)
    {

    }
}


