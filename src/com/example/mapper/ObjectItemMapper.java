package com.example.mapper;

import android.database.Cursor;
import com.example.db.DBHelper;
import com.example.model.ObjectItem;

public class ObjectItemMapper {
	public ObjectItem map(Cursor cursor) {
		ObjectItem objectItem = new ObjectItem();
		objectItem.setId(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_ID)));
		objectItem.setName(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_NAME)));
		return objectItem;
	}
}
