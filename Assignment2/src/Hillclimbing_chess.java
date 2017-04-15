import java.util.Random;

public class Hillclimbing_chess {
	int N;				// 퀸의 개수(N*N)
	long sTime,eTime;   // sTime : 탐색의 시작시간 eTime : 탐색의 종료시간  
	int[] cur_location; // 0~N-1 column의 퀸의 위치를 저장
	
	/**
	 * Hillclimbing_chess를 초기화 시키는 생성자ㅍ
	 * location 배열을 0~N-1 사이의 랜덤 값으로 초기화시킴
	 * @param N
	 */
	public Hillclimbing_chess(int N){
		this.N =N;
		this.cur_location = new int[N];
		Random random = new Random();			  // 랜덤 클래스
		for(int i=0;i<N;i++){
		 this.cur_location[i] = random.nextInt(N);
		}
		this.sTime = System.currentTimeMillis();  // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	
	/*
	 * hill_climbing을 이용해 문제를 해결하는 주 메소드
	 */
	public void solve(){
		int[] result = hill_climbing(cur_location);
		System.arraycopy(result, 0, this.cur_location, 0, N);
	}
			
	/* 
	 * 0~N-1 column에 있는 queen의 위치를 반환
	 */
	public int[] getLocation(){
		return this.cur_location;
	}
	
	/* 
	 * 걸린시간을 반환
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	/*
	 * 현재 state에서의 heuristic 값을 반환 
	 */
	private int getHeuristic(int loc[]){

		int H=0; // heuristic 값을 저장할 변수
		
		/* 0~N-1 column에 있는 퀸들을 체크함 */
		for(int i = 0; i < N-1; i++)
		{
			/* 같은 row에 있는 퀸이 있는지 체크하고 같은 퀸이 있을때마다 heuristic 값 1 증가 */
			for(int j = i+1; j < N; j++){
				if(loc[i] == loc[j]){
					H++;
				}
			}

			/* 북동쪽 방향 대각선에 퀸이 있는지를 체크하고 같은 퀸이 있을때마다 heuristic 값 1 증가 */
			for(int j = i+1; j < N; j++){
				
				if((loc[i] + j - i >= N)){
					break;
				}

				if(loc[j] == loc[i] + j - i){
					H++;
				}
			}

			/*남동쪽 방향 대각선에 퀸이 있는지를 체크하고 같은 퀸이 있을때마다 heuristic 값 1 증가 */
			for(int j = i+1; j < N; j++){
				if(loc[i] + i - j < 0){
					break;
				}

				if(loc[j] == loc[i] + i - j){
					H++;
				}
			}
		}
		
		/* Heuristic 값 반환 */
		return H;
	}
	
	/*
	 * hill climbing을 이용해서 optimal state를 찾는 recursive 메소드 
	 */
	private int[] hill_climbing(int[] previous) throws StackOverflowError{

		int[] successor_temp = new int[N];     // heuristic값을 검사할 successor들을 임시로 저장
		int[] successor_best = new int[N];	   // successor중 가장 heuristic 값이 작은 state를 저장
		int min = this.getHeuristic(previous); // 이전 state의 heuristic 값을 저장 
		
		/*0~N-1 colomn에 대해서 */
		for(int i=0;i<N;i++){		
			
			System.arraycopy(previous, 0, successor_temp, 0, N); /* temp에 이전 state를 복사 */
			
			/* i 번째 colomn의 값을 0~N-1로 바꾸면서 heuristic 체크  */
			for(int j=0;j<N;j++){
				
				successor_temp[i]=j;
				
				/* heuristic 값이 현재 best라고 생각되는 heuristic 값보다 작으면 현재 state로 갱신 */
				if(min>this.getHeuristic(successor_temp)){
					
					min = this.getHeuristic(successor_temp);
					System.arraycopy(successor_temp, 0, successor_best, 0, N); /* best에 temp를 복사 */
				}
			}
		}
		
		/* heuristic 값이 0인 경우 */
		if(min==0){
			
			this.eTime = System.currentTimeMillis(); /* 현재 시간을 체크하고 solution state를 반환 */
			return successor_best;
			
	    /* heuristic 값이 이전 state의 값보다 작아진 경우 */
		}else if(min<this.getHeuristic(previous)){
			
			return hill_climbing(successor_best);    /* successor중 heuristic 값이 가장 작은 state로 hill_climbing 다시 시도*/
		
		/* local optimum에 갇힌 경우 */
		}else{
			
			this.reset(); 							 /* state를 다시 랜덤값으로 초기화 시킴 */
			return hill_climbing(this.cur_location); /* restart */
		}	
	}
	
	/*
	 * local optimum에 걸린 경우 reset시켜주는 메소드  
	 */
	private void reset(){
		for(int i = 0; i < N; i++) {
			Random random = new Random();
			this.cur_location[i] = random.nextInt(N);
		}
	}
}
