package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.db.DBHelper;
import com.example.mapper.ObjectItemMapper;
import com.example.model.ObjectItem;

public class ObjectItemDao extends AbstractDao {

	private ObjectItemMapper mapper = new ObjectItemMapper();

	public ObjectItemDao(Context context) {
		super(context);
	}

	public boolean insert(ObjectItem objectItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.COLUMN_ID, objectItem.getId());
		contentvalues.put(DBHelper.COLUMN_NAME, objectItem.getName().trim());

		db.insert(DBHelper.OBJECT_ITEM_TABLE_NAME, null, contentvalues);
		return true;
	}

	public Cursor getData() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_ITEM_TABLE_NAME, null);
		return res;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_ITEM_TABLE_NAME + " where id=" + id + "",
				null);
		return res;
	}

	public Cursor getDataByName(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_ITEM_TABLE_NAME + " where name='" + name
				+ "'", null);
		return res;
	}

	public Cursor getData(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_ITEM_TABLE_NAME + " where name LIKE '" + name
				+ "%'", null);
		return res;
	}

	public List<ObjectItem> getObjectItemsByName(String name) {
		return resToObjectItem(getData(name));
	}

	public ObjectItem getObjectItemByName(String name) {
		List<ObjectItem> items = resToObjectItem(getDataByName(name));
		if (items != null && items.size() == 1) {
			return items.get(0);
		}
		return null;
	}

	public List<String> getObjectItemNames(String name) {
		Cursor cursor = null;
		if (name == null) {
			cursor = getData();
		} else {
			cursor = getData(name);
		}

		List<ObjectItem> obejctItems = resToObjectItem(cursor);
		List<String> names = new ArrayList<String>();
		for (ObjectItem objectItem : obejctItems) {
			names.add(objectItem.getName());
		}
		return names;
	}

	public List<ObjectItem> getObjectItemsByDDIId(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db
				.rawQuery("SELECT oi.ID, oi.NAME FROM "
						+ DBHelper.DDI_TABLE_NAME + " ddi INNER JOIN "
						+ DBHelper.OBJECT_CLASS_TABLE_NAME
						+ " oc ON ddi.fk_objectClass_id=oc.ID INNER JOIN "
						+ DBHelper.OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME
						+ " ocoi ON ocoi.OBJECT_CLASS_ID=oc.ID INNER JOIN "
						+ DBHelper.OBJECT_ITEM_TABLE_NAME
						+ " oi ON oi.ID=ocoi.OBJECT_ITEM_ID WHERE ddi.ID = "
						+ id, null);
		return resToObjectItem(res);
	}

	public String getObjectItemsByDDIIdAsString(String id) {
		List<ObjectItem> items = getObjectItemsByDDIId(id);
		String string = "";
		int i = 0;
		for (ObjectItem item : items) {
			string += item.getName();
			if (++i < items.size()) {
				string += "; ";
			}
		}
		return string;
	}

	public List<ObjectItem> resToObjectItem(Cursor res) {
		res.moveToFirst();
		List<ObjectItem> objectItems = new ArrayList<ObjectItem>();
		while (res.isAfterLast() == false) {
			ObjectItem objectItem = mapper.map(res);
			objectItems.add(objectItem);
			res.moveToNext();
		}
		return objectItems;
	}

	public int nummberOfRows() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				DBHelper.OBJECT_ITEM_TABLE_NAME);
		return numRows;
	}

	public boolean update(Integer id, String name, String info) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.COLUMN_NAME, name);
		db.update(DBHelper.OBJECT_CLASS_TABLE_NAME, contentvalues, "id = ? ",
				new String[] { Integer.toString(id) });
		return true;
	}

	public Integer delete(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.delete(DBHelper.OBJECT_ITEM_TABLE_NAME, "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAll() {
		// TODO Auto-generated method stub
		ArrayList array_list = new ArrayList();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.OBJECT_ITEM_TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(DBHelper.COLUMN_NAME)));
			res.moveToNext();
		}

		return array_list;
	}

}
