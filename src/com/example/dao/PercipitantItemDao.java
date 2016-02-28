package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.db.DBHelper;
import com.example.mapper.PercipitantItemMapper;
import com.example.model.PercipitantItem;

public class PercipitantItemDao extends AbstractDao {

	private PercipitantItemMapper mapper = new PercipitantItemMapper();

	public PercipitantItemDao(Context context) {
		super(context);
	}

	public boolean insert(PercipitantItem percipitantItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.COLUMN_ID, percipitantItem.getId());
		contentvalues.put(DBHelper.COLUMN_NAME, percipitantItem.getName()
				.trim());

		db.insert(DBHelper.PERCIPITANT_ITEM_TABLE_NAME, null, contentvalues);
		return true;
	}

	public Cursor getData() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME, null);
		return res;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery(
				"select * from " + DBHelper.PERCIPITANT_ITEM_TABLE_NAME
						+ " where id=" + id + "", null);
		return res;
	}

	public Cursor getDataByName(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME + " where name='" + name
				+ "'", null);
		return res;
	}

	public Cursor getData(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME + " where name LIKE '"
				+ name + "%'", null);
		return res;
	}

	public List<PercipitantItem> getPercipitantItemsByName(String name) {
		return resToPercipitantItem(getData(name));
	}

	public PercipitantItem getPercipitantItemByName(String name) {
		List<PercipitantItem> percipitantItems = resToPercipitantItem(getDataByName(name));
		if (percipitantItems != null && percipitantItems.size() == 1) {
			return percipitantItems.get(0);
		}
		return null;
	}

	public List<String> getPercipitantItemNames(String name) {
		Cursor cursor = null;
		if (name == null) {
			cursor = getData();
		} else {
			cursor = getData(name);
		}

		List<PercipitantItem> obejctItems = resToPercipitantItem(cursor);
		List<String> names = new ArrayList<String>();
		for (PercipitantItem PercipitantItem : obejctItems) {
			names.add(PercipitantItem.getName());
		}
		return names;
	}

	public List<PercipitantItem> getPercipitantItemByDDIPairId(String id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT pi.ID, pi.NAME FROM "
				+ DBHelper.DDI_TABLE_NAME + " ddi INNER JOIN "
				+ DBHelper.PERCIPITANT_CLASS_TABLE_NAME
				+ " pc ON ddi.fk_percipitantClass_id=pc.ID INNER JOIN "
				+ DBHelper.PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME
				+ " pcoi ON pcoi.PERCIPITANT_CLASS_ID=pc.ID INNER JOIN "
				+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME
				+ " pi ON pi.ID=pcoi.PERCIPITANT_ITEM_ID WHERE ddi.ID = " + id,
				null);
		return resToPercipitantItem(res);
	}

	public String getPercipitantItemByDDIPairIdAsString(String id) {
		List<PercipitantItem> items = getPercipitantItemByDDIPairId(id);
		String string = "";
		int i = 0;
		for (PercipitantItem item : items) {
			string += item.getName();
			if (++i < items.size()) {
				string += "; ";
			}
		}
		return string;
	}

	public List<PercipitantItem> resToPercipitantItem(Cursor res) {
		res.moveToFirst();
		List<PercipitantItem> PercipitantItems = new ArrayList<PercipitantItem>();
		while (res.isAfterLast() == false) {
			PercipitantItem PercipitantItem = mapper.map(res);
			PercipitantItems.add(PercipitantItem);
			res.moveToNext();
		}
		return PercipitantItems;
	}

	public int nummberOfRows() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				DBHelper.PERCIPITANT_ITEM_TABLE_NAME);
		return numRows;
	}

	public boolean update(Integer id, String name, String info) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.COLUMN_NAME, name);
		db.update(DBHelper.PERCIPITANT_ITEM_TABLE_NAME, contentvalues,
				"id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer delete(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.delete(DBHelper.PERCIPITANT_ITEM_TABLE_NAME, "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAll() {
		// TODO Auto-generated method stub
		ArrayList array_list = new ArrayList();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "
				+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(DBHelper.COLUMN_NAME)));
			res.moveToNext();
		}

		return array_list;
	}

}
