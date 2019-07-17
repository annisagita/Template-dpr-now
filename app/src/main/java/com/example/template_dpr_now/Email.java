package com.example.template_dpr_now;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Email extends AppCompatActivity implements View.OnClickListener {

    // Mendeklarasikan Variable
    FirebaseAuth mAuth;
    TextView daftarakun;
    EditText email, pass;
    Button login;
    private FirebaseAuth firebaseAuth;

    // Menampilkan layout activity_email.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memberikan nilai
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.E_mail);
        pass = (EditText) findViewById(R.id.passwordemail);
        login = (Button) findViewById(R.id.loginemail);
        daftarakun = (TextView) findViewById(R.id.daftardisini);

        // Mengecek apakah posisi sudah login atau belum jika sudah maka akan pindah ke MainActivity.java
        if(firebaseAuth.getCurrentUser()!=null){
            FirebaseUser user =  firebaseAuth.getCurrentUser();
            Intent i = new Intent(Email.this, MainActivity.class);
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Membuat Handler agar ada fungsi saat di click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        // Membuat Handler agar ada fungsi saat di click
        daftarakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    // Method untuk user login
    private void userLogin() {
        // Mendeklarasikan Variable dan mengambil variable yang telah diisi user
        String Email = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        // Mengecek apakah email sudah terisi atau belum
        if (Email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        // Mengecek apakah email valid atau tidak
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        // Mengecek apakah password sudah terisi atau belum
        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        // Mengecek apakah password sudah mencapain minimum karakter yaitu 6
        if (password.length() < 6) {
            pass.setError("Minimum lenght of password should be 6");
            pass.requestFocus();
            return;
        }

        // Login melalu Firebase Authentication
        mAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Jika berhasil maka akan pindah ke MainActivity.java dan memunculkan toast
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(Email.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(Email.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                }
                // Jika tidak maka akan memunculkan toast
                else {
                    Toast.makeText(Email.this, "Email atau Password salah", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // Pindah ke class Daftar.java
    public void Register() {
        Intent intent = new Intent(this, Daftar.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
