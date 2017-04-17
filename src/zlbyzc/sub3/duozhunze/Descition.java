package zlbyzc.sub3.duozhunze;

public class Descition {

	private int id;
	private String name;
	private String Consistency;
	private String VarScheme;
	private String varLastConsistency;
	
	public String getVarLastConsistency() {
		return varLastConsistency;
	}
	public void setVarLastConsistency(String varLastConsistency) {
		this.varLastConsistency = varLastConsistency;
	}
	public String getVarScheme() {
		return VarScheme;
	}
	public void setVarScheme(String varScheme) {
		VarScheme = varScheme;
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
	public String getConsistency() {
		return Consistency;
	}
	public void setConsistency(String consistency) {
		Consistency = consistency;
	}
	@Override
	public String toString() {
		return "Descition [id=" + id + ", name=" + name + ", Consistency="
				+ Consistency + "]";
	}
	
	
	
}
