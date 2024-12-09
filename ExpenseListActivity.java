package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExpenseListActivity extends AppCompatActivity implements ExpenseAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private ArrayList<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseList = AddExpenseActivity.expenseList; // Sử dụng danh sách chi tiêu từ AddExpenseActivity
        adapter = new ExpenseAdapter(expenseList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditClick(int position) {
        Expense expense = expenseList.get(position);
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("name", expense.getName());
        intent.putExtra("amount", expense.getAmount());
        intent.putExtra("category", expense.getCategory());
        intent.putExtra("date", expense.getDate());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDeleteClick(int position) {
        expenseList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Expense Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);
            String name = data.getStringExtra("name");
            String amount = data.getStringExtra("amount");
            String category = data.getStringExtra("category");
            String date = data.getStringExtra("date");

            if (position != -1) {
                expenseList.get(position).setName(name);
                expenseList.get(position).setAmount(amount);
                expenseList.get(position).setCategory(category);
                expenseList.get(position).setDate(date);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}