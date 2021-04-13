package controllers;

import com.google.gson.Gson;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SplashViewController implements Initializable {

    //Variables

    private User loginUser;


    //Components JavaFx

    public AnchorPane layer1;
    public AnchorPane layer2;

    private final ValidationChecker validationChecker = new ValidationChecker();

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
    private final TextFlow textFlow = new TextFlow();


    //Components JavaFx login

    public Label infoLabel;
    public Label signInLabel;
    public Button signInButton;

    public TextField nicknameSiTF;
    public PasswordField passwordSiPF;
    public FontIcon signInIcon;

    private double x,y;
    private final int[] tab = new int[] {0,0,0,0,0,0};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //textArea options
        validationChecker.setTab(tab);

        layer1.getChildren().add(textFlow);

        textFlow.setStyle("-fx-background-color: WHITE");

        textFlow.setMinSize(200,100);
        textFlow.setVisible(false);
        textFlow.setFocusTraversable(false);
        validationChecker.setTextFlow(textFlow);


        signInButton.setVisible(false);
        signInLabel.setVisible(true);
        nicknameSiTF.setVisible(true);
        passwordSiPF.setVisible(true);
        signInIcon.setVisible(true);

        nicknameTF.setVisible(false);
        firstnameTF.setVisible(false);
        emailTF.setVisible(false);
        lastnameTF.setVisible(false);
        passwordPF.setVisible(false);
        confirmPasswordPF.setVisible(false);
        signUpIcon.setVisible(false);
        SignUpLabel.setVisible(false);
        signUpButton.setVisible(true);

    }

    public void textFieldClicked() {

        if (nicknameTF.isFocused()) {
            Bounds nicknameTFBounds = nicknameTF.localToScene(nicknameTF.getBoundsInLocal());
            textFlow.setLayoutX(nicknameTFBounds.getCenterX());
            textFlow.setLayoutY(nicknameTFBounds.getMaxY());

            validationChecker.checkRegisterValidation(nicknameTF.getText(),0);

            if(tab[0] == 1)
                nicknameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nicknameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");


            textFlow.setVisible(true);
        } else if (firstnameTF.isFocused()){
            Bounds firstnameBounds = firstnameTF.localToScene(firstnameTF.getBoundsInLocal());
            textFlow.setLayoutX(firstnameBounds.getCenterX());
            textFlow.setLayoutY(firstnameBounds.getMaxY());

            validationChecker.checkRegisterValidation(firstnameTF.getText(),1);

            if(tab[1] == 1)
                firstnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                firstnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (lastnameTF.isFocused()) {
            Bounds lastnameBounds = lastnameTF.localToScene(lastnameTF.getBoundsInLocal());
            textFlow.setLayoutX(lastnameBounds.getCenterX());
            textFlow.setLayoutY(lastnameBounds.getMaxY());

            validationChecker.checkRegisterValidation(lastnameTF.getText(),2);

            if(tab[2] == 1)
                lastnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                lastnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (emailTF.isFocused()){
            Bounds emailBounds = emailTF.localToScene(emailTF.getBoundsInLocal());
            textFlow.setLayoutX(emailBounds.getCenterX());
            textFlow.setLayoutY(emailBounds.getMaxY());

            validationChecker.checkRegisterEmailValidation(emailTF.getText(),3);

            if(tab[3] == 1)
                emailTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                emailTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (passwordPF.isFocused()) {
            Bounds passwordBounds = passwordPF.localToScene(passwordPF.getBoundsInLocal());
            textFlow.setLayoutX(passwordBounds.getCenterX());
            textFlow.setLayoutY(passwordBounds.getMaxY());

            validationChecker.checkPasswordsValidation(passwordPF.getText(),confirmPasswordPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                passwordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                confirmPasswordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            }
            textFlow.setVisible(true);
        } else if (confirmPasswordPF.isFocused()){
            Bounds confirmPasswordBounds = confirmPasswordPF.localToScene(confirmPasswordPF.getBoundsInLocal());
            textFlow.setLayoutX(confirmPasswordBounds.getCenterX());
            textFlow.setLayoutY(confirmPasswordBounds.getMaxY());

            validationChecker.checkPasswordsValidation(confirmPasswordPF.getText(),passwordPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                passwordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                confirmPasswordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            }

            textFlow.setVisible(true);
        }else {
            textFlow.setVisible(false);

        }

        int sum=0;
        for(int i = 0; i < tab.length;i++)
          sum+=tab[i];


        if(sum == 6) {
            signUpIcon.setIconColor(Color.GREEN);
            signUpIcon.setDisable(false);
        }else {
            signUpIcon.setIconColor(Color.RED);
            signUpIcon.setDisable(true);
        }
    }

    public void signUpOMC() {
        textFieldClicked();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(layer2);
                slide.setToX(-880);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(layer1);
                slide2.setToX(-880);
                slide2.play();


                Thread.sleep(1000);

                signInButton.setVisible(true);
                signInLabel.setVisible(false);
                nicknameSiTF.setVisible(false);
                passwordSiPF.setVisible(false);
                signInIcon.setVisible(false);


                signUpButton.setVisible(false);
                nicknameTF.setVisible(true);
                firstnameTF.setVisible(true);
                emailTF.setVisible(true);
                lastnameTF.setVisible(true);
                passwordPF.setVisible(true);
                confirmPasswordPF.setVisible(true);
                signUpIcon.setVisible(true);
                SignUpLabel.setVisible(true);



                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(layer1);
                slide3.setToX(0);
                slide3.play();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void logInOMC() {
        resetTextFields();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(layer2);

                slide.setToX(0);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(layer1);

                slide2.setToX(880);
                slide2.play();

                Thread.sleep(1000);

                signInButton.setVisible(false);
                nicknameTF.setVisible(false);
                firstnameTF.setVisible(false);
                emailTF.setVisible(false);
                lastnameTF.setVisible(false);
                passwordPF.setVisible(false);
                confirmPasswordPF.setVisible(false);
                signUpIcon.setVisible(false);
                SignUpLabel.setVisible(false);
                userExistInDataBaseLabel.setVisible(false);

                signInLabel.setVisible(true);
                nicknameSiTF.setVisible(true);
                passwordSiPF.setVisible(true);
                signInIcon.setVisible(true);
                signUpButton.setVisible(true);

                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(layer1);

                slide3.setToX(0);
                slide3.play();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void registerOMC() {
        try {
            User tempUser = null;
            try {
                tempUser = new User(nicknameTF.getText(),
                        hashPassword(passwordPF.getText()),
                        firstnameTF.getText(),
                        lastnameTF.getText(),
                        emailTF.getText());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

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
                            logInOMC();
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
        User tempUser = null;
        try {
            tempUser = new User(nicknameSiTF.getText(),hashPassword(passwordSiPF.getText()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost postRequest = new HttpPost("http://localhost:8080/users/login");

        postRequest.addHeader("content-type", "application/json; charset=UTF-8");

        StringEntity newUser = new StringEntity(new Gson().toJson(tempUser), "UTF-8");

        postRequest.setEntity(newUser);

        try {
            HttpResponse response = httpClient.execute(postRequest);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            loginUser = new Gson().fromJson(responseString,User.class);
            System.out.println(loginUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetTextFields(){

        nicknameTF.clear();
        firstnameTF.clear();
        emailTF.clear();
        lastnameTF.clear();
        passwordPF.clear();
        confirmPasswordPF.clear();

        tab[0] = 0;
        tab[1] = 0;
        tab[2] = 0;
        tab[3] = 0;
        tab[4] = 0;
        tab[5] = 0;





        if(tab[0] == 1)
            nicknameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        else
            nicknameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

        if(tab[1] == 1)
            firstnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        else
            firstnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

        if(tab[2] == 1)
            lastnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        else
            lastnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

        if(tab[3] == 1)
            emailTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        else
            emailTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

        if(tab[4] == 1 && tab[5] == 1) {
            passwordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            confirmPasswordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        }else {
            passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        }



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
        textFlow.setVisible(false);
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
       return hexString.toString();
    }

    public void checkIfEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            logInButtonOMC();
    }
}

