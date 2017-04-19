package zlbyzc.sub3.zlpj.dea;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import Jama.Matrix;

public class Dea {
	private Matrix inputMat; // 投入矩阵
	private Matrix outputMat; // 产出矩阵
	private Matrix inputMattranspose; // 投入矩阵的转置
	private Matrix outputMattranspose; // 产出矩阵的转置
	private int inputNum;
	private int outputNum;
	private int nameNum;// 需要算出的结果个数
	private String deaaim_name;   //目标名称
	private String[] name_name_array;   //各项目名称数组
	private String[] input_name_array;  //投入决策数组
	private String[] output_name_array; //产出决策数组
	private double[] dearesult;

	public Dea(int name_num, int input_num, int output_num) {
		this.inputNum = input_num;
		this.outputNum = output_num;
		this.nameNum = name_num;
	}
	public Dea(String deaaim, String[] nameArray, String[] inputArray,String[] outputArray){
		deaaim_name = deaaim;
		name_name_array = nameArray;
		input_name_array = inputArray;
		output_name_array = outputArray;
		nameNum = nameArray.length;
		inputNum = inputArray.length;
		outputNum = outputArray.length;
	}

	public Matrix getInputMattranspose() {
		return inputMattranspose;
	}

	public void setInputMattranspose(Matrix inputMattranspose) {
		this.inputMattranspose = inputMattranspose;
	}

	public Matrix getOutputMattranspose() {
		return outputMattranspose;
	}

	public void setOutputMattranspose(Matrix outputMattranspose) {
		this.outputMattranspose = outputMattranspose;
	}

	public int getInputNum() {
		return inputNum;
	}

	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}

	public int getOutputNum() {
		return outputNum;
	}

	public void setOutputNum(int outputNum) {
		this.outputNum = outputNum;
	}

	public int getNameNum() {
		return nameNum;
	}

	public void setNameNum(int nameNum) {
		this.nameNum = nameNum;
	}

	public Dea(String[] nameArray, String[] inputArray, String[] outputArray) {
		nameNum = nameArray.length;
		inputNum = inputArray.length;
		outputNum = outputArray.length;
	}

	// 从面板读取投入矩阵
	public void read_inputMat(double[][] input_array2d) {
		this.setInputMat(new Matrix(input_array2d));
	}

	// 从面板读取产出矩阵
	public void read_outPutMat(double[][] output_array2d) {
		this.setOutputMat(new Matrix(output_array2d));
	}

	public Matrix getInputMat() {
		return inputMat;
	}

	public void setInputMat(Matrix inputMat) {
		this.inputMat = inputMat;
	}

	public Matrix getOutputMat() {
		return outputMat;
	}

	public void setOutputMat(Matrix outputMat) {
		this.outputMat = outputMat;
	}

	// 拼凑，m4不变，m2代表输入，m6代表输出
	public Matrix combination(Matrix m1, Matrix m2, Matrix m3, Matrix m4, Matrix m5, Matrix m6) {
		int rowNum = inputNum + outputNum + 1;
		int columnNum = nameNum + 2;

		Matrix result = new Matrix(rowNum, columnNum);
		result.setMatrix(0, inputNum - 1, 0, nameNum - 1, m1);
		result.setMatrix(0, inputNum - 1, nameNum, columnNum - 1, m2);
		result.setMatrix(inputNum, inputNum, 0, nameNum - 1, m3);
		result.setMatrix(inputNum, inputNum, nameNum, columnNum - 1, m4);
		result.setMatrix(inputNum + 1, rowNum - 1, 0, nameNum - 1, m5);
		result.setMatrix(inputNum + 1, rowNum - 1, nameNum, columnNum - 1, m6);
		return result;
	}

	// 得到算法需要的最后的二位数组,其中count就是需要算的次数
	public double[][] getlastMat(int n, int n1, int n2, Matrix m1, Matrix m5, int count) {
		double[][] lastArray = null;
		n = nameNum;
		n1 = inputNum;
		n2 = outputNum;
		inputMattranspose = m1.transpose();// m1
		outputMattranspose = m5.transpose();// m5
		Matrix equalMat = new Matrix(1, n, 1);// m3

		double[][] mm4 = { { 0, 1 } };
		Matrix m4 = new Matrix(mm4);// m4

		// m2
		Matrix m2_1 = inputMattranspose.getMatrix(0, n1 - 1, count, count);
		Matrix m2_2 = new Matrix(n1, 1);
		Matrix m2 = new Matrix(n1, 2);
		m2.setMatrix(0, n1 - 1, 0, 0, m2_1);
		m2.setMatrix(0, n1 - 1, 1, 1, m2_2);
		//int x[] = { 0, 0, 0, 0, 1 };
		m2 = m2.times(-1);
		// m6
		Matrix m6_1 = outputMattranspose.getMatrix(0, n2 - 1, count, count);
		Matrix m6_2 = new Matrix(n2, 1);
		Matrix m6 = new Matrix(n2, 2);
		m6.setMatrix(0, n2 - 1, 0, 0, m6_2);
		m6.setMatrix(0, n2 - 1, 1, 1, m6_1);

		lastArray = combination(inputMattranspose, m2, equalMat, m4, outputMattranspose, m6).getArray();

		return lastArray;
	}

	public double[] result() {
		DecimalFormat df = new DecimalFormat("######0.00");
		// 结果
		dearesult = new double[nameNum];
		// 目标结果,填充x，前面有nameNum个0，最后一个1
		int[] x = new int[nameNum + 1];
		for (int i = 0; i < nameNum; i++) {
			x[i] = 0;
		}
		x[nameNum] = 1;
		for (int i = 0; i < nameNum; i++) {
			double[][] lastArray = getlastMat(nameNum, inputNum, outputNum, getInputMat(), getOutputMat(), i);
			Simplex simplex = new Simplex(-1, inputNum + outputNum + 1, nameNum + 1, inputNum, 1, outputNum, lastArray,
					x);
			BigDecimal bg = new BigDecimal(simplex.solve());
			dearesult[i] = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return dearesult;
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
		s += "目标: "+deaaim_name;
		s += "\n各目标名称: ";
		for (String  name_name: name_name_array) {
			s += name_name+" ";
		}
		s += "\n投入决策: ";
		for (String  input_name: input_name_array) {
			s += input_name+" ";
		}
		s += "\n产出决策: ";
		for (String  output_name: output_name_array) {
			s += output_name+" ";
		}
		
		s += "\n\n投入矩阵: \n" + mattoString(inputMat);
		s += "\n产出矩阵: \n"+ mattoString(outputMat);
		
		
		//检查planWeights是否有值
		if(dearesult==null){
			System.out.println("还未计算结果！！！");
			return s;
		}
		s  += "\nDEA有效性指标E\n";
		for(int i=0;i<nameNum;i++){
			
			s += String.format("%s: %.3f\n", name_name_array[i],dearesult[i]);
		}
		return s;
	}	

	
	public static void main(String[] args) {
		/*int nameNum = 4;
		int n1 = 2;
		int n2 = 3;
		int x[] = { 0, 0, 0, 0, 1 };*/
		String aim="银行分理处";
		String[] allnames={"分理处1","分理处2","分理处3","分理处4"};;
		String[] inputs={"职员数","营业面积"};
		String[] outputs={"储蓄存款","贷款","中间时间",};
		Dea dea = new Dea(aim, allnames, inputs,outputs);
		double[][] m1d = { { 15, 140 }, { 20, 130 }, { 21, 120 }, { 20, 135 } };
		double[][] m5d = { { 1800, 200, 1600 }, { 1000, 350, 1000 }, { 800, 450, 1300 }, { 900, 420, 1500 } };
		dea.setInputMat(new Matrix(m1d));
		dea.setOutputMat(new Matrix(m5d));
		double[] result=dea.result();
		System.out.println(dea);
		/*
		 * Matrix m1=new Matrix(m1d); Matrix m5=new Matrix(m5d); Dea dea=new
		 * Dea(n,n1,n2); double[][] lastArray=dea.getlastMat(n,n1, n2,m1, m5);
		 * Simplex test=new Simplex(-1,n1+n2+1,n+1,n1,1,n2,lastArray,x);
		 * test.solve();
		 */
	}
	public String getdeaAim_name() {
		return deaaim_name;
	}

	public void setdeaAim_name(String deaaim_name) {
		this.deaaim_name = deaaim_name;
	}

	public String[] getname_name_array() {
		return name_name_array;
	}

	public void setnamename_array(String[] name_name_array) {
		this.name_name_array = name_name_array;
	}

	public String[] getinput_name_array() {
		return input_name_array;
	}

	public void setinput_name_array(String[] input_name_array) {
		this.input_name_array = input_name_array;
	}
	
	public String[] getoutput_name_array() {
		return output_name_array;
	}

	public void setoutput_name_array(String[] output_name_array) {
		this.output_name_array = output_name_array;
	}

}
