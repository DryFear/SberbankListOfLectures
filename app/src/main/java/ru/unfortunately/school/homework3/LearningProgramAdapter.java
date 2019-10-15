package ru.unfortunately.school.homework3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import ru.unfortunately.school.homework3.models.Lecture;



public class LearningProgramAdapter extends Adapter<LearningProgramAdapter.LectureHolder> {

    private List<Lecture> mLectures;

    private static final int LECTURES_PER_WEEK = 3;

    public static final int SORT_BY_WEEK = 1;
    public static final int NOT_SORT_BY_WEEK = 0;

    private int mTypeOfSortByWeek;

    @NonNull
    @Override
    public LectureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
        return new LectureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureHolder holder, int position) {
        Lecture lecture = mLectures.get(position);
        holder.mNumber.setText(lecture.getmNumber());
        holder.mDate.setText(lecture.getmDate());
        holder.mTheme.setText(lecture.getmTheme());
        holder.mLector.setText(lecture.getmLector());
    }

    public void setLectures(List<Lecture> lectures) {
        if(lectures == null ){
            mLectures = new ArrayList<>();
        }
        else{
            switch (mTypeOfSortByWeek){
                case SORT_BY_WEEK:
                    sortByWeek(lectures);
                    break;
                case NOT_SORT_BY_WEEK:
                default:
                    dontSortByWeek(lectures);
            }
        }
        notifyDataSetChanged();
    }

    private void dontSortByWeek(List<Lecture> lectures) {
        List<Lecture> result = new ArrayList<>();
        for (Lecture lecture : lectures) {
            if(!lecture.getmNumber().equals("")){
                result.add(lecture);
            }
        }
        mLectures = new ArrayList<>(result);
    }

    private void sortByWeek(List<Lecture> lectures) {
        List<Lecture> result = new ArrayList<>();
        int iterWeek = -1;
        for (Lecture lecture : lectures) {
            int noLecture = Integer.parseInt(lecture.getmNumber())-1;
            if(noLecture/LECTURES_PER_WEEK > iterWeek){
                iterWeek = noLecture/LECTURES_PER_WEEK;
                result.add(LearningProgramProvider.provideWeekAsLecture(iterWeek+1));
            }
            result.add(lecture);
        }
        mLectures = new ArrayList<>(result);
    }

    public void setTypeOfSortByWeek(int type){
        mTypeOfSortByWeek = type;
        setLectures(mLectures);
    }

    @Override
    public int getItemCount() {
        return mLectures == null ? 0 : mLectures.size();
    }

    static class LectureHolder extends RecyclerView.ViewHolder{

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mLector;

        public LectureHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.date);
            mTheme = itemView.findViewById(R.id.theme);
            mLector = itemView.findViewById(R.id.lector);
        }
    }
}
