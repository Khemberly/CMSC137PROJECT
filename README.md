# CMSC137PROJECT
It is multiplayer game that is playable over a network using UDP. 

### Compilation
Download the protobuf source release (version 3.6.1) then go to the java directory and traverse the com, google, protobuf folders until you reach the java files. In the directory:

```
$ javac -cp protobuf-java-3.6.1.jar -d . *.java
```

Copy the contents of the com folder that was made into the repository folder with the same name.
Go to the java-protos folder of the repository and compile:

```
$ javac -cp ../protobuf-java-3.6.1.jar -d . *.java
```

Copy the contents of the proto folder that was made into the repository folder with the same name.
Compile the main java files in the root directory of the repository:

```
$ javac *.java
```

The ending directories of the root folder would like this:

```
com/
    google/
        protobuf/
            <class files>
java-protos/
    player.proto
    PlayerProtos.java
    tcp_packet.proto
    TcpPacketProtos.java
proto/
    <class files>
.gitignore
protobuf-java-3.6.1.jar
README.md
TCPChat.java