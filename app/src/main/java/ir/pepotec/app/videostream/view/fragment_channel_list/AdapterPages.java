package ir.pepotec.app.videostream.view.fragment_channel_list;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;


public class AdapterPages extends FragmentStatePagerAdapter {

   private ArrayList<Fragment> source = new ArrayList<>();
   private ArrayList<String> titleSource = new ArrayList<>();
    public AdapterPages(FragmentManager fm) {
        super(fm);
    }

    public void addPage(String title, Fragment page){
        source.add(page);
        titleSource.add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return source.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleSource.get(position);
    }

    @Override
    public int getCount() {
        return source.size();
    }
}
