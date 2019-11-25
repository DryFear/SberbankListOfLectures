package ru.unfortunately.school.homework3.Presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.unfortunately.school.homework3.View.MainFragment;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {

    @Mock
    private MainFragment mMainFragment;

    private Presenter mPresenter;


    @Before
    public void setUp(){
        mPresenter = new Presenter(mMainFragment);
    }

    @Test
    public void testShowErrorMessage(){

    }
}