package io.edgeperformance.edge.EveningRoutine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.edgeperformance.edge.R;

public class OwnershipActivityE extends AppCompatActivity {

    private EditText ageInput, restingHrInput;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button calculateButton;
    private TextView targetHrLabel;
    private  ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownership_e);
        ageInput = findViewById(R.id.age_input);
        restingHrInput = findViewById(R.id.resting_hr_input);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        maleRadioButton = findViewById(R.id.male_radio_button);
        femaleRadioButton = findViewById(R.id.female_radio_button);
        calculateButton = findViewById(R.id.calculate_button);
        targetHrLabel = findViewById(R.id.target_hr_label);
        back = findViewById(R.id.back);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTargetHeartRate();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          finish();
            }
        });
    }

    private void calculateTargetHeartRate() {
        int age = Integer.parseInt(ageInput.getText().toString());
        int restingHr = Integer.parseInt(restingHrInput.getText().toString());
        int maxHr = 220 - age;

        double targetHrLower;
        double targetHrUpper;

        if (maleRadioButton.isChecked()) {
            targetHrLower = ((maxHr - restingHr) * 0.6) + restingHr;
            targetHrUpper = ((maxHr - restingHr) * 0.8) + restingHr;
        } else {
            targetHrLower = ((maxHr - restingHr) * 0.5) + restingHr;
            targetHrUpper = ((maxHr - restingHr) * 0.7) + restingHr;
        }

        String targetHrRange = String.format("Target Heart Rate Range: %.0f - %.0f BPM", targetHrLower, targetHrUpper);
        targetHrLabel.setText(targetHrRange);

}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}