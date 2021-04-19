package controllers;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Goal;
import model.Interval;
import model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

        Goal tempGoal = new Goal(nameTextField.getText(),descriptionTextArea.getText(), interval, false, dataPicker.getValue());

        user.getGoalsArrayList().add(tempGoal);

        addGoalToListview(tempGoal);

        System.out.println(user.getGoalsArrayList().toString());
    }


    public void addGoalToListview(Goal goal){

        LocalDateTime goalDate = goal.getDeadlineTime();

        LocalDateTime todayDate = LocalDateTime.now();

        long number = ChronoUnit.DAYS.between(todayDate,goalDate);


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
