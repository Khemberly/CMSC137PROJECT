import proto.TcpPacketProtos.*;
import proto.PlayerProtos.*;
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
        final TcpPacket.PacketType DISCONNECT = TcpPacket.PacketType.DISCONNECT;
        final TcpPacket.PacketType CONNECT = TcpPacket.PacketType.CONNECT;
        final TcpPacket.PacketType CREATE_LOBBY = TcpPacket.PacketType.CREATE_LOBBY;
        final TcpPacket.PacketType CHAT = TcpPacket.PacketType.CHAT;
        final TcpPacket.PacketType PLAYER_LIST = TcpPacket.PacketType.PLAYER_LIST;
        final TcpPacket.PacketType ERR_LDNE = TcpPacket.PacketType.ERR_LDNE;
        final TcpPacket.PacketType ERR_LFULL = TcpPacket.PacketType.ERR_LFULL;
        final TcpPacket.PacketType ERR = TcpPacket.PacketType.ERR;

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
                        TcpPacket.DisconnectPacket packet = TcpPacket.DisconnectPacket.parseFrom(chatData);
                        String playerName = packet.getPlayer().getName();
                        TcpPacket.DisconnectPacket.Update reason = packet.getUpdate();

                        if (reason == TcpPacket.DisconnectPacket.Update.NORMAL) {System.out.println(playerName + " has disconnected from the lobby.");} 
                        else {System.out.println(playerName + " has lost connection.");}
                        break;}
                    case CONNECT: {
                        // get connection update then print
                        String playerName = TcpPacket.ConnectPacket.parseFrom(chatData).getPlayer().getName();
                        System.out.println(playerName + " has connected to the lobby."); break;}
                    case CHAT: {
                        // get chat data then print
                        String playerName = TcpPacket.ChatPacket.parseFrom(chatData).getPlayer().getName();
                        String message = TcpPacket.ChatPacket.parseFrom(chatData).getMessage();
                        System.out.println(playerName + ": " + message); break;}
                    case PLAYER_LIST: {
                        // get players then print
                        List<Player> playerlist = TcpPacket.PlayerListPacket.parseFrom(chatData).getPlayerListList();
                        System.out.println("Players:");
                        for (Player player : playerlist) System.out.print(player.getName() + " ");
                    }
                    default: {System.out.println("hi");break;}
                }

            } catch(IOException e) { // error cannot connect to server
                System.out.println("Server returned an error.");
            } catch(NegativeArraySizeException e) { // unknown packet sent
                break;
            }
        }
    }
}