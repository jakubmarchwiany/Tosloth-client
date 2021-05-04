package controllers.splashview;

import com.google.gson.Gson;
import controllers.Client;
import controllers.mainview.MainViewController;
import javafx.fxml.FXMLLoader;

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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
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
import java.util.ArrayList;
import java.util.Arrays;

public class SplashViewController  {

    //Variables
    private Stage primaryStage;
    private Client client = new Client();
    
    //Components JavaFx
    public AnchorPane logInAndSignInLayer;
    public AnchorPane changeViewLayer;
    private double x,y;

    public final ValidationChecker validationChecker = new ValidationChecker();
    public final AnimationController animationController = new AnimationController();

    //JavaFx components Sign up

    public Label signUpLabel;
    public Button signUpBtn;
    public TextField nicknameSuTF;
    public TextField firstnameSuTF;
    public TextField lastnameSuTF;
    public TextField emailSuTF;
    public PasswordField passwordSuPF;
    public PasswordField confirmPasswordSuPF;
    public FontIcon signUpIcon;
    public Label userExistInDataBaseLabel;
    public TextFlow infoValidationTF = new TextFlow();
    
    //JavaFx components to Sign in

    public Label signInLabel;
    public TextField nicknameSiTF;
    public PasswordField passwordSiPF;
    public Label wrongLogInDataLabel;
    public Button signInBtn;
    public Label infoSiLabel;
    public FontIcon signInIcon;


    public void prepareSplashView(){
        validationChecker.setNodes(SplashViewController.this);
        animationController.setNodes(SplashViewController.this);


        infoValidationTF.setStyle("-fx-background-color: WHITE");
        infoValidationTF.setMinSize(200,100);
        infoValidationTF.setVisible(false);
        infoValidationTF.setFocusTraversable(false);
        logInAndSignInLayer.getChildren().add(infoValidationTF);
        validationChecker.setTextFlow(infoValidationTF);
        animationController.disableNodesLogIn();
    }

    public void userRegister() {

        User tempUser = new User(nicknameSuTF.getText(),
                    passwordSuPF.getText(),
                    firstnameSuTF.getText(),
                    lastnameSuTF.getText(),
                    emailSuTF.getText());

        int responseStatusCode = client.registerUserInDataBase(tempUser);

        if (responseStatusCode == 200)
            animationController.showInfoLabel(infoSiLabel,10000);
        else if(responseStatusCode == 500)
            animationController.showInfoLabel(userExistInDataBaseLabel,5000);

    }

    public void userLogin() {

        User tempUser = new User(nicknameSiTF.getText(),(passwordSiPF.getText()));

        User logUser = client.loginUser(tempUser);

            if (logUser != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent mainView = fxmlLoader.load((getClass().getResource("mainView.fxml").openStream()));
                    Scene scene = new Scene(mainView);
                    primaryStage.setScene(scene);

                    MainViewController mainViewController = fxmlLoader.getController();
                    mainViewController.setUser(logUser);
                    mainViewController.prepareMainGui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
                animationController.showInfoLabel(wrongLogInDataLabel,10000);


    }

    public void textFieldClicked() {
        validationChecker.textFieldClicked();
    }

    public void signUpOMC(MouseEvent mouseEvent) {
        animationController.signUpOMC();
    }

    public void logInOMC(MouseEvent mouseEvent) {
        animationController.changeViewToSignIn();
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
        logInAndSignInLayer.requestFocus();
        validationChecker.textFlow.setVisible(false);
    }

    public void checkIfEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            userLogin();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}