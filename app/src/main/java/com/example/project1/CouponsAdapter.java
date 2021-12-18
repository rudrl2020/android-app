package com.example.project1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CouponsAdapter extends FragmentStateAdapter {
    public CouponsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new CouponsFragment();
        }
        else {
            return new StampsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
