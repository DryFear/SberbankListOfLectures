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

    public void setmLectures(List<Lecture> mLectures) {
        this.mLectures = mLectures == null ? null :     new ArrayList<>(mLectures);
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
