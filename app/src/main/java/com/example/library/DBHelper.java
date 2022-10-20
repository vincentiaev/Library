package com.example.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final  String DB_NAME ="BookLibrary.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "library";
    public static final String _ID = "_id";
    public static final String TITLE = "book_title";
    public static final String YEAR = "book_year";
    public static final String AUTHOR = "book_author";
    public static final String PAGES = "book_pages";
    public static final String READ_PAGES = "read_pages";
    public static final String SUMMARY = "book_summary";
    public static final String READING_STAT = "reading_stat";



    DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE + " TEXT, " +
                        YEAR + " INTEGER, " +
                        AUTHOR + " TEXT, " +
                        PAGES + " INTEGER, " +
                        READ_PAGES + " INTEGER, " +
                        SUMMARY + " TEXT, " +
                        READING_STAT + " INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //create
    void addBook(String title, int year, String author, int pages,
                 int read_pages, String summary, int reading_stat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if(read_pages == pages) {
            reading_stat = 1;
        } else {
            reading_stat = 0;
        }

        cv.put(TITLE, title);
        cv.put(YEAR, year);
        cv.put(AUTHOR, author);
        cv.put(PAGES, pages);
        cv.put(READ_PAGES, read_pages);
        cv.put(SUMMARY, summary);
        cv.put(READING_STAT, reading_stat);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to add book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //read
    Cursor readAllData() {
        //BUAT AMBIL SEMUA DATA
        String query = "SELECT * FROM " + TABLE_NAME;

        //BUAT AMBIL DATA YANG READING STATNYA UDAH 1
        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + READING_STAT + " LIKE 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //update
    void updateData(String row_id, String title, String year, String author, String pages,
                    String read_pages, String summary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(YEAR, year);
        cv.put(AUTHOR, author);
        cv.put(PAGES, pages);
        cv.put(READ_PAGES, read_pages);
        cv.put(SUMMARY, summary);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    //delete
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete this book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    //delete all
    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
