package io.verdict.ui.DetailScreen;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailReviewItemAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public  void addFragment(Fragment fragment, String string){
        fragmentList.add(fragment);
        fragmentTitleList.add(string);
    }

    public DetailReviewItemAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return  fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
