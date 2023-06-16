package io.edgeperformance.edge.MorningRoutine.Models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.edgeperformance.edge.R;

public class CheckInAdapterM extends FirestorePagingAdapter<CheckInM, CheckInAdapterM.CheckInHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int Value;

    public CheckInAdapterM(@NonNull FirestorePagingOptions<CheckInM> options, SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull final CheckInHolder holder, int position, @NonNull CheckInM model) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        String id = model.getId();

        Value = (int) holder.Slider.getValue();

        holder.Question.setText(model.getQuestion());

        db.collection("Questions").document("Morning")
                .collection("CheckIn Answers").document(id).get()
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
                String id = model.getId();

                DocumentReference document_reference = db.collection("Questions").document("Morning")
                        .collection("CheckIn Answers").document(id);

                Map<String, Object> userMap = new HashMap<>();

                userMap.put("answer", Value);
                document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext, "Saving...", Toast.LENGTH_SHORT).show();

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
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {

            case LOADING_INITIAL:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading Initial data");
                break;
            case LOADING_MORE:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading next page");
                break;
            case FINISHED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "All data loaded");
                break;
            case LOADED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Total data loaded " + getItemCount());
                break;
            case ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Error loading data");
                break;
        }
    }

    @NonNull
    @Override
    public CheckInHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_in_layout,
                parent, false);

        return new CheckInHolder(view);
    }

    class CheckInHolder extends RecyclerView.ViewHolder {

        TextView Question;
        CardView Save;
        Slider Slider;

        public CheckInHolder(View itemView) {
            super(itemView);

            Question = itemView.findViewById(R.id.question);
            Save = itemView.findViewById(R.id.save);
            Slider = itemView.findViewById(R.id.continuousSlider);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
