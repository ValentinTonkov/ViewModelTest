package com.example.viewmodeltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
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
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        viewModel.getScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.scoreText.setText(String.valueOf(integer));
            }
        });

        viewModel.getAttempts().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.attempsText.setText(String.valueOf(integer));
            }
        });

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

            if (viewModel.checkForSuccess(imageIndex)){
                imageView.setImageResource(R.drawable.ic_baseline_android_100dp);
                showGameEndDialog(true);
            } else {
                showGameEndDialog(false);
                imageView.setImageResource(R.drawable.ic_baseline_disabled_by_default_100dp);
            }
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
        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        });

        builder.setNegativeButton("Stop", (dialogInterface, i) -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ResultActivity.class);
            intent.putExtra("score", viewModel.getScore().getValue());
            intent.putExtra("attempts", viewModel.getAttempts().getValue());
            startActivity(intent);

        });
        builder.show();
    }

    private void resetGame(){
        viewModel.resetGame();
        startNewGame();
    }

    private void startNewGame() {
        binding.image1.setImageResource(R.drawable.ic_baseline_question_mark_100dp);
        binding.image2.setImageResource(R.drawable.ic_baseline_question_mark_100dp);
    }

}