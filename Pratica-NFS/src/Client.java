import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==");

        // configurando o socket
        Socket socket = new Socket("127.0.0.1", 7001);
        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String[] comandos = {"readdir", "create", "remove", "rename"};
        // laço infinito do cliente
        while (true) {
            Scanner teclado = new Scanner(System.in);
            // escrevendo para o servidor
            String entrada = teclado.nextLine();
            dos.writeUTF(entrada);
            if(entrada.equals("create") || entrada.equals("remove")){
                String mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
                entrada = teclado.nextLine();
                dos.writeUTF(entrada);
                mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
            }
            else if(entrada.equals("rename")){
                String mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
                entrada = teclado.nextLine();
                dos.writeUTF(entrada);
                mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
                entrada = teclado.nextLine();
                dos.writeUTF(entrada);
                mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
            }
            else{
                // lendo o que o servidor enviou
                String mensagem = dis.readUTF();
                System.out.println("Servidor falou: " + mensagem);
            }
        }
        /*
         * Observe o while acima. Perceba que primeiro se escreve para o servidor (linha 27), depois se lê do canal de
         * entrada (linha 30), vindo do servidor. Agora observe o código while do Servidor2. Lá, primeiro se lê,
         * depois se escreve. De outra forma, haveria um deadlock.
         */
    }
}
