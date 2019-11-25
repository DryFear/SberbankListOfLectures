package ru.unfortunately.school.homework3.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.homework3.R;
import ru.unfortunately.school.homework3.Adapters.SubtopicsAdapter;
import ru.unfortunately.school.homework3.models.Lecture;

public class DetailsFragment extends Fragment {

    public static final String ARG_LECTURE = "lecture";

    public static DetailsFragment newInstance(Lecture lecture) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_LECTURE, lecture);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subtopics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Lecture lecture = getArguments().getParcelable(ARG_LECTURE);
            ((TextView)view.findViewById(R.id.text_view_lector)).setText(lecture.getLector());
            ((TextView)view.findViewById(R.id.text_view_date)).setText(lecture.getDate());
            ((TextView)view.findViewById(R.id.title)).setText(lecture.getTheme());
            RecyclerView recyclerView = view.findViewById(R.id.recycler_subtopics);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            SubtopicsAdapter adapter = new SubtopicsAdapter();
            adapter.setSubTopics(lecture.getSubTopics());
            recyclerView.setAdapter(adapter);
        }
    }
}
