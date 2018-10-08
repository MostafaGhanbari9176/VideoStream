package ir.pepotec.app.videostream.view.play;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.view.ActivityMain;
import ir.pepotec.app.videostream.view.fragment_channel_list.fragment_channel_items.AdapterChannels;

public class FragmentPlay extends Fragment implements AdapterChannels.OnChannelItemClick, View.OnClickListener {

    View view;
    RecyclerView list;
    ArrayList<StChannel> source = new ArrayList<>();
    VideoView videoView;
    JsonArray JArray;
    ImageView playControl;
    ProgressBar pBar;
    String url;
    LinearLayout playControlParent;
    LinearLayout refresh;
    RelativeLayout videoViewParent;
    ImageView imgBookMark;
    TextView txtAuto;
    TextView txtMediume;
    TextView txtHighe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play, container, false);
        init();
        // This work only for android 4.4+
        return view;
    }

    private void init() {

        pointers();
        if (ActivityMain.playData.favorait == 1)
            imgBookMark.setImageResource(R.drawable.ic_bookmark_por);
        settingUpList();
        createUrlArray();
        checkUrls();
        url = ((JsonObject) (JArray.get(0))).get("url").getAsString();
        settingUpPlayer(url);
        txtAuto.setBackgroundResource(R.drawable.que_border_selected);
    }

    private void createUrlArray() {
        JsonParser parser = new JsonParser();
        Object o = parser.parse(ActivityMain.playData.url);
        JArray = (JsonArray) o;

    }

    private void checkUrls() {
        for (JsonElement JElement : JArray) {
            JsonObject data = (JsonObject) JElement;
            String url = data.get("url").getAsString();
            if (url.equalsIgnoreCase("empty"))
                hideQueButton(data.get("qu").getAsString());
            else
                setUrlToButton(data.get("qu").getAsString(), url);
        }
    }

    private void hideQueButton(String sign) {
        switch (sign) {
            case "auto":
                txtAuto.setVisibility(View.GONE);
                break;
            case "medium":
                txtMediume.setVisibility(View.GONE);
                break;
            case "high":
                txtHighe.setVisibility(View.GONE);
                break;
        }
    }

    private void setUrlToButton(String sign, String url) {
        switch (sign) {
            case "auto":
                txtAuto.setHint(url);
                break;
            case "medium":
                txtMediume.setHint(url);
                break;
            case "high":
                txtHighe.setHint(url);
                break;
        }
    }

    private void settingUpPlayer(String url) {
        this.url = url;
        try {

            Uri video = Uri.parse(url);
            videoView.setVideoURI(video);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    pBar.setVisibility(View.GONE);
                }
            });
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    pBar.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(G.context, "خطا هنگام پخش", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

        } catch (Exception e) {
            pBar.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            Toast.makeText(G.context, "خطا هنگام پخش", Toast.LENGTH_SHORT).show();
        }
    }

    private void settingUpList() {
        source = LocalDataBase.getChannels(G.context, -1, ActivityMain.gsubId);
        AdapterChannels adapterChannels = new AdapterChannels(G.context, source, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(G.context, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(adapterChannels);
        adapterChannels.notifyDataSetChanged();
    }

    private void pointers() {
        txtAuto = view.findViewById(R.id.txtAutop);
        txtHighe = view.findViewById(R.id.txtHighp);
        txtMediume = view.findViewById(R.id.txtMediumep);
        imgBookMark = view.findViewById(R.id.imgAddBookmark);
        videoViewParent = view.findViewById(R.id.RLVideoView);
        playControlParent = view.findViewById(R.id.LLplayControl);
        refresh = view.findViewById(R.id.LLRefreshVideo);
        pBar = view.findViewById(R.id.pBarPlayControl);
        playControl = view.findViewById(R.id.imgPlayControl);
        videoView = view.findViewById(R.id.videoView);
        list = view.findViewById(R.id.RVChannelItemPlay);
        ((TextView) view.findViewById(R.id.txtChannelNAmePlay)).setText("کانال "+ActivityMain.playData.name);
        playControl.setOnClickListener(this);
        videoViewParent.setOnClickListener(this);
        refresh.setOnClickListener(this);
        (view.findViewById(R.id.imgRotation)).setOnClickListener(this);
        imgBookMark.setOnClickListener(this);
        txtAuto.setOnClickListener(this);
        txtMediume.setOnClickListener(this);
        txtHighe.setOnClickListener(this);
    }

    @Override
    public void channelItemClicked(int position) {
        ActivityMain.playData = source.get(position);
        ActivityMain.replacePage(new FragmentPlay());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtAutop:
               // showPlayControl(false);
                txtAuto.setBackgroundResource(R.drawable.que_border_selected);
                txtHighe.setBackgroundResource(R.drawable.que_border);
                txtMediume.setBackgroundResource(R.drawable.que_border);
                settingUpPlayer(txtAuto.getHint().toString());
                break;
            case R.id.txtMediumep:
               // showPlayControl(false);
                txtAuto.setBackgroundResource(R.drawable.que_border);
                txtHighe.setBackgroundResource(R.drawable.que_border);
                txtMediume.setBackgroundResource(R.drawable.que_border_selected);
                settingUpPlayer(txtMediume.getHint().toString());
                break;
            case R.id.txtHighp:
               // showPlayControl(false);
                txtAuto.setBackgroundResource(R.drawable.que_border);
                txtHighe.setBackgroundResource(R.drawable.que_border_selected);
                txtMediume.setBackgroundResource(R.drawable.que_border);
                settingUpPlayer(txtHighe.getHint().toString());
                break;
            case R.id.imgPlayControl:
                videoControl();
                break;
            case R.id.imgAddBookmark:
                changeFav();
                break;
            case R.id.LLRefreshVideo:
                settingUpPlayer(url);
                refresh.setVisibility(View.GONE);
                pBar.setVisibility(View.VISIBLE);
                showPlayControl(false);
                break;
            case R.id.imgRotation:
                if (videoView.isPlaying())
                    videoControl();
                ActivityMain.StarterActivity(ActivityPlayLand.class, false);
                break;
            case R.id.RLVideoView:
                if (playControlParent.getAlpha() != 0)
                    hidePlayControl();
                else
                    showPlayControl(true);

                break;
        }
    }

    private void changeFav() {
        if (ActivityMain.playData.favorait == 1) {
            ActivityMain.playData.favorait = 0;
            imgBookMark.setImageResource(R.drawable.ic_bookmark_border);
            LocalDataBase.changeFav(G.context, 0, ActivityMain.playData.id);
            Toast.makeText(G.context, "نشانه حذف شد.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityMain.playData.favorait = 1;
            imgBookMark.setImageResource(R.drawable.ic_bookmark_por);
            LocalDataBase.changeFav(G.context, 1, ActivityMain.playData.id);
            Toast.makeText(G.context, "نشانه گزاری شد.", Toast.LENGTH_SHORT).show();
        }

    }

    private void videoControl() {
        if (videoView.isPlaying()) {
            showPlayControl(true);
            videoView.pause();
            playControl.setImageResource(R.drawable.ic_play);
        } else {
            showPlayControl(true);
            videoView.start();
            playControl.setImageResource(R.drawable.ic_pause);
        }
    }

    private void showPlayControl(boolean withAnim) {
        playControlParent.animate().cancel();
        playControlParent.clearAnimation();
        if (withAnim)
            G.animatingForHide(playControlParent, 1f, 0f);
        else
            playControlParent.setAlpha(1);
    }

    private void hidePlayControl() {
        playControlParent.animate().cancel();
        playControlParent.clearAnimation();
        playControlParent.setAlpha(0f);
    }
}
