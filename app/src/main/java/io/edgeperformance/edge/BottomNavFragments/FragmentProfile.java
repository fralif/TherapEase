package io.edgeperformance.edge.BottomNavFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import io.edgeperformance.edge.Activities.GoalsActivity;
import io.edgeperformance.edge.Activities.NutritionActivity;
import io.edgeperformance.edge.Activities.SleepTrackerActivity;
import io.edgeperformance.edge.Activities.ValuesActivity;
import io.edgeperformance.edge.R;


public class FragmentProfile extends Fragment {
    CardView values, goals,nut,sleep;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        values = view.findViewById(R.id.values);
        goals = view.findViewById(R.id.goals);
        nut = view.findViewById(R.id.nut);
        sleep = view.findViewById(R.id.sleep);

        values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent values = new Intent(getActivity(), ValuesActivity.class);
                startActivity(values);
            }
        });
        nut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent values = new Intent(getActivity(), NutritionActivity.class);
                startActivity(values);
            }
        });
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent values1 = new Intent(getActivity(), SleepTrackerActivity.class);
                startActivity(values1);
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goals = new Intent(getActivity(), GoalsActivity.class);
                startActivity(goals);
            }
        });

        return view;
    }

}
