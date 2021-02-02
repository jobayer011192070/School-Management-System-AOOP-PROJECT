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
    Button Registrationbtn,saveResultButton,resultBackButton;
    @FXML
    Button resultBtn,addForAttendance,addResultButton;
    @FXML
    Button backToHome,attendanceDataSave,saveattendance,attendanceButton,attendanceBack;
    @FXML
    Button login, noticeButton, studentButton,studentSearch;
    @FXML
    Button searchReasultButton;
    @FXML
    TextField  resultIDType;

    @FXML
    TextField fn,ln,id,cn,classBtn,studentID,pass,studentSearchTextField,attendanceTextfield1,attendanceTextfield,AttendencePresent,AttendenceAbsent,AttendenceID;
    @FXML
    TextField idForResultTF,gradeForResultTF;

    @FXML
    CheckBox present,absent;
    @FXML
    TextArea studentSearchTextArea,showResultTextArea;
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
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Attendance.fxml"));
        Stage window = (Stage) attendanceButton.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void addForAttendanceClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AttendanceRecord.fxml"));
        Stage window = (Stage) addForAttendance.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void searchReasultClick(){
        String resultID = resultIDType.getText();
        resultIDType.setText("");
        File file = new File("Result.txt");
        class Result1 {
            public String resultId,grade;

            public Result1(String resultId, String grade) {
                this.resultId = resultId;
                this.grade = grade;
            }
        }

        try
        {

            FileReader fr = new FileReader(file);
            BufferedReader buf = new BufferedReader(fr);

            Result1[] student = new Result1[500];
            String r = buf.readLine();
            int i=0;
            String ab = null;
            while (r!=null)
            {
                String[] rt = r.split(" ");
                String resultId = rt[0];
                String grade = rt[1];
                student[i] = new Result1( resultId,grade);

                if(resultID.equals(student[i].resultId)) {
                    ab = "a";
                    showResultTextArea.setText(student[i].resultId+"\t\t\t\t\t"+student[i].grade);
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
    public void addResultClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/ResultRecord.fxml"));
        Stage window = (Stage) addResultButton.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void attendanceBackClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Attendance.fxml"));
        Stage window = (Stage) attendanceBack.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void resultBackClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Result.fxml"));
        Stage window = (Stage) resultBackButton.getScene().getWindow();
        window.setScene(new Scene(root,600,575));
    }
    @FXML
    public void attendanceDataSaveClick() {
        String str = AttendenceID.getText()+" "+AttendencePresent.getText()+" "+AttendenceAbsent.getText();
        AttendenceID.setText("");
        AttendencePresent.setText("");
        AttendenceAbsent.setText("");
        File file = new File("Attendance.txt");
        try {
            FileWriter data = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(data);
            bw.write(str);
            bw.newLine();
            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void saveResultClick() {
        String str1 = idForResultTF.getText()+" "+gradeForResultTF.getText();
        idForResultTF.setText("");
        gradeForResultTF.setText("");

        File file = new File("Result.txt");
        try {
            FileWriter data = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(data);
            bw.write(str1);
            bw.newLine();
            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

                    File file1 = new File("Data.txt");
                    if (present.equals(true)) {
//
                        try {
                            FileWriter data = new FileWriter(file1, true);
                            BufferedWriter bw = new BufferedWriter(data);
                            bw.write(student[i].id + " "+ student[i].CLASS);
                            bw.newLine();
                            bw.close();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
//                        student[i].absent++;
                    }
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
