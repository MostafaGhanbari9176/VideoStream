package ir.pepotec.app.videostream.view.fragment_channel_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.model.struct.StGrouping;
import ir.pepotec.app.videostream.view.ActivityMain;
import ir.pepotec.app.videostream.view.fragment_channel_list.fragment_channel_items.FragmentChannelItems;

public class FragmentChannelList extends Fragment implements AdapterGroot.grootItemsClicked {
    RecyclerView grootList;
    View view;
    public static ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<StGrouping> groupSource = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        init();
        return view;
    }

    private void init() {
        pointer();
        settingUpGrootList();
        settingUpViewPager(ActivityMain.currentGrootId, ActivityMain.currentPage);
    }

    private void settingUpViewPager(int grootId, int pagePosition) {
        tabLayout.setupWithViewPager(viewPager);
        AdapterPages adapter = new AdapterPages(getChildFragmentManager());
        ArrayList<StGrouping> subGroup = LocalDataBase.getGropuping(G.context, "sub");
        for (int i = subGroup.size() - 1; i >= 0; i--) {
            FragmentChannelItems f = new FragmentChannelItems();
            f.grootId = grootId;
            f.srootId = subGroup.get(i).id;
            f.source = LocalDataBase.getChannels(G.context, grootId, subGroup.get(i).id);
            adapter.addPage(subGroup.get(i).name, f);
        }
        viewPager.setAdapter(adapter);
        if (pagePosition == -1)
            viewPager.setCurrentItem(subGroup.size() - 1);
        else
            viewPager.setCurrentItem(pagePosition);
        viewPager.setOffscreenPageLimit(subGroup.size() - 1);
        adapter.notifyDataSetChanged();
    }

    private void settingUpGrootList() {

        groupSource = LocalDataBase.getGropuping(G.context, "root");
        AdapterGroot adapter = new AdapterGroot(G.context, groupSource, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, GridLayoutManager.HORIZONTAL, false);
        grootList.setLayoutManager(manager);
        grootList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void pointer() {
        grootList = view.findViewById(R.id.RVGroot);
        viewPager = view.findViewById(R.id.channelPages);
        tabLayout = view.findViewById(R.id.channelTabLayout);
    }

    @Override
    public void grootItemClicked(int position) {
        ActivityMain.currentGrootId = groupSource.get(position).id;
        ActivityMain.currentGrootPosition = position;
        settingUpViewPager(groupSource.get(position).id, viewPager.getCurrentItem());
    }
}
