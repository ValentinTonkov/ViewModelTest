package com.example.viewmodeltest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class GameViewModel extends ViewModel {

    private int winImageIndex;
    private final MutableLiveData<Integer> attempts = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final Random random = new Random();

    public void generateWinImagePosition() {
        int randNumber = random.nextInt(500);
        if (randNumber % 2 == 0){
            winImageIndex = 1;
        } else {
            winImageIndex = 2;
        }
    }

    public GameViewModel (){
        generateWinImagePosition();
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public LiveData<Integer> getAttempts() {
        return attempts;
    }

    public void increaseAttempts(){
        Integer i = attempts.getValue();
        i++;
        attempts.setValue(i);
    }

    public void increaseScore(){
        Integer i = score.getValue();
        i++;
        score.setValue(i);
    }


    public boolean checkForSuccess(int imageIndex) {
        increaseAttempts();
        boolean success = false;
        if (winImageIndex == imageIndex){
            increaseScore();
            success = true;
        }
        generateWinImagePosition();
        return success;
    }

    public void resetGame() {
        score.setValue(0);
        attempts.setValue(0);
    }
}
