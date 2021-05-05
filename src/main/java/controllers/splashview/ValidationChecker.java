package controllers.splashview;


import controllers.splashview.SplashViewController;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class ValidationChecker {

    private SplashViewController nodes;

    private static final int minLength = 3;
    private static final int maxLength = 20;

    public TextFlow textFlow;


    public void setTextFlow(TextFlow textFlow) {
        this.textFlow = textFlow;
    }


    public void prepareValidator(){
        this.textFlow = nodes.infoValidationTF;

        nodes.infoValidationTF.setStyle("-fx-background-color: WHITE");
        nodes.infoValidationTF.setMinSize(200,100);
        nodes.infoValidationTF.setVisible(false);
        nodes.infoValidationTF.setFocusTraversable(false);
        nodes.logInAndSignInLayer.getChildren().add(textFlow);


    }

    public final int[] tab = new int[] {0,0,0,0,0,0};

    public void checkRegisterValidation(String text,int index){
        textFlow.getChildren().clear();

        if ( lengthCheck(text) && characterCheck(text))
            tab[index]=1;
        else
            tab[index]=0;

    }

    public void checkRegisterEmailValidation(String text,int index){
        textFlow.getChildren().clear();

        lengthCheck(text);
        characterEmailCheck(text);
        if (lengthCheck(text) && characterEmailCheck(text))
            tab[index]=1;
        else
            tab[index]=0;
    }

    public void checkPasswordsValidation(String passsword,String confirmPassword,int index){
        textFlow.getChildren().clear();


        if ( lengthCheck(passsword) && characterCheck(passsword) && equalsCheck(passsword,confirmPassword)) {
            tab[index] = 1;
            tab[index+1] = 1;
        }else {
            tab[index] = 0;
            tab[index+1] = 0;
        }
    }

    public boolean characterCheck(String text){
        if(text.length() >= 3) {
            Text characterText = null;
            for (int i = 0; i < text.length(); i++) {
                if ((text.charAt(i) >= '0' && text.charAt(i) <= '9') || (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') || (text.charAt(i) >= 'a' && text.charAt(i) <= 'z')) {
                    characterText = new Text("Character correctly\n");
                    characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    characterText.setFill(Color.GREEN);


                } else {
                    characterText = new Text("Incorrect character\n");
                    characterText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    characterText.setFill(Color.RED);
                    textFlow.getChildren().add(characterText);
                    return false;
                }
            }
            textFlow.getChildren().add(characterText);
            return true;
        }
        return false;
    }

    public boolean characterEmailCheck(String text){
        if(text.length() >= 3) {
            Text characterText = null;
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
                    textFlow.getChildren().add(characterText);
                    return false;
                }
            }
            textFlow.getChildren().add(characterText);
            return true;

        }
        return false;
    }

    public boolean equalsCheck(String password, String confirmPassword){
        Text equalsText;
        if(password.equals(confirmPassword)){
            equalsText = new Text("Passwords equals\n");
            equalsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            equalsText.setFill(Color.GREEN);
            textFlow.getChildren().add(equalsText);
            return true;
        }else{
            equalsText = new Text("Passwords different\n");
            equalsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            equalsText.setFill(Color.RED);
            textFlow.getChildren().add(equalsText);
            return false;
        }

    }

    public boolean lengthCheck(String text){
        Text lengthText;

        if(text.length() >= minLength && text.length() <= maxLength+20) {
            lengthText = new Text("Length correctly\n");
            lengthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            lengthText.setFill(Color.GREEN);
            textFlow.getChildren().add(lengthText);
            return true;
        }else {
            lengthText = new Text("Incorrect length\n");
            lengthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            lengthText.setFill(Color.RED);
            textFlow.getChildren().add(lengthText);
            return false;
        }

    }

    public void textFieldClicked() {

        if (nodes.nicknameSuTF.isFocused()) {
            Bounds nicknameTFBounds = nodes.nicknameSuTF.localToScene(nodes.nicknameSuTF.getBoundsInLocal());
            textFlow.setLayoutX(nicknameTFBounds.getCenterX());
            textFlow.setLayoutY(nicknameTFBounds.getMaxY());

            checkRegisterValidation(nodes.nicknameSuTF.getText(),0);

            if(tab[0] == 1)
                nodes.nicknameSuTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.nicknameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");


            textFlow.setVisible(true);
        } else if (nodes.firstnameSuTF.isFocused()){
            Bounds firstnameBounds = nodes.firstnameSuTF.localToScene(nodes.firstnameSuTF.getBoundsInLocal());
            textFlow.setLayoutX(firstnameBounds.getCenterX());
            textFlow.setLayoutY(firstnameBounds.getMaxY());

            checkRegisterValidation(nodes.firstnameSuTF.getText(),1);

            if(tab[1] == 1)
                nodes.firstnameSuTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.firstnameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.lastnameSuTF.isFocused()) {
            Bounds lastnameBounds = nodes.lastnameSuTF.localToScene(nodes.lastnameSuTF.getBoundsInLocal());
            textFlow.setLayoutX(lastnameBounds.getCenterX());
            textFlow.setLayoutY(lastnameBounds.getMaxY());

            checkRegisterValidation(nodes.lastnameSuTF.getText(),2);

            if(tab[2] == 1)
                nodes.lastnameSuTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.lastnameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.emailSuTF.isFocused()){
            Bounds emailBounds = nodes.emailSuTF.localToScene(nodes.emailSuTF.getBoundsInLocal());
            textFlow.setLayoutX(emailBounds.getCenterX());
            textFlow.setLayoutY(emailBounds.getMaxY());

            checkRegisterEmailValidation(nodes.emailSuTF.getText(),3);

            if(tab[3] == 1)
                nodes.emailSuTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.emailSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.passwordSuPF.isFocused()) {
            Bounds passwordBounds = nodes.passwordSuPF.localToScene(nodes.passwordSuPF.getBoundsInLocal());
            textFlow.setLayoutX(passwordBounds.getCenterX());
            textFlow.setLayoutY(passwordBounds.getMaxY());

            checkPasswordsValidation(nodes.passwordSuPF.getText(),nodes.confirmPasswordSuPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                nodes.passwordSuPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                nodes.confirmPasswordSuPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                nodes.passwordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                nodes.confirmPasswordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            }
            textFlow.setVisible(true);
        } else if (nodes.confirmPasswordSuPF.isFocused()){
            Bounds confirmPasswordBounds = nodes.confirmPasswordSuPF.localToScene(nodes.confirmPasswordSuPF.getBoundsInLocal());
            textFlow.setLayoutX(confirmPasswordBounds.getCenterX());
            textFlow.setLayoutY(confirmPasswordBounds.getMaxY());

            checkPasswordsValidation(nodes.confirmPasswordSuPF.getText(),nodes.passwordSuPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                nodes.passwordSuPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                nodes.confirmPasswordSuPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                nodes.passwordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                nodes.confirmPasswordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            }

            textFlow.setVisible(true);
        }else {
            textFlow.setVisible(false);

        }

        int sum=0;
        for (int j : tab) sum += j;


        if(sum == 6) {
            nodes.signUpIcon.setIconColor(Color.GREEN);
            nodes.signUpIcon.setDisable(false);
        }else {
            nodes.signUpIcon.setIconColor(Color.RED);
            nodes.signUpIcon.setDisable(true);
        }
    }

    public void resetTextFields(){
        nodes.nicknameSuTF.clear();
        nodes.firstnameSuTF.clear();
        nodes.emailSuTF.clear();
        nodes.lastnameSuTF.clear();
        nodes.passwordSuPF.clear();
        nodes.confirmPasswordSuPF.clear();

        tab[0] = 0;
        tab[1] = 0;
        tab[2] = 0;
        tab[3] = 0;
        tab[4] = 0;
        tab[5] = 0;

        nodes.nicknameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.firstnameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.lastnameSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.emailSuTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.passwordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.confirmPasswordSuPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
    }

    public void setNodes(SplashViewController nodes) {
        this.nodes = nodes;
    }


}
