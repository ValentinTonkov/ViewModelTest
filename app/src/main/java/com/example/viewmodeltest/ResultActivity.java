package com.example.viewmodeltest;

import androidx.activity.result.ActivityResultCaller;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.viewmodeltest.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultBinding binding = ActivityResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int score= intent.getIntExtra("score", 0);
        int attempts= intent.getIntExtra("attempts", 0);

        binding.attemptsTextView.setText(String.valueOf(attempts));
        binding.scoreTextView.setText(String.valueOf(score));

        // TODO add restart button and logic
    }
}