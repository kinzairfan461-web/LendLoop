package com.lendloop.firstapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class regisActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regis);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();
            String confirm = etConfirmPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty()
                    || password.isEmpty() || confirm.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter a valid email");
                return;
            }

            if (password.length() < 6) {
                etPassword.setError(
                        "Password must be at least 6 characters");
                return;
            }

            if (!password.equals(confirm)) {
                etConfirmPassword.setError(
                        "Passwords do not match");
                return;
            }

            boolean created = Authstore.register(
                    this, name, email, password);

            if (created) {

                Toast.makeText(this,
                        "Account created successfully!",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(
                        regisActivity.this,
                        loginactivity.class);

                startActivity(intent);
                finish();

            } else {

                Toast.makeText(this,
                        "Email already registered or registration failed!",
                        Toast.LENGTH_LONG).show();
            }
        });

        tvLogin.setOnClickListener(v -> finish());
    }
}

