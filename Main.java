import java.util.*;
import proto.PlayerProtos.*;

public class Main {
    public static void main(String[] args) {
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
            System.out.println("\nWhat do you wish to do?");
            System.out.println("[1] Create a Chat Lobby");
            System.out.println("[2] Join a Chat Lobby");
            System.out.println("[3] Leave");
            System.out.print(">> ");

            choice = get.nextInt();
            switch(choice) {
                case 1: {lobbyId = new CreateLobby().getLobbyId();}
                case 2: {
                    if (lobbyId.equals("")) break;
                    System.out.println("Joining lobby"); break;}
                case 3: {System.out.println("Exiting.."); break;}
                default: System.out.println("Invalid choice.");
            }
        }

        get.close();
    }
}