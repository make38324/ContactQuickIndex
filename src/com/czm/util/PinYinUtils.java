package com.czm.util;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtils {
	public static String getPinYin(String hanzi){
		String pinyin = "";
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		char[] arr = hanzi.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if(Character.isWhitespace(arr[i]))continue;
			if(arr[i]>127){
				try {
					//由于多音字的存在，单 dan shan
					String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
					
					if(pinyinArr!=null){
						pinyin += pinyinArr[0];
					}else {
						pinyin += arr[i];
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					pinyin += arr[i];
				}
			}else {
				pinyin += arr[i];
			}
		}
		return pinyin;
	}
	public static String getPinYin2(String hanzi){
		String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		String s=getPinYin(hanzi);
		s=s.substring(0,1);
		List<String> asList = Arrays.asList(indexArr);
		if(asList.contains(s)){
			return s;
		}
		return "#";
	}
}
