package ua.leomovskii.tg.mybucketlist.db;

public class Wish {

	private long ownerId;
	private String title;
	private boolean checked;

	public Wish(long ownerId, String title, boolean checked) {
		this.ownerId = ownerId;
		this.title = title;
		this.checked = checked;
	}

	public Wish(long ownerId, String title) {
		this(ownerId, title, false);
	}

	public long getOwnerId() {
		return ownerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}