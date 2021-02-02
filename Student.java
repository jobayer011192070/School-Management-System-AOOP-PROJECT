import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Student implements Runnable {
    private BufferedWriter writer;
    private BufferedReader reader;
    ArrayList<Student> students;
    String studentName;

    Student(Socket socket, ArrayList<Student> students) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            writer = new BufferedWriter(outputStreamWriter);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);

//            studentName = reader.readLine();
//
            this.students = students;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = reader.readLine();
            while (line !=null){
                File file = new File("Data.txt");
                try {

                    FileWriter data = new FileWriter(file,true);
                    BufferedWriter bw = new BufferedWriter(data);
                    bw.write(line);
                    bw.newLine();
                    bw.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                writer.write(line+"\n");
                writer.flush();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
