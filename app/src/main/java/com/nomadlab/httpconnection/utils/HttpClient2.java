package com.nomadlab.httpconnection.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient2 {

    private static final String baseURL = "https://jsonplaceholder.typicode.com/";
    private HttpURLConnection conn;
    private static HttpClient2 httpClient2;
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT =";
    public final static String DELETE = "DELETE";

    private HttpClient2() {
    }

    public synchronized HttpURLConnection getConnection(String endPoint, String method) {
        try {
            URL url = new URL(baseURL + endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static synchronized HttpClient2 getInstance() {
        if (httpClient2 == null) {
            httpClient2 = new HttpClient2();
        }
        return httpClient2;
    }

}
