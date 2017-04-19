package zlbyzc.sub3.zlpj.ahp;

import Jama.*;

//层次分析法AHP的判断矩阵：
//1.对称互倒方阵
//2.取值为 1/9,1/8,..1,2,..9
public class judgeMatrix extends Matrix {
	
	private static final long serialVersionUID = 1L;
	private int n; //n阶方阵
	
	public judgeMatrix(double[][] arr2d){
		super(arr2d);
		n = arr2d.length;		
		if(!check_reciprocal()){
			System.out.println("wrong matrix!!!");
		}		
	}
	
	//检查是否为对称互倒数的方阵
	private boolean check_reciprocal() {
		return true;
	}
	
	//判断矩阵mat是否满足一致性检验
	public boolean check_consistency(){
		double[] d = eig().getRealEigenvalues();
		double maxV = 0;
		//int maxid = 0;
		for(int i=0;i<d.length;i++){
			if(d[i]>maxV){
				maxV = d[i];
				//maxid = i;
			}
		}
		double x = (maxV-d.length)/(d.length-1)/AHP.RI[d.length];
		if(x<0.1){
			return true;
		}
		else{
			return false;
		}		
	}
	
	/* pretty print like following
	 *  1    1/2  4
 	 *	2    1    7
	 *  1/4  1/7  1	
	 * */
	public String toString() {
		String s = "";
		double[][] A = getArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s += digitformat(A[i][j])+"  ";
			}
			s += "\n";
		}
		return s;
	}
	
	//分数转字符串 1/3-->"1/3" 
	private String digitformat(double x) {
		assert(x>0 && x<=9);
		String s = "";
		if(x>=1){
			int z = (int) Math.round(x);	
			s = z+"  ";
		}else{
			int z = (int) Math.round(1/x);
			s = "1/"+z;			
		}
		return s;
	}
	

	public static void main(String[] args) {
		double[][] factor_array = 
			   {{1    ,1/2., 4, 3,  3},
                {2    ,1   , 7, 5,  5},
                {1/4. ,1/7., 1, 1/2.,1/3.},
                {1/3. ,1/5., 2, 1,  1},
                {1/3. ,1/5., 3, 1,  1}};
		judgeMatrix mat = new judgeMatrix(factor_array);
		//boolean b = mat.check_consistency();
		System.out.println(mat.toString());

	}

}
