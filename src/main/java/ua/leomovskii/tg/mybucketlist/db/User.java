package ua.leomovskii.tg.mybucketlist.db;

public class User {
	private long id;
	private String name, lang;

	public User(long id, String name, String lang) {
		this.id = id;
		this.name = name;
		this.lang = lang;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}