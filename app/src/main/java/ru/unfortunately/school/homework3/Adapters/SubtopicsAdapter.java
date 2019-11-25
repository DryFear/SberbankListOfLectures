package ru.unfortunately.school.homework3.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.homework3.R;

public class SubtopicsAdapter extends RecyclerView.Adapter<SubtopicsAdapter.Holder> {


    private List<String> mSubTopics;


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtopic, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBindView(mSubTopics.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubTopics.size();
    }

    public List<String> getSubTopics() {
        return mSubTopics;
    }

    public void setSubTopics(List<String> subTopics) {
        mSubTopics = subTopics;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_subtopic);
        }

        private void onBindView(String topic){
            mTextView.setText(topic);
        }
    }
}
