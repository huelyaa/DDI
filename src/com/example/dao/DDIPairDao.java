package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.db.DBHelper;
import com.example.mapper.DDIPairMapper;
import com.example.model.DDIPair;
import com.example.model.ObjectItem;
import com.example.model.PercipitantItem;

public class DDIPairDao extends AbstractDao {

	public DDIPairDao(Context context) {
		super(context);
	}

	public boolean insert(DDIPair ddiPair) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();

		contentvalues.put(DBHelper.COLUMN_ID, ddiPair.getId());
		contentvalues.put(DBHelper.COLUMN_NAME, ddiPair.getName());
		contentvalues.put(DBHelper.DDI_COLUMN_STATUS, ddiPair.getStatus()
				.toString());
		contentvalues.put(DBHelper.COLUMN_MY_DDI,
				String.valueOf(ddiPair.getMyDdi()));
		contentvalues
				.put("fk_objectClass_id", ddiPair.getObjectClass().getId());
		contentvalues.put("fk_percipitantClass_id", ddiPair
				.getPercipitantClass().getId());
		db.insert(DBHelper.DDI_TABLE_NAME, null, contentvalues);
		ObjectClassDao objectClassDao = new ObjectClassDao(context);
		if (ddiPair.getObjectClass() != null) {
			objectClassDao.insert(ddiPair.getObjectClass());
		}
		PercipitantClassDao percipitantClassDao = new PercipitantClassDao(
				context);
		if (ddiPair.getPercipitantClass() != null) {
			percipitantClassDao.insert(ddiPair.getPercipitantClass());
		}
		if (ddiPair.getObjectClass().getObjectItems() != null) {
			for (ObjectItem objectItem : ddiPair.getObjectClass()
					.getObjectItems()) {
				objectClassDao.addObjectItem(ddiPair.getObjectClass(),
						objectItem);
			}
		}
		if (ddiPair.getPercipitantClass().getPercipitantItems() != null) {

			for (PercipitantItem percipitantItem : ddiPair
					.getPercipitantClass().getPercipitantItems()) {
				percipitantClassDao.addPercipitantItem(
						ddiPair.getPercipitantClass(), percipitantItem);
			}
		}
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery(
				"select id as _id, name as suggest_text_1 from "
						+ DBHelper.DDI_TABLE_NAME + " where id=" + id + "",
				null);
		return res;
	}

	public Cursor getDataForDisplay(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + DBHelper.DDI_TABLE_NAME
				+ " where id=" + id + "", null);
		return res;
	}

	public DDIPair getDDIPairById(long id) {
		List<DDIPair> ddiPair = resToDDI(getDataForDisplay(id));
		if (ddiPair != null && ddiPair.size() == 1) {
			return ddiPair.get(0);
		}
		return null;
	}

	public int nummberOfRows() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				DBHelper.DDI_TABLE_NAME);
		return numRows;
	}

	public boolean update(long id, String name, String info) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBHelper.COLUMN_NAME, name);
		contentvalues.put(DBHelper.DDI_COLUMN_STATUS, info);
		db.update(DBHelper.DDI_TABLE_NAME, contentvalues, "id = ? ",
				new String[] { Long.toString(id) });
		return true;
	}

	public Integer delete(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.delete(DBHelper.DDI_TABLE_NAME, "id = ? ",
				new String[] { Long.toString(id) });
	}

	public List<DDIPair> getAll() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + DBHelper.DDI_TABLE_NAME,
				null);
		return resToDDI(res);
	}

	public List<DDIPair> getAllMyDDIs() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + DBHelper.DDI_TABLE_NAME
				+ " where " + DBHelper.COLUMN_MY_DDI + " ='true'", null);
		return resToDDI(res);
	}

	public Cursor getAllAsCursor() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery(
				"select id as _id, name as suggest_text_1 from "
						+ DBHelper.DDI_TABLE_NAME, null);
		return res;
	}

	private List<DDIPair> resToDDI(Cursor res) {
		res.moveToFirst();
		List<DDIPair> ddiPairs = new ArrayList<DDIPair>();
		DDIPairMapper mapper = new DDIPairMapper();
		while (res.isAfterLast() == false) {
			DDIPair ddiPair = mapper.map(res);
			ddiPairs.add(ddiPair);
			res.moveToNext();
		}
		return ddiPairs;
	}

	public Cursor getDDIPairByQuery(String item1, String item2) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db
				.rawQuery(
						"SELECT ddi.ID as ID, ddi.NAME as NAME, ddi.STATUS as STATUS, ddi.MY_DDI as MY_DDI FROM "
								+ DBHelper.DDI_TABLE_NAME
								+ " ddi INNER JOIN "
								+ DBHelper.OBJECT_CLASS_TABLE_NAME
								+ " oc ON ddi.fk_objectClass_id=oc.ID INNER JOIN "
								+ DBHelper.OBJECT_CLASS_OBJECT_ITEM_TABLE_NAME
								+ " ocoi ON ocoi.OBJECT_CLASS_ID=oc.ID INNER JOIN "
								+ DBHelper.OBJECT_ITEM_TABLE_NAME
								+ " oi ON oi.ID=ocoi.OBJECT_ITEM_ID WHERE (oi.name = '"
								+ item1
								+ "' OR oi.name = '"
								+ item2
								+ "'"
								+ ") AND ddi.ID IN (SELECT ddi.ID FROM "
								+ DBHelper.DDI_TABLE_NAME
								+ " ddi INNER JOIN "
								+ DBHelper.PERCIPITANT_CLASS_TABLE_NAME
								+ " pc ON ddi.fk_percipitantClass_id=pc.ID INNER JOIN "
								+ DBHelper.PERCIPITANT_CLASS_PERCIPITANT_ITEM_TABLE_NAME
								+ " pcoi ON pcoi.PERCIPITANT_CLASS_ID=pc.ID INNER JOIN "
								+ DBHelper.PERCIPITANT_ITEM_TABLE_NAME
								+ " pi ON pi.ID=pcoi.PERCIPITANT_ITEM_ID WHERE pi.name = '"
								+ item2 + "' OR pi.name = '" + item1 + "')",
						null);

		return res;
	}

	public DDIPair checkIfInteractionExist(String item1, String item2) {
		Cursor cursor = getDDIPairByQuery(item1, item2);

		List<DDIPair> ddi = resToDDI(cursor);
		if (ddi.size() != 0) {
			return ddi.get(0);
		}
		return null;
	}


	public DDIPair getDDIPairByName(String name) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + DBHelper.DDI_TABLE_NAME
				+ " WHERE NAME='" + name + "'", null);
		if (res.getCount() != 0) {
			return resToDDI(res).get(0);
		}
		return null;
	}

}
