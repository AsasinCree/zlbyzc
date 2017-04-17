package zlbyzc.sub3.duozhunze;

public class Expert_Descition {

	private int id;
	private int expert_id;
	private int descition_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExpert_id() {
		return expert_id;
	}
	public void setExpert_id(int expert_id) {
		this.expert_id = expert_id;
	}
	public int getDescition_id() {
		return descition_id;
	}
	public void setDescition_id(int descition_id) {
		this.descition_id = descition_id;
	}
	@Override
	public String toString() {
		return "Expert_Descition [id=" + id + ", expert_id=" + expert_id
				+ ", descition_id=" + descition_id + "]";
	}
	
	
}
