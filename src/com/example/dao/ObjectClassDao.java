package com.example.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.db.DBHelper;
import com.example.mapper.ObjectClassMapper;
import com.example.model.ObjectClass;
import com.example.model.ObjectItem;

public class ObjectClassDao extends AbstractDao {

	private ObjectClassMapper objectClassMapper = new ObjectClassMapper();

	public ObjectClassDao(Context context) {
		super(context);
	}

	public boolean insert(ObjectClass objectClass) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.COLUMN_ID, objectClass.getId());
		contentvalues.put(DBHelper.COLUMN_NAME, objectClass.getName());

		db.insert(DBHelper.OBJECT_CLASS_TABLE_NAME, null, contentvalues);
		return true;
	}

	public boolean addObjectItem(ObjectClass objectClass, ObjectItem objectItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.OBJECT_CLASS_COLUMN_ID, objectClass.getId());
		contentvalues.put(DBHelper.OBJECT_ITEM_COLUMN_ID, objectItem.getId());

		db.insert(DBHelper.OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME, null,
				contentvalues);
		return true;
	}

	public boolean removeObjectItem(ObjectClass objectClass,
			ObjectItem objectItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DBHelper.OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME,
				DBHelper.OBJECT_ITEM_COLUMN_ID + "=? AND "
						+ DBHelper.OBJECT_CLASS_COLUMN_ID + "=?", new String[] {
						objectItem.getId(), objectClass.getId() });
		db.delete(DBHelper.OBJECT_ITEM_TABLE_NAME, DBHelper.COLUMN_ID + "=?",
				new String[] { objectItem.getId() });
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_CLASS_TABLE_NAME + " where id=" + id + "",
				null);
		return res;
	}

	public int nummberOfRows() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				DBHelper.OBJECT_CLASS_TABLE_NAME);
		return numRows;
	}

	public boolean update(String id, String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.COLUMN_NAME, name);
		db.update(DBHelper.OBJECT_CLASS_TABLE_NAME, contentvalues, "id = ? ",
				new String[] { id });
		return true;
	}

	public Integer delete(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.delete(DBHelper.OBJECT_CLASS_TABLE_NAME, "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAll() {
		// TODO Auto-generated method stub
		ArrayList array_list = new ArrayList();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_CLASS_TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(DBHelper.COLUMN_NAME)));
			res.moveToNext();
		}

		return array_list;
	}

	public ObjectClass getObjectClassByDDIPairId(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db
				.rawQuery("SELECT oc.ID as ID, oc.NAME as NAME FROM "
						+ DBHelper.DDI_TABLE_NAME + " ddi INNER JOIN "
						+ DBHelper.OBJECT_CLASS_TABLE_NAME
						+ " oc ON ddi.fk_objectClass_id=oc.ID WHERE ddi.ID="
						+ id, null);
		return objectClassMapper.map(res);
	}
}
