package zlbyzc.sub3.duozhunze;

import java.util.Date;

public class Descition {

	private int id;
	private String name;
	private String Consistency;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private String VarScheme;
	private String varLastConsistency;
	private Date date;
	
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
