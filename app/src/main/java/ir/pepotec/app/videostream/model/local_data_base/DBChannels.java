package ir.pepotec.app.videostream.model.local_data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.pepotec.app.videostream.model.struct.StChannel;

public class DBChannels extends SQLiteOpenHelper {

    private static final String databaseName = "DBChannel";
    private static final String tableName = "channel";
    private String column1 = "id";
    private String column2 = "name";
    private String column3 = "hd";
    private String column4 = "url";
    private String column5 = "groot_id";
    private String column6 = "gsub_id";
    private String column7 = "vpn";
    private String column8 = "last_seen";
    private String column9 = "counter";
    private String column10 = "favorait";
    private Context context;


    public DBChannels(Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " +
                tableName
                + " (" + column1 + " INTEGER , " + column2 + " TEXT , " + column3 + " INTEGER , " + column4 + " TEXT , " + column5 + " INTEGER , " + column6 + " INTEGER , " + column7 + " INTEGER , " + column8 + " INTEGER , " + column9 + " INTEGER , " + column10 + " INTEGER )");
        //db.execSQL("CREATE UNIQUE INDEX pk_index ON " + tableName + "(" + column1 + "," + column2 + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + tableName);
    }

    public void saveData(ArrayList<StChannel> data) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (StChannel d : data) {
            contentValues.put(column1, d.id);
            contentValues.put(column2, d.name);
            contentValues.put(column3, d.hd);
            contentValues.put(column4, d.url);
            contentValues.put(column5, d.groot_id);
            contentValues.put(column6, d.gsub_id);
            contentValues.put(column7, d.vpn);
            contentValues.put(column8, d.last_seen);
            contentValues.put(column9, d.counter);
            contentValues.put(column10, d.favorait);
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public ArrayList<StChannel> getData(int grootId, int gsubId) {
        ArrayList<StChannel> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor;
        if (grootId != -1)
            cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column5 + " = " + grootId + " AND " + column6 + " = " + gsubId + "", null);
        else
            cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column6 + " = " + gsubId + "", null);
        if (cursor.moveToFirst()) {
            do {
                StChannel data = new StChannel();
                data.id = cursor.getInt(cursor.getColumnIndex(column1));
                data.name = cursor.getString(cursor.getColumnIndex(column2));
                data.hd = cursor.getInt(cursor.getColumnIndex(column3));
                data.url = cursor.getString(cursor.getColumnIndex(column4));
                data.groot_id = cursor.getInt(cursor.getColumnIndex(column5));
                data.gsub_id = cursor.getInt(cursor.getColumnIndex(column6));
                data.vpn = cursor.getInt(cursor.getColumnIndex(column7));
                data.last_seen = cursor.getString(cursor.getColumnIndex(column8));
                data.counter = cursor.getInt(cursor.getColumnIndex(column9));
                data.favorait = cursor.getInt(cursor.getColumnIndex(column10));
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public ArrayList<StChannel> getFavoraitData() {
        ArrayList<StChannel> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column10 + " = " + 1 + "", null);
        if (cursor.moveToFirst()) {
            do {
                StChannel data = new StChannel();
                data.id = cursor.getInt(cursor.getColumnIndex(column1));
                data.name = cursor.getString(cursor.getColumnIndex(column2));
                data.hd = cursor.getInt(cursor.getColumnIndex(column3));
                data.url = cursor.getString(cursor.getColumnIndex(column4));
                data.groot_id = cursor.getInt(cursor.getColumnIndex(column5));
                data.gsub_id = cursor.getInt(cursor.getColumnIndex(column6));
                data.vpn = cursor.getInt(cursor.getColumnIndex(column7));
                data.last_seen = cursor.getString(cursor.getColumnIndex(column8));
                data.counter = cursor.getInt(cursor.getColumnIndex(column9));
                data.favorait = cursor.getInt(cursor.getColumnIndex(column10));
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public void changeFavorait(int fav, int id) {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        sqlWrite.execSQL("UPDATE " + tableName + " SET " + column10 + " = '" + fav + "' WHERE " + column1 + " = '" + id + "'");
        sqlWrite.close();
    }

    public void changeSeenDate(String date, int id) {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        sqlWrite.execSQL("UPDATE " + tableName + " SET " + column8 + " = '" + date + "' WHERE " + column1 + " = '" + id + "'");
        sqlWrite.close();
    }

    public ArrayList<StChannel> searchData(String signText) {
        ArrayList<StChannel> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column2 + " LIKE '%" + signText + "%' ", null);
        if (cursor.moveToFirst()) {
            do {
                StChannel data = new StChannel();
                data.id = cursor.getInt(cursor.getColumnIndex(column1));
                data.name = cursor.getString(cursor.getColumnIndex(column2));
                data.hd = cursor.getInt(cursor.getColumnIndex(column3));
                data.url = cursor.getString(cursor.getColumnIndex(column4));
                data.groot_id = cursor.getInt(cursor.getColumnIndex(column5));
                data.gsub_id = cursor.getInt(cursor.getColumnIndex(column6));
                data.vpn = cursor.getInt(cursor.getColumnIndex(column7));
                data.last_seen = cursor.getString(cursor.getColumnIndex(column8));
                data.counter = cursor.getInt(cursor.getColumnIndex(column9));
                data.favorait = cursor.getInt(cursor.getColumnIndex(column10));
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public void removeDataBase() {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        context.deleteDatabase(sqlWrite.getPath());
        sqlWrite.close();
    }

    public void removeData(String signText) {
        SQLiteDatabase sqlWrite = this.getWritableDatabase();
        sqlWrite.execSQL(" DELETE FROM " + tableName + " WHERE " + column1 + " LIKE '%" + signText + "%' ");
        sqlWrite.close();
    }
}
