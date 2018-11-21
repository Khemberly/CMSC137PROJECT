import proto.TcpPacketProtos.*;
import proto.PlayerProtos.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // connect socket to 202.92.144.45/80
            Socket server = new Socket("202.92.144.45", 80);
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            InputStream in = server.getInputStream();
            System.out.println("Connected to server!");    
                
            // initialize variables
            Scanner get = new Scanner(System.in);
            String lobbyId = "";
            String name = "";
            int choice = 0;

            // get player name
            while (name.length() < 3 || name.length() > 8) {
                System.out.print("Enter a name (3-8 characters): ");
                name = get.nextLine();
                if (name.length() < 3 || name.length() > 8) System.out.println("Invalid name."); 
            }

            // create player
            Player.Builder player = Player.newBuilder();
            player.setName(name);

            // menu
            while (choice != 3) {
                // clear data
                get = new Scanner(System.in);;
                choice = 0;

                // show
                System.out.println("\nWhat do you wish to do?");
                System.out.println("[1] Create a Chat Lobby");
                System.out.println("[2] Join a Chat Lobby");
                System.out.println("[3] Leave");
                System.out.print(">> ");

                choice = get.nextInt();
                switch(choice) {
                    case 1: {
                        CreateLobby lobby = new CreateLobby(server, out, in);
                        lobbyId = lobby.getLobbyId();}
                        // no break, will automatically connect to lobby
                    case 2: {
                        if (choice == 2) {
                            // clear data first
                            lobbyId = "";
                            get = new Scanner(System.in);

                            // get lobby id
                            while (lobbyId.length() == 0) {
                                System.out.print("Enter Lobby ID: ");
                                lobbyId = get.nextLine();
                            }
                        }
                        
                        // will go straight here if from create a lobby
                        JoinLobby connect = new JoinLobby(lobbyId, player, out, in);
                        break;}
                    case 3: {System.out.println("Exiting.."); break;}
                    default: System.out.println("Invalid choice.");
                }
            }

            // close instances
            get.close();
            server.close();

        } catch(IOException e) { // error cannot connect to server
            System.out.println("Cannot find (or disconnected from) Server");
        }
    }
}