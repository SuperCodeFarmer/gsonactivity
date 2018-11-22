package com.example.gsonactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    private List<City> cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_1);
        responseText=findViewById(R.id.rsponse_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkhttp();
            }
        });
    }

    private void sendRequestWithOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://guolin.tech/api/china")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    praseJSONWithJSONObject(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void praseJSONWithJSONObject(String jsonData) {
        Gson gson=new Gson();
        City city=gson.fromJson(jsonData,City.class);
            Log.e("MainActivity","id is"+city.getCityId());
            Log.e("MainActivity","name is"+city.getCityName());
    }
}
