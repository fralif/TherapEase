package io.edgeperformance.edge.EveningRoutine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import io.edgeperformance.edge.EveningRoutine.Models.CheckInAdapterE;
import io.edgeperformance.edge.EveningRoutine.Models.CheckInE;
import io.edgeperformance.edge.Models.RecyclerDecoration;
import io.edgeperformance.edge.R;

public class CheckInActivityE extends AppCompatActivity {

    ImageView back;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db;
    private CollectionReference notification;

    private CheckInAdapterE adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_e);
        back = findViewById(R.id.back);

        back = findViewById(R.id.back);

        recyclerView = findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerView.addItemDecoration(new RecyclerDecoration(topPadding, bottomPadding));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        db = FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        slider.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
////                int v = (int) value;
////                Toast.makeText(getApplicationContext(), String.valueOf(v), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        slider.setLabelFormatter(new LabelFormatter() {
//            @NonNull
//            @Override
//            public String getFormattedValue(float value) {
//                int v = (int) value;
//                return String.valueOf(v);
//            }
//        });

        loadData();
    }

    private void loadData() {

        notification = db.collection("Questions").document("Evening").collection("Checkin");

        Query query = notification.orderBy("question", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<CheckInE> options = new FirestorePagingOptions.Builder<CheckInE>()
                .setQuery(query, config, CheckInE.class)
                .build();

        adapter = new CheckInAdapterE(options, swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}