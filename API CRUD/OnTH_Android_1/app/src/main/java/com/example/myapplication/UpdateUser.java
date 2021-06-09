package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateUser extends AppCompatActivity {


    private TextView tv_id;
    private EditText et_name;
    private RadioGroup et_Gender;
    private EditText et_age;
    private User user;
    private Button btn_CapNhat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        tv_id = findViewById(R.id.tv_id_update);
        et_name = findViewById(R.id.name_update);
        et_Gender = findViewById(R.id.radioGroup);
        et_age = findViewById(R.id.age_update);
        btn_CapNhat = findViewById(R.id.btn_form_update);


        getData();
        writeForm();
        handleEvent();


    }

    private void handleEvent() {
        btn_CapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int txt_id = Integer.valueOf(tv_id.getText().toString());
                int txt_Age = Integer.valueOf(et_age.getText().toString());
                boolean txt_gender = true;
                String txt_name = et_name.getText().toString();
                int selectedId = et_Gender.getCheckedRadioButtonId();

                if (selectedId == R.id.update_male) {
                    txt_gender = false;
                }

                System.out.println("Checkkkkk gender ====="+txt_gender);

                JSONObject jo = new JSONObject();
                try {
                    jo.put("id", txt_id);
                    jo.put("name", txt_name);
                    jo.put("age", txt_Age);
                    jo.put("gender", txt_gender);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    PutApi(List_User.url + "user", jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void writeForm() {
        tv_id.setText(String.valueOf(user.getId()));
        et_name.setText(user.getName());
        et_age.setText(String.valueOf(user.getAge()));
        if (user.isGender()) {
            et_Gender.check(R.id.update_female);
        } else {
            et_Gender.check(R.id.update_male);
        }
    }


    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        user = bundle.getParcelable("user");


    }

    private void PutApi(String url, JSONObject jo) throws JSONException {
        int idUser = Integer.valueOf(jo.getString("id"));

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUser.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + '/' + idUser, jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(UpdateUser.this, "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateUser.this, List_User.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateUser.this, "Failed", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        requestQueue.add(request);
    }


}