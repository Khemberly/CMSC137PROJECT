import proto.TcpPacketProtos.TcpPacket.ConnectPacket;
import proto.TcpPacketProtos.TcpPacket.PacketType;
import proto.PlayerProtos.Player;
import java.io.*;
import java.net.*;
import java.util.*;

public class JoinLobby {
    public JoinLobby(String lobbyId, Player.Builder player, DataOutputStream out, InputStream in) {
        try {
            // create packet
            ConnectPacket.Builder connection = ConnectPacket.newBuilder();
            connection.setType(PacketType.CONNECT);
            connection.setLobbyId(lobbyId);
            connection.setPlayer(player);

            // send connect packet bytes
            out.write(connection.build().toByteArray());
            
            // get reply
            byte[] connectData = new byte[1024];
            int count = in.read(connectData);
            
            // parse reply
            connectData = Arrays.copyOf(connectData, count);
            System.out.println("\nYou are connected to Lobby " + ConnectPacket.parseFrom(connectData).getLobbyId());

            // start lobby
            Receive listener = new Receive(in);
            Chat client = new Chat(out, in);
            listener.start();
            client.start();

            // keep it running while threads are alive
            while(client.isAlive() && listener.isAlive());

            // kill threads when either is stopped
            listener.interrupt();
            client.interrupt();
        } catch(IOException e) { // error cannot connect to server
            System.out.println("Server returned an error.");
        }
    }
}