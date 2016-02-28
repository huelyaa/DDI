package com.example.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.db.DBHelper;
import com.example.mapper.PercipitantClassMapper;
import com.example.model.PercipitantClass;
import com.example.model.PercipitantItem;

public class PercipitantClassDao extends AbstractDao {

	private PercipitantClassMapper percipitantClassMapper = new PercipitantClassMapper();

	public PercipitantClassDao(Context context) {
		super(context);
	}

	public boolean insert(PercipitantClass percipitantClass) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.COLUMN_ID, percipitantClass.getId());
		contentvalues.put(DBHelper.COLUMN_NAME, percipitantClass.getName());

		db.insert(DBHelper.PERCIPITANT_CLASS_TABLE_NAME, null, contentvalues);
		return true;
	}

	public boolean addPercipitantItem(PercipitantClass percipitantClass,
			PercipitantItem percipitantItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.PERCIPITANT_CLASS_COLUMN_ID,
				percipitantClass.getId());
		contentvalues.put(DBHelper.PERCIPITANT_ITEM_COLUMN_ID,
				percipitantItem.getId());

		db.insert(DBHelper.PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME, null,
				contentvalues);
		return true;
	}

	public boolean removePercipitantItem(PercipitantClass percipitantClass,
			PercipitantItem percipitantItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(
				DBHelper.PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME,
				DBHelper.PERCIPITANT_ITEM_COLUMN_ID + "=? AND "
						+ DBHelper.PERCIPITANT_CLASS_COLUMN_ID + "=?",
				new String[] { percipitantItem.getId(),
						percipitantClass.getId() });
		db.delete(DBHelper.PERCIPITANT_ITEM_TABLE_NAME, DBHelper.COLUMN_ID
				+ "=?", new String[] { percipitantItem.getId() });
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_CLASS_TABLE_NAME + " where id=" + id
				+ "", null);
		return res;
	}

	public int nummberOfRows() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				DBHelper.PERCIPITANT_CLASS_TABLE_NAME);
		return numRows;
	}

	public boolean update(String id, String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.COLUMN_NAME, name);
		db.update(DBHelper.PERCIPITANT_CLASS_TABLE_NAME, contentvalues,
				"id = ? ", new String[] { id });
		return true;
	}

	public Integer delete(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.delete(DBHelper.PERCIPITANT_CLASS_TABLE_NAME, "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAll() {
		// TODO Auto-generated method stub
		ArrayList array_list = new ArrayList();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_CLASS_TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(DBHelper.COLUMN_NAME)));
			res.moveToNext();
		}

		return array_list;
	}

	public PercipitantClass getPercipitantClassByDDIPairId(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT oc.ID as ID, oc.NAME as NAME FROM "
				+ DBHelper.DDI_TABLE_NAME + " ddi INNER JOIN "
				+ DBHelper.PERCIPITANT_CLASS_TABLE_NAME
				+ " oc ON ddi.fk_percipitantClass_id=oc.ID WHERE ddi.ID=" + id,
				null);
		return percipitantClassMapper.map(res);
	}

}
