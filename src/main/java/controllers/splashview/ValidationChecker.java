package controllers.splashview;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.regex.Pattern;


public class ValidationChecker {

    private SplashViewController nodes;

    private static final int minLength = 3;
    private static final int maxLength = 30;

    public TextFlow infoValidationTF;

    public int[] correctField = new int[] {0,0,0,0,0,0};

    public void prepareValidator(){
        this.infoValidationTF = nodes.infoValidationTF;

        nodes.infoValidationTF.setStyle("-fx-background-color: WHITE");
        nodes.infoValidationTF.setMinSize(200,40);
        nodes.infoValidationTF.setVisible(false);
        nodes.infoValidationTF.setFocusTraversable(false);
        nodes.logInAndSignInLayer.getChildren().add(infoValidationTF);
    }

    public boolean charactersCheck(String text){
        Text characterText = new Text();
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) >= '0' && text.charAt(i) <= '9') || (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') || (text.charAt(i) >= 'a' && text.charAt(i) <= 'z')) {
                characterText = new Text("Character correctly\n");
                characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                characterText.setFill(Color.GREEN);
            } else {
                characterText = new Text("Incorrect character\n");
                characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                characterText.setFill(Color.RED);
                infoValidationTF.getChildren().add(characterText);
                return false;
            }
        }
        infoValidationTF.getChildren().add(characterText);
        return true;
    }

    public boolean lengthCheck(String text){
        Text lengthText;
        if(text.length() >= minLength && text.length() <= maxLength) {
            lengthText = new Text("Length correctly\n");
            lengthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            lengthText.setFill(Color.GREEN);
            infoValidationTF.getChildren().add(lengthText);
            return true;
        }else {
            lengthText = new Text("Incorrect length\n");
            lengthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            lengthText.setFill(Color.RED);
            infoValidationTF.getChildren().add(lengthText);
            return false;
        }
    }

    public void textFieldValidation(TextField tF, int index){
        if ( charactersCheck(tF.getText()) && lengthCheck(tF.getText())) {
            tF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            correctField[index] = 1;
        }else {
            tF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            correctField[index] = 0;
        }
    }

    public void textFieldEmailValidation(TextField tF, int index){
        if (characterEmailCheck(tF.getText()) && lengthCheck(tF.getText())){
            tF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            correctField[index] = 1;
        }else {
            tF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            correctField[index] = 0;
        }
    }

    public void checkPasswordsValidation(TextField password,TextField confirmPassword,int index){

        if ( charactersCheck(password.getText()) &&
                 lengthCheck(password.getText()) &&
                    equalsCheck(password.getText(),confirmPassword.getText())) {
            correctField[index] = 1;
            password.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            correctField[index+1] = 1;
            confirmPassword.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
        }else {
            correctField[index] = 0;
            password.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            correctField[index+1] = 0;
            confirmPassword.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        }
    }

    public boolean characterEmailCheck(String text){
        Text characterText = new Text();
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) >= '0' && text.charAt(i) <= '9') ||
                (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') ||
                (text.charAt(i) >= 'a' && text.charAt(i) <= 'z') ||
                text.charAt(i) == '@' ||
                text.charAt(i) == '.') {

                characterText = new Text("Character correctly\n");
                characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                characterText.setFill(Color.GREEN);

            } else {
                characterText = new Text("Incorrect character\n");
                characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                characterText.setFill(Color.RED);
                infoValidationTF.getChildren().add(characterText);
                return false;
            }
        }
        infoValidationTF.getChildren().add(characterText);
        return true;
    }

    public boolean equalsCheck(String password, String confirmPassword){
        Text equalsText;
        if(password.equals(confirmPassword)){
            equalsText = new Text("Passwords equals\n");
            equalsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            equalsText.setFill(Color.GREEN);
            infoValidationTF.getChildren().add(equalsText);
            return true;
        }else{
            equalsText = new Text("Passwords different\n");
            equalsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            equalsText.setFill(Color.RED);
            infoValidationTF.getChildren().add(equalsText);
            return false;
        }
    }

    public void setBoundsInfoValidationTF(Node node){
        Bounds textFieldBounds = node.localToScene(node.getBoundsInLocal());
        infoValidationTF.setLayoutX(textFieldBounds.getCenterX());
        infoValidationTF.setLayoutY(textFieldBounds.getMaxY());
    }

    public void textFieldCheckValidation() {

        infoValidationTF.getChildren().clear();
        infoValidationTF.setVisible(true);

        if (nodes.nicknameSuTF.isFocused()) {

            setBoundsInfoValidationTF(nodes.nicknameSuTF);
            textFieldValidation(nodes.nicknameSuTF,0);

        } else if (nodes.firstnameSuTF.isFocused()){
            setBoundsInfoValidationTF(nodes.firstnameSuTF);
            textFieldValidation(nodes.firstnameSuTF,1);

        } else if (nodes.lastnameSuTF.isFocused()) {

            setBoundsInfoValidationTF(nodes.lastnameSuTF);
            textFieldValidation(nodes.lastnameSuTF,2);

        } else if (nodes.emailSuTF.isFocused()){

            setBoundsInfoValidationTF(nodes.emailSuTF);
            textFieldEmailValidation(nodes.emailSuTF,3);

        } else if (nodes.passwordSuPF.isFocused() || nodes.confirmPasswordSuPF.isFocused()) {

            setBoundsInfoValidationTF(nodes.passwordSuPF);
            checkPasswordsValidation(nodes.passwordSuPF,nodes.confirmPasswordSuPF,4);

        } else {
            infoValidationTF.setVisible(false);
        }

        boolean correct = true;
        for (int j : correctField){
            if ( j == 0) {
                correct = false;
                break;
            }
        }

        if(correct) {
            nodes.signUpIcon.setIconColor(Color.GREEN);
            nodes.signUpIcon.setDisable(false);
        }else {
            nodes.signUpIcon.setIconColor(Color.RED);
            nodes.signUpIcon.setDisable(true);
        }

    }

    public void resetTextFields(){
        infoValidationTF.setVisible(false);
        nodes.signUpIcon.setIconColor(Color.RED);
        nodes.signUpIcon.setDisable(true);
        nodes.nicknameSuTF.clear();
        nodes.firstnameSuTF.clear();
        nodes.emailSuTF.clear();
        nodes.lastnameSuTF.clear();
        nodes.passwordSuPF.clear();
        nodes.confirmPasswordSuPF.clear();

        correctField = new int[] {0,0,0,0,0,0};

        nodes.nicknameSuTF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
        nodes.firstnameSuTF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
        nodes.lastnameSuTF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
        nodes.emailSuTF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
        nodes.passwordSuPF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
        nodes.confirmPasswordSuPF.setStyle("-fx-border-color: #82642c; -fx-border-width: 2px");
    }

    public void setNodes(SplashViewController nodes) {
        this.nodes = nodes;
    }
}
