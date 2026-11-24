package com.lendloop.firstapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class additemsActivity extends AppCompatActivity {

    EditText etItemName, etCategory, etDescription, etOwner;
    Button btnSaveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        etItemName = findViewById(R.id.etItemName);
        etCategory = findViewById(R.id.etCategory);
        etDescription = findViewById(R.id.etDescription);
        etOwner = findViewById(R.id.etOwner);
        btnSaveItem = findViewById(R.id.btnSaveItem);

        btnSaveItem.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {

        String name = etItemName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String owner = etOwner.getText().toString().trim();

        // Check empty fields
        if (name.isEmpty()) {
            etItemName.setError("Enter item name");
            etItemName.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            etCategory.setError("Enter category");
            etCategory.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Enter description");
            etDescription.requestFocus();
            return;
        }

        if (owner.isEmpty()) {
            etOwner.setError("Enter owner name");
            etOwner.requestFocus();
            return;
        }

        try {

            // Save item using SharedPreferences
            SharedPreferences preferences =
                    getSharedPreferences("LendLoopData", MODE_PRIVATE);

            String savedData = preferences.getString("saved_items", "[]");

            JSONArray itemsArray = new JSONArray(savedData);

            JSONObject item = new JSONObject();

            item.put("name", name);
            item.put("category", category);
            item.put("description", description);
            item.put("owner", owner);

            itemsArray.put(item);

            preferences.edit()
                    .putString("saved_items", itemsArray.toString())
                    .apply();

            Toast.makeText(
                    additemsActivity.this,
                    "Item added successfully!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (Exception e) {

            Toast.makeText(
                    additemsActivity.this,
                    "Error saving item!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}

