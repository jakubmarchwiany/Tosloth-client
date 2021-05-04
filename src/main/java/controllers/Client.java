package controllers;

import model.User;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    public int registerUserInDataBase(User userToRegister){



        return 0;
    }



}
