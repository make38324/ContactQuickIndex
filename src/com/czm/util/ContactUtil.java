package com.czm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.czm.pojo.ContactInfo;

/*
 * project:ContactQuickIndex
 * package:com.czm.util
 * author:曹智民
 * data:2015年6月4日 下午10:54:27
 */
public class ContactUtil {
	/**
	 * 得到所有联系人信息的集合
	 */
	public static List<ContactInfo> getContactInfos(Context context) {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		ContentResolver resolver = context.getContentResolver();
		String[] projection = { Phone.DISPLAY_NAME, Phone.NUMBER };
		Cursor cursor = resolver.query(Phone.CONTENT_URI, projection, null,
				null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			list.add(new ContactInfo(name, number));
		}
		Collections.sort(list);
		cursor.close();
		return list;
	}
}
