package ua.leomovskii.tg.mybucketlist.db;

import java.util.HashMap;
import java.util.Map;

public enum Intent {

	IDLE(0), CREATE(1), EDIT(2);

	private final int id;

	private static final Map<Integer, Intent> MAP = new HashMap<>();

	private Intent(int id) {
		this.id = id;
	}

	public static Intent fromInt(int intent) {
		return MAP.get(intent);
	}

	public static int toInt(Intent intent) {
		switch (intent) {
		case IDLE:
			return 0;
		case CREATE:
			return 1;
		case EDIT:
			return 2;
		default:
			return 0;
		}
	}

	static {
		for (Intent i : values())
			MAP.put(i.id, i);
	}

}