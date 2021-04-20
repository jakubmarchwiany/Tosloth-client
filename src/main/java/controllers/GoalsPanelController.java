package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Goal;
import model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;


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

    public void prepareListViews(){
        LocalDate todayDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String goalDateString;
        for (Goal goal : user.getGoalsArrayList()){
            goalDateString = goal.getDeadlineTime();
            LocalDate goalDate = LocalDate.parse(goalDateString, formatter);

            long number = DAYS.between(todayDate, goalDate);

            if (number == 0)
                goalsToDoTodayListView.getItems().add(goal);
            else if (number >= 1 && number <=7)
                goalsOfTheWeekListView.getItems().add(goal);
            else if (number >= 8 && number <=31)
                goalsOfTheMonthListView.getItems().add(goal);

        }
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
