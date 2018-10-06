package ir.pepotec.app.videostream.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.view.fragment_channel_list.FragmentChannelList;
import ir.pepotec.app.videostream.view.fragment_channel_list.fragment_channel_items.AdapterChannels;
import ir.pepotec.app.videostream.view.play.FragmentPlay;

public class FragmentFavorait  extends Fragment implements AdapterChannels.OnChannelItemClick{
    View view;
    RecyclerView list;
    ArrayList<StChannel> source = new ArrayList<>();
    TextView txtEmpty;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorait, container, false);
        init();
        return view;
    }

    private void init() {
        pointers();
        settingUpList();
    }

    private void settingUpList() {
        source = LocalDataBase.getFavoraitChannels(G.context);
        if(source.size() == 0)
        {
            txtEmpty.setVisibility(View.VISIBLE);
            return;
        }
        AdapterChannels adapter = new AdapterChannels(G.context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void pointers() {
        list = view.findViewById(R.id.RVFavotait);
        txtEmpty = view.findViewById(R.id.txtEmptyFavorait);
    }

    @Override
    public void channelItemClicked(int position) {
        ActivityMain.gsubId = source.get(position).gsub_id;
        ActivityMain.playData = source.get(position);
        (G.activity).getSupportActionBar().hide();
        ActivityMain.replacePage(new FragmentPlay());
    }
}
