import proto.TcpPacketProtos.TcpPacket.CreateLobbyPacket;
import proto.TcpPacketProtos.TcpPacket.PacketType;
import java.io.*;
import java.net.*;
import java.util.*;

public class CreateLobby {
  String lobbyId;

  public CreateLobby(Socket server, DataOutputStream out, InputStream in) {
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

      // create lobby packet
      CreateLobbyPacket.Builder lobby = CreateLobbyPacket.newBuilder();
      lobby.setType(PacketType.CREATE_LOBBY);
      lobby.setMaxPlayers(maxPlayers);

      // send lobby packet bytes
      out.write(lobby.build().toByteArray());
      
      // get reply
      byte[] lobbyData = new byte[1024];
      int count = in.read(lobbyData);
      
      // parse reply
      lobbyData = Arrays.copyOf(lobbyData, count);
      lobbyId = CreateLobbyPacket.parseFrom(lobbyData).getLobbyId();

    } catch(IOException e) { // server error
      System.out.println("Server returned an error.");
   }
  }

  // getters
  public String getLobbyId() {return this.lobbyId;}
}