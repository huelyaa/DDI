package com.example.mapper;

import android.database.Cursor;
import com.example.db.DBHelper;
import com.example.model.ObjectClass;

public class ObjectClassMapper {
	public ObjectClass map(Cursor cursor) {
		cursor.moveToFirst();
		ObjectClass objectClass = new ObjectClass();
		objectClass.setId(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_ID)));
		objectClass.setName(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_NAME)));
		return objectClass;
	}
}
