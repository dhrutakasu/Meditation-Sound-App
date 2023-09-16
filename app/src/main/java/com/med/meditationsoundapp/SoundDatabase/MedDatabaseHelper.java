package com.med.meditationsoundapp.SoundDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.med.meditationsoundapp.SoundModel.FavModel;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class MedDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SoundHelper.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "SoundFav";
    private static final String SOUND_ID = "SoundId";
    private static final String SOUND_NAME = "SoundFavName";
    private static final String SOUND_UNIQUE_ID = "SoundUniqueId";
    private static final String SOUND_PLAYER_POS = "SoundPos";
    private static final String SOUND_PLAYER_VOLUME = "SoundVolume";

    public MedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                SOUND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SOUND_NAME + " TEXT," +
                SOUND_UNIQUE_ID + " INTEGER," +
                SOUND_PLAYER_POS + " INTEGER," +
                SOUND_PLAYER_VOLUME + " TEXT)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
    }

    //todo fav insert
    public int InsertWidget(String Name, int id, int SoundPos, int Volu) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SOUND_NAME, Name);
        values.put(SOUND_UNIQUE_ID, id);
        values.put(SOUND_PLAYER_POS, SoundPos);
        values.put(SOUND_PLAYER_VOLUME, Volu);

        return (int) db.insert(TABLE_NAME, null, values);
    }

    //todo get Unique Id
    @SuppressLint("Range")
    public int getGroupByUniqueId() {
        SQLiteDatabase db = getReadableDatabase();
        String table_name = "SELECT * FROM " + TABLE_NAME + " GROUP BY " + SOUND_UNIQUE_ID;
        Cursor cursor = db.rawQuery(table_name, new String[]{});


        return cursor.getCount();
    }

    //todo get Unique Id List
    @SuppressLint("Range")
    public ArrayList<FavModel> getGroupByUniqueIdList() {
        ArrayList<FavModel> favModelArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String table_name = "SELECT * FROM " + TABLE_NAME + " GROUP BY " + SOUND_UNIQUE_ID;
        Cursor cursor = db.rawQuery(table_name, new String[]{});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    FavModel model = new FavModel(cursor.getString(cursor.getColumnIndex(SOUND_NAME)), cursor.getString(cursor.getColumnIndex(SOUND_UNIQUE_ID)),
                            cursor.getInt(cursor.getColumnIndex(SOUND_PLAYER_POS)), cursor.getInt(cursor.getColumnIndex(SOUND_PLAYER_VOLUME)));

                    favModelArrayList.add(model);
                } while (cursor.moveToNext());
            }
        }

        return favModelArrayList;
    }

    //todo get Fav List
    @SuppressLint("Range")
        public ArrayList<Integer> getGroupByFavList(int pos) {
        ArrayList<Integer> favModelArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String table_name = "SELECT * FROM " + TABLE_NAME + " WHERE " + SOUND_UNIQUE_ID + " =? ";
        Cursor cursor = db.rawQuery(table_name, new String[]{String.valueOf(pos)});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    favModelArrayList.add(cursor.getInt(cursor.getColumnIndex(SOUND_PLAYER_POS)));
                } while (cursor.moveToNext());
            }
        }

        return favModelArrayList;
    }
}
