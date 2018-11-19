import proto.TcpPacketProtos.*;
import java.io.*;
import java.net.*;

public class TCPChat {
  public static void main(String[] args) {
    try {
      // connect socket to 202.92.144.45/80
      Socket server = new Socket("202.92.144.45", 80);
      System.out.println("Just connected to " + server.getRemoteSocketAddress());    
      int choice = 0;

      // while (choice != 3) {
      //   print_menu();
      // }
      TcpPacket packet = TcpPacket.newBuilder();


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