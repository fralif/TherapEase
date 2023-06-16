package io.edgeperformance.edge.EveningRoutine.Models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.edgeperformance.edge.EveningRoutine.ReflectionActivityE;
import io.edgeperformance.edge.MorningRoutine.ActivationJournalActivityM;
import io.edgeperformance.edge.R;

public class JournalAdapterE extends FirestorePagingAdapter<JournalE, JournalAdapterE.Holder> {

    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public JournalAdapterE(@NonNull FirestorePagingOptions<JournalE> options, SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull final Holder holder, int position, @NonNull JournalE model) {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        String id = model.getId();

        holder.Question.setText(model.getQuestion());

        db.collection("Questions").document("Evening")
                .collection("Reflection Journal").document(id).collection("Answer").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    String answer = documentSnapshot.getString("answer");
                    holder.Answer.setText(answer);
                    holder.Layout.setVisibility(View.VISIBLE);

                }
            }
        });

        holder.Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.editText.getText().toString();
                String id = model.getId();

                DocumentReference document_reference = db.collection("Questions").document("Evening")
                        .collection("Reflection Journal").document(id).collection("Answer").document(id);

                if (!text.isEmpty()){

                    Map<String, Object> userMap = new HashMap<>();

                    userMap.put("answer", text);
                    userMap.put("timestamp", FieldValue.serverTimestamp());
                    document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mContext, "Adding...", Toast.LENGTH_SHORT).show();
                            holder.Answer.setText(text);
                            holder.editText.setText("");
                            holder.Layout.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(mContext, "You must enter answer!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = model.getId();

                DocumentReference document_reference = db.collection("Questions").document("Evening")
                        .collection("Reflection Journal").document(id).collection("Answer").document(id);

                document_reference.delete();
                Intent intent = new Intent(mContext, ReflectionActivityE.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
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
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_layout,
                parent, false);

        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView Question, Answer;
        EditText editText;
        ImageView Save, Delete;
        ConstraintLayout Layout;


        public Holder(View itemView) {
            super(itemView);
            Question = itemView.findViewById(R.id.question);
            Answer = itemView.findViewById(R.id.answer);
            editText = itemView.findViewById(R.id.editText);
            Save = itemView.findViewById(R.id.save);
            Delete = itemView.findViewById(R.id.delete);
            Layout = itemView.findViewById(R.id.answer_layout);

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
