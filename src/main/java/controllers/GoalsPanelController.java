package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
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

    public void listViewOMC()  {

        goalsToDoTodayListView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = goalsToDoTodayListView.getSelectionModel().getSelectedItem();
                loadManagerGoal(selectedGaol);
            });

            gaolContextMenu.getItems().add(go_into_goal);

            if(goalsToDoTodayListView.getSelectionModel().getSelectedItem()==null)
                goalsToDoTodayListView.setContextMenu(null);
            else
                goalsToDoTodayListView.setContextMenu(gaolContextMenu);
        });

        goalsOfTheWeekListView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = goalsOfTheWeekListView.getSelectionModel().getSelectedItem();
                loadManagerGoal(selectedGaol);
            });

            gaolContextMenu.getItems().add(go_into_goal);

            if(goalsOfTheWeekListView.getSelectionModel().getSelectedItem()==null)
                goalsOfTheWeekListView.setContextMenu(null);
            else
                goalsOfTheWeekListView.setContextMenu(gaolContextMenu);
        });

        goalsOfTheMonthListView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = goalsOfTheMonthListView.getSelectionModel().getSelectedItem();
                loadManagerGoal(selectedGaol);
            });

            gaolContextMenu.getItems().add(go_into_goal);

            if(goalsOfTheMonthListView.getSelectionModel().getSelectedItem()==null)
                goalsOfTheMonthListView.setContextMenu(null);
            else
                goalsOfTheMonthListView.setContextMenu(gaolContextMenu);
        });

    }

    private void loadManagerGoal(Goal selectedGaol){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent goalManager = fxmlLoader.load((getClass().getResource("goalManager.fxml").openStream()));
            mainViewController.getActionPanel().getChildren().add(goalManager);

            GoalManagerController goalManagerController = fxmlLoader.getController();

            goalManagerController.setGoalsPanelController(GoalsPanelController.this);
            goalManagerController.setMainViewController(mainViewController);
            goalManagerController.setGoal(selectedGaol);
            goalManagerController.prepareGoalManagerGui();
            goalManagerController.setUser(user);
            goalManagerController.setGoalManager(goalManager);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void prepareListViews(){
        goalsToDoTodayListView.getItems().clear();
        goalsOfTheWeekListView.getItems().clear();
        goalsOfTheMonthListView.getItems().clear();

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
