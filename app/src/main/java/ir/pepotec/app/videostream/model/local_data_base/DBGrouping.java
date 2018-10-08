package ir.pepotec.app.videostream.model.local_data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.pepotec.app.videostream.model.struct.StGrouping;

public class DBGrouping extends SQLiteOpenHelper {

    private static final String databaseName = "DBGrouping";
    private static final String tableName = "grouping";
    private String column1 = "id";
    private String column2 = "name";
    private String column3 = "sign";
    private String column4 = "img";
    private Context context;


    public DBGrouping(Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " +
                tableName
                + " (" + column1 + " INTEGER PRIMARY KEY AUTOINCREMENT , " + column2 + " TEXT, " + column3 + " TEXT , "+ column4 +" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + tableName);
    }

    public void saveData(ArrayList<StGrouping> data) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (StGrouping d : data) {
            //contentValues.put(column1, d.id);
            contentValues.put(column2, d.name);
            contentValues.put(column3, d.sign);
            writer.insert(tableName, null, contentValues);
        }
        writer.close();
    }

    public void saveData(StGrouping d) {
        SQLiteDatabase writer = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(column1, d.id);
        contentValues.put(column2, d.name);
        contentValues.put(column3, d.sign);
        contentValues.put(column4, d.img);
        writer.insert(tableName, null, contentValues);

        writer.close();
    }

    public ArrayList<StGrouping> getData(String signText) {
        ArrayList<StGrouping> dataList = new ArrayList<>();
        SQLiteDatabase reader = this.getReadableDatabase();
        Cursor cursor = reader.rawQuery(" SELECT * FROM " + tableName + " WHERE " + column3 + " LIKE '%" + signText + "%' ", null);
        if (cursor.moveToFirst()) {
            do {
                StGrouping data = new StGrouping();
                data.id = cursor.getInt(cursor.getColumnIndex(column1));
                data.name = cursor.getString(cursor.getColumnIndex(column2));
                data.sign = cursor.getString(cursor.getColumnIndex(column3));
                data.img = cursor.getString(cursor.getColumnIndex(column4));
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
