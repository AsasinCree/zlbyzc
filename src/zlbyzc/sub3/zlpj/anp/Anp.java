package zlbyzc.sub3.zlpj.anp;

import Jama.*;

public class Anp {
	public static double[] RI = { 0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49, 1.51 };
	private int planNum;
	private int factorNum;
	private String aim_name;  //目标名称
	private String[] factor_name_array; //准则名称数组
	private String[] plan_name_array; //方案名称数组
	private Matrix factorMat;
	private Matrix weightMat;
	private Matrix[] plan2factorMat;
	private Matrix[] factor2planMat;
	private Matrix resultMat;

	public Anp(int plan_num, int rule_num) {
		this.planNum = plan_num;
		this.factorNum = rule_num;
	}

	public Anp(String aim, String[] factorArray, String[] planArray) {
		aim_name = aim;
		factor_name_array = factorArray;
		plan_name_array = planArray;
		factorNum = factorArray.length;
		planNum = planArray.length;
	}

	// 从面板读取准则矩阵
	public void read_factorMat(double[][] rule_array2d) {
		this.setFactorMat(new Matrix(rule_array2d));
	}

	// 从面板读取权重矩阵
	public void read_weightMat(double[][] weight_array2d) {
		this.setWeightMat(new Matrix(weight_array2d));
	}

	// 得到判断矩阵，array3d[i]为第i个判断矩阵
	public Matrix[] read_matrix(double[][][] array3d, int num) {
		Matrix[] result = new Matrix[num];
		if (array3d.length != num) {
			throw new IllegalArgumentException("matrix mat has error dimensions!");
		}
		for (int i = 0; i < num; i++) {
			result[i] = new Matrix(array3d[i]);
		}
		return result;
	}

	// 判断一致性
	public static boolean check_one_consistency(Matrix mat) {
		double[] d = mat.eig().getRealEigenvalues();
		double maxV = 0;
		for (int i = 0; i < d.length; i++) {
			if (d[i] > maxV) {
				maxV = d[i];
			}
		}
		double x = (maxV - d.length) / (d.length - 1) / Anp.RI[d.length];
		if (x < 0.1) {
			return true;
		} else {
			return false;
		}
	}

	// 检查矩阵一致性
	public boolean checkConsistency(int num, Matrix[] matrix) {

		for (int i = 0; i < num; i++) {
			if (!check_one_consistency(matrix[i])) {
				return false;
			}
		}
		return true;
	}
	
	//判断列归一
	public boolean checkone(Matrix mat){
		double[] sum = new double[mat.getColumnDimension()];
		// 求每一列的和
		for (int j = 0; j < mat.getColumnDimension(); j++) {
			for (int i = 0; i < mat.getRowDimension(); i++) {
				sum[j] += mat.get(i, j);
			}
		}
		for(int i = 0; i < mat.getColumnDimension(); i++){
			
			 if((float)sum[i]!=1)
				 return false;
		}
		return true;
	}
		

	// 得到最大特征值对应的特征向量所组成的矩阵
	public Matrix getMaxVecMatrix(int m, int n, Matrix[] planMat) {
		Matrix result = new Matrix(m, n);
		for (int i = 0; i < n; i++) {
			double[] d = planMat[i].eig().getRealEigenvalues();
			double maxV = 0;
			double sumV = 0;
			int maxid = 0;
			for (int j = 0; j < d.length; j++) {
				if (d[j] > maxV) {
					maxV = d[j];
					maxid = j;
				}
			}
			Matrix matr = planMat[i].eig().getV().getMatrix(0, m - 1, maxid, maxid);
			for (int j = 0; j < d.length; j++)
				sumV += matr.getColumnPackedCopy()[j];
			result.setMatrix(0, m - 1, i, i, matr.times(1 / sumV));
		}
		return result;
	}

	// 拼凑4个矩阵,并将权重矩阵乘上对应块,其中m5是2×2的权重矩阵
	public Matrix combination(Matrix m1, Matrix m2, Matrix m3, Matrix m5) {
		m1 = m1.times(m5.get(0, 0));
		m2 = m2.times(m5.get(0, 1));
		m3 = m3.times(m5.get(1, 0));
		double[][] zero = new double[planNum][planNum];
		for (int i = 0; i < planNum; i++) {
			for (int j = 0; j < planNum; j++) {
				zero[i][j] = 0;
			}
		}
		Matrix m4 = new Matrix(zero);
		int row = planNum + factorNum;
		Matrix result = new Matrix(row, row);
		result.setMatrix(0, factorNum - 1, 0, factorNum - 1, m1);
		result.setMatrix(0, factorNum - 1, factorNum, row - 1, m2);
		result.setMatrix(factorNum, row - 1, 0, factorNum - 1, m3);
		result.setMatrix(factorNum, row - 1, factorNum, row - 1, m4);
		return result;
	}

	// 列归一化
	public Matrix ColumnNormalization(Matrix matrix) {
		double[] sum = new double[matrix.getColumnDimension()];
		// 求每一列的和
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			for (int i = 0; i < matrix.getRowDimension(); i++) {
				sum[j] += matrix.get(i, j);
			}
		}
		// 用每一列的和除以每一列
		for (int j = 0; j < matrix.getColumnDimension(); j++) {
			for (int i = 0; i < matrix.getRowDimension(); i++) {
				matrix.set(i, j, matrix.get(i, j) / sum[j]);
			}
		}
		return matrix;

	}

	// 判断两个矩阵是否相等
	public boolean isEquals(Matrix m1, Matrix m2) {
		for (int i = 0; i < m1.getRowDimension(); i++) {
			for (int j = 0; j < m1.getColumnDimension(); j++) {
				if (m1.get(i, j) - m2.get(i, j) > 0.001) {
					return false;
				}
			}
		}
		return true;
	}

	// 自乘得到最终结果,count是自乘次数
	public Matrix Multiself(Matrix matrix, int count) {
		Matrix result = new Matrix(matrix.getRowDimension(), 1);

		while (count >= 0) {
			// 每次自乘之前都列归一化
			Matrix temp = ColumnNormalization(matrix);
			// 自乘
			matrix = matrix.times(matrix);
			// 判断是否相等
			if (isEquals(temp, matrix)) {
				result = matrix.getMatrix(0, matrix.getRowDimension() - 1, 0, 0);
				break;
			}
			count--;
		}
		return result;

	}
   
	//得到最终结果
	public Matrix run_ahp(int num) {
		resultMat = new Matrix(planNum + factorNum, 1);
		Matrix m2 = getMaxVecMatrix(factorNum, planNum, factor2planMat);
		Matrix m3 = getMaxVecMatrix(planNum, factorNum, plan2factorMat);
		Matrix combinatiomMat = combination(factorMat, m2, m3, weightMat);
		resultMat = Multiself(combinatiomMat, num);
		return resultMat;		
	}
	
	public String mattoString(Matrix A) {
		String s = "";
		int n=A.getRowDimension();
		int m=A.getColumnDimension();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				s += String.format("%1.3f", A.get(i, j))+"  ";
			}
			s += "\n";
		}
		return s;
	}
	
	@Override
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
		
		s += "\n\n准则权重矩阵: \n" + mattoString(factorMat);
		s += "\n方案对准则判断矩阵: \n";
		for(int i=0;i<factorNum;i++){
			s += "准则: "+factor_name_array[i]+"\n";
			s += mattoString(plan2factorMat[i])+"\n";
		}
		s += "\n准则对方案判断矩阵: \n";
		for(int i=0;i<factorNum;i++){
			s += "方案: "+plan_name_array[i]+"\n";
			s += mattoString(factor2planMat[i])+"\n";
		}
		
		
		//检查planWeights是否有值
		if(resultMat==null){
			System.out.println("还未计算planWeights！！！");
			return s;
		}
		s  += "方案权重\n";
		for(int i=0;i<planNum+factorNum;i++){
			if(i < factorNum){
				s += String.format("%s: %.3f\n", factor_name_array[i],resultMat.get(i, 0));
			}else{
				s += String.format("%s: %.3f\n", plan_name_array[i - factorNum],resultMat.get(i, 0));
			}
		}
		return s;
	}	

	public static void main(String[] args) {
		// double[][] val = { { 1, 2 }, { 2, 2 }, { 3, 2 } };
		// Matrix m1 = new Matrix(val);
		// Matrix m2 = new Matrix(val);
		/*
		 * for(int i = 0;i < m1.getRowDimension();i++){ for(int j = 0;j <
		 * m1.getColumnDimension();j++){ if(m1.get(i ,j) != m2.get(i, j)){
		 * System.out.println(false); } } }
		 */
		/*
		 * m1 = ColumnNormalization(m1); for (int i = 0; i <
		 * m1.getRowDimension(); i++) { for (int j = 0; j <
		 * m1.getColumnDimension(); j++) { System.out.print(m1.get(i, j) + " ");
		 * } System.out.println(); }
		 */
		/*
		 * System.out.println(Arrays.deepToString(m1.getMatrix(0,2,0,0).getArray
		 * ()));;
		 */

		/*
		 * double[][] val1={{0.3,0.2,.6},{0.4,0.25,0.3},{0.3,0.55,0.1}};
		 * double[][] val2={{0.634,0.25,0.4},{0.192,0.25,0.2},{0.174,0.25,0.4}};
		 * double[][]
		 * val3={{0.637,0.582,0.105},{0.105,0.109,0.637},{0.258,0.309,0.258}};
		 * double[][] val4={{0.5,1},{0.5,0}}; Matrix m1 = new Matrix(val1);
		 * Matrix m2 = new Matrix(val2); Matrix m3 = new Matrix(val3); Matrix m5
		 * = new Matrix(val4); AHP ahp = new AHP(3,3); Matrix matrix =
		 * ahp.combination(m1, m2, m3, m5); Matrix result =
		 * ahp.Multiself(matrix, 10);
		 * System.out.println(Arrays.deepToString(result.getArray()));
		 */
		String aim="选车";
		String[] factors={"成本","维修","耐用性"};
		String[] plans={"美国车","欧洲车","日本车",};
		double[][] factor_array2d={{0.3,0.2,0.6},{0.4,0.3,0.3},{0.3,0.5,0.1}};
		double[][][] array_3d1 = { { { 1, 5, 3 }, { 1 / 5., 1, 1 / 3. }, { 1 / 3., 3, 1 } },
				{ { 1, 5, 2 }, { 1 / 5., 1, 1 / 3. }, { 1 / 2., 3, 1 } },
				{ { 1, 1 / 5., 1 / 3. }, { 5, 1, 3 }, { 3, 1 / 3., 1 } } };
		double[][][] array_3d2 = { { { 1, 3, 4 }, { 1 / 3., 1, 1 }, { 1 / 4., 1, 1 } },
				{ { 1, 1, 1 / 2. }, { 1, 1, 1 / 2. }, { 2, 2, 1 } },
				{ { 1, 2, 1 }, { 1 / 2., 1, 1 / 2. }, { 1, 2, 1 } } };
		double[][] weight_array2d={{0.5,1},{0.5,0}};
		Anp anp = new Anp(aim,factors,plans);
		anp.read_factorMat(factor_array2d);
		anp.setPlan2factorMat(anp.read_matrix(array_3d1, factors.length));
		anp.setFactor2planMat(anp.read_matrix(array_3d2, plans.length));
		anp.read_weightMat(weight_array2d);
		
		Matrix result = anp.run_ahp(10);
		//anp.run_ahp(10);
		System.out.println(anp);
		//System.out.println(anp);
		

	}

	public Matrix getFactorMat() {
		return factorMat;
	}

	public void setFactorMat(Matrix factorMat) {
		this.factorMat = factorMat;
	}

	public Matrix[] getPlan2factorMat() {
		return plan2factorMat;
	}

	public void setPlan2factorMat(Matrix[] plan2factorMat) {
		this.plan2factorMat = plan2factorMat;
	}

	public Matrix[] getFactor2planMat() {
		return factor2planMat;
	}

	public void setFactor2planMat(Matrix[] factor2planMat) {
		this.factor2planMat = factor2planMat;
	}

	public Matrix getWeightMat() {
		return weightMat;
	}

	public void setWeightMat(Matrix weightMat) {
		this.weightMat = weightMat;
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

}
