package zlbyzc.sub3.zlpj.fuzzy;

import java.math.BigDecimal;
import Jama.Matrix;

public class Fuzzy {
	private int indexNum;
	private int commentNum;
	private Matrix indexMat; // 指标矩阵
	private Matrix commentMat; // 评语矩阵
	private String fuzzyaim_name;  //目标名称
	private String[] index_name_array; //指标名称数组
	private String[] comment_name_array; //评语名称数组 
	private Matrix fuzzyresult;//算子4的结果
	
	public Fuzzy(int index_num, int comment_num) {
		this.indexNum = index_num;
		this.commentNum = comment_num;
	}

	public Fuzzy(String aim, String[] indexArray, String[] commentArray) {
		fuzzyaim_name = aim;
		index_name_array = indexArray;
		comment_name_array = commentArray;
		indexNum = indexArray.length;
		commentNum = commentArray.length;
	}
	
	public Matrix getIndexMat() {
		return indexMat;
	}

	public void setIndexMat(Matrix indexMat) {
		this.indexMat = indexMat;
	}

	public Matrix getCommentMat() {
		return commentMat;
	}

	public void setCommentMat(Matrix commentMat) {
		this.commentMat = commentMat;
	}

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	// 从面板读取指标矩阵
	public void read_indexMat(double[][] index_array2d) {
		this.setIndexMat(new Matrix(index_array2d));
	}

	// 从面板读取评语矩阵
	public void read_commentMat(double[][] comment_array2d) {
		this.setCommentMat(new Matrix(comment_array2d));
	}

	// M(∧，∨)
	public Matrix min2Max() {
		Matrix result = new Matrix(1, commentMat.getColumnDimension());
		for (int i = 0; i < commentMat.getColumnDimension(); i++) {
			result.set(0, i, min(indexMat, commentMat, i));
		}
		return result;
	}

	// M(·,∨)
	public Matrix multiply2Max() {
	    Matrix result = new Matrix(1, commentMat.getColumnDimension());
		for (int i = 0; i < commentMat.getColumnDimension(); i++) {
			result.set(0, i, max(indexMat, commentMat, i));
		}
		return result;
	}

	// M(∧,+)
	public Matrix plus2Max() {
		Matrix result = new Matrix(1, commentMat.getColumnDimension());
		for (int i = 0; i < commentMat.getColumnDimension(); i++) {
			result.set(0, i, sum(indexMat, commentMat, i));
		}
		return result;
	}

	// M(·,+)
	public Matrix multiply2Plus() {
		fuzzyresult = new Matrix(1, commentMat.getColumnDimension());
		for (int i = 0; i < commentMat.getColumnDimension(); i++) {
			fuzzyresult.set(0, i, sum1(indexMat, commentMat, i));
		}
		return fuzzyresult;
	}

	// 求M(∧，∨)运算每一列的结果
	public double min(Matrix m1, Matrix m2, int j) {
		// 存每一列比较后的最小值数组
		double[] temp = new double[m2.getRowDimension()];
		for (int i = 0; i < m2.getRowDimension(); i++) {
			temp[i] = Math.min(m1.get(0, i), m2.get(i, j));
		}
		// 最小值数组中的最大值
		double max = temp[0];
		for (int i = 1; i < temp.length; i++) {
			max = Math.max(max, temp[i]);
		}
		return max;
	}

	// 求M(·,∨)运算每一列的结果
	public double max(Matrix m1, Matrix m2, int j) {
		// 存每一行列相乘后的数组
		double[] temp = new double[m2.getRowDimension()];
		for (int i = 0; i < m2.getRowDimension(); i++) {
			temp[i] = multiply(m1.get(0, i) , m2.get(i, j));//处理精度问题
		}
		// 相乘数组中的最大值
		double max = temp[0];
		for (int i = 1; i < temp.length; i++) {
			max = Math.max(max, temp[i]);
		}
		return max;
	}

	// 求M(∧,+)运算每一列的结果
	public double sum(Matrix m1, Matrix m2, int j) {
		// 存每一行列比较后的数组
		double[] temp = new double[m2.getRowDimension()];
		for (int i = 0; i < m2.getRowDimension(); i++) {
			temp[i] = Math.min(m1.get(0, i), m2.get(i, j));
		}
		// 求和
		double sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum = add(sum, temp[i]);//处理精度问题
		}
		return sum;
	}
	
	// 求M(·,+)运算每一列的结果
	public double sum1(Matrix m1, Matrix m2, int j) {
		// 存每一行列相乘后的数组
		double[] temp = new double[m2.getRowDimension()];
		for (int i = 0; i < m2.getRowDimension(); i++) {
			temp[i] = multiply(m1.get(0, i), m2.get(i, j));
		}
		// 求和
		double sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum = add(sum, temp[i]);//处理精度问题
		}
		return sum;
	}

	//避免出现精度问题，double类型的两数相加
	public double add(double d1,double d2){
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();
		
	}
	
	//避免出现精度问题，double类型的两数相加
	public double multiply(double d1,double d2){
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
		
	}
	
	public String formatMat(Matrix matrix) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < matrix.getRowDimension();i++){
			for(int j = 0;j < matrix.getColumnDimension();j++){
				sb.append(matrix.get(i, j));
				sb.append("\t");
			}
		}
		return sb.toString();
	}
	
	//检验行归一
	public boolean checkone(Matrix mat){
		double[] sum = new double[mat.getRowDimension()];
		// 求每一行的和
		for (int j = 0; j < mat.getRowDimension(); j++) {
			for (int i = 0; i < mat.getColumnDimension(); i++) {
				sum[j] += mat.get(j, i);
			}
		}
		for(int i = 0; i < mat.getRowDimension(); i++){
			 if((float)sum[i]!=1.0)
				 return false;
		}
		return true;
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
		s += "目标: "+fuzzyaim_name;
		s += "\n准则: ";
		for (String  index_name: index_name_array) {
			s += index_name+" ";
		}
		s += "\n方案: ";
		for (String  comment_name: comment_name_array) {
			s += comment_name+" ";
		}
		
		s += "\n\n指标权重向量: \n" + mattoString(indexMat);
		s += "\n模糊评价矩阵断矩阵: \n"+ mattoString(commentMat);
		
		
		//检查planWeights是否有值
		if(fuzzyresult==null){
			System.out.println("还未计算结果！！！");
			return s;
		}
		s  += "\n评价权重\n";
		for(int i=0;i<commentNum;i++){
			
			s += String.format("%s: %.3f\n", comment_name_array[i],fuzzyresult.get(0, i));
		}
		return s;
	}	

	public static void main(String[] args) { 
		String target = "科研成果评价";
		String[] index = { "学术水平", "社会效益", "经济效益" };
		String[] comment = { "很好", "好", "一般", "差" };
		double[][] val1 = { { 0.3, 0.3, 0.4 } };
		double[][] val2 = { { 0.5, 0.3, 0.2, 0 }, { 0.3, 0.4, 0.2, 0.1 }, { 0.2, 0.2, 0.3, 0.2 } };
		Fuzzy fuzzy = new Fuzzy(target,index,comment);
		fuzzy.read_indexMat(val1);
		fuzzy.read_commentMat(val2);
//		System.out.println(Arrays.deepToString(fuzzy.min2Max().getArray()));
//		
//		System.out.println(Arrays.deepToString(fuzzy.multiply2Max().getArray()));
//		System.out.println(Arrays.deepToString(fuzzy.plus2Max().getArray()));
//		System.out.println(Arrays.deepToString(fuzzy.multiply2Plus().getArray()));
//		/*BigDecimal b1 = new BigDecimal(Double.toString(0.4));
//		BigDecimal b2 = new BigDecimal(Double.toString(0.2));
//		System.out.println(b1.multiply(b2));*/
//		System.out.println(fuzzy.multiply(0.2, 0.4));
//		System.out.println(fuzzy.formatMat(fuzzy.multiply2Plus()));
		Matrix result =fuzzy.multiply2Plus();
		System.out.println(fuzzy);
	}
	
	public String getfuzzyAim_name() {
		return fuzzyaim_name;
	}

	public void setfuzzyAim_name(String fuzzyaim_name) {
		this.fuzzyaim_name = fuzzyaim_name;
	}

	public String[] getindex_name_array() {
		return index_name_array;
	}

	public void setindexname_array(String[] index_name_array) {
		this.index_name_array = index_name_array;
	}

	public String[] getcomment_name_array() {
		return comment_name_array;
	}

	public void setcomment_name_array(String[] comment_name_array) {
		this.comment_name_array = comment_name_array;
	}
}
