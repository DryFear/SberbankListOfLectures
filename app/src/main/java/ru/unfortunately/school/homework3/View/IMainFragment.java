package ru.unfortunately.school.homework3.View;

import ru.unfortunately.school.homework3.Adapters.LearningNamesSpinnerAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningProgramAdapter;
import ru.unfortunately.school.homework3.Adapters.LearningWeeksSpinnerAdapter;

public interface IMainFragment {

    void setAdapterForRecyclerView(LearningProgramAdapter adapter);

    void setWeeksSpinnerAdapter(LearningWeeksSpinnerAdapter adapter);

    void setLectorsSpinnerAdapter(LearningNamesSpinnerAdapter adapter);

}
