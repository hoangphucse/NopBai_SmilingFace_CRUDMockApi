package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class List_User extends AppCompatActivity {

    public static final String url = "https://60ad9ba880a61f0017331403.mockapi.io/";
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private Button btn_add;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__user);
        btn_add = findViewById(R.id.btn_Them);



        recyclerView = findViewById(R.id.rcl_view);
        getData();
        handleEvent();


    }

    private void handleEvent() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(List_User.this,add_user.class);
                startActivity(intent);
            }
        });




    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getData() {
        ArrayList<User> users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url + "user",
                        response -> {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = (JSONObject) response.get(i);
                                    int id = Integer.valueOf(object.getString("id"));
                                    String name = object.getString("name");
                                    int age = Integer.valueOf(object.getString("age"));
                                    boolean gender = Boolean.valueOf(object.getString("gender"));

                                    User user = new User(id, age, gender, name);

                                    users.add(user);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            buildView(users);

                        }, error -> Toast.makeText(this, "Error by get Json Array!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void buildView(ArrayList<User> users) {
        System.out.println("check run");
        adapter = new UserAdapter(List_User.this, users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}