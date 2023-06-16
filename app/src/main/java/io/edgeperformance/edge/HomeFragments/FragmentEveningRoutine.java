package io.edgeperformance.edge.HomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import io.edgeperformance.edge.EveningRoutine.BreathworkActivityE;
import io.edgeperformance.edge.EveningRoutine.CheckInActivityE;
import io.edgeperformance.edge.EveningRoutine.OwnershipActivityE;
import io.edgeperformance.edge.EveningRoutine.ReflectionActivityE;
import io.edgeperformance.edge.EveningRoutine.SummaryActivityE;
import io.edgeperformance.edge.EveningRoutine.ValuesActivityE;
import io.edgeperformance.edge.MorningRoutine.CheckInActivityM;
import io.edgeperformance.edge.R;


public class FragmentEveningRoutine extends Fragment {

    CardView checkIn, reflectionJournal,values, ownership, breathWork, summary;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evening_routine, container, false);

        checkIn = view.findViewById(R.id.check_in_e);
        reflectionJournal = view.findViewById(R.id.reflection_journal_e);
        values = view.findViewById(R.id.values_e);
        ownership = view.findViewById(R.id.ownership_e);
        breathWork = view.findViewById(R.id.breath_work_e);
        summary = view.findViewById(R.id.summary_e);

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckInActivityE.class);
                startActivity(intent);
            }
        });

        reflectionJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReflectionActivityE.class);
                startActivity(intent);
            }
        });

        values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ValuesActivityE.class);
                startActivity(intent);
            }
        });

        ownership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OwnershipActivityE.class);
                startActivity(intent);
            }
        });

        breathWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BreathworkActivityE.class);
                startActivity(intent);
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SummaryActivityE.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
