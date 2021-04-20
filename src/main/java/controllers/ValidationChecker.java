package controllers;


import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class ValidationChecker {

    private static final int minLength = 3;
    private static final int maxLength = 20;

    public TextFlow textFlow;


    public void setTextFlow(TextFlow textFlow) {
        this.textFlow = textFlow;
    }

    private SplashViewController nodes;

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

        if(text.length() >= minLength && text.length() <= maxLength) {
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

        if (nodes.nicknameTF.isFocused()) {
            Bounds nicknameTFBounds = nodes.nicknameTF.localToScene(nodes.nicknameTF.getBoundsInLocal());
            textFlow.setLayoutX(nicknameTFBounds.getCenterX());
            textFlow.setLayoutY(nicknameTFBounds.getMaxY());

            checkRegisterValidation(nodes.nicknameTF.getText(),0);

            if(tab[0] == 1)
                nodes.nicknameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.nicknameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");


            textFlow.setVisible(true);
        } else if (nodes.firstnameTF.isFocused()){
            Bounds firstnameBounds = nodes.firstnameTF.localToScene(nodes.firstnameTF.getBoundsInLocal());
            textFlow.setLayoutX(firstnameBounds.getCenterX());
            textFlow.setLayoutY(firstnameBounds.getMaxY());

            checkRegisterValidation(nodes.firstnameTF.getText(),1);

            if(tab[1] == 1)
                nodes.firstnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.firstnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.lastnameTF.isFocused()) {
            Bounds lastnameBounds = nodes.lastnameTF.localToScene(nodes.lastnameTF.getBoundsInLocal());
            textFlow.setLayoutX(lastnameBounds.getCenterX());
            textFlow.setLayoutY(lastnameBounds.getMaxY());

            checkRegisterValidation(nodes.lastnameTF.getText(),2);

            if(tab[2] == 1)
                nodes.lastnameTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.lastnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.emailTF.isFocused()){
            Bounds emailBounds = nodes.emailTF.localToScene(nodes.emailTF.getBoundsInLocal());
            textFlow.setLayoutX(emailBounds.getCenterX());
            textFlow.setLayoutY(emailBounds.getMaxY());

            checkRegisterEmailValidation(nodes.emailTF.getText(),3);

            if(tab[3] == 1)
                nodes.emailTF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            else
                nodes.emailTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");

            textFlow.setVisible(true);
        } else if (nodes.passwordPF.isFocused()) {
            Bounds passwordBounds = nodes.passwordPF.localToScene(nodes.passwordPF.getBoundsInLocal());
            textFlow.setLayoutX(passwordBounds.getCenterX());
            textFlow.setLayoutY(passwordBounds.getMaxY());

            checkPasswordsValidation(nodes.passwordPF.getText(),nodes.confirmPasswordPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                nodes.passwordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                nodes.confirmPasswordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                nodes.passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                nodes.confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
            }
            textFlow.setVisible(true);
        } else if (nodes.confirmPasswordPF.isFocused()){
            Bounds confirmPasswordBounds = nodes.confirmPasswordPF.localToScene(nodes.confirmPasswordPF.getBoundsInLocal());
            textFlow.setLayoutX(confirmPasswordBounds.getCenterX());
            textFlow.setLayoutY(confirmPasswordBounds.getMaxY());

            checkPasswordsValidation(nodes.confirmPasswordPF.getText(),nodes.passwordPF.getText(),4);

            if(tab[4] == 1 && tab[5] == 1) {
                nodes.passwordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
                nodes.confirmPasswordPF.setStyle("-fx-border-color: GREEN; -fx-border-width: 3px");
            }else {
                nodes.passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
                nodes.confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
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
        nodes.nicknameTF.clear();
        nodes.firstnameTF.clear();
        nodes.emailTF.clear();
        nodes.lastnameTF.clear();
        nodes.passwordPF.clear();
        nodes.confirmPasswordPF.clear();

        tab[0] = 0;
        tab[1] = 0;
        tab[2] = 0;
        tab[3] = 0;
        tab[4] = 0;
        tab[5] = 0;

        nodes.nicknameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.firstnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.lastnameTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.emailTF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.passwordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
        nodes.confirmPasswordPF.setStyle("-fx-border-color: RED; -fx-border-width: 3px");
    }

    public void setNodes(SplashViewController nodes) {
        this.nodes = nodes;
    }


}
