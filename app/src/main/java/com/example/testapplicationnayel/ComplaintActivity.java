package com.example.testapplicationnayel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.messaging.FirebaseMessaging;

public class ComplaintActivity extends AppCompatActivity {


    TextInputEditText nameField, emailField, mobileField, feedbackField;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        nameField = findViewById(R.id.name);
        emailField = findViewById(R.id.emailAddress);
        mobileField = findViewById(R.id.mobileNumber);
        feedbackField = findViewById(R.id.feedback);
        submitButton = findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback(nameField.getEditableText().toString(),
                        emailField.getEditableText().toString(),
                        mobileField.getEditableText().toString(),
                        feedbackField.getEditableText().toString());
            }
        });

    }
    private void submitFeedback(final String name, String email, String mobileField, String feedbackField) {
        if (!name.equals("") && name.length() > 3){
            if (!email.equals("")){
                if (!mobileField.equals("") && mobileField.length() > 7){
                    if (!feedbackField.equals("")){
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){


                            Intent emailIntent = new Intent(Intent.ACTION_SEND);

                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "razarajwani@live.com"});
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Complaint / Feedback");
                            String message = "";
                            message += "Name : " + name + "\n";
                            message += "Email : " + email + "\n";
                            message += "Mobile : " + mobileField + "\n";
                            message += "Feedback : " + feedbackField;
                            emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                            emailIntent.setType("message/rfc822");

                            startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
                        }
                        else{
                            Toast.makeText(this, "Email address is badly formatted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Please enter a feedback", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
        }
    }

}