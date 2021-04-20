package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class MainViewController {

    private double x,y;

    @FXML
    private AnchorPane actionPanel = new AnchorPane();

    public Pane goalsPanel;

    public User user;


    public void prepareMainGui(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            goalsPanel = fxmlLoader.load((getClass().getResource("goalsPanel.fxml").openStream()));
            actionPanel.getChildren().add(goalsPanel);

            GoalsPanelController goalsPanelController = fxmlLoader.getController();

            goalsPanelController.setMainViewController(MainViewController.this);
            System.out.println(user.toString());
            goalsPanelController.setUser(user);
            goalsPanelController.prepareListViews();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public AnchorPane getActionPanel() {
        return actionPanel;
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

    public void setUser(User user) {
        this.user = user;
    }
}
