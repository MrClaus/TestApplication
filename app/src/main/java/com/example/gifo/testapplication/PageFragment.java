package com.example.gifo.testapplication;

/**
 * Created by gifo on 23.04.2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {

    int pageNumber; // номер текущего фрагмента страницы PageView

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.pageNumber = page;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (pageNumber==0) view = inflater.inflate(R.layout.home_screen, null);
        else if (pageNumber==1) view = inflater.inflate(R.layout.info_screen, null);
        return view;
    }
}
