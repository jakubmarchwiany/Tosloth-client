package controllers;

import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Goal;
import model.Interval;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoalManagerController {

    public MainViewController mainViewController;
    public GoalsPanelController goalsPanelController;

    public User user;
    public Goal goal;

    Parent goalManager;

    public TextField nameTF;
    public TextArea descriptionTA;
    public TextField creationTimeTF;
    public TextField endTimeTF;
    public DatePicker deadLineDP;
    public ListView<Goal> subGoalsListView;

    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;


    public AnchorPane goalMakerPanel;

    public void prepareGoalManagerGui(){
        nameTF.setText(goal.getName());
        descriptionTA.setText(goal.getDescription());
        creationTimeTF.setText(goal.getCreationTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate goalDate = LocalDate.parse(goal.getDeadlineTime(), formatter);

        deadLineDP.setValue(goalDate);


        for (Goal g: goal.getSubGoalsArrayList())
            subGoalsListView.getItems().add(g);


    }

    public void addSubGoal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            goalMakerPanel = fxmlLoader.load((getClass().getResource("subGoalMaker.fxml").openStream()));
            mainViewController.getActionPanel().getChildren().add(goalMakerPanel);

            SubGoalMakerController subGoalMakerController = fxmlLoader.getController();

            subGoalMakerController.setGoalManagerController(GoalManagerController.this);
            subGoalMakerController.setMainViewController(mainViewController);
            subGoalMakerController.setGoal(goal);
            subGoalMakerController.setUser(user);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateGoal() {
        Interval interval = Interval.NONE;

        if (dayRadioButton.isSelected())
            interval = Interval.EVERYDAY;
        else if (weekRadioButton.isSelected())
            interval = Interval.EVERYWEEK;
        else if (twoWeeksRadioButton.isSelected())
            interval = Interval.EVERYTWOWEEKS;
        else if (monthRadioButton.isSelected())
            interval = Interval.EVERYMONTH;

        goal.setName(nameTF.getText());
        goal.setDescription(descriptionTA.getText());
        goal.setInterval(interval);
        goal.setDeadlineTime(deadLineDP.getValue());


        goalsPanelController.prepareListViews();


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost("http://localhost:8080/goals/update");
        StringEntity newGoal = new StringEntity(new Gson().toJson(goal), "UTF-8");


        postRequest.addHeader("content-type", "application/json; charset=UTF-8");
        postRequest.setEntity(newGoal);

        try {
            HttpResponse response = httpClient.execute(postRequest);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void backOMC() {
        mainViewController.getActionPanel().getChildren().clear();
        mainViewController.getActionPanel().getChildren().add(mainViewController.goalsPanel);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setGoalsPanelController(GoalsPanelController goalsPanelController) {
        this.goalsPanelController = goalsPanelController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGoalManager(Parent goalManager) {
        this.goalManager = goalManager;
    }
}
