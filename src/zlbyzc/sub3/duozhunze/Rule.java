package zlbyzc.sub3.duozhunze;

public class Rule {
	private int id;
	private String name;
	private String descripition;
	private String weight;
	private int descrition_id;
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
	public String getDescripition() {
		return descripition;
	}
	public void setDescripition(String descripition) {
		this.descripition = descripition;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public int getDescrition_id() {
		return descrition_id;
	}
	public void setDescrition_id(int descrition_id) {
		this.descrition_id = descrition_id;
	}
	@Override
	public String toString() {
		return "Rule [id=" + id + ", name=" + name + ", descripition="
				+ descripition + ", weight=" + weight + ", descrition_id="
				+ descrition_id + "]";
	}
}
