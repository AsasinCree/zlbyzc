package zlbyzc.sub3.duozhunze;

public class Scheme {

	private int id;
	private String name;
	private String descripition;
	private String score;
	private int descition_id;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getDescition_id() {
		return descition_id;
	}
	public void setDescition_id(int descition_id) {
		this.descition_id = descition_id;
	}
	@Override
	public String toString() {
		return "Scheme [id=" + id + ", name=" + name + ", descripition="
				+ descripition + ", score=" + score + ", descition_id="
				+ descition_id + "]";
	}
	
	
}
