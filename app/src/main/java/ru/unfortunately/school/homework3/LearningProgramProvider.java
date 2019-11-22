package ru.unfortunately.school.homework3;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ru.unfortunately.school.homework3.models.Lecture;


public class LearningProgramProvider {


    private static final String LECTURES_URL = "http://landsovet.ru/learning_program.json";
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

    public List<Lecture> mLectures;
//    = Arrays.asList(
//            new Lecture("1",	"24.09.2019", "Вводное занятие", "Соколов", subTopics),
//            new Lecture("2",	"26.09.2019", "android.view.View, Layouts","Соколов", subTopics),
//            new Lecture("3",	"28.09.2019", "Drawables", "Соколов", subTopics),
//            new Lecture("4",	"01.10.2019", "Activity", "Сафарян", subTopics),
//            new Lecture("5",	"03.10.2019", "Адаптеры", "Чумак", subTopics),
//            new Lecture("6",	"05.10.2019", "UI: практика", "Кудрявцев", subTopics),
//            new Lecture("7",	"08.10.2019", "Custom View", "Кудрявцев", subTopics),
//            new Lecture("8",	"10.10.2019", "Touch events", "Бильчук", subTopics),
//            new Lecture("9",	"12.10.2019", "Сложные жесты", "Соколов", subTopics),
//            new Lecture("10",	"15.10.2019", "Layout & Measurement", "Кудрявцев", subTopics),
//            new Lecture("11",	"17.10.2019", "Custom ViewGroup", "Кудрявцев", subTopics),
//            new Lecture("12",	"19.10.2019", "Анимации", "Чумак", subTopics),
//            new Lecture("13",	"22.10.2019", "Практика view", "Соколов", subTopics),
//            new Lecture("14",	"24.10.2019", "Фрагменты: база", "Бильчук", subTopics),
//            new Lecture("15",	"26.10.2019", "Фрагменты: практика", "Соколов", subTopics),
//            new Lecture("16",	"29.10.2019", "Фоновая работа", "Чумак", subTopics),
//            new Lecture("17",	"31.10.2019", "Абстракции фон/UI", "Леонидов", subTopics),
//            new Lecture("18",	"5.11.2019", "Фон: практика", "Чумак", subTopics),
//            new Lecture("19",	"7.11.2019", "BroadcastReceiver", "Бильчук", subTopics),
//            new Lecture("20",	"9.11.2019", "Runtime permissions", "Кудрявцев", subTopics),
//            new Lecture("21",	"12.11.2019", "Service", "Леонидов", subTopics),
//            new Lecture("22",	"14.11.2019", "Service: практика", "Леонидов", subTopics),
//            new Lecture("23",	"16.11.2019", "Service: биндинг", "Леонидов", subTopics),
//            new Lecture("24",	"19.11.2019", "Preferences", "Сафарян", subTopics),
//            new Lecture("25",	"21.11.2019", "SQLite", "Бильчук", subTopics),
//            new Lecture("26",	"23.11.2019", "SQLite: Room", "Соколов", subTopics),
//            new Lecture("27",	"26.11.2019", "ContentProvider", "Сафарян", subTopics),
//            new Lecture("28",	"28.11.2019", "FileProvider", "Соколов", subTopics),
//            new Lecture("29",	"30.11.2019", "Геолокация", "Леонидов", subTopics),
//            new Lecture("30",	"3.12.2019", "Material", "Чумак", subTopics),
//            new Lecture("31",	"5.12.2019", "UI-тесты", "Сафарян", subTopics),
//            new Lecture("32",	"7.12.2019", "Service: практика", "Соколов", subTopics)
//    );


    public List<Lecture> provideLectures(){
        return mLectures;
    }

    public static Lecture provideWeekAsLecture(int n){
        return new Lecture("", "", "Неделя " + n, "", Arrays.asList("1", "2"));
    }

    public List<String> provideLectors(){
        Set<String> lectors = new HashSet<>();
        for (Lecture lecture: mLectures) {
            lectors.add(lecture.getLector());
        }
        return new ArrayList<>(lectors);
    }

    public List<Lecture> provideLecturesByLector(String lector){
        List<Lecture> result = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if(lecture.getLector().equals(lector)){
                result.add(lecture);
            }
        }
        return result;
    }

    public Lecture getNextLecture(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        for(Lecture lecture: mLectures){
            try{
                if(format.parse(lecture.getDate()).after(date)){
                    return lecture;
                }
            }catch (ParseException e){e.printStackTrace();}
        }
        return mLectures.get(mLectures.size()-1);
    }

    public List<Lecture> downloadLectureFromInternet(){
        if(mLectures != null){
            return mLectures;
        }
        InputStream inputStream = null;
        try {
            final URL url = new URL(LECTURES_URL);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Lecture[] lectures = mapper.readValue(inputStream, Lecture[].class);
            mLectures = Arrays.asList(lectures);
            return new ArrayList<>(mLectures);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
