package cn.org.fjiot.infusion.util;

import java.sql.Timestamp;

public class TimeUtil {

	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String next() {
		return "0dac";
	}

}
