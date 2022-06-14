import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    //declare variables
    //Constructor
    Database db;
    Socket clientSocket;
    int clientId;

    PrintWriter outToClient = null;
    BufferedReader inFromClient = null;

    public ClientHandler (Socket socket, int clientId, Database db) {
//        complete the constructor
        this.clientSocket = socket;
        this.db = db;
        this.clientId = clientId;
    }

    public void run() {
            /*System.out.println("ClientHandler started...");
              Create I/O streams to read/write data, PrintWriter and BufferedReader
              Receive messages from the client and send replies, until the user types "stop"
                  System.out.println("Client sent the artist name " + clientMessage);
                  Request the number of titles from the db
                  Send reply to Client:
                  outToClient.println("Number of titles: " + titlesNum + " records found");

              System.out.println("Client " + clientId + " has disconnected");
              outToClient.println("Connection closed, Goodbye!");
              Close I/O streams and socket*/

        try {
            System.out.println("ClientHandler started...");
            // Message from client
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Message for client
            outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            String ClientMsg;
            while (!(ClientMsg = inFromClient.readLine()).equals("stop")) {
                System.out.println("Client sent the artist name " + ClientMsg);
                int titlesNum = db.getTitles(ClientMsg);
                outToClient.println("Number of titles: " + titlesNum + " records found");


            }

            System.out.println("Client " + clientId + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");

            outToClient.close();
            inFromClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
