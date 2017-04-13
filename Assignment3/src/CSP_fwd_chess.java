import java.util.Arrays;
import java.util.Stack;


/**
 *	DFS로 문제를 푸는 클래스
 */
class CSP_fwd_chess extends method_chess{
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	Stack<Integer> fringe; // DFS 이용을 위해 fringe를 Stack으로 만듬
	
	/**
	 * DFS_chess를 초기화시키는 생성자
	 * location 배열을 0~N-1로 초기화시키고 fringe에 0~N-1을 넣어줌
	 * @param N 체스판의 column의 개수
	 */
	public CSP_fwd_chess(int N){
		
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		this.domain = new int[N][N];
		this.domain_count = new int[N];
		
		for(int i = 0; i < N; i++) {
			domain_count[i] = N;
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	/*
	 * DFS를 이용해 문제를 해결하는 주 메소드
	 */
	public boolean solve(){
		
		/* fringe가 비어있을 때 까지 반복 */
		int cnt = 0;
		boolean loop = true;
		while(loop){
			cnt++;
			
			location[cur_depth] = fringe.pop(); /* fringe에서 뽑은 값을 현재 column에 넣음(i번째 column은 cur_depth i를 의미함) */

			/* consistent */
			if(isConsistent() && ForwardChecking()){
				
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("fwd"+cnt);
					return true;
				}
				
		
				/* expand */
				for(int i = 0; i < N; i++){
					fringe.push(i);
				}
				cur_depth++;
				continue;
			
			/* not consistent */
			}else{
				
				if(location[cur_depth] == 0){

					int up = 1; // 몇번째 부모까지 올라가야 하는지를 저장하는 변수

					/* 부모 노드들을 체크한 뒤 cur_depth를 update 해준다 */
					
					try{
						
						while(location[cur_depth-up] == 0){
							++up;
						}
						
					}catch(ArrayIndexOutOfBoundsException e){
						return false;
					}
					cur_depth -= up;
					BacktrackDomain();
					
				}
			}		
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	

	
	private boolean ForwardChecking(){
		
		int[] new_domain_count = new int[N];
		int[][] new_domain = new int[N][N];
		
		
		getNewDomain(new_domain_count,new_domain);
		
		
		for(int i = cur_depth + 1; i < N; i++){
			if(new_domain_count[i]==0){
				return false;
			}
		}
		

		domain_count = new_domain_count;
		domain = new_domain;
		
		return true;
	}
	
}