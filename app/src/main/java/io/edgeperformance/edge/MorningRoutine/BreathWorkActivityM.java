package io.edgeperformance.edge.MorningRoutine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import io.edgeperformance.edge.Activities.PlayerActivity;
import io.edgeperformance.edge.EveningRoutine.BreathworkActivityE;
import io.edgeperformance.edge.Models.AudioModel;
import io.edgeperformance.edge.R;
import io.edgeperformance.edge.utils.AppConstants;
import io.edgeperformance.edge.utils.AppUtils;

public class BreathWorkActivityM extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView;
    private TextView inhaleText;
    private TextView exhaleText;
    private Button startButton;
    private Button stopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_work_m);
        lottieAnimationView = findViewById(R.id.lottie_animation_view);
        inhaleText = findViewById(R.id.inhale_text);
        exhaleText = findViewById(R.id.exhale_text);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreathingExercise();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBreathingExercise();
            }
        });
    }

    private void startBreathingExercise() {
        lottieAnimationView.setProgress(0f);
        lottieAnimationView.playAnimation();

        inhaleText.setVisibility(View.VISIBLE);
        exhaleText.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);

        lottieAnimationView.addAnimatorListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                inhaleText.setVisibility(View.INVISIBLE);
                exhaleText.setVisibility(View.VISIBLE);
                lottieAnimationView.setProgress(0f);
                lottieAnimationView.playAnimation();
            }
        });
    }

    private void stopBreathingExercise() {
        lottieAnimationView.cancelAnimation();
        inhaleText.setVisibility(View.INVISIBLE);
        exhaleText.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    private static class SimpleAnimatorListener implements Animator.AnimatorListener {
        @Override public void onAnimationStart(Animator animation) {}
        @Override public void onAnimationEnd(Animator animation) {}
        @Override public void onAnimationCancel(Animator animation) {}
        @Override public void onAnimationRepeat(Animator animation) {}
    }
}