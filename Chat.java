import proto.TcpPacketProtos.*;
import proto.PlayerProtos.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Chat extends Thread {
    DataOutputStream out;
    InputStream in;

    public Chat(DataOutputStream out, InputStream in) {
        this.out = out;
        this.in = in;
    }

    public void run() {
        Scanner get = new Scanner(System.in);
        String message;
        while (true) {
            try {
                // get message
                message = ""; // clear
                message = get.nextLine();
                if (message.length() == 0) continue; // empty messages should not be sent
                else if (message.equals("/quit")) { // if message is /quit, leave lobby
                    // disconnect
                    TcpPacket.DisconnectPacket.Builder dc = TcpPacket.DisconnectPacket.newBuilder();
                    dc.setType(TcpPacket.PacketType.DISCONNECT);

                    // send disconnect packet bytes
                    out.write(dc.build().toByteArray());
                    break;}
                else if (message.equals("/players")) { // if message is /players, get player list
                    // get players
                    TcpPacket.PlayerListPacket.Builder getPlayers = TcpPacket.PlayerListPacket.newBuilder();
                    getPlayers.setType(TcpPacket.PacketType.PLAYER_LIST);

                    // send player list checker packet bytes
                    out.write(getPlayers.build().toByteArray());}
                else { // else send as chat message
                    // create lobby packet
                    TcpPacket.ChatPacket.Builder chat = TcpPacket.ChatPacket.newBuilder();
                    chat.setType(TcpPacket.PacketType.CHAT);
                    chat.setMessage(message);

                    // send lobby packet bytes
                    out.write(chat.build().toByteArray());
                }
            
            } catch(IOException e) { // error cannot connect to server
                System.out.println("Server returned an error.");
            }
        }
    }
}