package com.kevin.suishouji;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {

	public DBConnection(Context context) {
		super(context, "suishouji", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "create table content(_id integer primary key autoincrement,title varchar(32),content varchar(32),writetime varchar(20))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}