import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(7777);
            ArrayList<Student> students = new ArrayList<>();

            while (true) {
                try {
                    Socket socket = server.accept();
                    Student client = new Student(socket, students);
                    students.add(client);
                    Thread clientThread = new Thread(client);
                    clientThread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
