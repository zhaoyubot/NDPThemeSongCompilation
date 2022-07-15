package sg.edu.rp.c346.id20011119.ndpthemesongcompilation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ndpsongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_SINGERS = "_Singer";
    private static final String COLUMN_YEAR = "Year";
    private static final String COLUMN_STAR = "Stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = " CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT ,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + "INTEGER ,"
                + COLUMN_STAR + "INTEGER) ";
        db.execSQL(createSongTableSql);
        Log.i("Song info", "Tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_SONGS + " ADD COLUMN  module_name TEXT ");
        onCreate(db);

    }

    public long insertSongs(String title, String singers, String year, Integer stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STAR, stars);
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result);
        return result;

    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> Songlist = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + ","
                + COLUMN_SINGERS + ","
                + COLUMN_YEAR + ","
                + COLUMN_STAR + " FROM " + TABLE_SONGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song song = new Song(id, title, singers, year, stars);
                Songlist.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Songlist;
    }

    public ArrayList<Song> getAllSongsbyStars(int starsFilter) {
        ArrayList<Song> Songlist = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STAR};
        String condition = COLUMN_STAR + ">= ?";
        String[] args = {String.valueOf(starsFilter)};
        Cursor cursor = db.query(TABLE_SONGS, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song newsong = new Song(id, title, singers, year, stars);
                getAllSongs().add(newsong);
                ;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Songlist;

    }

    public int updateSong(Song data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, data.get_id());
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STAR, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_SONGS, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }


}
