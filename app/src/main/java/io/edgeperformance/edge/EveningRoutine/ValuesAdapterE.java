package io.edgeperformance.edge.EveningRoutine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.edgeperformance.edge.R;

class ValuesAdapterE extends RecyclerView.Adapter<ValuesAdapterE.ViewHolder> {

    private List<String> listData;
    private Context mContext;
    int Value;

    // RecyclerView recyclerView;
    public ValuesAdapterE(List<String> listData) {
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.values_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
//        String id = model.getId();

        Value = (int) holder.Slider.getValue();

        holder.Question.setText(listData.get(position));

        holder.Slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                int v = (int) value;
                return String.valueOf(v);
            }
        });

        db.collection("Questions").document("Evening")
                .collection("Values").document(listData.get(position)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            long answer = documentSnapshot.getLong("answer");
                            holder.Slider.setValue(answer);

                        }
                    }
                });

        holder.Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Value = (int) holder.Slider.getValue();

                DocumentReference document_reference = db.collection("Questions").document("Evening")
                        .collection("Values").document(listData.get(position));

                Map<String, Object> userMap = new HashMap<>();

                userMap.put("answer", Value);
                document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(v.getContext(), "Saving...", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Question;
        Slider Slider;
        CardView Save;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Question = itemView.findViewById(R.id.question);
            this.Slider = itemView.findViewById(R.id.continuousSlider);
            this.Save = itemView.findViewById(R.id.save);
        }
    }
}