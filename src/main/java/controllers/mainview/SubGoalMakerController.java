package controllers.mainview;

import com.google.gson.Gson;
import javafx.scene.control.*;
import model.Goal;
import model.Interval;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class SubGoalMakerController {

    public User user;

    public Goal goal;


    public GoalManagerController goalManagerController;
    public MainViewController mainViewController;

    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public DatePicker dataPicker;
    public ToggleGroup periodicityGroup;
    public RadioButton noneRadioButton;
    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;

    public void backOMC() {
        mainViewController.getActionPanel().getChildren().clear();
        mainViewController.getActionPanel().getChildren().add(goalManagerController.goalManager);
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

        goal.getSubGoalsArrayList().add(tempGoal);

        goalManagerController.subGoalsListView.getItems().add(tempGoal);

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setGoalManagerController(GoalManagerController goalManagerController) {
        this.goalManagerController = goalManagerController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
