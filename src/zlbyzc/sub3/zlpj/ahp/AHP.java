package zlbyzc.sub3.zlpj.ahp;

import Jama.*;


public class AHP {
	
	public static double[] RI={0,0,0.58,0.9,1.12,1.24,1.32,1.41,1.45,1.49,1.51};
	private int planNum; //方案数量
	private int factorNum; //准则数量
	private String aim_name;  //目标名称
	private String[] factor_name_array; //准则名称数组
	private String[] plan_name_array; //方案名称数组
	
	private judgeMatrix factorMat; //各个准则对目标的判断矩阵，大小为factorNum*factorNum。
	private judgeMatrix[] planMat; //各个方案对准则的判断矩阵，共factorNum个，每个为planNum*planNum的矩阵。
	private Matrix planWeights; //runAHP计算得到
	
	public AHP(int plan_num, int factor_num){
		planNum = plan_num;
		factorNum = factor_num;
		planMat = new judgeMatrix[factor_num];
	}
	
	public AHP(String aim,String[] factorArray,String[] planArray){
		aim_name = aim;
		factor_name_array = factorArray;
		plan_name_array = planArray;
		factorNum = factorArray.length;
		planNum = planArray.length;
		planMat = new judgeMatrix[factorNum];
	}
	
	//读入准则层判断矩阵（二维数组）
	public void read_factorMat(double[][] factor_array2d){
		this.factorMat = new judgeMatrix(factor_array2d);
	}
	
	//读入方案层判断矩阵（二维数组）
	//plan_array3d[i]为第i个判断矩阵
	public void read_planMat(double[][][] plan_array3d){
        if( plan_array3d.length!=this.factorNum){
            throw new IllegalArgumentException("plan mat has error dimensions!");
        }
        for (int i=0;i<this.factorNum;i++){
        	this.planMat[i] = new judgeMatrix(plan_array3d[i]);
        }
	}
	
	//所有矩阵是否满足一致性检验
	public boolean checkConsistency(){
		//检查factorMat
		if(!factorMat.check_consistency()){
			//return false;
		}
		//检查planMat[]
		for(int i=0;i<factorNum;i++){
			if(!planMat[i].check_consistency()){
				return false;
			}
		}
		return true;
	}
	
	//A_weight_vec: factorNum*1.  
	//B_weight_mat: planNum*factorNum.  B_weight_mat第i列表示planMat[i]最大特征值对应的归一化特征向量。
	//plan_weight_vec: B_weight_mat*A_weight_vec, planNum*1. 表示planNum个plan对于aim的权值。
	public Matrix run_ahp(){
		assert(checkConsistency());
		Matrix A_weight_vec = new Matrix(factorNum,1);
		for(int i=0;i<1;i++){
			double[] d = factorMat.eig().getRealEigenvalues();
			double maxV = 0;
			double sumV = 0;
			int maxid = 0;
			for(int j=0;j<d.length;j++){
				if(d[j]>maxV){
					maxV = d[j];
					maxid = j;
				}
			}
			Matrix m = factorMat.eig().getV().getMatrix(0, factorNum-1, maxid, maxid);
			for(int j=0;j<d.length;j++) sumV += m.getColumnPackedCopy()[j]; 
			A_weight_vec.setMatrix(0, factorNum-1, i, i, m.times(1/sumV));
		}
		
		Matrix B_weight_mat = new Matrix(planNum,factorNum);
		for(int i=0;i<factorNum;i++){
			double[] d = planMat[i].eig().getRealEigenvalues();
			double maxV = 0;
			double sumV = 0;
			int maxid = 0;
			for(int j=0;j<d.length;j++){
				if(d[j]>maxV){
					maxV = d[j];
					maxid = j;
				}
			}
			Matrix m = planMat[i].eig().getV().getMatrix(0, planNum-1, maxid, maxid);
			for(int j=0;j<d.length;j++) sumV += m.getColumnPackedCopy()[j]; 
			B_weight_mat.setMatrix(0, planNum-1, i, i, m.times(1/sumV));
		}
		planWeights = B_weight_mat.times(A_weight_vec);
		return planWeights.copy();
	}
	
	
	public String toString() {
		String s ="";
		s += "目标: "+aim_name;
		s += "\n准则: ";
		for (String  factor_name: factor_name_array) {
			s += factor_name+" ";
		}
		s += "\n方案: ";
		for (String  plan_name: plan_name_array) {
			s += plan_name+" ";
		}
		s += "\n\n准则层判断矩阵: \n" + factorMat;
		s += "\n方案层判断矩阵: \n";
		for(int i=0;i<factorNum;i++){
			s += "准则: "+factor_name_array[i]+"\n";
			s += planMat[i]+"\n";
		}
		
		//检查planWeights是否有值
		if(planWeights==null){
			System.out.println("还未计算planWeights！！！");
			return s;
		}
		s  += "方案权重\n";
		for(int i=0;i<planNum;i++){
			s += plan_name_array[i]+": "+String.format("%1.3f", planWeights.get(i, 0))+"\n";
		}
		return s;
	}	
	
	public String getAim_name() {
		return aim_name;
	}

	public void setAim_name(String aim_name) {
		this.aim_name = aim_name;
	}

	public String[] getFactor_name_array() {
		return factor_name_array;
	}

	public void setFactor_name_array(String[] factor_name_array) {
		this.factor_name_array = factor_name_array;
	}

	public String[] getPlan_name_array() {
		return plan_name_array;
	}

	public void setPlan_name_array(String[] plan_name_array) {
		this.plan_name_array = plan_name_array;
	}

	public Matrix getPlanWeights() {
		return planWeights;
	}

	public void setPlanWeights(Matrix planWeights) {
		this.planWeights = planWeights;
	}

	public int getPlanNum() {
		return planNum;
	}
	public void setPlanNum(int planNum) {
		this.planNum = planNum;
	}
	
	public int getFactorNum() {
		return factorNum;
	}
	public void setFactorNum(int factorNum) {
		this.factorNum = factorNum;
	}	

	public void setFactorMat(judgeMatrix factorMat) {
		this.factorMat = factorMat;
	}
	
	public judgeMatrix getFactorMat() {
		return factorMat;
	}

	public void setPlanMat(judgeMatrix[] planMat) {
		this.planMat = planMat;
	}
	
	public judgeMatrix[] getPlanMat() {
		return planMat;
	}
	
	public static void main(String[] args) {
		String aim="选择旅游地";
		String[] factors={"风景","费用","时间花费","路程","风险"};
		String[] plans={"成都","武汉","杭州",};
		//int plan_num = 3;
		//int factor_num = 5;
		double[][] factor_array = {{1    ,1/2., 4, 3,  3},
				                 {2    ,1   , 7, 5,  5},
				                 {1/4. ,1/7., 1, 1/2.,1/3.},
				                 {1/3. ,1/5., 2, 1,  1},
				                 {1/3. ,1/5., 3, 1,  1}};
		double[][][] plan_array3d ={{{1,2,5},{1/2.0,1,2},{1/5.,1/2.,1}},
									{{1,1/3.,1/8.},{3,1,1/3.},{8,3,1}},
									{{1,1,3},{1,1,3},{1/3.,1/3.,1}},
									{{1,3,4},{1/3.,1,1},{1/4.,1,1}},
									{{1,1,1/4.},{1,1,1/4.},{4,4,1}}  
								   };

		AHP ahp = new AHP(aim,factors,plans);
		ahp.read_factorMat(factor_array);
		//System.out.println("factorMat:\n"+ahp.factorMat.toString());

		ahp.read_planMat(plan_array3d);
		//System.out.println(ahp.factorNum+" planMat:");
		//for(int i=0;i<ahp.factorNum;i++){
		//	System.out.println(ahp.planMat[i].toString());
		//}

		if(ahp.checkConsistency()){
			System.out.println("Consistent!");
		}
		else
			System.out.println("not Consistent!");
		
		ahp.run_ahp();
		
		System.out.println(ahp);
	}

}
