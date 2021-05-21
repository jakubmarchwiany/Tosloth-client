package controllers.mainview;

import com.google.gson.Gson;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.Goal;
import model.Interval;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SubGoalManagerController {

    public MainViewController mainViewController;
    public GoalManagerController goalManagerController;

    Parent goalManager;

    public Goal goal;
    public Goal subGoal;


    public TextField nameTF;
    public TextArea descriptionTA;
    public TextField creationTimeTF;
    public TextField endTimeTF;
    public DatePicker deadLineDP;

    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;


    public void prepareSubGoalManagerGui(){
        nameTF.setText(subGoal.getName());
        descriptionTA.setText(subGoal.getDescription());
        creationTimeTF.setText(subGoal.getCreationTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate goalDate = LocalDate.parse(subGoal.getDeadlineTime(), formatter);

        deadLineDP.setValue(goalDate);
    }

    public void subGoalDone() {
        subGoal.setDone(true);
        goalManagerController.prepareSubGoalsListView();

    }

    public void updateSubGoal() {
        Interval interval = Interval.NONE;

        if (dayRadioButton.isSelected())
            interval = Interval.EVERYDAY;
        else if (weekRadioButton.isSelected())
            interval = Interval.EVERYWEEK;
        else if (twoWeeksRadioButton.isSelected())
            interval = Interval.EVERYTWOWEEKS;
        else if (monthRadioButton.isSelected())
            interval = Interval.EVERYMONTH;

        subGoal.setName(nameTF.getText());
        subGoal.setDescription(descriptionTA.getText());
        subGoal.setInterval(interval);
        subGoal.setDeadlineTime(deadLineDP.getValue());



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
        mainViewController.getActivePanel().getChildren().clear();
        mainViewController.getActivePanel().getChildren().add(goalManager);
    }

    public void setSubGoal(Goal subGoal) {
        this.subGoal = subGoal;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setGoalManager(Parent goalManager) {
        this.goalManager = goalManager;
    }

    public void setGoalManagerController(GoalManagerController goalManagerController) {
        this.goalManagerController = goalManagerController;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void removeGoal() {

        goal.getSubGoalsArrayList().remove(subGoal);
        goalManagerController.prepareSubGoalsListView();

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

        prepareSubGoalManagerGui();
        backOMC();
    }


}
