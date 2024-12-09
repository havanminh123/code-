package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button viewExpensesButton;
    private Button addExpenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewExpensesButton = findViewById(R.id.viewExpensesButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);

        viewExpensesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
            startActivity(intent);
        });

        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });
    }
}