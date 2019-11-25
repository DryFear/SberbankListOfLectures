package ru.unfortunately.school.homework3.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.homework3.Adapters.LearningNamesSpinnerAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningProgramAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningWeeksSpinnerAdapter;
import ru.unfortunately.school.homework3.Presenter.Presenter;
import ru.unfortunately.school.homework3.R;
import ru.unfortunately.school.homework3.models.Lecture;

public class MainFragment extends Fragment implements OnItemClickListener{

    private static final String TAG = "MainActivity";

    private Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private Spinner mLectorsSpinner;
    private Spinner mWeekSortSpinner;



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
        mPresenter = new Presenter(this);
    }

    public void setupLectorsSpinner(LearningNamesSpinnerAdapter adapter, AdapterView.OnItemSelectedListener listener) {
        mLectorsSpinner.setAdapter(adapter);
        mLectorsSpinner.setOnItemSelectedListener(listener);
    }

    public void setupWeeksSpinner(LearningWeeksSpinnerAdapter adapter, AdapterView.OnItemSelectedListener listener){
        mWeekSortSpinner.setAdapter(adapter);
        mWeekSortSpinner.setOnItemSelectedListener(listener);
    }

    public void initRecyclerView(LearningProgramAdapter adapter, int scrollTo) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.scrollToPosition(scrollTo);
    }


    public void setAdapterForRecyclerView(LearningProgramAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void setWeeksSpinnerAdapter(LearningWeeksSpinnerAdapter adapter) {
        mWeekSortSpinner.setAdapter(adapter);
    }

    public void setLectorsSpinnerAdapter(LearningNamesSpinnerAdapter adapter) {
        mLectorsSpinner.setAdapter(adapter);
    }


    @Override
    public void onItemClick(Lecture lecture) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, DetailsFragment.newInstance(lecture))
                .addToBackStack(null)
                .commit();
    }

    public void makeToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
