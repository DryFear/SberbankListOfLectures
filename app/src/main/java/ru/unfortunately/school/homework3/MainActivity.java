package ru.unfortunately.school.homework3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.homework3.models.Lecture;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int LECTURES_PER_WEEK = 3;
    private static final int POSITION_ALL = 0;

    private LearningProgramProvider mLearningProgramProvider =
            new LearningProgramProvider();

    private Spinner mLectorsSpinner;
    private Spinner mWeekSortSpinner;

    private LearningProgramAdapter mLearningProgramAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initSpinners();
    }

    private void initSpinners() {
        mLectorsSpinner = findViewById(R.id.lectors_spinner);
        final List<String> lectors = mLearningProgramProvider.provideLectors();
        Collections.sort(lectors);
        lectors.add(POSITION_ALL, getString(R.string.all));
        mLectorsSpinner.setAdapter(new LearningNamesSpinnerAdapter(lectors));

        mLectorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == POSITION_ALL){
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.provideLectures());
                }
                else{
                    Log.d(TAG, "onItemSelected: " + lectors.size());
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.provideLecturesByLector(lectors.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mWeekSortSpinner = findViewById(R.id.week_spinner);
        final List<String> typesOfSortByWeek = new ArrayList<>();
        typesOfSortByWeek.add(LearningProgramAdapter.NOT_SORT_BY_WEEK, getString(R.string.dont_show_by_weeks));
        typesOfSortByWeek.add(LearningProgramAdapter.SORT_BY_WEEK, getString(R.string.show_by_weeks));
        mWeekSortSpinner.setAdapter(new LearningWeeksSoinnerAdapter(typesOfSortByWeek));
        mWeekSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLearningProgramAdapter.setTypeOfSortByWeek(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mLearningProgramAdapter = new LearningProgramAdapter();
        List<Lecture> allLectures = mLearningProgramProvider.provideLectures();
        mLearningProgramAdapter.setLectures(allLectures);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mLearningProgramAdapter);
        Lecture currentLecture = mLearningProgramProvider.getNextLecture(new Date());
        recyclerView.scrollToPosition(allLectures.indexOf(currentLecture));
    }
}
