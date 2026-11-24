package com.lendloop.firstapplication;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class browseitemsActivity extends AppCompatActivity {

    EditText searchItems;
    LinearLayout itemsContainer;

    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        searchItems = findViewById(R.id.searchItems);
        itemsContainer = findViewById(R.id.itemsContainer);

        loadSampleItems();
        displayItems(itemList);

        searchItems.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadSampleItems() {

        itemList.add(new Item(
                "Programming Book",
                "Books",
                "Useful book for programming students.",
                "Ali"
        ));

        itemList.add(new Item(
                "Headphones",
                "Electronics",
                "Good condition headphones available.",
                "Sara"
        ));

        itemList.add(new Item(
                "Laptop Charger",
                "Electronics",
                "Laptop charger available for borrowing.",
                "Ahmed"
        ));

        itemList.add(new Item(
                "Scientific Calculator",
                "Study Materials",
                "Calculator for university students.",
                "Ayesha"
        ));

        itemList.add(new Item(
                "Notebook",
                "Stationery",
                "Extra notebook available for students.",
                "Usman"
        ));
    }

    private void displayItems(List<Item> items) {

        itemsContainer.removeAllViews();

        if (items.isEmpty()) {

            TextView emptyText = new TextView(this);

            emptyText.setText("No items found.");
            emptyText.setTextSize(16);

            emptyText.setTextColor(
                    getResources().getColor(R.color.text_secondary)
            );

            emptyText.setGravity(Gravity.CENTER);
            emptyText.setPadding(10, 30, 10, 30);

            itemsContainer.addView(emptyText);

            return;
        }

        for (Item item : items) {

            LinearLayout card = new LinearLayout(this);

            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(18, 16, 18, 16);
            card.setBackgroundResource(R.drawable.item_card_bg);

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            cardParams.setMargins(0, 0, 0, 14);
            card.setLayoutParams(cardParams);

            // Item Name
            TextView itemName = new TextView(this);

            itemName.setText(item.name);
            itemName.setTextSize(19);

            itemName.setTextColor(
                    getResources().getColor(R.color.deep_purple)
            );

            itemName.setTypeface(null, Typeface.BOLD);

            // Category
            TextView category = new TextView(this);

            category.setText("Category: " + item.category);
            category.setTextSize(14);

            category.setTextColor(
                    getResources().getColor(R.color.text_secondary)
            );

            // Description
            TextView description = new TextView(this);

            description.setText(item.description);
            description.setTextSize(14);

            description.setTextColor(
                    getResources().getColor(R.color.text_primary)
            );

            description.setPadding(0, 8, 0, 8);

            // Owner
            TextView owner = new TextView(this);

            owner.setText("Owner: " + item.owner);
            owner.setTextSize(14);

            owner.setTextColor(
                    getResources().getColor(R.color.deep_purple)
            );

            // Status
            TextView status = new TextView(this);

            status.setText("Available for borrowing");
            status.setTextSize(13);

            status.setTextColor(
                    getResources().getColor(R.color.success)
            );

            status.setPadding(0, 8, 0, 0);

            // Add views to card
            card.addView(itemName);
            card.addView(category);
            card.addView(description);
            card.addView(owner);
            card.addView(status);

            // Card click listener
            card.setOnClickListener(v ->
                    Toast.makeText(
                            browseitemsActivity.this,
                            item.name + " selected",
                            Toast.LENGTH_SHORT
                    ).show()
            );

            itemsContainer.addView(card);
        }
    }

    private void filterItems(String query) {

        List<Item> filteredList = new ArrayList<>();

        for (Item item : itemList) {

            if (item.name.toLowerCase(Locale.ROOT)
                    .contains(query.toLowerCase(Locale.ROOT))

                    || item.category.toLowerCase(Locale.ROOT)
                    .contains(query.toLowerCase(Locale.ROOT))) {

                filteredList.add(item);
            }
        }

        displayItems(filteredList);
    }

    // Item Model Class
    private static class Item {

        String name;
        String category;
        String description;
        String owner;

        Item(String name, String category,
             String description, String owner) {

            this.name = name;
            this.category = category;
            this.description = description;
            this.owner = owner;
        }
    }
}
