package com.nomadlab.httpconnection.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nomadlab.httpconnection.models.request.ReqPost;
import com.nomadlab.httpconnection.models.response.Post;
import com.nomadlab.httpconnection.models.response.Todo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpClient {

    private static final String baseURL = "https://jsonplaceholder.typicode.com/";
    private HttpURLConnection conn;
    private static HttpClient httpClient;
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT =";
    public final static String DELETE = "DELETE";

    private HttpClient() {
    }

    public static synchronized HttpClient getInstance() {
        if (httpClient == null) {
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    // 요청
    public synchronized ArrayList<Todo> todos(String path) throws Exception {

        ArrayList<Todo> todoArrayList = new ArrayList<>();
        URL url = new URL(baseURL + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(GET);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),
                            StandardCharsets.UTF_8));
            Type userListType = new TypeToken<ArrayList<Todo>>() {
            }.getType();
            todoArrayList = new Gson().fromJson(reader, userListType);
        }
        conn.disconnect();

        return todoArrayList;
    }

    public synchronized ArrayList<Post> posts(String path) throws Exception {
        ArrayList<Post> postArrayList = new ArrayList<>();
        URL url = new URL(baseURL + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(GET);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),
                            StandardCharsets.UTF_8));
            Type userListType = new TypeToken<ArrayList<Post>>() {
            }.getType();
            postArrayList = new Gson().fromJson(reader, userListType);
        }
        conn.disconnect();
        return postArrayList;
    }

    /**
     * 데이터 저장 요청
     * @param path 경로 입력
     * @param method POST 방식
     * @return 성공 여부
     * @throws Exception 예외처리
     */
    public synchronized void posts(String path, String method) throws Exception {
        //ArrayList<Post> postArrayList = new ArrayList<>();

        URL url = new URL(baseURL + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        // 데이터 만들어 놓기
        ReqPost reqPost = new ReqPost("글 저장해보기", "가나다라마", 1111);
        String jsonObject = new Gson().toJson(reqPost);
        writer.write(jsonObject);
        writer.close();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),
                        StandardCharsets.UTF_8));

        Post post = new Gson().fromJson(reader, Post.class);

        Log.d("TAG", post.toString());
    }

}
