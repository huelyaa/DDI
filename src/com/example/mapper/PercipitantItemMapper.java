package com.example.mapper;

import android.database.Cursor;
import com.example.db.DBHelper;
import com.example.model.PercipitantItem;

public class PercipitantItemMapper {
	public PercipitantItem map(Cursor cursor) {
		PercipitantItem percipitantItem = new PercipitantItem();
		percipitantItem.setId(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_ID)));
		percipitantItem.setName(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_NAME)));
		return percipitantItem;
	}
}
