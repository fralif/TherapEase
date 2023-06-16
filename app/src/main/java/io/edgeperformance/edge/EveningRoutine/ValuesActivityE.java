package io.edgeperformance.edge.EveningRoutine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.edgeperformance.edge.Models.RecyclerDecoration;
import io.edgeperformance.edge.R;

public class ValuesActivityE extends AppCompatActivity {

    ImageView back;
    Slider slider1,slider2,slider3,slider4, slider5;
    RecyclerView recyclerView;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values_e);
        back = findViewById(R.id.back);

        recyclerView = findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerView.addItemDecoration(new RecyclerDecoration(topPadding, bottomPadding));

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadData();

    }

    private void loadData() {
        db.collection("Values").document(userID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                List<String> values = (List<String>) document.get("values");


                ValuesAdapterE adapter = new ValuesAdapterE(values);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}