package controllers;

import com.google.gson.Gson;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class RegisterUserViewController {

    public TextField nicknameTF;
    public TextField firstnameTF;
    public TextField lastnameTF;
    public TextField emailTF;
    public PasswordField passwordPF;
    public PasswordField confirmPasswordPF;

    public Label infoLabel;

    private double x,y;

    public void dragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    public void pressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    public void min(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void registerOMC(MouseEvent mouseEvent) {
        try {
            User tempUser = new User(nicknameTF.getText(),
                                    passwordPF.getText(),
                                    firstnameTF.getText(),
                                    lastnameTF.getText(),
                                    emailTF.getText());


            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:8080/users");
//
            StringEntity newUser = new StringEntity(new Gson().toJson(tempUser), "UTF-8");


            postRequest.addHeader("content-type", "application/json; charset=UTF-8");
            postRequest.setEntity(newUser);

            HttpResponse response = httpClient.execute(postRequest);

            HttpEntity entity = response.getEntity();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);


            if (response.getStatusLine().getStatusCode() == 200){
                Thread thread = new Thread(){
                    public void run(){
                        infoLabel.setVisible(true);
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        infoLabel.setVisible(false);
                    }
                };

                thread.start();

                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.close();

            }else {
                alert.setTitle("Information about Sign in");
                alert.setContentText("Fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }

    public void setInfoLabel(Label infoLabel) {
        this.infoLabel = infoLabel;
    }
}
