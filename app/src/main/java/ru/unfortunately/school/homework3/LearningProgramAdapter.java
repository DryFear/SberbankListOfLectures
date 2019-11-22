package ru.unfortunately.school.homework3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import ru.unfortunately.school.homework3.models.Lecture;



public class LearningProgramAdapter extends Adapter<LearningProgramAdapter.BaseHolder> {

    private List<Object> mList;

    private static final int LECTURES_PER_WEEK = 3;

    public static final int SORT_BY_WEEK = 1;
    public static final int NOT_SORT_BY_WEEK = 0;

    private static final int VIEW_TYPE_LECTURE = 0;
    private static final int VIEW_TYPE_WEEK = 1;

    private int mTypeOfSortByWeek;

    private OnItemClickListener mOnClickListener;

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_LECTURE:
                View lectureView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_lecture, parent, false);
                return new LectureHolder(lectureView);
            case VIEW_TYPE_WEEK:
                View weekView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_week, parent, false);
                return new WeekHolder(weekView);
            default:
                throw new RuntimeException("Illegal item type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mList.get(position);
        if(item instanceof Lecture){
            return VIEW_TYPE_LECTURE;
        }else if (item instanceof String){
            return VIEW_TYPE_WEEK;
        }else throw new RuntimeException("Illegal item in list");
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        Object item = mList.get(position);
        switch (getItemViewType(position)){
            case VIEW_TYPE_LECTURE:
                ((LectureHolder)holder).bindView((Lecture)item);
                break;
            case VIEW_TYPE_WEEK:
                ((WeekHolder)holder).bindView((String) item);
                break;
            default:
                throw new RuntimeException("Illegal item in list");
        }
    }



    public void setLectures(List<Lecture> lectures) {
        if(lectures == null ){
            mList = new ArrayList<>();
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
//        List<Lecture> result = new ArrayList<>();
//        for (Lecture lecture : lectures) {
//            if(!lecture.getNumber().equals("")){
//                result.add(lecture);
//
//        }
        mList = new ArrayList<Object>(lectures);
    }

    private void sortByWeek(List<Lecture> lectures) {
        List<Object> result = new ArrayList<>();
        int iterWeek = -1;
        for (Lecture lecture : lectures) {
            int noLecture = Integer.parseInt(lecture.getNumber())-1;
            if(noLecture/LECTURES_PER_WEEK > iterWeek){
                iterWeek = noLecture/LECTURES_PER_WEEK;
                result.add("Неделя " + (iterWeek+1));
            }
            result.add(lecture);
        }
        mList = new ArrayList<>(result);
    }

    public void setTypeOfSortByWeek(int type){
        mTypeOfSortByWeek = type;
        List<Lecture> lectures = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if(getItemViewType(i) == VIEW_TYPE_LECTURE){
                lectures.add((Lecture) mList.get(i));
            }
        }
        setLectures(lectures);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public OnItemClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    static abstract class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class LectureHolder extends BaseHolder {

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

        private void bindView(final Lecture lecture){
            mNumber.setText(lecture.getNumber());
            mDate.setText(lecture.getDate());
            mLector.setText(lecture.getLector());
            mTheme.setText(lecture.getTheme());
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(lecture);
                }
            });
        }
    }



    static class WeekHolder extends BaseHolder{

        private final TextView mTextView;

        public WeekHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view_week);
        }

        private void bindView(String week){
            mTextView.setText(week);
        }

    }
}
