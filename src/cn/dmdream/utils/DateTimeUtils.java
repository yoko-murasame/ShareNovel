package cn.dmdream.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	/**
	 * 返回当前时间字符串 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentFormatDateTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
}
