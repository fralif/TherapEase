package io.edgeperformance.edge.MorningRoutine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import io.edgeperformance.edge.R;

public class IntentionDetailsActivityM extends AppCompatActivity {

    ImageView back;
    String Value;
    TextView value, quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intention_details);

        back = findViewById(R.id.back);
        value = findViewById(R.id.value);
        quote = findViewById(R.id.quote);

        final Intent intent = getIntent();
        Value = intent.getStringExtra(IntentionActivityM.EXTRA_VALUE);

        value.setText(Value);


        String[] texts = new String[] {getString(R.string.quote1),getString(R.string.quote2),getString(R.string.quote3),
                getString(R.string.quote4),getString(R.string.quote5),getString(R.string.quote6),getString(R.string.quote7),
                getString(R.string.quote8),getString(R.string.quote9),getString(R.string.quote10),getString(R.string.quote11),
                getString(R.string.quote12),getString(R.string.quote13),getString(R.string.quote14),getString(R.string.quote15),
                getString(R.string.quote16),getString(R.string.quote17),getString(R.string.quote18),getString(R.string.quote19),
                getString(R.string.quote20),};



        Random random = new Random();
        quote.setText(texts[random.nextInt(3)]);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}