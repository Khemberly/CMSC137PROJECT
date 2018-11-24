import proto.TcpPacketProtos.TcpPacket.DisconnectPacket;
import proto.TcpPacketProtos.TcpPacket.PlayerListPacket;
import proto.TcpPacketProtos.TcpPacket.ConnectPacket;
import proto.TcpPacketProtos.TcpPacket.PacketType;
import proto.TcpPacketProtos.TcpPacket.ChatPacket;
import proto.TcpPacketProtos.TcpPacket;
import proto.PlayerProtos.Player;
import java.io.*;
import java.net.*;
import java.util.*;

public class Receive extends Thread {
    InputStream in;

    public Receive(InputStream in) {
        this.in = in;
    }

    public void run() {
        // some constants
        final PacketType DISCONNECT = PacketType.DISCONNECT;
        final PacketType CONNECT = PacketType.CONNECT;
        final PacketType CREATE_LOBBY = PacketType.CREATE_LOBBY;
        final PacketType CHAT = PacketType.CHAT;
        final PacketType PLAYER_LIST = PacketType.PLAYER_LIST;
        final PacketType ERR_LDNE = PacketType.ERR_LDNE;
        final PacketType ERR_LFULL = PacketType.ERR_LFULL;
        final PacketType ERR = PacketType.ERR;
        final DisconnectPacket.Update NORMAL = DisconnectPacket.Update.NORMAL;

        while (true) {
            try {
                // get reply
                byte[] chatData = new byte[1024];
                int count = in.read(chatData);

                // parse reply
                chatData = Arrays.copyOf(chatData, count);
                switch(TcpPacket.parseFrom(chatData).getType()) {
                    case DISCONNECT: {
                        // get disconnect data then print
                        DisconnectPacket packet = DisconnectPacket.parseFrom(chatData);
                        DisconnectPacket.Update reason = packet.getUpdate();
                        String playerName = packet.getPlayer().getName();

                        if (reason == NORMAL) {System.out.println(playerName + " has disconnected from the lobby.");} 
                        else {System.out.println(playerName + " has lost connection.");}
                        break;}
                    case CONNECT: {
                        // get connection update then print
                        String playerName = ConnectPacket.parseFrom(chatData).getPlayer().getName();
                        System.out.println(playerName + " has connected to the lobby."); break;}
                    case CHAT: {
                        // get chat data then print
                        String playerName = ChatPacket.parseFrom(chatData).getPlayer().getName();
                        String message = ChatPacket.parseFrom(chatData).getMessage();
                        System.out.println(playerName + ": " + message); break;}
                    case PLAYER_LIST: {
                        // get players then print
                        List<Player> playerlist = PlayerListPacket.parseFrom(chatData).getPlayerListList();
                        System.out.println("Players:");
                        for (Player player : playerlist) System.out.print(player.getName() + " ");
                        System.out.println(""); break;}
                    default: {System.out.println("Should not go here");break;}
                }

            } catch(IOException e) { // error cannot connect to server
                System.out.println("Server returned an error.");
            } catch(NegativeArraySizeException e) { // unknown packet sent
                break;
            }
        }
    }
}