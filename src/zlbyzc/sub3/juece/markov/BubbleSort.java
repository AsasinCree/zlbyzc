package zlbyzc.sub3.juece.markov;
import java.util.Arrays;

import zlbyzc.sub3.juece.ui.MainFrame;
public class BubbleSort {

	MainFrame mf = null;
	
	public BubbleSort(MainFrame mf) {
		this.mf = mf;
	}
	
	//矩陣相乘算法
	public static float[][] multiplyMatrix(float[][] a, float[][] b) 
	{
		// 判断是否合法
		if (a == null || a == null || a.length == 0 || b.length == 0
				|| a[0].length != b.length) {
			return null;
		}
		// 计算相乘
		float[][] c = new float[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) 
		{
			for (int j = 0; j < b[0].length; j++)
			{
				for (int k = 0; k < a[0].length; k++) 
				{
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}

	// 打印矩阵
	public void printMatrix(int[][] c) {
		if (c != null) {
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < c[0].length; j++) {
					this.mf.output.append(c[i][j] + " ");
					//System.out.print(c[i][j] + " ");
				}
				this.mf.output.append("\r\n");
//				System.out.println();
			}
		} else {
			this.mf.output.append("无效");
//			System.out.println("无效");
		}
		this.mf.output.append("\r\n");
//		System.out.println();
	}
	
/*	public static void main(String[] args) {
		/*float R[][]={{5,10,4,6},{-1,2,4,3},{5,10,4,4}};
		float [][][] arry1={
				{{4,5,1},{2,6,2},{7,2,1}},
				{{0,9,1},{5,5,0},{7,2,1}},
				{{5,5,0},{5,3,2},{4,5,1}},
				{{5,3,2},{5,4,1},{5,5,0}}			
		};*/
		/*float [][][] P;
		P=new float[4][3][3];
		for(int i=0;i<arry1.length;i++)
		{
			for(int j=0;j<arry1[0].length;j++)
				
			{
				for (int k = 0; k < arry1[0][0].length; k++)

				{
					P[i][j][k] = arry1[i][j][k] / 10;
					System.out.print( P[i][j][k]+"\t");// 输出每个数组元素值
				}
				System.out.print(" \n" );// 输出每个数组元素值
			}
			System.out.print(" \n" );// 输出每个数组元素值
		}

		float discount=9;
		discount=discount/10;//折算率为：0.9
	    //int a=0;
		//float[][] c = multiplyMatrix(P[a], P[a]);
		BubbleSort mdp = new BubbleSort();
		mdp.mdp_value_iteration(P, R, discount);
	//	System.out.print(" discount=\n"+discount );// 输出每个数组元素值
		 //mdp_value_iteration(P,R,discount);
		// 创建一个数组，这个数组元素是乱序的
		//int[] array = { 63, 4, 24, 1, 3, 15 };
		// 创建冒泡排序类的对象
		//BubbleSort sorter = new BubbleSort();
		// 调用排序方法将数组排序
		//sorter.sort(array);
	}*/
	
	public void mdp_value_iteration(float [][][]P,float [][]R,float discount)
	{
		if (discount <= 0 || discount > 1) {
			this.mf.output.append(" ---------------------");
			this.mf.output.append("Error! Discount rate must be in [0,1]");
			this.mf.output.append(" ---------------------");
//			System.out.print(" ---------------------");
//			System.out.print("Error! Discount rate must be in [0,1]");
//			System.out.print(" ---------------------");
		}
		else
		{
			float PR[][];
			float V0[];
			float Q[][];
			float V[];
			float Vprev[];
			int policy[];
			int max_iter;//最大迭代次数=100
			int iter;
			float thresh;
			//PR=new float[4][3][3];
			int S=P[0].length;//S=3
			int A=P.length;//  A=4
			//System.out.print(" S=:"+S+"  "+"A="+A);//S=3,
			
			PR=R;//将R保存到先前的R值（PR）
			V0=new float[S];
			Vprev=new float[S];
			Q=new float[S][A];
			policy=new int[S];
			for(int i=0;i<S;i++)
			{
			  V0[i]=0;
			  //PR[i][i][i]=0;
			}
			float epsilon=1;
			epsilon=epsilon/100;//epsilon=0.01
			//System.out.print("epsilon=:"+epsilon);
			
			//if (discount!=1)	
				max_iter=100;
			
			if(discount!=1)
				thresh=epsilon*(1-discount)/discount;
			else
				thresh=epsilon;
			
			iter=0;
			V=V0;
			boolean is_done=false; //定义布尔类型
			
			
		//	Vprev=V; 不能直接赋值，否则V改变，Vprev
		//	System.out.print(" v="+Vprevlength);
			while (!is_done) {
				iter = iter + 1;
				//System.out.print("迭代的次数为："+iter+"  ");

				for(int Len=0;Len<V.length;Len++)
				{
				Vprev[Len] = V[Len];
				}
				
				for (int a = 0; a < A; a++) {

					// 两个矩阵相乘：
					float c[] = new float[P[0].length];// 定义相乘后矩阵的大小
														// P(:,:,a)*Vprev;
														// 即a=P(:,:,a) b=Vprev
					for (int i = 0; i < P[0].length; i++) {
						for (int j = 0; j < V.length; j++) {

							c[i] += P[a][i][j] * V[j];
						}

					}

					for (int s = 0; s < S; s++) {

						Q[s][a] = PR[s][a] + discount * c[s];
					}

				}
				// 求出最大的Q值，及其對應的的policy
				
				for (int x = 0; x < S; x++) {
					{
						int max = 0;
						for (int y = 1; y < A; y++) {
							if (Q[x][y] > Q[x][max])
								{
								
								max = y;
								Q[x][max]=Q[x][y];
								}
						}
						V[x] = Q[x][max];
						//System.out.print("V的值为："+V[x]+"  ");
						
						policy[x] = max;
					}

				}
				//收斂
				float remain_min=100000;
				float remain_max=-10000;
				for(int i=0;i<V.length;i++)
				{
				float remain=V[i]-Vprev[i];
				//System.out.print("remain为："+remain+"  ");
				if (remain>remain_max)
					remain_max=remain;
				if(remain<remain_min)
					remain_min=remain;
				 
				}
				float variation=remain_max-remain_min;
				//System.out.print("variation为："+variation+"  ");
				//System.out.print("thresh为："+thresh+"  ");
				if (variation < thresh) {
					is_done = true;
					this.mf.output.append("iterations stopped, epsilon-optimal policy found");
		//			System.out.print("iterations stopped, epsilon-optimal policy found");
				}
				else if (iter==max_iter)
				{
					is_done = true;
					this.mf.output.append("iterations stopped by maximum number of iteration condition");
//					System.out.print("iterations stopped by maximum number of iteration condition");
				}
					

			}
			this.mf.output.append("\r\n");
//			System.out.print("\n");
			for ( int i=0;i<V.length;i++)
			{
				this.mf.output.append("迭代的最优值为："+V[i]+"  ");
//				System.out.print("迭代的最优值为："+V[i]+"  ");
				
			}
			this.mf.output.append("\r\n");
//			System.out.print("\n");
			
			for ( int i=0;i<policy.length;i++)
			{
				policy[i]=policy[i]+1;//由于数组的下标第一个为0，所以要加1
				this.mf.output.append("迭代的最策略为："+policy[i]+"  ");
//				System.out.print("迭代的最策略为："+policy[i]+"  ");
			}
		}
	}
}
	

