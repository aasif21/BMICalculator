package com.example.cal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void displayToast(View v)
    {
        Toast.makeText(MainActivity.this,"this is a toast message in interval of 0.5 seconds for MAD Practical", Toast.LENGTH_SHORT).show();
    }
}