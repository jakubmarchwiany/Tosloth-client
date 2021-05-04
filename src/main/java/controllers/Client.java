package controllers;

import com.google.gson.Gson;
import model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Client {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    public int registerUserInDataBase(User userToRegister){
        try {
            HttpPost registerPostRequest = new HttpPost("http://localhost:8080/users");
            StringEntity newUser = new StringEntity(new Gson().toJson(userToRegister), "UTF-8");
            registerPostRequest.addHeader("content-type", "application/json; charset=UTF-8");
            registerPostRequest.setEntity(newUser);
            HttpResponse response = httpClient.execute(registerPostRequest);

            return response.getStatusLine().getStatusCode();
        }catch (IOException e) {
            e.printStackTrace();
            return 500;
        }
    }

    public int logInUser(User userToLogIn){

        return 0;
    }



}
