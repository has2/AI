import java.util.Arrays;
import java.util.Stack;

abstract class method_chess {
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	int N;							// 퀸의 개수(N*N)
	int[] location; 				// 0~N-1 column의 퀸의 위치를 저장
	int[] domain_count;				// 각 column들이 가질 수 있는 value들의 개수를 저장
	int[][] domain;					// 각 column들이 가질 수 있는 value들이 ABLE한지 UNABLE한지를 저장
	int cur_depth=0;    			// 현재 탐색중인 노드의 깊이를 저장
	long sTime,eTime;   			// sTime : 탐색의 시작시간 eTime : 탐색의 종료시간  
	Stack<Integer> fringe; // DFS 이용을 위해 fringe를 Stack으로 만듬

	/**
	 * 각각의 탐색 방법을 통해 NQueen문제를 해결하는 메소드
	 */
	public abstract boolean solve();
		
	/* 
	 * 0~N-1 column에 있는 queen의 위치를 반환
	 */
	public int[] getLocation(){
		return this.location;
	}
	
	/* 
	 * 걸린시간을 반환
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	/* 
	 * expand 가능한지를 체크해줌 
	 */
	protected boolean canExpand(){
		return cur_depth != N-1 ? true : false;  /*expand가능 -> true, expand불가 -> false*/
	}
	
	/*
	 * 현재 assign된 value가 consistent한지 check 
	 */
	protected boolean isConsistent(){
		
		/* i~cur_depth-1번째 column에 대해  */
		for(int i = 0; i < cur_depth; i++)
		{
			/* i번째 column과 j번째 column의 consistent를 체크 */
			for(int j= i + 1;j < cur_depth + 1; j++){
				
				/* 퀸이 서로 공격하고 있다면 */
				if(location[i] == location[j] 
						|| j - i == Math.abs(location[i] - location[j])){
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Backtracking시에 domain[]과 domain_count[]를 예전상태로 update해주는 method
	 * (CSP_fwd_chess,CSP_arc_chess class에서 사용) 
	 */
	protected void BacktrackDomain(){

		/* update를 위해 전부 ABLE로 reset */
		for(int[] dep : domain)
			Arrays.fill(dep, ABLE);

		/* 0 ~ cur_depth-1까지는 이미 assign된 상태이므로 count를 0으로 setting */
		for(int i = 0; i < cur_depth; i++)
			domain_count[i] = 0;
		
		/* cur_depth ~ N-1까지는 다시 update 해줘야 하므로 count를 N으로 reset */
		for(int i = cur_depth; i < N; i++)
			domain_count[i] = N;

		/* cur_depth가 0이 아닐때만 update 해주면 됨 */
		if(cur_depth != 0){
			
			/* 0~cur_depth-1번째 column에 할당된 value에 의해 UNABLE이 되는 domain을 체크한 뒤 update 해줌 */
			for(int i = 0; i < cur_depth; i++){

				int var = location[i];
				for(int j = cur_depth+1; j < N; j++){
					
					/* 평행으로 공격할 때*/
					if(domain[j][var] == ABLE){
						domain_count[j]--;
						domain[j][var] = UNABLE;	
					}

					/* 대각선 위쪽 방향으로 공격할 때 */
					if(var + j - i < N){
						if(domain[j][var+j-i] == ABLE){
							domain[j][var+j-i] = UNABLE;
							domain_count[j]--;
						}
					}
					
					/* 대각선 아래 방향으로 공격할 때 */
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
	 * 현재 assign된  후 변하게 되는 domain을 update
	 * domain[][]과 domain_count[]를 update 해주는 method
	 * (CSP_fwd_chess,CSP_arc_chess class에서 사용)
	 */
	protected void getNewDomain(int[] new_domain_count,int[][] new_domain){
		
		int var = location[cur_depth];	// 	현재 assign한 value를 저장  
		
		/* domain_count[]를 new_domain_count[]에 복사해줌 */
		System.arraycopy(domain_count, 0, new_domain_count, 0, N);
		
		/* domain[][]을 new_domain[][]에 복사해줌 */
		for(int i = 0; i < N; i++){
			System.arraycopy(domain[i], 0, new_domain[i], 0, N);
		}
		
		/* 현재 assign한 column의 value의 위치는 FIXED로 setting하고 나머지는 UNABLE로 setting */ 
		for(int i = 0; i < N; i++){
			new_domain[cur_depth][i] = UNABLE;
		}
		new_domain_count[cur_depth] = 0;
		new_domain[cur_depth][var] = FIXED;
		
		/* cur_depth+1~N-1번째 column에 대해 */
		for(int i = cur_depth+1; i < N; i++){
			
			/* i번째 column이 평행으로 공격 받으면 UNABLE로 setting */
			if(new_domain[i][var] == ABLE){
				new_domain_count[i]--;
				new_domain[i][var] = UNABLE;	
			}
			
			/* i번째 column이 대각선 위쪽으로 공격 받으면 UNABLE로 setting */
			if(var+i-cur_depth<N){
				if(new_domain[i][var+i-cur_depth] == ABLE){
					new_domain[i][var+i-cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
			
			/* i번째 column이 대각선 아래쪽으로 공격 받으면 UNABLE로 setting */
			if(var-i+cur_depth>=0){
				if(new_domain[i][var-i+cur_depth] == ABLE){
					new_domain[i][var-i+cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
		}
	}
}
