package zlbyzc.sub3.duozhunze;

public class Expert_field {

	private int id;
	private int expert_id;
	private int field_id;
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
	public int getField_id() {
		return field_id;
	}
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	@Override
	public String toString() {
		return "Expert_field [id=" + id + ", expert_id=" + expert_id
				+ ", field_id=" + field_id + "]";
	}
	
	
}
