import java.util.Stack;


/**
 *	Standard CSP class
 */
class CSP_std_chess extends method_chess{

	/**
	 * CSP_std_chess를 초기화시키는 생성자
	 * location 배열을 0~N-1로 초기화시키고 fringe에 0~N-1을 넣어줌
	 * @param N 체스판의 column의 개수
	 */
	public CSP_std_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		for(int i = 0; i < N; i++) {
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	/*
	 * Standard CSP를 이용해 문제를 해결하는 주 메소드
	 */
	public boolean solve(){
		
		int cnt = 0;	// 방문한 node의 개수를 저장하기 위한 변수
		
		/* fringe가 비어있을 때 까지 반복 */
		boolean loop = true;
		while(loop){
			
			cnt++;
			location[cur_depth] = fringe.pop(); /* fringe에서 뽑은 값을 현재 column에 넣음(i번째 column은 cur_depth i를 의미함) */
			
			/* 현재 assign한 변수가 consistent 하다면 */
			if(isConsistent()){
				
				/* 마지막 column에 assign한 것이라면 solution이므로 true 반환하고 종료 */
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("Standard CSP iterate count : "+cnt);
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
			}
		}

		this.eTime = System.currentTimeMillis();
		return false;
	}
	
}