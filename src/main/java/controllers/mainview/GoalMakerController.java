package controllers.mainview;

import controllers.Client;
import javafx.scene.control.*;
import model.Goal;
import model.Interval;
import model.User;


public class GoalMakerController {

    public User loginUser;

    public Client client;

    private MainViewController mainViewC;
    private GoalsPanelController goalsPanelC;


    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public DatePicker dataPicker;
    public ToggleGroup periodicityGroup;

    public RadioButton noneRadioButton;
    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;

    public void back() {
        mainViewC.getActivePanel().getChildren().clear();
        mainViewC.getActivePanel().getChildren().add(mainViewC.goalsPanel);
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

        Goal tempGoal = new Goal(loginUser.getNickname(), nameTextField.getText(),descriptionTextArea.getText(), interval, false, dataPicker.getValue());

        loginUser.getGoalsArrayList().add(tempGoal);

        client.addGoal(tempGoal);
        goalsPanelC.updateListsView();
        back();
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

    public void setGoalsPanelC(GoalsPanelController goalsPanelC) {
        this.goalsPanelC = goalsPanelC;
    }
}
