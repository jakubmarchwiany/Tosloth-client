package controllers.mainview;

import controllers.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import model.Goal;
import model.User;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.DAYS;


public class GoalsPanelController {

    public User loginUser;

    public Client client;

    private MainViewController mainViewC;

    public ListView<Goal> goalsTodayLV;
    public ListView<Goal> goalsTwoWeeksLV;
    public ListView<Goal> goalsIncomingLV;
    public ListView<Goal> goalsCompletedLV;

    public void prepareGoalsPanel(){
        prepareListView(goalsTodayLV);
        prepareListView(goalsTwoWeeksLV);
        prepareListView(goalsIncomingLV);
        prepareListView(goalsCompletedLV);
    }

    public void addGoal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane goalMakerPanel = fxmlLoader.load((getClass().getResource("goalMaker.fxml").openStream()));
            mainViewC.getActivePanel().getChildren().add(goalMakerPanel);

            GoalMakerController goalMakerC = fxmlLoader.getController();

            goalMakerC.setGoalsPanelC(GoalsPanelController.this);
            goalMakerC.setMainViewC(mainViewC);
            goalMakerC.setLoginUser(loginUser);
            goalMakerC.setClient(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadManagerGoal(Goal selectedGaol){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent goalManager = fxmlLoader.load((getClass().getResource("goalManager.fxml").openStream()));
            mainViewC.getActivePanel().getChildren().add(goalManager);

            GoalManagerController goalManagerC = fxmlLoader.getController();

            goalManagerC.setGoalsPanelController(GoalsPanelController.this);
            goalManagerC.setMainViewController(mainViewC);
            goalManagerC.setGoal(selectedGaol);
            goalManagerC.setUser(loginUser);
            goalManagerC.setGoalManager(goalManager);
            goalManagerC.prepareGoalManagerGui();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateListsView(){
        Collections.sort(loginUser.getGoalsArrayList(), new Goal.SortByDate());

        goalsTodayLV.getItems().clear();
        goalsTwoWeeksLV.getItems().clear();
        goalsIncomingLV.getItems().clear();
        goalsCompletedLV.getItems().clear();

        LocalDate todayDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Goal goal : loginUser.getGoalsArrayList()){
            if (goal.getDone()) {
                goalsCompletedLV.getItems().add(goal);
            }else {
                String goalDateString;
                goalDateString = goal.getDeadlineTime();
                LocalDate goalDate = LocalDate.parse(goalDateString, formatter);

                long number = DAYS.between(todayDate, goalDate);
                if (number == 0)
                    goalsTodayLV.getItems().add(goal);
                else if (number >= 1 && number <= 15)
                    goalsTwoWeeksLV.getItems().add(goal);
                else if (number >= 16)
                    goalsIncomingLV.getItems().add(goal);
            }
        }
    }

    public String colorPicker(Goal goal){

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String goalDateString = goal.getDeadlineTime();;
        LocalDate goalDate = LocalDate.parse(goalDateString, formatter);

        long numberBetween = DAYS.between(todayDate, goalDate);

        Color color;
        int number=(int)numberBetween;
        if (number > 28 || goal.getDone())
            color = new Color(0, 255,0);
        else
            color = new Color(225 - number * 8, 25 + number * 8,0);

        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public void prepareListView(ListView<Goal> listView){

        listView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = listView.getSelectionModel().getSelectedItem();
                loadManagerGoal(selectedGaol);
            });

            gaolContextMenu.getItems().add(go_into_goal);

            if(listView.getSelectionModel().getSelectedItem()==null)
                listView.setContextMenu(null);
            else
                listView.setContextMenu(gaolContextMenu);
        });


        listView.setCellFactory(param -> new ListCell<Goal>() {
            @Override
            protected void updateItem(Goal item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    setText(item.getName());
                    setStyle("-fx-background-color: " + colorPicker(item) + ";");
                }
            }
        });

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMainViewC(MainViewController mainViewC) {
        this.mainViewC = mainViewC;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}
