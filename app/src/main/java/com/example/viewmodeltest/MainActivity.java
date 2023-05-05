package com.example.viewmodeltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.viewmodeltest.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int winImageIndex;
    private int attempts = 0;
    private int score = 0;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resetGame();
        addImageListeners();
        addRestartButtonListener();

    }

    private void addRestartButtonListener() {
        binding.restartButton.setOnClickListener(v -> {
            resetGame();
        });
    }

    private void addImageListeners() {

        addImageListener(binding.image1, 1);
        addImageListener(binding.image2, 2);
    }


    private void addImageListener(ImageView imageView, int imageIndex){
        imageView.setOnClickListener(v -> {
            attempts++;
            if (winImageIndex == imageIndex){
                imageView.setImageResource(R.drawable.ic_baseline_android_100dp);
                score++;
                showGameEndDialog(true);
            } else {
                showGameEndDialog(false);
                imageView.setImageResource(R.drawable.ic_baseline_disabled_by_default_100dp);
            }
            updateUI();
        });
    }

    private void showGameEndDialog(boolean success) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        if (success){
            builder.setTitle("Congratulations!");
        }else {
            builder.setTitle("Better luck next time!");
        }

        builder.setMessage("Do you want to play again?");
        builder.setCancelable(false);
        builder.setPositiveButton("Try again", (dialogInterface, i) -> {
            startNewGame();
        });

        builder.setNegativeButton("Stop", (dialogInterface, i) -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("attempts", attempts);
            startActivity(intent);

        });
        builder.show();
    }

    private void resetGame(){
        score = 0;
        attempts = 0;
        updateUI();
        startNewGame();
    }

    private void startNewGame() {
        binding.image1.setImageResource(R.drawable.ic_baseline_question_mark_100dp);
        binding.image2.setImageResource(R.drawable.ic_baseline_question_mark_100dp);
        generateWinImagePosition();
    }

    private void generateWinImagePosition() {
        int randNumber = random.nextInt(500);
        if (randNumber % 2 == 0){
            winImageIndex = 1;
        } else {
            winImageIndex = 2;
        }
    }

    private void updateUI(){
        binding.scoreText.setText(String.valueOf(score));
        binding.attempsText.setText(String.valueOf(attempts));
    }
}