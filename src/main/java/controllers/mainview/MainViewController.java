package controllers.mainview;

import controllers.Client;
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
    private AnchorPane activePanel = new AnchorPane();

    public Pane goalsPanel;

    public User loginUser;

    public Client client;


    public void prepareMainGui(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            goalsPanel = fxmlLoader.load((getClass().getResource("goalsPanel.fxml").openStream()));
            activePanel.getChildren().add(goalsPanel);

            GoalsPanelController goalsPanelC = fxmlLoader.getController();
            goalsPanelC.prepareGoalsPanel();
            goalsPanelC.setMainViewC(MainViewController.this);
            goalsPanelC.setLoginUser(loginUser);
            goalsPanelC.setClient(client);
            goalsPanelC.updateListsView();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getLoginUser() {
        return loginUser;
    }

    public AnchorPane getActivePanel() {
        return activePanel;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public void setClient(Client client) {
        this.client = client;
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
}
