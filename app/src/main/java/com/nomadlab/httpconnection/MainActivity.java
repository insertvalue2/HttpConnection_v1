package com.nomadlab.httpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nomadlab.httpconnection.models.UserDto;
import com.nomadlab.httpconnection.models.response.Person;
import com.nomadlab.httpconnection.models.response.Post;
import com.nomadlab.httpconnection.models.response.Todo;
import com.nomadlab.httpconnection.service.UserService;
import com.nomadlab.httpconnection.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Todo> todos;
    private ArrayList<Post> posts;
    private HttpClient httpClient;
    private UserDto userDto;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserService userService = new UserService();
        // 쓰레드에 대한 동작 이해 설명 (디버깅)
        userDto = userService.read("1");
//        Log.d("TAG", "userDto : " + userDto.getUsername());
//        httpClient = HttpClient.getInstance();
//        requestTodos();
//        requestPosts();
//        savePost();
    }

    private void savePost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    httpClient.posts("posts", HttpClient.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void requestTodos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    todos = httpClient.todos("todos");
                } catch (Exception e) {
                    Log.d("TAG", e.getMessage());
                }

                Log.d("TAG", todos.toString());
            }
        }).start();
    }

    private void requestPosts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    posts = httpClient.posts("posts");
                } catch (Exception e) {
                    Log.d("TAG", e.getMessage());
                }

                Log.d("TAG", posts.toString());
            }
        }).start();
    }


    private void testCodeForJson() {
        // 1. 하나의 jsonObject 만들기
        // 2. JsonArray 만들기

        // JSONObject 를 만드는 방법
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        // 이름 = 홍길동 , 나이 = 20, 직업 : 개발자, 취미 = 게임, 결혼여부 = false;
        try {

            jsonObject.put("이름", "홍길동");
            jsonObject.put("나이", 20);
            jsonObject.put("직업", "개발자");
            jsonObject.put("취미", "게임");
            jsonObject.put("결혼여부", false);

            // jsonArray
            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject);

            // 사용해보기
//            jsonObject
//            Log.d("TAG", jsonObject.toString());
//            // json 파싱 --> 형식이 있는 문자열 타입을 --> Object
//            Person person = new Gson().fromJson(jsonObject.toString(), Person.class);
//            Log.d("TAG", person.toString());

            // jsonArray
//            Log.d("TAG", jsonArray.toString());
            // jsonArray 파싱
            // 1. 배열로 파싱하기
//            Person[] people = new Gson().fromJson(jsonArray.toString(), Person[].class);
//            //Log.d("TAG", Arrays.toString(people));
//            Log.d("TAG", people[0].toString());

            // 2. ArrayList 로 파싱하기
            Type listType = new TypeToken<ArrayList<Person>>() {
            }.getType();

            ArrayList<Person> personArrayList = new Gson().fromJson(jsonArray.toString(), listType);
            Log.d("TAG", personArrayList.toString());
            Log.d("TAG", personArrayList.get(2).toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void HttpConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1. URL 객체 만들기
                String baseUrl = "https://jsonplaceholder.typicode.com/todos/1";
                try {

                    // Http Request Message 생성 (시작줄 + http header)
                    URL url = new URL(baseUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET"); // GET, POST, PUT, DELETE
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    // 결과값 받기
                    // 응답헤더 + 응답바디

                    // 상태 코드 확인해 보기
                    Log.d("TAG", "status code : " + conn.getResponseCode());
                    // 1xx(진행중), 2xx(성공), 3xx(리다이렉트), 4xx(실패: 잘못된요청), 5xx(연산오류)
                    // GET 성공시 ---> 200 호출
                    // POST 성공시 ---> 201 호출 (HttpURLConnection.HTTP_CREATED)
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // InputStream : byte 단위로 읽어 들인다.
                        // InputStreamReader : 문자 단위로 읽어 들인다.
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                "UTF-8"));
//                        String line = null;
//                        StringBuffer sb = new StringBuffer();
////                        line = reader.readLine();
////                        sb.append(line+"\n");
//                        // 위 코드를 반복 (정확한 횟수는 알 수 없다. --> while)
//                        while ( (line = reader.readLine()) != null) {
//                            sb.append(line);
//                        }
//                        Log.d("TAG", sb.toString());
//                        String responseString = sb.toString();
//                        // X <-- 문자열을 파싱하는 방법은 너무 불편함!!
//                        String key = responseString.substring(3, 10);
//                        String value = responseString.substring(11, 14);
//                        Log.d("TAG", "key : " + key);
//                        Log.d("TAG", "value : " + value);

                        Todo todo = new Gson().fromJson(reader, Todo.class);
                        // Http response message 에 응답 바디에 있는 데이터를 파싱
//                        Log.d("TAG", "Result :\n" + todo.toString());
                        Log.d("TAG", "userId : " + todo.getUserId());
                        Log.d("TAG", "id : " + todo.getUserId());
                        Log.d("TAG", "title : " + todo.getTitle());
                        Log.d("TAG", "result : " + todo.isCompleted());


                        //배열 타입으로 변환 방법
                        //Todo[] todos = new Gson().fromJson(reader, Todo[].class);

                    } else {
                        Log.d("TAG", "http fail");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}