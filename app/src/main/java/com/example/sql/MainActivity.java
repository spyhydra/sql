package com.example.sql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private Spinner spinnerTopic;
    private ListView listViewRegistered;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> registeredList;
    private ArrayAdapter<String> adapter;
    Button web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerTopic = findViewById(R.id.spinnerTopic);
        listViewRegistered = findViewById(R.id.listViewRegistered);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        web=findViewById(R.id.web);
        // Initialize the database helper and array list
        databaseHelper = new DatabaseHelper(this);
        registeredList = new ArrayList<>();

        // Set up the adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registeredList);
        listViewRegistered.setAdapter(adapter);

        // Load existing registered candidates
        displayRegisteredCandidates();

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, webview.class);
                startActivity(intent);
            }
        });

        // Set the onClick listener for the Register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCandidate();
            }
        });
    }

    private void registerCandidate() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String topic = spinnerTopic.getSelectedItem().toString();


        boolean isInserted = databaseHelper.registerCandidate(name, email, topic);

        if (isInserted) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            displayRegisteredCandidates();
        } else {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayRegisteredCandidates() {
        registeredList.clear();
        Cursor cursor = databaseHelper.getRegisteredCandidates();

        if (cursor.getCount() == 0) {
            registeredList.add("No Registrations Found");
        } else {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String topic = cursor.getString(cursor.getColumnIndex("topic"));
                registeredList.add("Name: " + name + "\nEmail: " + email + "\nTopic: " + topic);
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged(); // Refresh the ListView with updated data
    }
}
