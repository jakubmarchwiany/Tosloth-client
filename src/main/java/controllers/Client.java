package controllers;

import com.google.gson.Gson;
import model.Goal;
import model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    CloseableHttpClient httpClient = HttpClients.createDefault();

    public int registerUserInDataBase(User userToRegister){
        try {
            HttpPost registerPostRequest = new HttpPost("http://localhost:8080/users");
            StringEntity newUser = new StringEntity(new Gson().toJson(userToRegister), "UTF-8");
            registerPostRequest.addHeader("content-type", "application/json; charset=UTF-8");
            registerPostRequest.setEntity(newUser);
            HttpResponse registerResponse = httpClient.execute(registerPostRequest);
            return registerResponse.getStatusLine().getStatusCode();
        }catch (IOException e) {
            e.printStackTrace();
            return 500;
        }
    }

    public User loginUser(User userToLogIn){
        try {
            HttpPost logInPostRequest = new HttpPost("http://localhost:8080/users/login");
            logInPostRequest.addHeader("content-type", "application/json; charset=UTF-8");
            StringEntity userEntity = new StringEntity(new Gson().toJson(userToLogIn), "UTF-8");
            logInPostRequest.setEntity(userEntity);
            HttpResponse logInResponse = httpClient.execute(logInPostRequest);

            if ( logInResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = logInResponse.getEntity();
                String userResponseString = EntityUtils.toString(entity, "UTF-8");
                User loginUser = new Gson().fromJson(userResponseString, User.class);

                loginUser.setGoalsArrayList(getGoalsFromDataBase(loginUser));

                return loginUser;
            }else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Goal> getGoalsFromDataBase(User loginUser){
        try {
            HttpGet goalsGetRequest = new HttpGet("http://localhost:8080/goals/"+ loginUser.getNickname());
            goalsGetRequest.addHeader("content-type", "application/json; charset=UTF-8");
            HttpResponse goalsResponse = httpClient.execute(goalsGetRequest);

            HttpEntity goalsEntity = goalsResponse.getEntity();

            String goalsResponseString = EntityUtils.toString(goalsEntity, "UTF-8");
            Goal[] userGoalsArray = new Gson().fromJson(goalsResponseString,Goal[].class);
            return new ArrayList<>(Arrays.asList(userGoalsArray));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addGoal(Goal tempGoal){
        try {
            HttpPost postRequest = new HttpPost("http://localhost:8080/goals");
            StringEntity newGoal = new StringEntity(new Gson().toJson(tempGoal), "UTF-8");
            postRequest.addHeader("content-type", "application/json; charset=UTF-8");
            postRequest.setEntity(newGoal);

            httpClient.execute(postRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGoal(Goal tempGoal){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:8080/goals/update");
            StringEntity newSubGoal = new StringEntity(new Gson().toJson(tempGoal), "UTF-8");

            postRequest.addHeader("content-type", "application/json; charset=UTF-8");
            postRequest.setEntity(newSubGoal);

            httpClient.execute(postRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeGoal(Goal goalToRemove){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet goalsGetRequest = new HttpGet("http://localhost:8080/goals/remove/" + goalToRemove.getId());
            goalsGetRequest.addHeader("content-type", "application/json; charset=UTF-8");
            httpClient.execute(goalsGetRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
