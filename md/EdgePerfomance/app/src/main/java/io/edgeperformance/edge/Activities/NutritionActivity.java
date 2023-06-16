package io.edgeperformance.edge.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import io.edgeperformance.edge.R;

public class NutritionActivity extends AppCompatActivity {
    private EditText weightInput, timeInput;
    private RadioButton runningRadio, swimmingRadio, cyclingRadio;
    private TextView resultText;
    private Button calculateButton;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        weightInput = findViewById(R.id.weight_input);
        timeInput = findViewById(R.id.time_input);
         back = findViewById(R.id.back);
  
        runningRadio = findViewById(R.id.running_radio);
        swimmingRadio = findViewById(R.id.swimming_radio);
        cyclingRadio = findViewById(R.id.cycling_radio);

        resultText = findViewById(R.id.result_text);

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCaloriesBurned();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
            }
        });
    }

    private void calculateCaloriesBurned() {
        int weight = Integer.parseInt(weightInput.getText().toString());
        int time = Integer.parseInt(timeInput.getText().toString());

        double caloriesBurned = 0;
        double speed = 0;

        if (runningRadio.isChecked()) {
            speed = 10;
            caloriesBurned = 0.0175 * speed * weight * time;
        } else if (swimmingRadio.isChecked()) {
            speed = 3;
            caloriesBurned = 0.0015 * speed * weight * time;
        } else if (cyclingRadio.isChecked()) {
            speed = 15;
            caloriesBurned = 0.0075 * speed * weight * time;
        }

        String result = String.format("Calories burned: %.2f", caloriesBurned);
        resultText.setText(result);
    }
}
