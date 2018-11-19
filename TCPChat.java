import proto.TcpPacketProtos.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class TCPChat {
  public static void main(String[] args) {
    try {
      // variables
      String lobbyId;
      int choice = 0;

      // connect socket to 202.92.144.45/80
      Socket server = new Socket("202.92.144.45", 80);
      DataOutputStream out = new DataOutputStream(server.getOutputStream());
      InputStream in = server.getInputStream();
      System.out.println("Just connected to " + server.getRemoteSocketAddress());    

      // create packet
      TcpPacket.Builder packet = TcpPacket.newBuilder();
      packet.setType(TcpPacket.PacketType.CONNECT);

      // create lobby packet
      TcpPacket.CreateLobbyPacket.Builder lobby = TcpPacket.CreateLobbyPacket.newBuilder();
      lobby.setType(TcpPacket.PacketType.CREATE_LOBBY);
      lobby.setMaxPlayers(4);

      // send lobby packet bytes
      out.write(lobby.build().toByteArray())
      
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
}

// public void println(String text) {System.out.println(text);} // easier printing
// public void print(String text) {System.out.print(text);} // easier printing
// public void print_menu() {
//   println("What do you wish to do?");
//   println("[1] Create a chat lobby");
//   println("[2] Join a chat lobby");
//   print(">>> ");
// }