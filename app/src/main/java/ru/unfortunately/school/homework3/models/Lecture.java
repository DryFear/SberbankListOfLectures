package ru.unfortunately.school.homework3.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class Lecture {

    private final String mNumber;
    private final String mDate;
    private final String mTheme;
    private final String mLector;
    private final List<String> mSubTopics;

    @JsonCreator
    public Lecture(
        @JsonProperty("number") int number,
        @JsonProperty("date") @NonNull String date,
        @JsonProperty("theme") @NonNull String theme,
        @JsonProperty("lector") @NonNull String lector,
        @JsonProperty("subtopics") @NonNull List<String> subTopics){
        mDate = date;
        mNumber = String.valueOf(number);
        mTheme = theme;
        mLector = lector;
        mSubTopics = new ArrayList<>(subTopics);
    }

    public Lecture(
            @NonNull String mNumber,
            @NonNull String mDate,
            @NonNull String mTheme,
            @NonNull String mLector,
            @NonNull List<String> subTopics) {
        this.mNumber = mNumber;
        this.mDate = mDate;
        this.mTheme = mTheme;
        this.mLector = mLector;
        mSubTopics = subTopics;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLector() {
        return mLector;
    }

    public List<String> getSubTopics(){
        return new ArrayList<>(mSubTopics);
    }
}