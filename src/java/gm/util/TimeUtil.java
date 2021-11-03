package gm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获得当前时间格式化后的字符串。
	 * @return 以 yyyy-MM-dd HH:mm:ss 形式显示的字符串
	 */
	public static String formatCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
		return sdf.format(new Date());
	}
}
