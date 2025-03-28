package com.example.demo_livestream.MainActivity.MainActivity_Home;

import androidx.fragment.app.Fragment;

public class MainActivity_Home_Model {
    private Fragment fragment_home;

    public MainActivity_Home_Model(Fragment fragment) {
        this.fragment_home = fragment;
    }

    public Fragment getFragment_home() {
        return fragment_home;
    }
}
