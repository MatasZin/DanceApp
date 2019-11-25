package edu.ktu.labaratorywork1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper implements Serializable {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="danceDatabase";
    private static final String TABLE_DANCELESSON ="danceLesson";
    private static final String KEY_ID ="id";
    private static final String KEY_TITLE ="title";
    private static final String KEY_STARTTIME ="starttime";
    private static final String KEY_ENDTIME ="endtime";
    private static final String KEY_WEEKDAY ="weekday";
    private static final String KEY_DATE ="date";
    private static final String KEY_DESCRIPTION ="description";
    private static final String KEY_TYPE ="type";
    private List<DanceLesson> danceLessonList;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LESSON_TABLE = "CREATE TABLE " + TABLE_DANCELESSON +
                "(" + KEY_ID +" INTEGER PRIMARY KEY, " + KEY_TITLE + "TEXT, "+
                KEY_STARTTIME + "TEXT, "+KEY_ENDTIME + "TEXT, "+
                KEY_WEEKDAY + "TEXT, "+KEY_DATE + "TEXT, "+KEY_DESCRIPTION + "TEXT, "+
                KEY_TYPE + "TEXT" + ")";
        db.execSQL(CREATE_LESSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANCELESSON);

        onCreate(db);
    }

    void addDanceLesson(DanceLesson danceLesson){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,danceLesson.getTitle());
        values.put(KEY_STARTTIME,danceLesson.getStartTime());
        values.put(KEY_ENDTIME,danceLesson.getEndTime());
        values.put(KEY_WEEKDAY,danceLesson.getWeekDay());
        values.put(KEY_DATE,danceLesson.getDate());
        values.put(KEY_DESCRIPTION,danceLesson.getDescription());
        values.put(KEY_TYPE,danceLesson.getType());

        db.insert(TABLE_DANCELESSON,null, values);
        db.close();
    }

    DanceLesson getLesson(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DANCELESSON, new String[] {KEY_ID, KEY_TITLE, KEY_STARTTIME
                , KEY_ENDTIME, KEY_WEEKDAY, KEY_DATE, KEY_DESCRIPTION, KEY_TYPE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        DanceLesson danceLesson = new DanceLesson(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6),
                cursor.getString(7)
                );
        return  danceLesson;
    }

    public List<DanceLesson> getAllLessons(){
        List<DanceLesson> danceLessonList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DANCELESSON;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                DanceLesson danceLesson = new DanceLesson();
                danceLesson.setiD(Integer.parseInt(cursor.getString(0)));
                danceLesson.setTitle(cursor.getString(1));
                danceLesson.setStartTime(cursor.getString(2));
                danceLesson.setEndTime(cursor.getString(3));
                danceLesson.setWeekDay(cursor.getString(4));
                danceLesson.setDate(cursor.getString(5));
                danceLesson.setDescription(cursor.getString(6));
                danceLesson.setType(cursor.getString(7));

                danceLessonList.add(danceLesson);
            } while (cursor.moveToNext());
        }
        return danceLessonList;
    }

    public int updateDanceLesson(DanceLesson danceLesson){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE,danceLesson.getTitle());
        values.put(KEY_STARTTIME,danceLesson.getStartTime());
        values.put(KEY_ENDTIME,danceLesson.getEndTime());
        values.put(KEY_WEEKDAY,danceLesson.getWeekDay());
        values.put(KEY_DATE,danceLesson.getDate());
        values.put(KEY_DESCRIPTION,danceLesson.getDescription());
        values.put(KEY_TYPE,danceLesson.getType());
        return db.update(TABLE_DANCELESSON, values, KEY_ID + "=?",
                new String[]{String.valueOf(danceLesson.getiD())});
    }

    public void deleteDanceLesson(DanceLesson danceLesson){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DANCELESSON, KEY_ID + "=?",
                new String[]{String.valueOf(danceLesson.getiD())});
        db.close();
    }

    public int getDanceLessonsCount(){
        String countQuery = "SELECT * FROM " + TABLE_DANCELESSON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void addBaseLessons(){
        DanceLesson publicDanceLessonMonday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Monday", "", "Pair's", "Latin"
        );
        DanceLesson publicDanceLessonMonday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Monday", "", "Pair's", "Latin"
        );
        DanceLesson publicDanceLessonWednesday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Wednesday", "", "Pair's", "Ballroom"
        );
        DanceLesson publicDanceLessonWednesday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Wednesday", "", "Pair's", "Ballroom"
        );
        DanceLesson publicDanceLessonTuesday1 = new DanceLesson(
                "Medalist bronze", "17:00", "18:30",
                "Tuesday", "", "Solo", "Latin"
        );
        DanceLesson publicDanceLessonTuesday2 = new DanceLesson(
                "Medalist silver", "18:30", "20:00",
                "Tuesday", "", "Solo", "Latin"
        );
        DanceLesson publicDanceLessonThursday1 = new DanceLesson(
                "Argentine", "17:00", "18:30",
                "Thursday", "", "Pair's", "Social dance"
        );
        DanceLesson publicDanceLessonThursday2 = new DanceLesson(
                "Caribean", "18:30", "20:00",
                "Thursday", "", "Pair's", "Social dance"
        );

        this.danceLessonList.add(publicDanceLessonMonday1);
        this.danceLessonList.add(publicDanceLessonMonday2);
        this.danceLessonList.add(publicDanceLessonTuesday1);
        this.danceLessonList.add(publicDanceLessonTuesday2);
        this.danceLessonList.add(publicDanceLessonWednesday1);
        this.danceLessonList.add(publicDanceLessonWednesday2);
        this.danceLessonList.add(publicDanceLessonThursday1);
        this.danceLessonList.add(publicDanceLessonThursday2);

        addDanceLesson(publicDanceLessonMonday1);
        addDanceLesson(publicDanceLessonMonday2);
        addDanceLesson(publicDanceLessonTuesday1);
        addDanceLesson(publicDanceLessonTuesday2);
        addDanceLesson(publicDanceLessonWednesday1);
        addDanceLesson(publicDanceLessonWednesday2);
        addDanceLesson(publicDanceLessonThursday1);
        addDanceLesson(publicDanceLessonThursday2);
    }
}
