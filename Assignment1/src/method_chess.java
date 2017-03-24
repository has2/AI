abstract class method_chess {
	int N;				// 퀸의 개수(N*N)
	long sTime,eTime;   // sTime : 탐색의 시작시간 eTime : 탐색의 종료시간  
	int[] location; 	// 0~N-1 column의 퀸의 위치를 저장
	int cur_depth=0;    // 현재 탐색중인 노드의 깊이를 저장
	
	
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
	 * goal state인지를 체크 
	 */
	protected boolean isGoal(){
		
		/*
		 * 체크할 때 cur_depth가 N-1이 아니면 무조건 goal state가 아님
		 */
		if(cur_depth!=N-1){			
			return false;
		}
		
		/*0~N-1 column에 있는 퀸들을 체크함*/
		for(int i = 0; i < N-1; i++)
		{
			
			/*같은 row에 있는 퀸이 있는지 체크*/
			for(int j = i+1; j < N; j++){
				if(location[i] == location[j]){
					return false;
				}
			}
			
			/*북동쪽 방향 대각선에 퀸이 있는지를 체크*/
			for(int j = i+1; j < N; j++){
				if((location[i] + j - i >= N)){
					break;
				}
				
				if(location[j] == location[i] + j - i){
					return false;
				}
			}
			
			/*남동쪽 방향 대각선에 퀸이 있는지를 체크*/
			for(int j = i+1; j < N; j++){
				if(location[i] + i - j < 0){
					break;
				}
				
				if(location[j] == location[i] + i - j){
					return false;
				}
			}
		}
		return true;  /*위의 모든 조건을 통과했으므로 goal*/
	}
	
}
