import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{
        System.out.println("== Cliente ==");

        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true){
            System.out.println("Cliente - " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println("-> " + mensagem);

            dos.writeUTF("Recebi sua mensagem: " + mensagem);
        }
    }
}
