package com.example.demo_livestream.MainActivity_Home;

import androidx.fragment.app.Fragment;

public class MainActivity_Home_Model {
    private Fragment fragment;

    public MainActivity_Home_Model(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
