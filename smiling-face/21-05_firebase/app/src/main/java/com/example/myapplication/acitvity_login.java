package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class acitvity_login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btnLogin;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitvity_login);

        btnLogin = findViewById(R.id.btn_signin_login);

        handleEvent();
    }

    private void handleEvent() {
        email = findViewById(R.id.txt_email_login);
        password = findViewById(R.id.txt_password_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email.getText().toString();
                String password_txt= password.getText().toString();

                System.out.println(email_txt);
                System.out.println(password_txt);
                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_txt)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(acitvity_login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(acitvity_login.this,"Error",Toast.LENGTH_LONG);


                        }else{
                            Intent intent = new Intent(acitvity_login.this,acivity_evaluate.class);
                            startActivity(intent);
                            Toast.makeText(acitvity_login.this,"Sucesss",Toast.LENGTH_LONG);

                        }
                    }
                });

            }
        });


    }

}