package ir.pepotec.app.videostream.view;


import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.ImageView;


import android.widget.TextView;

import ir.pepotec.app.videostream.G;
import ir.pepotec.app.videostream.R;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.view.fragment_channel_list.FragmentChannelList;


public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    public static boolean editeMode = false;
    ImageView btnSearch;
    Toolbar tlb;
    ImageView btnBookMark;
    TextView txtSearch;
    public static int gsubId = -1;
    public static StChannel playData;
    public static int currentPage = -1;
    public static int currentGrootId = 2;
    public static int currentGrootPosition = 0;
    public static boolean homeShow = true;
    FragmentSearch FSearch;
    ImageView btnEditeMode;
    public static String pPath = "";

    public interface OnResultListener{
        void dataFromActivity(Intent data, int reqCode);
    }

    public static OnResultListener onResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        G.context = this;
        G.activity = this;
        pointers();
        setUpTxtSearch();
        setSupportActionBar(tlb);
        replacePage(new FragmentChannelList());
    }

    private void setUpTxtSearch() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FSearch != null)
                    FSearch.settingUpList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void StarterActivity(Class aClass, boolean result) {
        Intent intent = new Intent(G.context, aClass);
        if (!result)
            G.activity.startActivity(intent);
        else
            G.activity.startActivityForResult(intent, 1);
    }

    private void pointers() {
        btnSearch = findViewById(R.id.btnSearch);
        btnBookMark = findViewById(R.id.btnBookMarkCollection);
        txtSearch = findViewById(R.id.txtSearch);
        tlb = findViewById(R.id.tlb);
        btnEditeMode = findViewById(R.id.btnEditMode);

        btnEditeMode.setOnClickListener(this);
        btnBookMark.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    public static void replacePage(Fragment fragment) {
        G.activity.getSupportFragmentManager()
                .beginTransaction().replace(R.id.mainContainer, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.activity = this;
    }

    @Override
    public void onBackPressed() {

        txtSearch.setVisibility(View.GONE);
        if (!homeShow) {
            homeShow = true;
            getSupportActionBar().show();
            replacePage(new FragmentChannelList());
        } else if (editeMode) {
            changeTollBarColor();
            editeMode = false;
            homeShow = true;
            replacePage(new FragmentChannelList());
            btnBookMark.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
        } else
            showSnackBar();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.mainContainer), "خروج از برنامه ؟ ", Snackbar.LENGTH_SHORT)
                .setAction("خروج", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityMain.this.finish();
                    }
                });
        View snackbarView = snackbar.getView();
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
        snackbarView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        textView.setTextColor(ContextCompat.getColor(this, R.color.light));
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                G.animatingForVisible(txtSearch);
                homeShow = false;
                currentPage = FragmentChannelList.viewPager.getCurrentItem();
                FSearch = new FragmentSearch();
                replacePage(FSearch);
                break;
            case R.id.btnBookMarkCollection:
                txtSearch.setVisibility(View.GONE);
                homeShow = false;
                currentPage = FragmentChannelList.viewPager.getCurrentItem();
                replacePage(new FragmentFavorait());
                break;
            case R.id.btnEditMode:
                homeShow = true;
                editeMode = true;
                btnBookMark.setVisibility(View.GONE);
                btnSearch.setVisibility(View.GONE);
                txtSearch.setVisibility(View.GONE);
                currentPage = FragmentChannelList.viewPager.getCurrentItem();
                replacePage(new FragmentChannelList());
                changeTollBarColor();
                break;
        }
    }

    private void changeTollBarColor() {
        TransitionDrawable transition = (TransitionDrawable) tlb.getBackground();
        transition.reverseTransition(400);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            onResultListener.dataFromActivity(data, requestCode);
        }

    }

    /* private void playVideo(String url) {

    }*/
}
