package controllers.mainview;

import com.google.gson.Gson;
import controllers.Client;
import controllers.splashview.AnimationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Goal;
import model.Interval;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class GoalManagerController {

    public MainViewController mainViewC;
    public GoalsPanelController goalsPanelC;
    public GoalManagerController goalManagerC;

    public User loginUser;
    public Goal currentGoal;
    public Goal mainGoal;
    public Client client;

    public boolean isGoal;
    public Label mainLabel;



    Parent goalManager;

    public TextField nameTF;
    public TextArea descriptionTA;
    public TextField creationTimeTF;
    public TextField endTimeTF;
    public DatePicker deadLineDP;
    public ListView<Goal> subGoalsListView;

    public RadioButton noneRadioButton;
    public RadioButton dayRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton twoWeeksRadioButton;
    public RadioButton monthRadioButton;
    public Label allSubGoalsDoneLabel;

    public Label subGoalsLabel;
    public Button addSubGoalBtn;

    public void prepareGoalManagerGui(){
        allSubGoalsDoneLabel.setVisible(false);
        nameTF.setText(currentGoal.getName());
        descriptionTA.setText(currentGoal.getDescription());
        creationTimeTF.setText(currentGoal.getCreationTime());
        endTimeTF.setText(currentGoal.getEndTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate goalDate = LocalDate.parse(currentGoal.getDeadlineTime(), formatter);
        deadLineDP.setValue(goalDate);

        switch (currentGoal.getInterval()) {
            case NONE -> noneRadioButton.setSelected(true);
            case EVERYDAY -> dayRadioButton.setSelected(true);
            case EVERYWEEK -> weekRadioButton.setSelected(true);
            case EVERYTWOWEEKS -> twoWeeksRadioButton.setSelected(true);
            case EVERYMONTH -> monthRadioButton.setSelected(true);
        }

        if(isGoal) {
            mainLabel.setText("GOAL MANAGER");
            updateSubGoalsLV();
            subGoalsListView.setCellFactory(param -> new ListCell<Goal>() {
                @Override
                protected void updateItem(Goal item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle(null);
                    } else {
                        setText(item.getName());
                        if (item.getDone()) {
                            setText(item.getName() + "      DONE");
                            setStyle("-fx-background-color: #00ff00");
                        } else {
                            setStyle("-fx-background-color: " + goalsPanelC.colorPicker(item) + ";");
                        }

                    }
                }
            });
        }else{
            mainLabel.setText("SUB-GOAL MANAGER");
//            addSubGoalBtn.setDisable(true);
            subGoalsListView.setDisable(true);
            subGoalsListView.setVisible(false);
            subGoalsLabel.setVisible(false);
            addSubGoalBtn.setVisible(false);
        }

    }

    public void updateSubGoalsLV(){
        subGoalsListView.getItems().clear();
        Collections.sort(currentGoal.getSubGoalsArrayList(), new Goal.SortByDate());


        for (Goal g: currentGoal.getSubGoalsArrayList())
            subGoalsListView.getItems().add(g);
    }

    public void addSubGoal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane goalMakerPanel = fxmlLoader.load((getClass().getResource("goalMaker.fxml").openStream()));
            mainViewC.getActivePanel().getChildren().add(goalMakerPanel);

            GoalMakerController goalMakerC = fxmlLoader.getController();
            goalMakerC.setGoal(false);
            goalMakerC.setGoalManagerC(GoalManagerController.this);
            goalMakerC.setMainViewC(mainViewC);
            goalMakerC.setCurrentGoal(currentGoal);
            goalMakerC.setLoginUser(loginUser);
            goalMakerC.setClient(client);
            goalMakerC.prepareGoalMaker();
            goalMakerC.checkValidation();

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

        currentGoal.setName(nameTF.getText());
        currentGoal.setDescription(descriptionTA.getText());
        currentGoal.setInterval(interval);
        currentGoal.setDeadlineTime(deadLineDP.getValue());

        if(isGoal){
            goalsPanelC.updateListsView();
            client.updateGoal(currentGoal);
        }else{
            goalManagerC.updateSubGoalsLV();
            client.updateGoal(mainGoal);
        }

        back();

    }

    public void listViewOMC()  {

        subGoalsListView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = subGoalsListView.getSelectionModel().getSelectedItem();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent goalManager2 = fxmlLoader.load((getClass().getResource("goalManager.fxml").openStream()));
                    mainViewC.getActivePanel().getChildren().add(goalManager2);

                    GoalManagerController goalManagerC = fxmlLoader.getController();

                    goalManagerC.setGoalManagerC(GoalManagerController.this);
                    goalManagerC.setIsGoal(false);
                    goalManagerC.setClient(client);
                    goalManagerC.setMainGoal(currentGoal);
                    goalManagerC.setMainViewC(mainViewC);
                    goalManagerC.setCurrentGoal(selectedGaol);
                    goalManagerC.setLoginUser(loginUser);
                    goalManagerC.setGoalManager(goalManager);
                    goalManagerC.prepareGoalManagerGui();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            gaolContextMenu.getItems().add(go_into_goal);

            if(subGoalsListView.getSelectionModel().getSelectedItem()==null)
                subGoalsListView.setContextMenu(null);
            else
                subGoalsListView.setContextMenu(gaolContextMenu);
        });
    }

    public void back() {
        mainViewC.getActivePanel().getChildren().clear();
        if(isGoal)
            mainViewC.getActivePanel().getChildren().add(mainViewC.goalsPanel);
        else
            mainViewC.getActivePanel().getChildren().add(goalManager);
    }

    public void goalDone() {
        if(isGoal){

            boolean done = true;
            for (Goal g: currentGoal.getSubGoalsArrayList()){
                if (!g.getDone()){
                    done = false;
                    break;
                }
            }

            if (done){
                currentGoal.setDone(true);
                currentGoal.setEndTime();
                goalsPanelC.updateListsView();

                LocalDate deadlineTime = currentGoal.getDeadlineDate();

                System.out.println(deadlineTime.toString());

                switch (currentGoal.getInterval()) {
                    case NONE -> deadlineTime = deadlineTime.plusDays(0);
                    case EVERYDAY -> deadlineTime = deadlineTime.plusDays(1);
                    case EVERYWEEK -> deadlineTime = deadlineTime.plusDays(7);
                    case EVERYTWOWEEKS -> deadlineTime = deadlineTime.plusDays(14);
                    case EVERYMONTH -> deadlineTime = deadlineTime.plusDays(30);
                }

                System.out.println(deadlineTime.toString());

                Goal newGoal = new Goal(currentGoal.getOwner(),currentGoal.getName(),currentGoal.getDescription(),currentGoal.getInterval(),false,deadlineTime);

                for (Goal g: newGoal.getSubGoalsArrayList()) {
                    g.setDone(false);

                }

                client.addGoal(newGoal);
                loginUser.getGoalsArrayList().add(newGoal);

                client.updateGoal(currentGoal);
                goalsPanelC.updateListsView();
                back();
            }else{
                AnimationController animationController = new AnimationController();

                animationController.showInfoLabel(allSubGoalsDoneLabel,5000);
            }

        }else{
            currentGoal.setDone(true);
            currentGoal.setEndTime();
            goalManagerC.updateSubGoalsLV();
            client.updateGoal(mainGoal);
            back();
        }
    }

    public void removeGoal() {
        if(isGoal) {
            client.removeGoal(currentGoal);
            loginUser.getGoalsArrayList().remove(currentGoal);
            goalsPanelC.updateListsView();

        }else{
            mainGoal.getSubGoalsArrayList().remove(currentGoal);
            goalManagerC.updateSubGoalsLV();
            client.updateGoal(mainGoal);
        }
        back();
    }



    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public void setGoalManager(Parent goalManager) {
        this.goalManager = goalManager;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMainViewC(MainViewController mainViewC) {
        this.mainViewC = mainViewC;
    }

    public void setGoalsPanelC(GoalsPanelController goalsPanelC) {
        this.goalsPanelC = goalsPanelC;
    }

    public void setIsGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    public void setMainGoal(Goal mainGoal) {
        this.mainGoal = mainGoal;
    }

    public void setGoalManagerC(GoalManagerController goalManagerC) {
        this.goalManagerC = goalManagerC;
    }
}
