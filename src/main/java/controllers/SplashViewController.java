package controllers;

import com.google.gson.Gson;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Goal;
import model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SplashViewController implements Initializable {

    //Variables

    private Stage primaryStage;


    //Components JavaFx

    public AnchorPane layer1;
    public AnchorPane layer2;

    public final ValidationChecker validationChecker = new ValidationChecker();
    public final AnimationController animationController = new AnimationController();

    //Components JavaFx registration

    public Label SignUpLabel;
    public Button signUpButton;
    public TextField nicknameTF;
    public TextField firstnameTF;
    public TextField lastnameTF;
    public TextField emailTF;
    public PasswordField passwordPF;
    public PasswordField confirmPasswordPF;
    public FontIcon signUpIcon;
    public Label userExistInDataBaseLabel;

    public TextFlow textFlow = new TextFlow();


    //Components JavaFx login

    public Label infoLabel;
    public Label wrongLoPLabel;
    public Label signInLabel;
    public Button signInButton;
    public TextField nicknameSiTF;
    public PasswordField passwordSiPF;
    public FontIcon signInIcon;

    private double x,y;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validationChecker.setNodes(SplashViewController.this);
        animationController.setNodes(SplashViewController.this);

        textFlow.setStyle("-fx-background-color: WHITE");
        textFlow.setMinSize(200,100);
        textFlow.setVisible(false);
        textFlow.setFocusTraversable(false);
        layer1.getChildren().add(textFlow);
        validationChecker.setTextFlow(textFlow);

        animationController.disableNodesLogIn();

    }

    public void registerOMC() {
        try {
            User tempUser = new User(nicknameTF.getText(),
                        (passwordPF.getText()),
                        firstnameTF.getText(),
                        lastnameTF.getText(),
                        emailTF.getText());


            System.out.println(tempUser.toString());

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:8080/users");

            StringEntity newUser = new StringEntity(new Gson().toJson(tempUser), "UTF-8");


            postRequest.addHeader("content-type", "application/json; charset=UTF-8");
            postRequest.setEntity(newUser);

            HttpResponse response = httpClient.execute(postRequest);

            HttpEntity entity = response.getEntity();

            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200){
                Thread thread = new Thread(){
                    public void run(){
                        try {
                            animationController.logInOMC();
                            sleep(1000);

                            infoLabel.setVisible(true);

                            sleep(10000);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        userExistInDataBaseLabel.setVisible(false);
                    }
                };

                thread.start();

            }

            if (response.getStatusLine().getStatusCode() == 500){
                Thread thread = new Thread(){
                    public void run(){
                        try {
                            userExistInDataBaseLabel.setVisible(true);

                            sleep(10000);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        userExistInDataBaseLabel.setVisible(false);
                    }
                };

                thread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInButtonOMC() {
        User tempUser = new User(nicknameSiTF.getText(),(passwordSiPF.getText()));


        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost postRequest = new HttpPost("http://localhost:8080/users/login");

        postRequest.addHeader("content-type", "application/json; charset=UTF-8");

        StringEntity newUser = new StringEntity(new Gson().toJson(tempUser), "UTF-8");

        postRequest.setEntity(newUser);

        try {
            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                User loginUser = new Gson().fromJson(responseString, User.class);
                System.out.println(loginUser);




                CloseableHttpClient httpClient2 = HttpClients.createDefault();

                HttpGet getRequest2 = new HttpGet("http://localhost:8080/goals/"+ loginUser.getNickname());

                getRequest2.addHeader("content-type", "application/json; charset=UTF-8");

                HttpResponse response2 = httpClient.execute(getRequest2);

                HttpEntity entity2 = response2.getEntity();
                String responseString2 = EntityUtils.toString(entity2, "UTF-8");



                Goal[] goalArray = new Gson().fromJson(responseString2,Goal[].class);


                loginUser.setGoalsArrayList(new ArrayList<>(Arrays.asList(goalArray)));


                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent mainView = fxmlLoader.load((getClass().getResource("mainView.fxml").openStream()));
                Scene scene = new Scene(mainView);
                primaryStage.setScene(scene);


                MainViewController mainViewController = fxmlLoader.getController();

                mainViewController.setUser(loginUser);
                mainViewController.prepareMainGui();

            }else {
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            wrongLoPLabel.setVisible(true);

                            sleep(10000);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        wrongLoPLabel.setVisible(false);
                    }
                };
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void textFieldClicked() {
        validationChecker.textFieldClicked();
    }

    public void signUpOMC(MouseEvent mouseEvent) {
        animationController.signUpOMC();
    }

    public void logInOMC(MouseEvent mouseEvent) {
        animationController.logInOMC();
    }

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

    public void focusOn() {
        layer1.requestFocus();
        validationChecker.textFlow.setVisible(false);
    }

    public void checkIfEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            logInButtonOMC();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}