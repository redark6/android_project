package com.chouli.musicalappproject.layoutManagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.chouli.musicalappproject.fragments.TablayoutHomeSingleRankingFragment;
import com.chouli.musicalappproject.fragments.TablayoutHomeAlbumRankingFragment;

public class HomeTabLayoutFragmentAdapter extends FragmentStateAdapter {

    private String[] tabsTitles = new String[]{"Titres","Albums"};

    public HomeTabLayoutFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TablayoutHomeSingleRankingFragment();
            case 1:
                return new TablayoutHomeAlbumRankingFragment();
        }
        return new TablayoutHomeSingleRankingFragment();
    }

    @Override
    public int getItemCount() {
        return tabsTitles.length;
    }
}
