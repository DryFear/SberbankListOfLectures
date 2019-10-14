package ru.unfortunately.school.homework3;

import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int POSITION_ALL = 0;
    private static final int SORT_BY_WEEK = 0;
    private static final int NOT_SORT_BY_WEEK = 1;
    private LearningProgramProvider mLearningProgramProvider =
            new LearningProgramProvider();

    private Spinner mLectorsSpinner;
    private Spinner mWeekSortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initSpinners();

    }

    private void initSpinners() {
        mLectorsSpinner = findViewById(R.id.lectors_spinner);
        List<String> lectors = mLearningProgramProvider.provideLectors();
        Collections.sort(lectors);
        lectors.add(POSITION_ALL, getString(R.string.all));
        mLectorsSpinner.setAdapter(new LearningSpinnerAdapter(lectors));

        mWeekSortSpinner = findViewById(R.id.week_spinner);
        List<String> typesOfSortByWeek = new ArrayList<>();
        typesOfSortByWeek.add(SORT_BY_WEEK, getString(R.string.show_by_weeks));
        typesOfSortByWeek.add(NOT_SORT_BY_WEEK, getString(R.string.dont_show_by_weeks));
        //mWeekSortSpinner.setAdapter();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LearningProgramAdapter adapter = new LearningProgramAdapter();
        adapter.setmLectures(mLearningProgramProvider.provideLectures());
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //decoration.setDrawable(getResources().getDrawable(R.drawable.rect));

        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }
}
