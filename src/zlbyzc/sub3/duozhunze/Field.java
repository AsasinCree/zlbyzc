package zlbyzc.sub3.duozhunze;

public class Field {

	private int id;
	private String name;
	private String descipition;
	private String Father_id;
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
	public String getDescipition() {
		return descipition;
	}
	public void setDescipition(String descipition) {
		this.descipition = descipition;
	}
	public String getFather_id() {
		return Father_id;
	}
	public void setFather_id(String father_id) {
		Father_id = father_id;
	}
	@Override
	public String toString() {
		return "Field [id=" + id + ", name=" + name + ", descipition="
				+ descipition + ", Father_id=" + Father_id + "]";
	}
	
	
	
}
