import java.util.Stack;


/**
 *	DFS로 문제를 푸는 클래스
 */
class DFS_chess extends method_chess{

	Stack<Integer> fringe; // DFS 이용을 위해 fringe를 Stack으로 만듬
		
	/**
	 * DFS_chess를 초기화시키는 생성자
	 * location 배열을 0~N-1로 초기화시키고 fringe에 0~N-1을 넣어줌
	 * @param N 체스판의 column의 개수
	 */
	public DFS_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		for(int i = 0; i < N; i++) {
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	
	/*
	 * DFS를 이용해 문제를 해결하는 주 메소드
	 */
	public boolean solve(){
		
		/* fringe가 비어있을 때 까지 반복 */
		boolean loop = true;
		while(loop){
			
			location[cur_depth] = fringe.pop(); /* fringe에서 뽑은 값을 현재 column에 넣음(i번째 column은 cur_depth i를 의미함) */
			
			/* 만약 현재 state가 goal state면 종료시간을 기록하고 메소드를 종료 */		
			if(isGoal()){						
						
				this.eTime = System.currentTimeMillis();
				return true;
				
			/* goal state가 아닌 경우 */	
			}else{
				
				/* expand가능한 경우 : 현재 방문중인 node를 expand한 뒤에 cur_depth를 1 증가시킴 */
				if(canExpand()){							
					for(int i = 0; i < N; i++){
						fringe.push(i);
					}
					cur_depth++;
					continue;
				}

				/* expand가능하지 않고 fringe가 비어있는 경우 : 모든 node를 방문했음에도 답이 없는 경우이므로 false를 return 하고종료 */												 
				if(fringe.empty()){
								
					this.eTime = System.currentTimeMillis();
					return false;
				}
				
                
				/*
				 * expand 가능하지 않고 location[cur_depth]가 0인 경우 : 이 경우는 DFS를 생각했을 때 leaf까지 도착해서 expand가 불가능 하지만
				 * fringe에는 값이 남아있으므로 cur_depth보다 높은 곳으로 다시 올라가게 된다. 그 노드의 위치를 계산해서 cur_depth를 
				 * update 해주는 코드이다.
				 */
				if(location[cur_depth] == 0){
					
					int up = 1; // 몇번째 부모까지 올라가야 하는지를 저장하는 변수
					
					/* 부모 노드들을 체크한 뒤 cur_depth를 update 해준다 */
					while(location[cur_depth-up] == 0) ++up;
					cur_depth -= up;
				}
			}								
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	
}