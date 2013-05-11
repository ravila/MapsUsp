package com.example.mapsexample;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InstitutoDatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	 
    private static final String DATABASE_NAME = "mapaUsp";
 
    private static final String TABLE_INSTITUTOS = "institutos";
 
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY_ID = "categoryId";
    private static final String KEY_ICON = "icon";
	
	public InstitutoDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_INSTITUTO_TABLE = "CREATE TABLE " + TABLE_INSTITUTOS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LATITUDE + " TEXT," + KEY_LONGITUDE
                + " TEXT," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_CATEGORY_ID
                + " INTEGER," + KEY_ICON + " INTEGER" + ")";
        db.execSQL(CREATE_INSTITUTO_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTITUTOS);
		 
        onCreate(db);
	}
	
	public long insert(Instituto i) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, i.getName());
        values.put(KEY_LATITUDE, String.valueOf(i.getLatitude()));
        values.put(KEY_LONGITUDE, String.valueOf(i.getLongitude()));
        values.put(KEY_TITLE, i.getTitle());
        values.put(KEY_DESCRIPTION, i.getDescription());
        values.put(KEY_CATEGORY_ID, i.getCategory().ordinal());
        values.put(KEY_ICON, i.getIcon());
 
        long id = db.insert(TABLE_INSTITUTOS, null, values);
        db.close();
        
        return id;
	}
	
	public List<Instituto> getAllInstitutos() {
        String selectQuery = "SELECT  * FROM " + TABLE_INSTITUTOS
                + " ORDER BY id ASC";
        
        return getInstitutosBySQLQuery(selectQuery);
    }
    
    public List<Instituto> getInstitutosByCategory(Integer categoryId) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSTITUTOS
                + " WHERE " + KEY_CATEGORY_ID + "=" + categoryId + " ORDER BY id ASC";
        System.out.println(selectQuery);
        
        return getInstitutosBySQLQuery(selectQuery);
    }
 
    private List<Instituto> getInstitutosBySQLQuery(String selectQuery) {
        List<Instituto> institutoList = new ArrayList<Instituto>();
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	Instituto instituto = new Instituto();
                instituto.setId(cursor.getInt(0));
                instituto.setName(cursor.getString(1));
                instituto.setLatitude(Double.parseDouble(cursor.getString(2)));
                instituto.setLongitude(Double.parseDouble(cursor.getString(3)));
                instituto.setTitle(cursor.getString(4));
                instituto.setDescription(cursor.getString(5));
                instituto.setCategory(ECategory.getById(cursor.getInt(6)));
                instituto.setIcon(cursor.getInt(7));
                
                institutoList.add(instituto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
 
        return institutoList;
    }

    public Instituto getInstitutoInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_INSTITUTOS, new String[] { KEY_ID, KEY_NAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_TITLE,
                KEY_DESCRIPTION, KEY_CATEGORY_ID, KEY_ICON }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Instituto instituto = new Instituto(cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)),
                cursor.getString(4), cursor.getString(5), ECategory.getById(cursor.getInt(6)), cursor.getInt(7));
 
        instituto.setId(cursor.getInt(0));
        
        cursor.close();
        db.close();
        
        return instituto;
    }
    
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INSTITUTOS, null, null);
        db.close();
    }
    
    public boolean isEmpty() {
		boolean isEmpty = false;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_INSTITUTOS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.getCount() == 0)
			isEmpty = true;
		
		cursor.close();
		db.close();
		
		return isEmpty;
	}
}
