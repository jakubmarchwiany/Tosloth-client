package controllers.splashview;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationController {

    private final int speedOfAnimation = 1000;

    private SplashViewController nodes;


    public void showInfoLabel(Node node,int duration){
        Thread thread = new Thread(() -> {
            try {

                Thread.sleep(speedOfAnimation);
                node.setVisible(true);
                Thread.sleep(duration);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            node.setVisible(false);
        });
        thread.start();
    }

    public void showInfoLabelWithMove(Node node,int duration){
        Thread thread = new Thread(() -> {
            try {

                changeViewToSignIn();

                Thread.sleep(speedOfAnimation);
                node.setVisible(true);
                Thread.sleep(duration);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            node.setVisible(false);
        });
        thread.start();
    }




    public void signUpOMC() {
        nodes.textFieldClicked();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(nodes.changeViewLayer);
                slide.setToX(-880);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(nodes.logInAndSignInLayer);
                slide2.setToX(-880);
                slide2.play();


                Thread.sleep(1000);


                disableNodesSignUp();


                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(nodes.logInAndSignInLayer);
                slide3.setToX(0);
                slide3.play();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void disableNodesSignUp(){

        nodes.signInBtn.setVisible(true);
        nodes.signInLabel.setVisible(false);
        nodes.nicknameSiTF.setVisible(false);
        nodes.passwordSiPF.setVisible(false);
        nodes.signInIcon.setVisible(false);
        nodes.wrongLogInDataLabel.setVisible(false);

        nodes.signUpBtn.setVisible(false);
        nodes.nicknameSuTF.setVisible(true);
        nodes.firstnameSuTF.setVisible(true);
        nodes.emailSuTF.setVisible(true);
        nodes.lastnameSuTF.setVisible(true);
        nodes.passwordSuPF.setVisible(true);
        nodes.confirmPasswordSuPF.setVisible(true);
        nodes.signUpIcon.setVisible(true);
        nodes.signUpLabel.setVisible(true);

    }

    public void changeViewToSignIn() {
        nodes.validationChecker.resetTextFields();
        Thread thread = new Thread(() -> {
            try {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(nodes.changeViewLayer);

                slide.setToX(0);
                slide.play();

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(1));
                slide2.setNode(nodes.logInAndSignInLayer);

                slide2.setToX(880);
                slide2.play();

                Thread.sleep(1000);

                disableNodesLogIn();

                TranslateTransition slide3 = new TranslateTransition();
                slide3.setDuration(Duration.seconds(1));
                slide3.setNode(nodes.logInAndSignInLayer);

                slide3.setToX(0);
                slide3.play();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

    public void disableNodesLogIn(){
        nodes.signInBtn.setVisible(false);
        nodes.nicknameSuTF.setVisible(false);
        nodes.firstnameSuTF.setVisible(false);
        nodes.emailSuTF.setVisible(false);
        nodes.lastnameSuTF.setVisible(false);
        nodes.passwordSuPF.setVisible(false);
        nodes.confirmPasswordSuPF.setVisible(false);
        nodes.signUpIcon.setVisible(false);
        nodes.signUpLabel.setVisible(false);
        nodes.userExistInDataBaseLabel.setVisible(false);

        nodes.signInLabel.setVisible(true);
        nodes.nicknameSiTF.setVisible(true);
        nodes.passwordSiPF.setVisible(true);
        nodes.signInIcon.setVisible(true);
        nodes.signUpBtn.setVisible(true);
    }



    public void setNodes(SplashViewController nodes) {
        this.nodes = nodes;
    }
}
