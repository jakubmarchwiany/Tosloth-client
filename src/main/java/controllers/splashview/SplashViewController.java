package controllers.splashview;

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
import model.User;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SplashViewController  {

    //Variables
    private Stage primaryStage;
    private final Client client = new Client();
    
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
        validationChecker.prepareValidator();
        animationController.disableNodesSignIn();
    }

    public void userRegister() {

        User tempUser = new User(nicknameSuTF.getText(),
                    passwordSuPF.getText(),
                    firstnameSuTF.getText(),
                    lastnameSuTF.getText(),
                    emailSuTF.getText());

        int responseStatusCode = client.registerUserInDataBase(tempUser);

        if (responseStatusCode == 200)
            animationController.showInfoLabelWithMove(infoSiLabel,10000);
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

    public void checkIfEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            userLogin();
    }

    public void textFieldCheck() {
        validationChecker.textFieldCheckValidation();
    }

    public void changeViewToSignUp() {
        animationController.changeView(-880,-880);
    }

    public void changeViewToSignIn() {
        animationController.changeView(0,880);
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
        validationChecker.infoValidationTF.setVisible(false);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}