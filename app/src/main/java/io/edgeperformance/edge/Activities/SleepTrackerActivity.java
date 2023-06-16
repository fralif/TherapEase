package io.edgeperformance.edge.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import io.edgeperformance.edge.R;

public class SleepTrackerActivity extends AppCompatActivity {
    // declare variables
    private EditText ageInput;
    private RadioGroup activityLevel;
    private RadioButton activitySelected;
    private TextView result;
    private ImageView back;
    private Button calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        // initialize variables
        ageInput = findViewById(R.id.edit_age);
        activityLevel = findViewById(R.id.radio_group);
        result = findViewById(R.id.text_result);
        calculateButton = findViewById(R.id.button_calculate);
        back = findViewById(R.id.back);

        // set onClickListener for calculateButton
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSleep();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           finish();
            }
        });
    }

    private void calculateSleep() {
        // get user input
        int age = Integer.parseInt(ageInput.getText().toString());
        int activityLevelId = activityLevel.getCheckedRadioButtonId();
        activitySelected = findViewById(activityLevelId);

        // calculate recommended sleep based on user input
        int recommendedSleep;
        if (age >= 18 && age <= 64) {
            switch (activitySelected.getText().toString()) {
                case "Sedentary":
                    recommendedSleep = 7;
                    break;
                case "Moderately Active":
                    recommendedSleep = 7;
                    break;
                case "Vigorously Active":
                    recommendedSleep = 7;
                    break;
                default:
                    recommendedSleep = 0;
                    break;
            }
        } else if (age >= 65) {
            switch (activitySelected.getText().toString()) {
                case "Sedentary":
                    recommendedSleep = 7;
                    break;
                case "Moderately Active":
                    recommendedSleep = 7;
                    break;
                case "Vigorously Active":
                    recommendedSleep = 7;
                    break;
                default:
                    recommendedSleep = 0;
                    break;
            }
        } else {
            recommendedSleep = 0;
        }

        // display recommended sleep to user
        result.setText("Recommended sleep: " + recommendedSleep + " hours");
    }
}