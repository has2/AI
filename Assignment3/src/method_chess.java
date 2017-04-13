import java.util.Arrays;

abstract class method_chess {
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	int N;				// 퀸의 개수(N*N)
	int[] location; 	// 0~N-1 column의 퀸의 위치를 저장
	int[] domain_count;
	int[][] domain;
	int cur_depth=0;    // 현재 탐색중인 노드의 깊이를 저장
	long sTime,eTime;   // sTime : 탐색의 시작시간 eTime : 탐색의 종료시간  

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
	
	protected boolean isConsistent(){
		for(int i = 0; i < cur_depth; i++)
		{
			for(int j= i + 1;j < cur_depth + 1; j++){
				if(location[i] == location[j] 
						|| j - i == Math.abs(location[i] - location[j])){
					return false;
				}
			}
		}

		return true;
	}
	
	protected void BacktrackDomain(){

		/* 0에서 backtrack 할때*/

		for(int[] dep : domain)
			Arrays.fill(dep, ABLE);

		for(int i = 0; i < cur_depth; i++)
			domain_count[i] = 0;

		for(int i = cur_depth; i < N; i++)
			domain_count[i] = N;

		if(cur_depth != 0){
			for(int i = 0; i < cur_depth; i++){

				int var = location[i];
				for(int j = cur_depth+1; j < N; j++){

					if(domain[j][var] == ABLE){
						domain_count[j]--;
						domain[j][var] = UNABLE;	
					}

					if(var + j - i < N){
						if(domain[j][var+j-i] == ABLE){
							domain[j][var+j-i] = UNABLE;
							domain_count[j]--;
						}
					}

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
	
	protected void getNewDomain(int[] new_domain_count,int[][] new_domain){
		
		
		int var = location[cur_depth];
		
		System.arraycopy(domain_count, 0, new_domain_count, 0, N);
		for(int i = 0; i < N; i++){
			System.arraycopy(domain[i], 0, new_domain[i], 0, N);
		}
		
		for(int i = 0; i < N; i++){
			new_domain[cur_depth][i] = UNABLE;
		}
		new_domain_count[cur_depth] = 0;
		new_domain[cur_depth][var] = FIXED;
		
		for(int i = cur_depth+1; i < N; i++){
			
			if(new_domain[i][var] == ABLE){
				new_domain_count[i]--;
				new_domain[i][var] = UNABLE;	
			}
			
			if(var+i-cur_depth<N){
				if(new_domain[i][var+i-cur_depth] == ABLE){
					new_domain[i][var+i-cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
			
			if(var-i+cur_depth>=0){
				if(new_domain[i][var-i+cur_depth] == ABLE){
					new_domain[i][var-i+cur_depth] = UNABLE;
					new_domain_count[i]--;
				}
			}
		}
	}
		
}
