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

import io.edgeperformance.edge.MorningRoutine.ActivationJournalActivityM;
import io.edgeperformance.edge.MorningRoutine.BreathWorkActivityM;
import io.edgeperformance.edge.MorningRoutine.CheckInActivityM;
import io.edgeperformance.edge.MorningRoutine.IntentionActivityM;
import io.edgeperformance.edge.MorningRoutine.SummaryActivityM;
import io.edgeperformance.edge.R;

public class FragmentMorningRoutine extends Fragment {

    CardView checkIn, activationJournal, breathWork, intention, summary;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_morning_routine, container, false);

        checkIn = view.findViewById(R.id.check_in);
        activationJournal = view.findViewById(R.id.activation_journal);
        breathWork = view.findViewById(R.id.breath_work);
        intention = view.findViewById(R.id.intention);
        summary = view.findViewById(R.id.summary);

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckInActivityM.class);
                startActivity(intent);
            }
        });

        activationJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivationJournalActivityM.class);
                startActivity(intent);
            }
        });

        breathWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BreathWorkActivityM.class);
                startActivity(intent);
            }
        });

        intention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IntentionActivityM.class);
                startActivity(intent);
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SummaryActivityM.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
