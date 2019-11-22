package ru.unfortunately.school.homework3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.homework3.models.Lecture;

public class MainFragment extends Fragment {

    private static final String TAG = "MainActivity";


    private static final int LECTURES_PER_WEEK = 3;
    private static final int POSITION_ALL = 0;

    private LearningProgramProvider mLearningProgramProvider =
            new LearningProgramProvider();

    private RecyclerView mRecyclerView;
    private Spinner mLectorsSpinner;
    private Spinner mWeekSortSpinner;

    private LearningProgramAdapter mLearningProgramAdapter;

    private MainFragment(){
        setRetainInstance(true);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLectorsSpinner = view.findViewById(R.id.lectors_spinner);
        mWeekSortSpinner = view.findViewById(R.id.week_spinner);
        mRecyclerView = view.findViewById(R.id.recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null){
            new LoadLecturesTask(this).execute();
        }
    }

    private void initSpinners() {
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
        final List<String> typesOfSortByWeek = new ArrayList<>();
        typesOfSortByWeek.add(LearningProgramAdapter.NOT_SORT_BY_WEEK, getString(R.string.dont_show_by_weeks));
        typesOfSortByWeek.add(LearningProgramAdapter.SORT_BY_WEEK, getString(R.string.show_by_weeks));
        mWeekSortSpinner.setAdapter(new LearningWeeksSpinnerAdapter(typesOfSortByWeek));
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

    private void initRecyclerView(List<Lecture> lectures) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mLearningProgramAdapter = new LearningProgramAdapter();
        mLearningProgramAdapter.setLectures(lectures);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mLearningProgramAdapter);
        Lecture currentLecture = mLearningProgramProvider.getNextLecture(new Date());
        mRecyclerView.scrollToPosition(lectures.indexOf(currentLecture));
    }

    private static class LoadLecturesTask extends AsyncTask<Void, Void, List<Lecture>>{

        private final WeakReference<MainFragment> mFragmentRef;
        private final LearningProgramProvider mProvider;

        private LoadLecturesTask(@NonNull MainFragment fragment){
            mFragmentRef = new WeakReference<>(fragment);
            mProvider = fragment.mLearningProgramProvider;
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return new ArrayList<>(mProvider.downloadLectureFromInternet());
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            MainFragment fragment = mFragmentRef.get();
            if(fragment == null){
                return;
            }
            if(lectures == null){
                Toast.makeText(fragment.getContext(), "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
            }else{
                fragment.initSpinners();
                fragment.initRecyclerView(lectures);
            }
        }
    }
}
