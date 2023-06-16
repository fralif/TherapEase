package io.edgeperformance.edge.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.edgeperformance.edge.R;

public class BodyFatActivity extends AppCompatActivity {
    private EditText mAgeEditText;
    private EditText mHeightEditText;
    private EditText mWeightEditText;
    private EditText mWaistEditText;
    private EditText mHipEditText;
    private TextView mResultTextView;
    private Button mCalculateButton;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat);
        // Initialize views
        mAgeEditText = findViewById(R.id.age_edit_text);
        mHeightEditText = findViewById(R.id.height_edit_text);
        mWeightEditText = findViewById(R.id.weight_edit_text);
        mWaistEditText = findViewById(R.id.waist_edit_text);
        mHipEditText = findViewById(R.id.hip_edit_text);
        mResultTextView = findViewById(R.id.result_text_view);
        mCalculateButton = findViewById(R.id.calculate_button);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                int age = Integer.parseInt(mAgeEditText.getText().toString());
                float height = Float.parseFloat(mHeightEditText.getText().toString());
                float weight = Float.parseFloat(mWeightEditText.getText().toString());
                float waist = Float.parseFloat(mWaistEditText.getText().toString());
                float hip = Float.parseFloat(mHipEditText.getText().toString());

                // Calculate body fat percentage using the US Navy formula
                float bodyFatPercentage = calculateBodyFatPercentage(age, height, waist, hip);

                // Display the result
                String result = "Body Fat Percentage: " + String.format("%.2f", bodyFatPercentage) + "%";
                mResultTextView.setText(result);
            }
        });
    }

    private float calculateBodyFatPercentage(int age, float height, float waist, float hip) {
        // Calculate lean body mass
        float leanBodyMass = (float) (height * 1.082 - (waist * 4.15) + 94.42);

        // Calculate body fat mass
     //   float bodyFatMass = (float) (weight - leanBodyMass);

        // Calculate body fat percentage using the US Navy formula
        float bodyFatPercentage = (float) ((495 / (1.0324 - 0.19077 * Math.log10(waist - hip) + 0.15456 * Math.log10(height))) - 450);

        return bodyFatPercentage;
    }
}
