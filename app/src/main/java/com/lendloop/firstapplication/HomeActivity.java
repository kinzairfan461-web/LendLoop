package com.lendloop.firstapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button btnAddItem;
    Button btnHome, btnMyItems, btnRequests, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnHome = findViewById(R.id.btnHome);
        btnMyItems = findViewById(R.id.btnMyItems);
        btnRequests = findViewById(R.id.btnRequests);
        btnProfile = findViewById(R.id.btnProfile);
        btnAddItem.setOnClickListener(v ->
                showMessage("Add Item Screen Coming Soon!")
        );
        btnHome.setOnClickListener(v ->
                showMessage("You are already on Home")
        );
        btnMyItems.setOnClickListener(v ->
                showMessage("My Items Screen Coming Soon!")
        );
        btnRequests.setOnClickListener(v ->
                showMessage("Requests Screen Coming Soon!")
        );
        btnProfile.setOnClickListener(v ->
                showMessage("Profile Screen Coming Soon!")
        );
    }
    private void showMessage(String message) {
        Toast.makeText(
                HomeActivity.this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}
