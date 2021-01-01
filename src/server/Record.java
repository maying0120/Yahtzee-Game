package server;

public class Record {
	private String name;
	private String date;
	private String data;

	public Record(String name, String date, String data) {
		this.name = name;
		this.date = date;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
