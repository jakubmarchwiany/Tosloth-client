package controllers;

import com.google.gson.Gson;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class GoalMakerController {

    public User user;

    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public DatePicker dataPicker;
    public ToggleGroup periodicityGroup;

    private MainViewController mainViewController;
    private GoalsPanelController goalsPanelController;

    public RadioButton noneRadioButton;
    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;

    public void backOMC(MouseEvent mouseEvent) {
        mainViewController.getActionPanel().getChildren().clear();
        mainViewController.getActionPanel().getChildren().add(mainViewController.goalsPanel);
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void makeGoal() {
        Interval interval = Interval.NONE;

        if (dayRadioButton.isSelected())
            interval = Interval.EVERYDAY;
        else if (weekRadioButton.isSelected())
            interval = Interval.EVERYWEEK;
        else if (twoWeeksRadioButton.isSelected())
            interval = Interval.EVERYTWOWEEKS;
        else if (monthRadioButton.isSelected())
            interval = Interval.EVERYMONTH;

        Goal tempGoal = new Goal(user.getNickname(), nameTextField.getText(),descriptionTextArea.getText(), interval, false, dataPicker.getValue());

        user.getGoalsArrayList().add(tempGoal);

        addGoalToListview(tempGoal);


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost("http://localhost:8080/goals");

        StringEntity newGoal = new StringEntity(new Gson().toJson(tempGoal), "UTF-8");


        postRequest.addHeader("content-type", "application/json; charset=UTF-8");
        postRequest.setEntity(newGoal);


        try {
            HttpResponse response = httpClient.execute(postRequest);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void addGoalToListview(Goal goal){

        String goalDateString = goal.getDeadlineTime();
        System.out.println(goalDateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate goalDate = LocalDate.parse(goalDateString, formatter);



        LocalDate todayDate = LocalDate.now();


        long number = DAYS.between(todayDate, goalDate);

        if (number == 0)
            goalsPanelController.goalsToDoTodayListView.getItems().add(goal);
        else if (number >= 1 && number <=7)
            goalsPanelController.goalsOfTheWeekListView.getItems().add(goal);
        else if (number >= 8 && number <=31)
            goalsPanelController.goalsOfTheMonthListView.getItems().add(goal);




    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGoalsPanelController(GoalsPanelController goalsPanelController) {
        this.goalsPanelController = goalsPanelController;
    }
}
