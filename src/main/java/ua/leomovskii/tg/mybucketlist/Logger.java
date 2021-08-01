package ua.leomovskii.tg.mybucketlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Logger {

	private static DateFormat format;

	static {
		format = new SimpleDateFormat("MM/dd HH:mm:ss", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
	}

	public static void info(String text) {
		log("INFO", text);
	}

	public static void warn(String text) {
		log("WARN", text);
	}

	public static void error(String text) {
		log("ERROR", text);
	}

	private static void log(String level, String text) {
		Date date = new Date(System.currentTimeMillis());
		System.out.printf("[%s] %s: %s\n", format.format(date), level, text);
	}

}