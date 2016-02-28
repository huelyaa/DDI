package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";

	// DDIPair
	public static final String DDI_TABLE_NAME = "DDI_PAIR";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_NAME = "NAME";
	public static final String DDI_COLUMN_STATUS = "STATUS";
	public static final String COLUMN_MY_DDI = "MY_DDI";

	// OBJECT CLASS
	public static final String OBJECT_CLASS_TABLE_NAME = "OBJECT_CLASS";

	// OBJECT ITEM
	public static final String OBJECT_ITEM_TABLE_NAME = "OBJECT_ITEM";

	// PRECIPITANT CLASS
	public static final String PERCIPITANT_CLASS_TABLE_NAME = "PERCIPITANT_CLASS";

	// PRECIPITANT ITEM
	public static final String PERCIPITANT_ITEM_TABLE_NAME = "PERCIPITANT_ITEM";

	// FOREIGN TABLES
	public static final String OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME = "OBJECT_CLASS_OBJECT_ITEM";
	public static final String OBJECT_CLASS_COLUMN_ID = "OBJECT_CLASS_ID";
	public static final String OBJECT_ITEM_COLUMN_ID = "OBJECT_ITEM_ID";

	public static final String PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME = "PERCIPITANT_CLASS_PERCIPITANT_ITEM";
	public static final String PERCIPITANT_CLASS_COLUMN_ID = "PERCIPITANT_CLASS_ID";
	public static final String PERCIPITANT_ITEM_COLUMN_ID = "PERCIPITANT_ITEM_ID";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + DDI_TABLE_NAME + " ( " + COLUMN_ID
				+ " integer primary key, " + COLUMN_NAME + " text, "
				+ DDI_COLUMN_STATUS + " text, " + COLUMN_MY_DDI
				+ " text,  fk_objectClass_id integer, "
				+ "fk_percipitantClass_id integer, "
				+ " FOREIGN KEY(fk_objectClass_id) REFERENCES "
				+ OBJECT_CLASS_TABLE_NAME + "(" + COLUMN_ID + ")"
				+ " FOREIGN KEY(fk_percipitantClass_id) REFERENCES "
				+ PERCIPITANT_CLASS_TABLE_NAME + "(" + COLUMN_ID + ")" + ")");
		db.execSQL("CREATE TABLE " + OBJECT_CLASS_TABLE_NAME + " ( "
				+ COLUMN_ID + " integer primary key, " + COLUMN_NAME + " text)");
		db.execSQL("CREATE TABLE " + OBJECT_ITEM_TABLE_NAME + " ( " + COLUMN_ID
				+ " integer primary key, " + COLUMN_NAME + " text)");
		db.execSQL("CREATE TABLE " + PERCIPITANT_CLASS_TABLE_NAME + " ( "
				+ COLUMN_ID + " integer primary key, " + COLUMN_NAME + " text)");
		db.execSQL("CREATE TABLE " + PERCIPITANT_ITEM_TABLE_NAME + " ( "
				+ COLUMN_ID + " integer primary key, " + COLUMN_NAME + " text)");
		db.execSQL("CREATE TABLE " + OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME
				+ " ( " + OBJECT_CLASS_COLUMN_ID + " integer, "
				+ OBJECT_ITEM_COLUMN_ID + " integer, " + " FOREIGN KEY("
				+ OBJECT_CLASS_COLUMN_ID + ") REFERENCES "
				+ OBJECT_CLASS_TABLE_NAME + " (" + COLUMN_ID + "), "
				+ " FOREIGN KEY(" + OBJECT_ITEM_COLUMN_ID + ") REFERENCES "
				+ OBJECT_ITEM_TABLE_NAME + " (" + COLUMN_ID + "), PRIMARY KEY("
				+ OBJECT_CLASS_COLUMN_ID + ", " + OBJECT_ITEM_COLUMN_ID + "))");
		db.execSQL("CREATE TABLE "
				+ PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME + " ( "
				+ PERCIPITANT_CLASS_COLUMN_ID + " integer, "
				+ PERCIPITANT_ITEM_COLUMN_ID + " integer, " + " FOREIGN KEY("
				+ PERCIPITANT_CLASS_COLUMN_ID + ") REFERENCES "
				+ PERCIPITANT_CLASS_TABLE_NAME + " (" + COLUMN_ID + "), "
				+ " FOREIGN KEY(" + PERCIPITANT_ITEM_COLUMN_ID
				+ ") REFERENCES " + PERCIPITANT_ITEM_TABLE_NAME + " ("
				+ COLUMN_ID + "), PRIMARY KEY(" + PERCIPITANT_CLASS_COLUMN_ID
				+ "," + PERCIPITANT_ITEM_COLUMN_ID + "))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS "
				+ PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "
				+ OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PERCIPITANT_ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PERCIPITANT_CLASS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + OBJECT_ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + OBJECT_CLASS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DDI_TABLE_NAME);

		onCreate(db);
	}
}
