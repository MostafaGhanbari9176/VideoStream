package ir.pepotec.app.videostream;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ir.pepotec.app.videostream.model.Pref;
import ir.pepotec.app.videostream.model.local_data_base.LocalDataBase;
import ir.pepotec.app.videostream.model.struct.PrefKey;
import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.model.struct.StGrouping;

public class G extends Application {
    public static Context context;
    public static AppCompatActivity activity;
    public static SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = context.getSharedPreferences("PePoTec", MODE_PRIVATE);
        String s = stData("channels.json");
        if(!(Pref.getBollValue(PrefKey.createDB, false))) {
            LocalDataBase.removeDB(G.context);
            createChannelDB(s);
            s = stData("grouping.json");
            createGroupDB(s);
            Pref.saveBollValue(PrefKey.createDB,true);
        }
/*        ArrayList<StChannel> result = LocalDataBase.getChannels(this,1,1);
        ArrayList<StGrouping>result2 = LocalDataBase.getGropuping(context,"root");
        int i = 0;*/
    }

    private void createGroupDB(String JData) {
        JsonParser parser = new JsonParser();
        Object o = parser.parse(JData);
        JsonArray array = (JsonArray)o;
        ArrayList<StGrouping> dataSource = new ArrayList<>();
        for(JsonElement JElement : array){
            JsonObject data = (JsonObject)JElement;
            StGrouping stGrouping = new StGrouping();
            stGrouping.id = (data.get("id")).getAsInt();
            stGrouping.name = (data.get("name")).getAsString();
            stGrouping.sign = (data.get("sign")).getAsString();
            dataSource.add(stGrouping);
        }
        LocalDataBase.saveGroupings(this, dataSource);
    }

    private void createChannelDB(String JData){
        JsonParser parser = new JsonParser();
        Object o = parser.parse(JData);
        JsonArray array = (JsonArray)o;
        ArrayList<StChannel> dataSource = new ArrayList<>();
        for(JsonElement JElement : array){
            JsonObject data = (JsonObject)JElement;
            StChannel stChannel = new StChannel();
            stChannel.id = (data.get("id")).getAsInt();
            stChannel.name = (data.get("name")).getAsString();
            stChannel.url = (data.get("url")).getAsString();
            stChannel.groot_id = (data.get("rootg_id")).getAsInt();
            stChannel.gsub_id = (data.get("roots_id")).getAsInt();
            stChannel.vpn = (data.get("vpn")).getAsInt();
            stChannel.hd = (data.get("hd")).getAsInt();
            dataSource.add(stChannel);
        }
        LocalDataBase.saveChannels(this, dataSource);
        }

    private String stData(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = this.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null)
                text.append(line);

            reader.close();

            return text.toString();

        } catch (Exception e) {
            return "";
        }
    }

    public static void steImage(Context context, String imgName, ImageView imgView){
        Glide.with(context)
                .load(getImage(context, imgName))
                .into(imgView);
    }

    private static int getImage(Context context,String imageName) {

        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public static void animatingForGone(final View view, float firstAlpha, float lastAlpha) {
        view.setAlpha(firstAlpha);
        view.animate()
                .alpha(lastAlpha)
                .setDuration(5000);
        }

    public static void animatingForVisible(final View view, float firstAlpha, float lastAlpha) {
        view.setAlpha(firstAlpha);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(lastAlpha)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }
}
