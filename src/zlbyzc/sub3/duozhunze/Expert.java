package zlbyzc.sub3.duozhunze;

public class Expert {

	private int id;
	private String name;
	private String Sex;
	private String Title;
	private String WorkUnit;
	private String Number;
	
	
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	@Override
	public String toString() {
		return "Expert [id=" + id + ", name=" + name + ", Sex=" + Sex
				+ ", Title=" + Title + ", WorkUnit=" + WorkUnit + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getWorkUnit() {
		return WorkUnit;
	}
	public void setWorkUnit(String workUnit) {
		WorkUnit = workUnit;
	}
	
	
}
