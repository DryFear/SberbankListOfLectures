package ru.unfortunately.school.homework3;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class LearningSpinnerAdapter extends BaseAdapter {

    private final List<String> mLectors;

    public LearningSpinnerAdapter(@NonNull List<String> mLectors) {
        this.mLectors = new ArrayList<>(mLectors);
    }

    @Override
    public int getCount() {
        return mLectors.size();
    }

    @Override
    public String getItem(int position) {
        return mLectors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mLectorName.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder{
        private final TextView mLectorName;

        private ViewHolder(View view) {
            mLectorName = view.findViewById(android.R.id.text1);
        }
    }
}
