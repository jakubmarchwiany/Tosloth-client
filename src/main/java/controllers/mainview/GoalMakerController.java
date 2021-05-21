package controllers.mainview;

import controllers.Client;
import javafx.scene.control.*;
import model.Goal;
import model.Interval;
import model.User;


public class GoalMakerController {

    public User loginUser;
    public Goal currentGoal;
    public Client client;

    public boolean isGoal;

    private MainViewController mainViewC;
    private GoalsPanelController goalsPanelC;
    private GoalManagerController goalManagerC;

    public Label goalCreatorLabel;
    public Button makeBtn;
    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public DatePicker dataPicker;
    public ToggleGroup periodicityGroup;

    public RadioButton noneRadioButton;
    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;

    public void prepareGoalMaker(){
        if (isGoal)
            goalCreatorLabel.setText("Goal Creator");
        else
            goalCreatorLabel.setText("Sub-Goal Creator");
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

        if (isGoal){
            loginUser.getGoalsArrayList().add(tempGoal);
            client.addGoal(tempGoal);
            goalsPanelC.updateListsView();
        }else{
            currentGoal.getSubGoalsArrayList().add(tempGoal);
            client.updateGoal(currentGoal);
            goalManagerC.updateSubGoalsLV();
        }
        back();
    }

    public void back() {
        if(isGoal){
            mainViewC.getActivePanel().getChildren().clear();
            mainViewC.getActivePanel().getChildren().add(mainViewC.goalsPanel);
        }else{
            mainViewC.getActivePanel().getChildren().clear();
            mainViewC.getActivePanel().getChildren().add(goalManagerC.goalManager);
        }
    }

    public void checkValidation(){
        if(nameTextField.getText().length() >= 3 && dataPicker.getValue() != null) {
            makeBtn.setStyle("-fx-background-color: #00ff00");
            makeBtn.setDisable(false);
        }else {
            makeBtn.setStyle("-fx-background-color: #ff0000");
            makeBtn.setDisable(true);
        }
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

    public void setGoal(boolean goal) {
        isGoal = goal;
    }

    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    public void setGoalManagerC(GoalManagerController goalManagerC) {
        this.goalManagerC = goalManagerC;
    }
}
