package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText;
    private EditText amountEditText;
    private Spinner categorySpinner;
    private EditText expenseDateEditText;
    private Button saveButton;
    private Button resetButton;

    public static ArrayList<Expense> expenseList = new ArrayList<>();

    private int position = -1; // Vị trí chi tiêu nếu đang sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEditText = findViewById(R.id.expenseName);
        amountEditText = findViewById(R.id.expenseAmount);
        categorySpinner = findViewById(R.id.categorySpinner);
        expenseDateEditText = findViewById(R.id.expenseDate);
        saveButton = findViewById(R.id.saveExpenseButton);
        resetButton = findViewById(R.id.resetButton);

        String[] categories = {"Food", "Transport", "Bills", "Entertainment", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Kiểm tra xem có dữ liệu sửa đổi không
        Intent intent = getIntent();
        if (intent.hasExtra("position")) {
            position = intent.getIntExtra("position", -1);
            expenseNameEditText.setText(intent.getStringExtra("name"));
            amountEditText.setText(intent.getStringExtra("amount"));
            categorySpinner.setSelection(adapter.getPosition(intent.getStringExtra("category")));
            expenseDateEditText.setText(intent.getStringExtra("date"));
        }

        expenseDateEditText.setOnClickListener(v -> showDatePickerDialog());

        saveButton.setOnClickListener(v -> saveExpense());
        resetButton.setOnClickListener(v -> resetFields());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    expenseDateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveExpense() {
        String name = expenseNameEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String date = expenseDateEditText.getText().toString();

        if (position == -1) {
            // Thêm chi tiêu mới
            expenseList.add(new Expense(name, amount, category, date));
            Toast.makeText(this, "Expense Saved: " + name, Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật chi tiêu
            expenseList.get(position).setName(name);
            expenseList.get(position).setAmount(amount);
            expenseList.get(position).setCategory(category);
            expenseList.get(position).setDate(date);
            Toast.makeText(this, "Expense Updated: " + name, Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK, new Intent().putExtra("position", position)
                .putExtra("name", name)
                .putExtra("amount", amount)
                .putExtra("category", category)
                .putExtra("date", date));
        finish();
    }

    private void resetFields() {
        expenseNameEditText.setText("");
        amountEditText.setText("");
        expenseDateEditText.setText("");
        categorySpinner.setSelection(0);
    }
}