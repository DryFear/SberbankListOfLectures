package ru.unfortunately.school.homework3.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.unfortunately.school.homework3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.root, MainFragment.newInstance())
                .commit();
    }


}
