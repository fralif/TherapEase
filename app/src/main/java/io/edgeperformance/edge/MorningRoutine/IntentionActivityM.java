package io.edgeperformance.edge.MorningRoutine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.edgeperformance.edge.R;

public class IntentionActivityM extends AppCompatActivity {

    ImageView back;
    ChipGroup categories;
    String Category;
    CardView next;

    Chip patience, intensity, courage, curiosity, learning, discomfort, stillness,
            challenge, resilience, growth, self_care, love, responsibility, discipline, difficulty,
            adaptability, detachment, forgiveness, creativity, commitment, spontaneity, flexibility,
            letting_go, change, mindfulness, generosity, consistency;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    public static final String EXTRA_VALUE = "io.edgeperformance.edge.EXTRA_VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intention_m);

        back = findViewById(R.id.back);
        next = findViewById(R.id.next);

        categories = findViewById(R.id.categories);

        patience = findViewById(R.id.patience);
        intensity = findViewById(R.id.intensity);
        courage = findViewById(R.id.courage);
        curiosity = findViewById(R.id.curiosity);
        learning = findViewById(R.id.learning);
        discomfort = findViewById(R.id.discomfort);
        stillness = findViewById(R.id.stillness);
        challenge = findViewById(R.id.challenge);
        resilience = findViewById(R.id.resilience);
        growth = findViewById(R.id.growth);
        self_care = findViewById(R.id.self_care);
        love = findViewById(R.id.love);
        responsibility = findViewById(R.id.responsibility);
        discipline = findViewById(R.id.discipline);
        difficulty = findViewById(R.id.difficulty);
        adaptability = findViewById(R.id.adaptability);
        detachment = findViewById(R.id.detachment);
        forgiveness = findViewById(R.id.forgiveness);
        creativity = findViewById(R.id.creativity);
        commitment = findViewById(R.id.commitment);
        spontaneity = findViewById(R.id.spontaneity);
        flexibility = findViewById(R.id.flexibility);
        letting_go = findViewById(R.id.letting_go);
        change = findViewById(R.id.change);
        mindfulness = findViewById(R.id.mindfulness);
        generosity = findViewById(R.id.generosity);
        consistency = findViewById(R.id.consistency);


        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        document_reference = db.collection("Questions").document("Morning")
                .collection("Intention").document(userID);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Chip chip = findViewById(buttonView.getId());
                if (isChecked) {
                    //Get all checked chips in the group
                    List<Integer> ids = categories.getCheckedChipIds();
                    Category = (String) chip.getChipText();
                    if (ids.size() > 1) {
                        chip.setChecked(false);  //force to unchecked the chip
                    }
                }
            }
        };

        patience.setOnCheckedChangeListener(checkedChangeListener);
        intensity.setOnCheckedChangeListener(checkedChangeListener);
        courage.setOnCheckedChangeListener(checkedChangeListener);
        curiosity.setOnCheckedChangeListener(checkedChangeListener);
        learning.setOnCheckedChangeListener(checkedChangeListener);
        discomfort.setOnCheckedChangeListener(checkedChangeListener);
        stillness.setOnCheckedChangeListener(checkedChangeListener);
        challenge.setOnCheckedChangeListener(checkedChangeListener);
        resilience.setOnCheckedChangeListener(checkedChangeListener);
        growth.setOnCheckedChangeListener(checkedChangeListener);
        self_care.setOnCheckedChangeListener(checkedChangeListener);
        love.setOnCheckedChangeListener(checkedChangeListener);
        responsibility.setOnCheckedChangeListener(checkedChangeListener);
        discipline.setOnCheckedChangeListener(checkedChangeListener);
        difficulty.setOnCheckedChangeListener(checkedChangeListener);
        adaptability.setOnCheckedChangeListener(checkedChangeListener);
        detachment.setOnCheckedChangeListener(checkedChangeListener);
        forgiveness.setOnCheckedChangeListener(checkedChangeListener);
        creativity.setOnCheckedChangeListener(checkedChangeListener);
        commitment.setOnCheckedChangeListener(checkedChangeListener);
        spontaneity.setOnCheckedChangeListener(checkedChangeListener);
        flexibility.setOnCheckedChangeListener(checkedChangeListener);
        letting_go.setOnCheckedChangeListener(checkedChangeListener);
        change.setOnCheckedChangeListener(checkedChangeListener);
        mindfulness.setOnCheckedChangeListener(checkedChangeListener);
        generosity.setOnCheckedChangeListener(checkedChangeListener);
        consistency.setOnCheckedChangeListener(checkedChangeListener);

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                for (int i=0; i<categories.getChildCount();i++){
//                    Chip chip = (Chip)categories.getChildAt(i);
//                    if (chip.isChecked()){
//                        String chipText = String.valueOf(chip.getText());
//                        list.add(chipText);
//                    }
//                }
//                if (!list.isEmpty()) {
//                    Map<String, Object> userMap = new HashMap<>();
//
//                    userMap.put("values", list);
//                    userMap.put("user_id", userID);
//                    userMap.put("timestamp", FieldValue.serverTimestamp());
//                    document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(getApplicationContext(), "Adding..", Toast.LENGTH_SHORT).show();
//                            Intent saved = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(saved);
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(), "You must select a value!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Category.isEmpty()){
                    setDetails();
                }else{
                    Toast.makeText(getApplicationContext(), "You need to select a value!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setDetails() {

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("value", Category);
        userMap.put("user_id", userID);
        userMap.put("timestamp", FieldValue.serverTimestamp());
        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent saved = new Intent(getApplicationContext(), IntentionDetailsActivityM.class);
                saved.putExtra(EXTRA_VALUE, Category);
                startActivity(saved);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getData() {

        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String Values = documentSnapshot.getString("value");

                    switch (Values) {
                        case "Patience":
                            patience.setChecked(true);
                            break;
                        case "Intensity":
                            intensity.setChecked(true);
                            break;
                        case "Courage":
                            courage.setChecked(true);
                            break;
                        case "Curiosity":
                            curiosity.setChecked(true);
                            break;
                        case "Learning":
                            learning.setChecked(true);
                            break;
                        case "Discomfort":
                            discomfort.setChecked(true);
                            break;
                        case "Stillness":
                            stillness.setChecked(true);
                            break;
                        case "Challenge":
                            challenge.setChecked(true);
                            break;
                        case "Resilience":
                            resilience.setChecked(true);
                            break;
                        case "Growth":
                            growth.setChecked(true);
                            break;
                        case "Self-care":
                            self_care.setChecked(true);
                            break;
                        case "Love":
                            love.setChecked(true);
                            break;
                        case "Responsibility":
                            responsibility.setChecked(true);
                            break;
                        case "Discipline":
                            discipline.setChecked(true);
                            break;
                        case "Difficulty":
                            difficulty.setChecked(true);
                            break;
                        case "Adaptability":
                            adaptability.setChecked(true);
                            break;
                        case "Detachment":
                            detachment.setChecked(true);
                            break;
                        case "Forgiveness":
                            forgiveness.setChecked(true);
                            break;
                        case "Creativity":
                            creativity.setChecked(true);
                            break;
                        case "Commitment":
                            commitment.setChecked(true);
                            break;
                        case "Spontaneity":
                            spontaneity.setChecked(true);
                            break;
                        case "Flexibility":
                            flexibility.setChecked(true);
                            break;
                        case "Letting Go":
                            letting_go.setChecked(true);
                            break;
                        case "Change":
                            change.setChecked(true);
                            break;
                        case "Mindfulness":
                            mindfulness.setChecked(true);
                            break;
                        case "Generosity":
                            generosity.setChecked(true);
                            break;
                        case "Consistency":
                            consistency.setChecked(true);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}