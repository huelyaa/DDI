package com.example.mapper;

import android.database.Cursor;
import com.example.db.DBHelper;
import com.example.model.PercipitantClass;

public class PercipitantClassMapper {
	public PercipitantClass map(Cursor cursor) {
		cursor.moveToFirst();
		PercipitantClass PercipitantClass = new PercipitantClass();
		PercipitantClass.setId(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_ID)));
		PercipitantClass.setName(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_NAME)));
		return PercipitantClass;
	}
}
