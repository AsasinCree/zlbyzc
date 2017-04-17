package zlbyzc.sub3.duozhunze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Dao extends BaseDao {

	private static Dao dao = null;

	public static Dao getInstance() {
		if (dao == null)
			dao = new Dao();
		return dao;
	}

	

	// TbDutyInfo
	public List queryDutyInfoOfAccessionDateMax(String date) {
		return this.queryList("from TbDutyInfo where accessionDate <=to_date('"
				+ date + "','yyyy-mm-dd')");
	}

	// AccessionForm
	public List queryAccessionForm() {
		return this.queryList("from TbAccessionForm");
	}

	public Object queryAccessionFormByName(String name) {
		return this.queryObject("from TbAccessionForm where name='" + name
				+ "'");
	}

	public void initAccessionForm() {
		deleteOfBatch("delete from TbAccessionForm where id >2");
	}

	// AccountItem
	public List queryAccountItem() {
		return this.queryList("from TbAccountItem");
	}

	public List queryAccountItemUsedTimecard() {
		return this.queryList("from TbAccountItem where isTimecard='��'");
	}

	public Object queryAccountItemByName(String name) {
		return this.queryObject("from TbAccountItem where name='" + name + "'");
	}

	public Object queryAccountItemByNameUnit(String name, String unit) {
		return this.queryObject("from TbAccountItem where name='" + name
				+ "' and unit='" + unit + "'");
	}

	public void initAccountItem() {
		deleteOfBatch("delete from TbAccountItem where id >6");
	}

	// BringUpContent
	public List queryBringUpContent() {
		return this.queryList("from TbBringUpContent");
	}

	public Object queryBringUpContentById(String id) {
		return this.queryObject("from TbBringUpContent where id=" + id);
	}

	public void initBringUpContent() {
		deleteOfBatch("delete from TbBringUpContent");
	}

	// BringUpOntent
	public void initBringUpOntent() {
		deleteOfBatch("delete from TbBringUpOntent");
	}

	// Dept
	public List queryDept() {
		return this.queryList("from TbDept");
	}

	public Object queryDeptById(int id) {
		return this.queryObject("from TbDept where id=" + id);
	}

	public Object queryDeptByName(String name) {
		return this.queryObject("from TbDept where name='" + name + "'");
	}

	public void initDept() {
		deleteOfBatch("delete from TbDept where id>8");
	}

	// Duty
	public List queryDuty() {
		return this.queryList("from TbDuty");
	}
	
	//根据编号查找准则
	public List queryruleByNumber(int number) {
		return this.queryList("from Rule where intDescition='" + number + "'");
	}
	//查找决策
	public List querydescition(String keyword,int i) {
		double n=0.0;
		boolean k=true;
		try {
			 n=Double.parseDouble(keyword); 
		} catch (Exception e) {
			// TODO: handle exception
			k=false;
		} 
		if(k){
			return this.queryList("from Descition as m  where m.name like '%"+keyword+"%' or m.VarScheme like '%" + keyword + "%' or m.varLastConsistency >= '%"+n+"%'");
			}else{
			return this.queryList("from Descition as m  where m.name like '%"+keyword+"%' or m.VarScheme like '%" + keyword + "%'");
			}
	}
	
	public int querydescition(String keyword) {
		double n=0.0;
		boolean k=true;
		try {
			 n=Double.parseDouble(keyword); 
		} catch (Exception e) {
			// TODO: handle exception
			k=false;
		}
		int a=0;
		List<Descition>s=new ArrayList();
		if(k){
		s=this.queryList("from Descition as m  where m.name like '%"+keyword+"%' or m.VarScheme like '%" + keyword + "%' or m.varLastConsistency >= '%"+n+"%'");
		}else{
		s=this.queryList("from Descition as m  where m.name like '%"+keyword+"%' or m.VarScheme like '%" + keyword + "%'");
		}
		for(Descition d:s){
			a++;
			}
		return a; 
	}
	//根据编号查找方案
	public List queryschemeByNumber(int number) {
		return this.queryList("from Scheme where intDescitionId='" + number + "'");
	}
	//根据编号和方案名查找方案
		public Object queryscheme(int number,String name) {
			Object d = null;
			for(Object s: this.queryList("from Scheme where intDescitionId='" + number + "' and varName='" + name + "'"))
				{d=s;}
			 return d;
		}
	//根据关键字查找专家
		public List queryExpertByNumber(String number) {
			return this.queryList("from Expert as m where intDescitionId='" + number + "'");
		}
	//删除方案 根据id
	public void delefangan(int number) {
		deleteOfBatch("delete from Scheme where id='" + number + "'");
	}
	//删除准则 根据id
		public void delezhunze(int number) {
			deleteOfBatch("delete from Rule where id='" + number + "'");
		}
	//根据编号查找专家
	public Object queryexpertByNumber(String number) {
		return this.queryObject("from Expert where intNumber='" + number + "'");
	}
	//根据名字查找决策
	public boolean queryDescitionbyName(String Name){
		if(this.queryObject("from Descition where varName='" + Name + "'")!=null)
		return true;
		return false;
	}
	public Object queryDescitionbyName2(String Name){
		return  this.queryObject("from Descition where varName='" + Name + "'");
	}
	//根据id查找决策
	public Object queryDescitionById(int number) {
		return this.queryObject("from Descition where id='" + number + "'");
	}
	//根据专家id查找专家
		public Object queryexpertByid(int number) {
			return this.queryObject("from Expert where id='" + number + "'");
		}
	//根据决策id查找参会专家
		public List<Expert_Descition> queryexpertByDescitionId(int number) {
			return this.queryList("from Expert_Descition where intDescition='" + number + "'");
		}

	public void initDuty() {
		deleteOfBatch("delete from TbDuty where id>4");
	}

	// 分页查找专家
	public List queryExpert(int i,String keyWord) {
			return this.queryList("from Expert where (varName='" + keyWord + "' or varSex ='" + keyWord + "' or varTitle ='" + keyWord + "' or varWorkUnit ='" + keyWord + "' or intNumber ='" + keyWord + "')",i);
	}
	public List queryExpert(int i){return this.queryList("from Expert",i);}
	//读取专家表个数+1
	public int queryExpertLength(String keyWord) {
		List<Expert> s;
		if(!keyWord.equals("")){
			s=this.queryList("from Expert where (varName='" + keyWord + "' or varSex ='" + keyWord + "' or varTitle ='" + keyWord + "' or varWorkUnit ='" + keyWord + "' or intNumber ='" + keyWord + "')");
		}else
			s=this.queryList("from Expert");
		int i=0;
		for(Iterator pit=s.iterator();pit.hasNext();){
			i++;
			pit.next();
		}
		return i;
	}

	public Object queryRecordByNum(String num) {
		return this.queryObject("from TbRecord where record_number='" + num
				+ "'");
	}

	public Object queryRecordOfMaxRecordNum() {
		return this.queryObject("select max(recordNumber) from TbRecord");
	}

	public Object queryRecordOfMinAccessionDate() {
		return this.queryObject("select min(accessionDate) from TbDutyInfo");
	}

	public void initRecord() {
		deleteOfBatch("delete from TbRecord");
	}

	// Reckoning
	public List queryReckoning() {
		return this.queryList("from TbReckoning");
	}

	// Manager
	public List queryManager() {
		return this.queryList("from TbManager");
	}

	public List queryManagerOfNatural() {
		return this.queryList("from TbManager where state='��'");
	}

	public void initManager() {
		deleteOfBatch("delete from TbManager");
	}

	// Nation
	public List queryNation() {
		return this.queryList("from TbNation");
	}

	public Object queryNationByName(String name) {
		return this.queryObject("from TbNation where name='" + name + "'");
	}

	public void initNation() {
		deleteOfBatch("delete from TbNation where id>4");
	}

	// NativePlace
	public List queryNativePlace() {
		return this.queryList("from TbNativePlace");
	}

	public Object queryNativePlaceByName(String name) {
		return this.queryObject("from TbNativePlace where name='" + name + "'");
	}

	public void initNativePlace() {
		deleteOfBatch("delete from TbNativePlace where id>6");
	}

	// Reckoning
	public void initReckoning() {
		deleteOfBatch("delete from TbReckoning where id>2");
	}

	// ReckoningInfo
	public void initReckoningInfo() {
		deleteOfBatch("delete from TbReckoningInfo");
	}

	// ReckoningList
	public void initReckoningList() {
		deleteOfBatch("delete from TbReckoningList");
	}

	// RewardsAndPunishment
	public void initRewardsAndPunishment() {
		deleteOfBatch("delete from TbRewardsAndPunishment");
	}

	// Timecard
	public void initTimecard() {
		deleteOfBatch("delete from TbTimecard");
	}

	// PersonalInfo
	public void initPersonalInfo() {
		deleteOfBatch("delete from TbPersonalInfo");
	}

	// DutyInfo
	public void initDutyInfo() {
		deleteOfBatch("delete from TbDutyInfo");
	}

}
