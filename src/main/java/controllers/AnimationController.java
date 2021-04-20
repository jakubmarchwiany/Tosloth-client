package controllers;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class AnimationController {

    private SplashViewController nodes;


    public void signUpOMC() {
        nodes.textFieldClicked();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(nodes.layer2);
                slide.setToX(-880);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(nodes.layer1);
                slide2.setToX(-880);
                slide2.play();


                Thread.sleep(1000);


                disableNodesSignUp();


                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(nodes.layer1);
                slide3.setToX(0);
                slide3.play();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void disableNodesSignUp(){

        nodes.signInButton.setVisible(true);
        nodes.signInLabel.setVisible(false);
        nodes.nicknameSiTF.setVisible(false);
        nodes.passwordSiPF.setVisible(false);
        nodes.signInIcon.setVisible(false);
        nodes.wrongLoPLabel.setVisible(false);

        nodes.signUpButton.setVisible(false);
        nodes.nicknameTF.setVisible(true);
        nodes.firstnameTF.setVisible(true);
        nodes.emailTF.setVisible(true);
        nodes.lastnameTF.setVisible(true);
        nodes.passwordPF.setVisible(true);
        nodes.confirmPasswordPF.setVisible(true);
        nodes.signUpIcon.setVisible(true);
        nodes.SignUpLabel.setVisible(true);

    }

    public void logInOMC() {
        nodes.validationChecker.resetTextFields();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(nodes.layer2);

                slide.setToX(0);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(nodes.layer1);

                slide2.setToX(880);
                slide2.play();

                Thread.sleep(1000);

                disableNodesLogIn();

                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(nodes.layer1);

                slide3.setToX(0);
                slide3.play();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void disableNodesLogIn(){
        nodes.signInButton.setVisible(false);
        nodes.nicknameTF.setVisible(false);
        nodes.firstnameTF.setVisible(false);
        nodes.emailTF.setVisible(false);
        nodes.lastnameTF.setVisible(false);
        nodes.passwordPF.setVisible(false);
        nodes.confirmPasswordPF.setVisible(false);
        nodes.signUpIcon.setVisible(false);
        nodes.SignUpLabel.setVisible(false);
        nodes.userExistInDataBaseLabel.setVisible(false);

        nodes.signInLabel.setVisible(true);
        nodes.nicknameSiTF.setVisible(true);
        nodes.passwordSiPF.setVisible(true);
        nodes.signInIcon.setVisible(true);
        nodes.signUpButton.setVisible(true);
    }



    public void setNodes(SplashViewController nodes) {
        this.nodes = nodes;
    }
}
