import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            Socket connection = new Socket("localhost", 6666);
            System.out.println("Connected to Server...");

            DataInputStream dis = new DataInputStream(connection.getInputStream());

            DataOutputStream dout = new DataOutputStream(connection.getOutputStream());
            while (connection.isConnected()) {
                String serverMessage = (String) dis.readUTF();
                switch (serverMessage) {
                    case "Hello":
                        System.out.println(serverMessage);
                        dout.writeUTF("Hello");
                        dout.flush();
                        break;
                    case "Send Username":
                        System.out.println("Enter Username:");
                        String username = scanner.nextLine();
                        dout.writeUTF(username);
                        dout.flush();
                        break;
                    case "Send Password":
                        System.out.println("Enter Password:");
                        String password = scanner.nextLine();
                        dout.writeUTF(password);
                        dout.flush();
                        break;
                    case "See ya!":
                        dout.close();
                        connection.close();
                        break;
                    default:
                        System.out.println(serverMessage);
                        break;
                }
            }
            scanner.close();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}