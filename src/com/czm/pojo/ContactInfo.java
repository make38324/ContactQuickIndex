package com.czm.pojo;

import com.czm.util.PinYinUtils;

/*
 * project:safeapp03
 * package:com.czm.pojo
 * author:曹智民
 * data:2015年5月25日 下午7:44:21
 */
public class ContactInfo implements Comparable<ContactInfo>{
	private String name;
	private String number;
	private String pinyin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ContactInfo(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}
	public String getPinyin(){
		return PinYinUtils.getPinYin2(name);
	}
	public ContactInfo(String name) {
		super();
		this.name = name;
		this.number="12345";
	}
	public int compareTo(ContactInfo another) {
		/*
		 * >0
		 * =0
		 * <0
		 */
		//根据pinyin来比较,#号必须在前面
		if(this.getPinyin().equals("#")){
			return -1;
		}
		return this.getPinyin().compareTo(another.getPinyin());
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
