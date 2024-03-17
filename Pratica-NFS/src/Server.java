import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    public static void main(String[] args) throws IOException{
        Path path = Paths.get("Z:\\IFPB\\Sistemas-Distribuidos\\Pratica-NFS\\Pratica-NFS\\arquivos");

        System.out.println("== Server ==");

        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("Client - " + socket.getInetAddress());

        while (true){
            String message = dis.readUTF();
            System.out.println("-> " + message);

            // READDIR
            if(message.equals("readdir")){
                String saida = "Those are the files...\n";
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                    for (Path file: stream) {
                        saida += (file.getFileName().toString()+"\n");
                    }
                    dos.writeUTF(saida);
                    System.out.println(saida);
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            // CREATE
            else if(message.equals("create")){
                dos.writeUTF("Enter the file name:");
                String fileName = dis.readUTF();
                Path file = Paths.get(path.toString() + "\\" + fileName);
                if(Files.exists(file)){
                    dos.writeUTF("File already exists");
                    System.out.println("File already exists");
                }else{
                    Files.createFile(file);
                    dos.writeUTF("File created");
                    System.out.println("File created - " + file.getFileName());
                }
            }

            // DELETE
            else if(message.equals("delete")){
                dos.writeUTF("Enter the file name:");
                String fileName = dis.readUTF();
                Path file = Paths.get(path.toString() + "\\" + fileName);
                if(Files.exists(file)){
                    Files.delete(file);
                    dos.writeUTF("File deleted");
                    System.out.println("File deleted - " + file.getFileName());
                }else{
                    dos.writeUTF("File not found");
                    System.out.println("File not found");
                }
            }
            
            else{
                dos.writeUTF("Receive your message - " + message);
            }
        }
    }
}
