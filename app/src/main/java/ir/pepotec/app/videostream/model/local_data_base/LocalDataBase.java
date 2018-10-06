package ir.pepotec.app.videostream.model.local_data_base;

import android.content.Context;

import java.util.ArrayList;

import ir.pepotec.app.videostream.model.struct.StChannel;
import ir.pepotec.app.videostream.model.struct.StGrouping;

public class LocalDataBase {

    public static ArrayList<StChannel> getChannels(Context context, int grootId, int gsubId){
        return  (new DBChannels(context)).getData(grootId, gsubId);
    }

    public static ArrayList<StChannel> getFavoraitChannels(Context context){
        return  (new DBChannels(context)).getFavoraitData();
    }

    public static void changeFav(Context context, int fav, int id){
        (new DBChannels(context)).changeFavorait(fav, id);
    }

    public static ArrayList<StChannel> searchData(Context context, String signText){
        return  (new DBChannels(context)).searchData(signText);
    }

    public static void changeSeenDate(Context context, String date, int id){
        (new DBChannels(context)).changeSeenDate(date, id);
    }

    public static void saveChannels(Context context, ArrayList<StChannel> data){
        (new DBChannels(context)).saveData(data);
    }

    public static ArrayList<StGrouping> getGropuping(Context context, String sign){
        return  (new DBGrouping(context)).getData(sign);
    }

    public static void saveGroupings(Context context, ArrayList<StGrouping> data){
        (new DBGrouping(context)).saveData(data);
    }

    public static void removeDB(Context context){
        (new DBGrouping(context)).removeDataBase();
        (new DBChannels(context)).removeDataBase();
    }

}
