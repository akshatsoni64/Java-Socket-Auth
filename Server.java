import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ServerSocket server = new ServerSocket(6666);
            Socket connection = server.accept();

            DataOutputStream dout = new DataOutputStream(connection.getOutputStream());
            DataInputStream dis = new DataInputStream(connection.getInputStream());

            String prompt = "\nSelect from available choices:\n";
            prompt += "1: Greet Client\n";
            prompt += "2: Authenticate\n";
            prompt += "3: Terminate Connection";

            System.out.println(prompt);
            int input = scanner.nextInt();
            while (connection.isConnected()) {
                switch (input) {
                    case 1:
                        dout.writeUTF("Hello");
                        dout.flush();
                        break;
                    case 2:
                        dout.writeUTF("Send Username");
                        dout.flush();

                        String username = dis.readUTF();
                        // System.out.print("Username: ");
                        // System.out.println(username);

                        dout.writeUTF("Send Password");
                        dout.flush();

                        String password = dis.readUTF();
                        // System.out.print("Password: ");
                        // System.out.println(password);

                        Boolean authed = (username.equals("username") && password.equals("password"));
                        String response = authed ? "Authentication Successful" : "Authentication Failed";
                        System.out.println(response);                        

                        dout.writeUTF(response);
                        dout.flush();

                        break;
                    case 3:
                        dout.writeUTF("See ya!");
                        dout.flush();

                        connection.close();
                        connection.shutdownInput();
                        server.close();
                        break;
                    default:
                        break;
                }
                System.out.println(prompt);
                input = scanner.nextInt();
                // String clientMessage = dis.readUTF();
                // System.out.println("message= " + clientMessage);
            }
            scanner.close();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}