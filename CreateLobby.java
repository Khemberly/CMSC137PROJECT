import proto.TcpPacketProtos.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CreateLobby {
  String lobbyId;

  public CreateLobby() {
    try {
      // variables
      Scanner get = new Scanner(System.in);
      int maxPlayers = 0;

      // get max players
      while (maxPlayers < 3 || maxPlayers > 16) {
        System.out.println("\nHow many players can join the lobby? (3-16 only)");
        System.out.print(">> ");
        maxPlayers = get.nextInt();
        // error
        if (maxPlayers < 3 || maxPlayers > 16) System.out.println("Invalid number of players.");
      }

      // connect socket to 202.92.144.45/80
      Socket server = new Socket("202.92.144.45", 80);
      DataOutputStream out = new DataOutputStream(server.getOutputStream());
      InputStream in = server.getInputStream();
      System.out.println("Just connected to " + server.getRemoteSocketAddress());    

      // create lobby packet
      TcpPacket.CreateLobbyPacket.Builder lobby = TcpPacket.CreateLobbyPacket.newBuilder();
      lobby.setType(TcpPacket.PacketType.CREATE_LOBBY);
      lobby.setMaxPlayers(maxPlayers);

      // send lobby packet bytes
      out.write(lobby.build().toByteArray());
      
      // get reply
      byte[] lobbyData = new byte[1024];
      int count = in.read(lobbyData);
      
      // parse reply
      lobbyData = Arrays.copyOf(lobbyData, count);
      lobbyId = TcpPacket.CreateLobbyPacket.parseFrom(lobbyData).getLobbyId();
      System.out.println(lobbyId);
      
    } catch(IOException e) { // error cannot connect to server
      e.printStackTrace();
      System.out.println("Cannot find (or disconnected from) Server");
   }
  }

  public String getLobbyId() {
    return this.lobbyId;
  }
}