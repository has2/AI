import java.util.Arrays;
import java.util.Stack;

abstract class method_chess {
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	int N;							// ���� ����(N*N)
	int[] location; 				// 0~N-1 column�� ���� ��ġ�� ����
	int[] domain_count;				// �� column���� ���� �� �ִ� value���� ������ ����
	int[][] domain;					// �� column���� ���� �� �ִ� value���� ABLE���� UNABLE������ ����
	int cur_depth=0;    			// ���� Ž������ ����� ���̸� ����
	long sTime,eTime;   			// sTime : Ž���� ���۽ð� eTime : Ž���� ����ð�  
	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����

	/**
	 * ������ Ž�� ����� ���� NQueen������ �ذ��ϴ� �޼ҵ�
	 */
	public abstract boolean solve();
		
	/* 
	 * 0~N-1 column�� �ִ� queen�� ��ġ�� ��ȯ
	 */
	public int[] getLocation(){
		return this.location;
	}
	
	/* 
	 * �ɸ��ð��� ��ȯ
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	/* 
	 * expand ���������� üũ���� 
	 */
	protected boolean canExpand(){
		return cur_depth != N-1 ? true : false;  /*expand���� -> true, expand�Ұ� -> false*/
	}
	
	/*
	 * ���� assign�� value�� consistent���� check 
	 */
	protected boolean isConsistent(){
		
		/* i~cur_depth-1��° column�� ����  */
		for(int i = 0; i < cur_depth; i++)
		{
			/* i��° column�� j��° column�� consistent�� üũ */
			for(int j= i + 1;j < cur_depth + 1; j++){
				
				/* ���� ���� �����ϰ� �ִٸ� */
				if(location[i] == location[j] 
						|| j - i == Math.abs(location[i] - location[j])){
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Backtracking�ÿ� domain[]�� domain_count[]�� �������·� update���ִ� method
	 * (CSP_fwd_chess,CSP_arc_chess class���� ���) 
	 */
	protected void BacktrackDomain(){

		/* update�� ���� ���� ABLE�� reset */
		for(int[] dep : domain)
			Arrays.fill(dep, ABLE);

		/* 0 ~ cur_depth-1������ �̹� assign�� �����̹Ƿ� count�� 0���� setting */
		for(int i = 0; i < cur_depth; i++)
			domain_count[i] = 0;
		
		/* cur_depth ~ N-1������ �ٽ� update ����� �ϹǷ� count�� N���� reset */
		for(int i = cur_depth; i < N; i++)
			domain_count[i] = N;

		/* cur_depth�� 0�� �ƴҶ��� update ���ָ� �� */
		if(cur_depth != 0){
			
			/* 0~cur_depth-1��° column�� �Ҵ�� value�� ���� UNABLE�� �Ǵ� domain�� üũ�� �� update ���� */
			for(int i = 0; i < cur_depth; i++){

				int var = location[i];
				for(int j = cur_depth+1; j < N; j++){
					
					/* �������� ������ ��*/
					if(domain[j][var] == ABLE){
						domain_count[j]--;
						domain[j][var] = UNABLE;	
					}

					/* �밢�� ���� �������� ������ �� */
					if(var + j - i < N){
						if(domain[j][var+j-i] == ABLE){
							domain[j][var+j-i] = UNABLE;
							domain_count[j]--;
						}
					}
					
					/* �밢�� �Ʒ� �������� ������ �� */
					if(var - j + i >= 0){
						if(domain[j][var-j+i] == ABLE){
							domain[j][var-j+i] = UNABLE;
							domain_count[j]--;
						}
					}
				}			
			}
		}
	}
	
	/*
	 * ���� assign��  �� ���ϰ� �Ǵ� domain�� update
	 * domain[][]�� domain_count[]�� update ���ִ� method
	 * (CSP_fwd_chess,CSP_arc_chess class���� ���)
	 */
	protected void getNewDomain(int[] new_domain_count,int[][] new_domain){
		
		int var = location[cur_depth];	// 	���� assign�� value�� ����  
		
		/* domain_count[]�� new_domain_count[]�� �������� */
		System.arraycopy(domain_count, 0, new_domain_count, 0, N);
		
		/* domain[][]�� new_domain[][]�� �������� */
		for(int i = 0; i < N; i++){
			System.arraycopy(domain[i], 0, new_domain[i], 0, N);
		}
		
		/* ���� assign�� column�� value�� ��ġ�� FIXED�� setting�ϰ� �������� UNABLE�� setting */ 
		for(int i = 0; i < N; i++){
			new_domain[cur_depth][i] = UNABLE;
		}
		new_domain_count[cur_depth] = 0;
		new_domain[cur_depth][var] = FIXED;
		
		/* cur_depth+1~N-1��° column�� ���� */
		for(int i = cur_depth+1; i < N; i++){
			
			/* i��° column�� �������� ���� ������ UNABLE�� setting */
			if(new_domain[i][var] == ABLE){
				new_domain_count[i]--;
				new_domain[i][var] = UNABLE;	
			}
			
			/* i��° column�� �밢�� �������� ���� ������ UNABLE�� setting */
			if(var+i-cur_depth<N){
				if(new_domain[i][var+i-cur_depth] == ABLE){
					new_domain[i][var+i-cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
			
			/* i��° column�� �밢�� �Ʒ������� ���� ������ UNABLE�� setting */
			if(var-i+cur_depth>=0){
				if(new_domain[i][var-i+cur_depth] == ABLE){
					new_domain[i][var-i+cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
		}
	}
}
