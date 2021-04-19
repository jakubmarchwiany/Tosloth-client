package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Goal;
import model.User;

import java.io.IOException;


public class GoalsPanelController {

    public ListView<Goal> goalsToDoTodayListView;
    public ListView<Goal> goalsOfTheWeekListView;
    public ListView<Goal> goalsOfTheMonthListView;
    public ListView<Goal> lastCompletedGoalsListView;
    private MainViewController mainViewController;

    public AnchorPane goalMakerPanel;

    public User user;

    public void addGoalOMC() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            goalMakerPanel = fxmlLoader.load((getClass().getResource("goalMaker.fxml").openStream()));
            mainViewController.getActionPanel().getChildren().add(goalMakerPanel);

            GoalMakerController goalMakerController = fxmlLoader.getController();

            goalMakerController.setGoalsPanelController(GoalsPanelController.this);
            goalMakerController.setMainViewController(mainViewController);
            goalMakerController.setUser(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
