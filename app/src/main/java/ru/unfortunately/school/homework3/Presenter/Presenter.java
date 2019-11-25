package ru.unfortunately.school.homework3.Presenter;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import ru.unfortunately.school.homework3.Adapters.LearningNamesSpinnerAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningProgramAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningWeeksSpinnerAdapter;
import ru.unfortunately.school.homework3.LearningProgramProvider;
import ru.unfortunately.school.homework3.R;
import ru.unfortunately.school.homework3.View.MainFragment;
import ru.unfortunately.school.homework3.models.Lecture;

public class Presenter {

    private static final int POSITION_ALL = 0;

    private LearningProgramProvider mLearningProgramProvider;
    private LearningProgramAdapter mLearningProgramAdapter;
    private WeakReference<MainFragment> mMainFragmentRef;

    public Presenter(MainFragment fragment){
        mMainFragmentRef = new WeakReference<>(fragment);
        mLearningProgramAdapter = new LearningProgramAdapter();
        mLearningProgramProvider = new LearningProgramProvider();

        mLearningProgramAdapter.setOnClickListener(fragment);

        LoadLecturesTask task = new LoadLecturesTask(this);
        task.execute();
    }

    private void showErrorMessage(String message){
        MainFragment fragment = mMainFragmentRef.get();
        if(fragment == null) return;
        fragment.makeToast(message);
    }

    private void initSpinners(){
        setupLectorSpinner();
        setupWeekSpinner();
    }

    private void setupWeekSpinner(){
        MainFragment fragment = mMainFragmentRef.get();
        if(fragment == null) return;
        final List<String> typesOfSortByWeek = new ArrayList<>();
        typesOfSortByWeek.add(LearningProgramAdapter.NOT_SORT_BY_WEEK, fragment.getString(R.string.dont_show_by_weeks));
        typesOfSortByWeek.add(LearningProgramAdapter.SORT_BY_WEEK, fragment.getString(R.string.show_by_weeks));
        LearningWeeksSpinnerAdapter adapter = new LearningWeeksSpinnerAdapter(typesOfSortByWeek);
        OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLearningProgramAdapter.setTypeOfSortByWeek(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        fragment.setupWeeksSpinner(adapter, listener);
    }

    private void setupLectorSpinner() {
        final List<String> lectors = mLearningProgramProvider.provideLectors();
        Collections.sort(lectors);
        Collections.sort(lectors);
        LearningNamesSpinnerAdapter adapter = new LearningNamesSpinnerAdapter(lectors);
        OnItemSelectedListener lectorListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == POSITION_ALL){
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.provideLectures());
                }
                else{
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.provideLecturesByLector(lectors.get(position)));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        MainFragment fragment = mMainFragmentRef.get();
        if(fragment != null){
            fragment.setupLectorsSpinner(adapter, lectorListener);
        }
    }

    private void initRecyclerView(List<Lecture> lectures){
        MainFragment fragment = mMainFragmentRef.get();
        if(fragment == null) return;
        Lecture currentLecture = mLearningProgramProvider.getNextLecture(new Date());
        mLearningProgramAdapter.setLectures(lectures);
        fragment.initRecyclerView(mLearningProgramAdapter, lectures.indexOf(currentLecture));
    }

    private static class LoadLecturesTask extends AsyncTask<Void, Void, List<Lecture>> {

        private final WeakReference<Presenter> mFragmentRef;
        private final LearningProgramProvider mProvider;

        private LoadLecturesTask(@NonNull Presenter presenter){
            mFragmentRef = new WeakReference<>(presenter);
            mProvider = presenter.mLearningProgramProvider;
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return new ArrayList<>(mProvider.downloadLectureFromInternet());
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            Presenter presenter = mFragmentRef.get();
            if(presenter == null){
                return;
            }
            if(lectures == null){
                presenter.showErrorMessage("Не удалось загрузить данные");
            }else{
                presenter.initSpinners();
                presenter.initRecyclerView(lectures);
            }
        }
    }
}
