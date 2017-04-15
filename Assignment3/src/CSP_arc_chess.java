import java.util.Stack;
import java.util.LinkedList;

/**
 *	CSP Arc consistency class
 */
class CSP_arc_chess extends method_chess{
	
		
	/**
	 * CSP_arc_chess를 초기화시키는 생성자
	 * location 배열을 0~N-1로 초기화시키고 fringe에 0~N-1을 넣어줌
	 * @param N 체스판의 column의 개수
	 */
	public CSP_arc_chess(int N){
		
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		this.domain = new int[N][N];		// 각 column들이 가질 수 있는 value들이 ABLE한지 UNABLE한지를 저장
		this.domain_count = new int[N];		// 각 column	들이 가질 수 있는 value들의 개수를 저장
		
		for(int i = 0; i < N; i++) {
			domain_count[i] = N;			/* 초기 domain count를 N으로 초기화 */
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	/*
	 * Arc consistency를 이용해 문제를 해결하는 주 메소드
	 */
	int cnt=0;
	public boolean solve(){
		
		
		int cnt = 0;	// 방문한 node의 개수를 저장하기 위한 변수
		
		/* fringe가 비어있을 때 까지 반복 */		
		boolean loop = true;
		while(loop){
			
			cnt++;
			location[cur_depth] = fringe.pop(); /* fringe에서 뽑은 값을 현재 column에 넣음(i번째 column은 cur_depth i를 의미함) */
				
			/* 현재 assign한 변수가 consistent 하고  ArcConsistencyChecking뒤에도 consistent 하다면 */
			if(isConsistent() && ArcConsistencyChecking()){
				
				/* 마지막 column에 assign한 것이라면 solution이므로 true 반환하고 종료 */
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("CSP with arc consistency iterate count : "+cnt);
					return true;
				}
				
		
				/* 마지막 column이 아니라면 expand하고 cur_depth 1 증가 */
				for(int i = 0; i < N; i++){
					fringe.push(i);
				}
				cur_depth++;
				continue;

			/* 현재 assign한 변수가 consistent하지 않고 현재 depth에서 모든 변수를 할당해본 경우(Backtracking 필요) */
			}else if(location[cur_depth] == 0){

				
				try{
					/* ancestor들을 체크한 뒤 cur_depth를 update 해준다 */
					while(location[--cur_depth] == 0){}

				/* ArrayIndexOutOfBoundsException은 Solution이 존재하지 않는 2,3일때 발생하는데 그 상황을 처리하기 위한 catch문 */
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("No solution");
					return false;
				}
				/* Backtrack시에  domain[][]과 domain_count[]를 이전 상태로 돌려놓는 method 호출 */
				BacktrackDomain();					
			}		
		}

		this.eTime = System.currentTimeMillis();
		return false;
	}
	
	/*
	 * ArcConsistencyChecking을 하고 그 뒤에도 consistent하면 true를 반환하고 inconsistent시 false 반환
	 */
	private boolean ArcConsistencyChecking(){
		
		int[] new_domain_count = new int[N];		// update된  domain_count를 임시저장
		int[][] new_domain = new int[N][N];			// update된  domain을 임시저장
		
		getNewDomain(new_domain_count,new_domain);	// update
		
		LinkedList<Integer> changed = new LinkedList<Integer>(); // domain에 변화가 생긴 column들의 번호를 저장하는 queue
		
		/* 
		 * cur_depth번째 column에 assign을 했기 때문에 
		 * cur_depth+1번째 column부터 N-1번째 column은 
		 * 전부 domain이 적어도 1개는 줄어들었으므로 전부 queue에 넣어줌 
		 */
		for(int i = cur_depth + 1; i < N; i++)
			changed.offer(i);
		
		/* queue가 빌때까지 반복 */
		while(!changed.isEmpty()){
	
			int changedVar = changed.poll();
			
			/* queue에서 한개를 빼내고 그것과 연관있는 arc를 검사 */
			for(int i= cur_depth+1; i < N; i++){
				
				/* cur_depth+1~N-1까지의 column i에 대해 changedVar의 domain이 변했을 때  i의 domain도 update되었는지 확인 */
				if(i!=changedVar && SrcIsChanged(i,changedVar,new_domain,new_domain_count)){
					
					/* update된 domain의 count가 0이라면 */
					if(new_domain_count[i] == 0) return false;
					
					/* domain이 변했으므로 queue에 넣어줌 */
					changed.offer(i);					
				}
			}		
		}
		
		/* consistent 하다면 update된 domain을 복사 */
		domain_count = new_domain_count;
		domain = new_domain;
		return true;
	}
	
	/**
	 * column source,target에 대해 target에 의해 source column의 domain이 변경되었다면 true 아닐시 false return
	 * @param src				: 검사할 column 번호 
	 * @param target			: domain의 변화가 있었던 column 번호
	 * @param new_domain		: update를 위한 parameter
	 * @param new_domain_count	: update를 위한 parameter
	 * @return
	 */
	private boolean SrcIsChanged(int src,int target,int new_domain[][],int new_domain_count[]){
		
		boolean isSrcChanged = false;
		
		/* src의 value 0~N-1에 대해 */
		for(int k = 0; k < N; k++){
			
			/* value가 ABLE한 상태라면 */
			if(new_domain[src][k] == ABLE){
				int up = k+Math.abs(src-target);	// src의 대각선 위쪽 값 
				int down = k-Math.abs(src-target);  // src의 대각선 아래쪽 값
				
				/* target의 value 0~N-1에 대해 */
				for(int i = 0; i < N; i++){
					
					/* src의 대각선 위,대각선 아래값도 아니면서 평행하지도 않은 value에 대해 ABLE한 값이 있으면 src의 value k도 여전히 ABLE을 유지 */
					if((i != up) && (i != down) && (i != k) && (new_domain[target][i] == ABLE)){
						break;
					}
					
					/* src의 value k가 UNABLE할 때 */
					if(i == N-1){
						new_domain[src][k] = UNABLE;
						new_domain_count[src]--;
						isSrcChanged = true;
					}
				}
			}
		}
		
		return isSrcChanged;
	}
	

}