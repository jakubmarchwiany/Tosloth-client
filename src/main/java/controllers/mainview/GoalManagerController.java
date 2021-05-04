package controllers.mainview;

import com.google.gson.Gson;
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
import java.time.format.DateTimeFormatter;
import java.util.Collections;

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

    public Label allSubGoalsDoneLabel;
    public AnchorPane goalMakerPanel;

    public void prepareGoalManagerGui(){
        allSubGoalsDoneLabel.setVisible(false);
        nameTF.setText(goal.getName());
        descriptionTA.setText(goal.getDescription());
        creationTimeTF.setText(goal.getCreationTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate goalDate = LocalDate.parse(goal.getDeadlineTime(), formatter);
        deadLineDP.setValue(goalDate);
        prepareSubGoalsListView();

    }

    public void prepareSubGoalsListView(){
        subGoalsListView.getItems().clear();
        Collections.sort(goal.getSubGoalsArrayList(), new Goal.SortByDate());


        for (Goal g: goal.getSubGoalsArrayList())
            subGoalsListView.getItems().add(g);


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
                    }else{
                        setStyle("-fx-background-color: " + goalsPanelController.colorPicker(item) + ";");
                    }

                }
            }
        });

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

    public void listViewOMC()  {

        subGoalsListView.setOnMouseClicked(lv -> {

            ContextMenu gaolContextMenu = new ContextMenu();
            MenuItem go_into_goal = new MenuItem("Go into goal");

            go_into_goal.setOnAction(actionEvent -> {
                Goal selectedGaol = subGoalsListView.getSelectionModel().getSelectedItem();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent subGoalManager = fxmlLoader.load((getClass().getResource("subGoalManager.fxml").openStream()));
                    mainViewController.getActionPanel().getChildren().add(subGoalManager);

                    SubGoalManagerController subGoalManagerController = fxmlLoader.getController();


                    subGoalManagerController.setMainViewController(mainViewController);
                    subGoalManagerController.setGoalManagerController(GoalManagerController.this);
                    subGoalManagerController.setSubGoal(selectedGaol);
                    subGoalManagerController.setGoalManager(goalManager);
                    subGoalManagerController.setGoal(goal);

                    subGoalManagerController.prepareSubGoalManagerGui();

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

    public void removeGoal() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet goalsGetRequest = new HttpGet("http://localhost:8080/goals/remove/"+ goal.getId());
            goalsGetRequest.addHeader("content-type", "application/json; charset=UTF-8");
            HttpResponse goalsResponse = httpClient.execute(goalsGetRequest);

            user.getGoalsArrayList().remove(goal);
            backOMC();

            goalsPanelController.prepareListViews();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void goalDone() {
        Boolean done = true;
        for (Goal g: goal.getSubGoalsArrayList()){
            if (!g.getDone()){
                done = false;
                break;
            }
        }

        if (done){
            goal.setDone(true);
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

        }else{
            Thread thread = new Thread(() -> {
                try {

                    allSubGoalsDoneLabel.setVisible(true);
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                allSubGoalsDoneLabel.setVisible(false);
            });
            thread.start();

        }

    }
}
