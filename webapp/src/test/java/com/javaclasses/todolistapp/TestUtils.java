package com.javaclasses.todolistapp;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.javaclasses.todolistapp.Parameters.CONFIRM_PASSWORD;
import static com.javaclasses.todolistapp.Parameters.EMAIL;
import static com.javaclasses.todolistapp.Parameters.PASSWORD;
import static com.javaclasses.todolistapp.UrlConstants.BASE_URL;
import static com.javaclasses.todolistapp.UrlConstants.LOGIN_URL;
import static com.javaclasses.todolistapp.UrlConstants.REGISTRATION_URL;
import static org.apache.http.HttpHeaders.USER_AGENT;

public class TestUtils {

    private final static HttpClient client = HttpClientBuilder.create().build();

    /*package*/
    static HttpResponse sendRegistrationRequest(String email, String password, String confirmPassword) throws IOException {

        final String registrationUrl = BASE_URL + REGISTRATION_URL;

        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair(EMAIL, email));
        parameters.add(new BasicNameValuePair(PASSWORD, password));
        parameters.add(new BasicNameValuePair(CONFIRM_PASSWORD, confirmPassword));

        return sendRequest(registrationUrl, parameters);
    }

    /*package*/
    static HttpResponse sendLoginRequest(String email, String password) throws IOException {

        final String loginUrl = BASE_URL + LOGIN_URL;

        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair(EMAIL, email));
        parameters.add(new BasicNameValuePair(PASSWORD, password));

        return sendRequest(loginUrl, parameters);
    }

    /*package*/
    static JSONObject getResponseContent(HttpResponse response) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return new JSONObject(result.toString());
    }

    private static HttpResponse sendRequest(String url, List<NameValuePair> parameters) throws IOException {

        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader("User-Agent", USER_AGENT);

        postRequest.setEntity(new UrlEncodedFormEntity(parameters));

        return client.execute(postRequest);
    }

}
