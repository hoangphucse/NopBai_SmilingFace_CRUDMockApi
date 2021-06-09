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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class add_user extends AppCompatActivity {

    private EditText id;
    private EditText name;
    private EditText age;
    private RadioGroup ra_gr;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        btnSubmit = findViewById(R.id.btn_Submit);

        id = findViewById(R.id.id_dk);
        name = findViewById(R.id.name_dk);
        age = findViewById(R.id.age_dk);
        ra_gr = findViewById(R.id.rd_group);
        handleEvent();



    }





    public void handleEvent() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int txt_id = Integer.valueOf(id.getText().toString());
                String txt_name = name.getText().toString();
                int txt_Age = Integer.valueOf(age.getText().toString());
                boolean txt_gender = true;
                int selectedId = ra_gr.getCheckedRadioButtonId();
                if (selectedId == R.id.male) {
                    txt_gender = false;
                }
                JSONObject jo = new JSONObject();
                try {
                    jo.put("id", txt_id);
                    jo.put("name", txt_name);
                    jo.put("age", txt_Age);
                    jo.put("gender", txt_gender);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                PostApi(List_User.url + "user", jo);
                Toast.makeText(add_user.this, "Thêm user thành công", Toast.LENGTH_LONG);

            }
        });


    }


    public void PostApi(String url, JSONObject jo) {

        RequestQueue requestQueue = Volley.newRequestQueue(add_user.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(add_user.this, "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(add_user.this, List_User.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(add_user.this, "Failed", Toast.LENGTH_LONG).show();
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



