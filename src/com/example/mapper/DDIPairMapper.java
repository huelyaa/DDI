package com.example.mapper;

import android.database.Cursor;
import com.example.db.DBHelper;
import com.example.model.DDIPair;
import com.example.model.Status;

public class DDIPairMapper {
	public DDIPair map(Cursor res) {
		DDIPair ddiPair = new DDIPair();
		ddiPair.setId(res.getString(res.getColumnIndex(DBHelper.COLUMN_ID)));
		ddiPair.setName(res.getString(res.getColumnIndex(DBHelper.COLUMN_NAME)));
		ddiPair.setMyDdi(Boolean.valueOf(res.getString(res
				.getColumnIndex(DBHelper.COLUMN_MY_DDI))));
		ddiPair.setStatus(Status.valueOf(res.getString(res
				.getColumnIndex(DBHelper.DDI_COLUMN_STATUS))));
		return ddiPair;
	}
}
