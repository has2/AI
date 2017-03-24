import java.util.LinkedList;

/**
 *	DFS로 문제를 푸는 클래스
 */
class BFS_chess extends method_chess{

	LinkedList<Integer> fringe; // BFS 이용을 위해 fringe를 Queue로 만듬(LinkedList 이용)
	
	/**
	 * BFS_chess를 초기화시키는 생성자
	 * location 배열을 0~N-1로 초기화시키고 fringe에 0~N-1을 넣어줌
	 * @param N 체스판의 column의 개수
	 */
	public BFS_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new LinkedList<Integer>(); // fringe를 초기화 (초기상태를 구분하기 위해 -1을 가장 먼저 넣어줌)
		for(int i = -1; i < N; i++) {
			fringe.offer(i);
		}
		this.sTime = System.currentTimeMillis(); // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	/*
	 * BFS를 이용해 문제를 해결하는 주 메소드
	 */	
	public boolean solve(){
		
		/* fringe가 비어있을 때 까지 반복 */
		boolean loop = true;
		while(loop){
			
				
			/* fringe에서 뽑은 값을 현재 column에 넣음 (i번째 column은 cur_depth i를 의미함) */
			location[cur_depth] = fringe.poll();
					
			/* cur_depth가 0인 경우는 따로 처리해줌 */
			if(cur_depth == 0){
				
				/*
				 * location[cur_depth](현재 column에서 선택된 퀸의 위치)값이 -1이라면 초기상태이므로
				 * 한번 더 dequeue를 해줘서 첫 state를 만듬
				 */
				if(location[cur_depth] == -1){
					location[cur_depth] = fringe.poll();
					
				
				/* location[cur_depth](현재 column에서 선택된 퀸의 위치)값이 0인 경우는 depth 0 에서의 서치가 끝나서 depth 1로 넘어갈때*/				 
				}else if(location[cur_depth] == 0){
					cur_depth++;
					location[cur_depth] = 0;
				}
				
			/*
			 * cur_depth가 1이상일 때 location[cur_depth](현재 column에서 선택된 퀸의 위치)값이 0인 경우는 cur_depth에서의 서치가 끝나고
			 * 다음 depth로 넘어가서 0이 된 경우와 cur_depth에서 한 서브트리의 N-1번째 리프까지 탐색이 끝나서 오른쪽 트리의 0번째 리프로 넘어온 경우가 있다.
			 * 두 경우 모두 부모 노드들을 수정해 주어야 하므로 reArrange라는 재귀함수로 그 작업을 수행한다.
			 */
			}else if(location[cur_depth] == 0){
				reArrange(cur_depth-1);
			}
			
			/* 만약 현재 state가 goal state면 종료시간을 기록하고 메소드를 종료*/	
			if(isGoal()){		
				this.eTime = System.currentTimeMillis();
				return true;
				
			/* goal state가 아닌 경우 */	
			}else{
				
				/* expand 가능한 경우 : 현재 방문중인 node를 expand한 뒤에 반복 */
				if(canExpand()){
					for(int i = 0; i < N; i++){
						fringe.offer(i);
					}
					continue;
				}
				
				/* expand 가능하지 않고 fringe가 비어있는 경우 : 모든 node를 방문했음에도 답이 없는 경우이므로 false를 return 하고종료 */				
				if(fringe.isEmpty()){
					this.eTime = System.currentTimeMillis();
					return false;
				}
			}		
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
		
	/*
	 * location[cur_depth]의 값이 N-1 -> 0으로 넘어 갈 때
	 * 부모 노드들을 업데이트 해주는 recursive 메소드
	 */
	protected void reArrange(int dep){
		
		/* 부모노드의 값도 N-1이었을 경우 */
		if(location[dep] == N-1){
			
			/* 부모의 값도 0으로 바꿔줌  */
			location[dep] = 0; 
			
			/* 만약 루트까지 N-1이라면 */
			if(dep == 0){
				
				/* depth를 증가시킴  */
				cur_depth++; 
				location[cur_depth] = 0;
				return;
			}
			
			/* 부모의 부모까지 재귀적으로 확인 */
			reArrange(dep-1);
		
	    /* 부모노드의 값이 N-1이 아닌 경우 */
		}else{
			location[dep]++;
		}		
	}
	
}