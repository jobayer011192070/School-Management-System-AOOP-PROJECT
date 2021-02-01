package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;


public class Controller {
    @FXML
    Button registrationBtn;
    @FXML
    Button Registrationbtn;
    @FXML
    Button resultBtn;
    @FXML
    Button backToHome,attendanceBtn,saveattendance;
    @FXML
    Button login, noticeButton, studentButton,studentSearch;
    @FXML
    TextField fn,ln,id,cn,classBtn,studentID,pass,studentSearchTextField,attendanceTextfield;
    @FXML
    CheckBox present,absent;
    @FXML
    TextArea studentSearchTextArea;
    private BufferedWriter writer;
    private BufferedReader reader;
    public Controller(){
        try{
            Socket socket = new Socket("127.0.0.1",5555);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            writer = new BufferedWriter(outputStreamWriter);
            Thread thread = new Thread()
            {
                public void run(){
                    try {
                        String line = reader.readLine()+"\n";
                        while (line !=null){
                            System.out.println(line);
                            line = reader.readLine();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            };
            thread.start();
        }catch (IOException e){

        }

    }

    @FXML
    public void registration() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Registration.fxml"));
        Stage window = (Stage) registrationBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    void RegistrationClick() throws IOException {
        String msg = fn.getText()+" "+ln.getText()+" "+id.getText()+" "+cn.getText()+" "+classBtn.getText();
        fn.setText("");
        ln.setText("");
        id.setText("");
        cn.setText("");
        classBtn.setText("");
//          writer.write("add\n");
        writer.write(msg);
        writer.newLine();

        writer.flush();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        Stage window = (Stage) Registrationbtn.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }

    @FXML
    public void loginAction(){

        String ID = studentID.getText();
        String password = pass.getText();
        File file = new File("Data.txt");
        class Data {
            public String firstName,lastName,id,contact,CLASS;

            public Data(String firstName, String lastName, String id, String contact, String CLASS) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.id = id;
                this.contact = contact;
                this.CLASS = CLASS;
            }
        }

        try
        {

            FileReader fr = new FileReader(file);
            BufferedReader buf = new BufferedReader(fr);

            Data[] student = new Data[500];
            String r = buf.readLine();
            int i=0;
            String ab = null;
            while (r!=null)
            {
                String[] rt = r.split(" ");
                String firstName = rt[0];
                String lastName = rt[1];
                String id = rt[2];
                String contact = rt[3];
                String CLASS = rt[4];
                student[i] = new Data(firstName,lastName,id,contact,CLASS);

                if(ID.equals(student[i].id) && password.equals(student[i].id)) {
                    ab = "a";

                    Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
                    Stage window = (Stage) login.getScene().getWindow();
                    window.setScene(new Scene(root,600,575));
                }
                i++;
                r = buf.readLine();
            }
            if(ab==null){
                JOptionPane.showMessageDialog(null, "Invalid login, please try again.");
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    @FXML
    public void noticeclick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Notice.fxml"));
        Stage window = (Stage) noticeButton.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void studentClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Student.fxml"));
        Stage window = (Stage) studentButton.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void resultClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Result.fxml"));
        Stage window = (Stage) resultBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void backToHomeClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        Stage window = (Stage) backToHome.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void attendanceClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        Stage window = (Stage) attendanceBtn.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void studentSearchClick(){

        String searchID = studentSearchTextField.getText();
        File file = new File("Data.txt");
        class Data {
            public String firstName,lastName,id,contact,CLASS;

            public Data(String firstName, String lastName, String id, String contact, String CLASS) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.id = id;
                this.contact = contact;
                this.CLASS = CLASS;
            }
        }

        try
        {

            FileReader fr = new FileReader(file);
            BufferedReader buf = new BufferedReader(fr);

            Data[] student = new Data[500];
            String r = buf.readLine();
            int i=0;
            String ab = null;
            while (r!=null)
            {
                String[] rt = r.split(" ");
                String firstName = rt[0];
                String lastName = rt[1];
                String id = rt[2];
                String contact = rt[3];
                String CLASS = rt[4];
                student[i] = new Data(firstName,lastName,id,contact,CLASS);

                if(searchID.equals(student[i].id)) {
                    ab = "a";
                    studentSearchTextArea.setText(student[i].firstName+" "+student[i].lastName+" "+student[i].id+" "+student[i].contact+" "+student[i].CLASS);
                }
                i++;
                r = buf.readLine();
            }
            if(ab==null){
                JOptionPane.showMessageDialog(null, "Invalid ID, please put valid ID.");
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void saveattendanceClick(){
        String attId = attendanceTextfield.getText();
        File file = new File("Data.txt");
        class Data {
            public String firstName,lastName,id,contact,CLASS;

            public Data(String firstName, String lastName, String id, String contact, String CLASS) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.id = id;
                this.contact = contact;
                this.CLASS = CLASS;
            }
        }

        try
        {

            FileReader fr = new FileReader(file);
            BufferedReader buf = new BufferedReader(fr);

            Data[] student = new Data[500];
            String r = buf.readLine();
            int i=0;
            String ab = null;
            while (r!=null)
            {
                String[] rt = r.split(" ");
                String firstName = rt[0];
                String lastName = rt[1];
                String id = rt[2];
                String contact = rt[3];
                String CLASS = rt[4];
                student[i] = new Data(firstName,lastName,id,contact,CLASS);

                if(attId.equals(student[i].id)) {
                    ab = "a";
                    studentSearchTextArea.setText(student[i].firstName+" "+student[i].lastName+" "+student[i].id+" "+student[i].contact+" "+student[i].CLASS);
                }
                i++;
                r = buf.readLine();
            }
            if(ab==null){
                JOptionPane.showMessageDialog(null, "Invalid ID, please put valid ID.");
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
