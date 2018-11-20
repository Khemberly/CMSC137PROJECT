import proto.TcpPacketProtos.*;
import proto.PlayerProtos.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class JoinLobby extends Thread {
    public JoinLobby(String lobbyId, Player.Builder player, DataOutputStream out, InputStream in) {
        try {
            // create packet
            TcpPacket.ConnectPacket.Builder connection = TcpPacket.ConnectPacket.newBuilder();
            connection.setType(TcpPacket.PacketType.CONNECT);
            connection.setLobbyId(lobbyId);
            connection.setPlayer(player);

            // send lobby packet bytes
            out.write(connection.build().toByteArray());
            
            // get reply
            byte[] connectData = new byte[1024];
            int count = in.read(connectData);
            
            // parse reply
            connectData = Arrays.copyOf(connectData, count);
            System.out.println("You are connected to Lobby " + TcpPacket.ConnectPacket.parseFrom(connectData).getLobbyId());
        } catch(IOException e) { // error cannot connect to server
            System.out.println("Server returned an error.");
        }
    }
}