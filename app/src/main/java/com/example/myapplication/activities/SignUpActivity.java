package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.model.content.CircleShape;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private CircleImageView userprofile;
    private EditText userName, userPassword, userEmail, userCollegeId;
    private Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.have_acc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userprofile = findViewById(R.id.profile_image);
        userName = findViewById(R.id.user_name);
        userPassword = findViewById(R.id.user_password);
        userEmail = findViewById(R.id.user_email);
        userCollegeId = findViewById(R.id.college_id);
        signUpBtn = findViewById(R.id.sign_up_btn);

        mAuth = FirebaseAuth.getInstance();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String collegeId = userCollegeId.getText().toString().trim();

                if (TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        && !TextUtils.isEmpty(collegeId)) {
                    createUser(email, password);
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SignUpActivity.this, "Fail try again", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();  // Print the exception stack trace
                Toast.makeText(SignUpActivity.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}