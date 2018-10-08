package ir.pepotec.app.videostream.view.play;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
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

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.view.ActivityMain;

public class ActivityPlayLand extends AppCompatActivity implements View.OnClickListener {

    View view;
    VideoView videoView;
    JsonArray JArray;
    ImageView playControl;
    ProgressBar pBar;
    LinearLayout playControlParent;
    LinearLayout refresh;
    RelativeLayout videoViewParent;
    CardView queControl;
    TextView txtAuto;
    TextView txtMediume;
    TextView txtHighe;
    String url;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            this.getWindow().getDecorView().setSystemUiVisibility(flags);
            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = this.getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        setContentView(R.layout.activity_play_land);
        init();
    }

    private void init() {
        pointers();
        createUrlArray();
        checkUrls();
        settingUpPlayer(((JsonObject) (JArray.get(0))).get("url").getAsString());
        txtAuto.setBackgroundResource(R.drawable.que_border_selected);
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

    private void createUrlArray() {
        JsonParser parser = new JsonParser();
        Object o = parser.parse(ActivityMain.playData.url);
        JArray = (JsonArray) o;

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
                    Toast.makeText(ActivityPlayLand.this, "خطا هنگام پخش", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

        } catch (Exception e) {
            pBar.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            Toast.makeText(this, "خطا هنگام پخش", Toast.LENGTH_SHORT).show();
        }
    }

    private void pointers() {
        videoViewParent = findViewById(R.id.RLVideoViewLand);
        playControlParent = findViewById(R.id.LLplayControlLand);
        refresh = findViewById(R.id.LLRefreshVideoLand);
        pBar = findViewById(R.id.pBarPlayControlLand);
        playControl = findViewById(R.id.imgPlayControlLand);
        videoView = findViewById(R.id.videoViewLand);
        queControl = findViewById(R.id.CVQueControlLand);
        txtAuto = findViewById(R.id.txtAuto);
        txtHighe = findViewById(R.id.txtHigh);
        txtMediume = findViewById(R.id.txtMediume);
        playControl.setOnClickListener(this);
        txtAuto.setOnClickListener(this);
        txtMediume.setOnClickListener(this);
        txtHighe.setOnClickListener(this);
        videoViewParent.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPlayControlLand:
                videoControl();
                break;
            case R.id.LLRefreshVideoLand:
                settingUpPlayer(url);
                refresh.setVisibility(View.GONE);
                pBar.setVisibility(View.VISIBLE);
                showPlayControl(false);
                break;
            case R.id.txtAuto:
                showPlayControl(true);
                txtAuto.setBackgroundResource(R.drawable.que_border_selected);
                txtHighe.setBackgroundResource(R.drawable.que_border);
                txtMediume.setBackgroundResource(R.drawable.que_border);
                settingUpPlayer(txtAuto.getHint().toString());
                break;
            case R.id.txtMediume:
                showPlayControl(true);
                txtAuto.setBackgroundResource(R.drawable.que_border);
                txtHighe.setBackgroundResource(R.drawable.que_border);
                txtMediume.setBackgroundResource(R.drawable.que_border_selected);
                settingUpPlayer(txtMediume.getHint().toString());
                break;
            case R.id.txtHigh:
                showPlayControl(true);
                txtAuto.setBackgroundResource(R.drawable.que_border);
                txtHighe.setBackgroundResource(R.drawable.que_border_selected);
                txtMediume.setBackgroundResource(R.drawable.que_border);
                settingUpPlayer(txtHighe.getHint().toString());
                break;
            case R.id.RLVideoViewLand:
                if (playControlParent.getAlpha() != 0)
                    hidePlayControl();
                else
                    showPlayControl(true);

                break;
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
        queControl.animate().cancel();
        queControl.clearAnimation();
        G.animatingForHide(queControl, 1f, 0f);
    }

    private void hidePlayControl() {
        playControlParent.animate().cancel();
        playControlParent.clearAnimation();
        playControlParent.setAlpha(0f);

        queControl.animate().cancel();
        queControl.clearAnimation();
        queControl.setAlpha(0f);
    }

    @Override
    public void onBackPressed() {
        videoView.pause();
        this.finish();
        super.onBackPressed();
    }
}
