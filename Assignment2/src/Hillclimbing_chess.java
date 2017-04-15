import java.util.Random;

public class Hillclimbing_chess {
	int N;				// ���� ����(N*N)
	long sTime,eTime;   // sTime : Ž���� ���۽ð� eTime : Ž���� ����ð�  
	int[] cur_location; // 0~N-1 column�� ���� ��ġ�� ����
	
	/**
	 * Hillclimbing_chess�� �ʱ�ȭ ��Ű�� �����ڤ�
	 * location �迭�� 0~N-1 ������ ���� ������ �ʱ�ȭ��Ŵ
	 * @param N
	 */
	public Hillclimbing_chess(int N){
		this.N =N;
		this.cur_location = new int[N];
		Random random = new Random();			  // ���� Ŭ����
		for(int i=0;i<N;i++){
		 this.cur_location[i] = random.nextInt(N);
		}
		this.sTime = System.currentTimeMillis();  // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	
	/*
	 * hill_climbing�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	public void solve(){
		int[] result = hill_climbing(cur_location);
		System.arraycopy(result, 0, this.cur_location, 0, N);
	}
			
	/* 
	 * 0~N-1 column�� �ִ� queen�� ��ġ�� ��ȯ
	 */
	public int[] getLocation(){
		return this.cur_location;
	}
	
	/* 
	 * �ɸ��ð��� ��ȯ
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	/*
	 * ���� state������ heuristic ���� ��ȯ 
	 */
	private int getHeuristic(int loc[]){

		int H=0; // heuristic ���� ������ ����
		
		/* 0~N-1 column�� �ִ� ������ üũ�� */
		for(int i = 0; i < N-1; i++)
		{
			/* ���� row�� �ִ� ���� �ִ��� üũ�ϰ� ���� ���� ���������� heuristic �� 1 ���� */
			for(int j = i+1; j < N; j++){
				if(loc[i] == loc[j]){
					H++;
				}
			}

			/* �ϵ��� ���� �밢���� ���� �ִ����� üũ�ϰ� ���� ���� ���������� heuristic �� 1 ���� */
			for(int j = i+1; j < N; j++){
				
				if((loc[i] + j - i >= N)){
					break;
				}

				if(loc[j] == loc[i] + j - i){
					H++;
				}
			}

			/*������ ���� �밢���� ���� �ִ����� üũ�ϰ� ���� ���� ���������� heuristic �� 1 ���� */
			for(int j = i+1; j < N; j++){
				if(loc[i] + i - j < 0){
					break;
				}

				if(loc[j] == loc[i] + i - j){
					H++;
				}
			}
		}
		
		/* Heuristic �� ��ȯ */
		return H;
	}
	
	/*
	 * hill climbing�� �̿��ؼ� optimal state�� ã�� recursive �޼ҵ� 
	 */
	private int[] hill_climbing(int[] previous) throws StackOverflowError{

		int[] successor_temp = new int[N];     // heuristic���� �˻��� successor���� �ӽ÷� ����
		int[] successor_best = new int[N];	   // successor�� ���� heuristic ���� ���� state�� ����
		int min = this.getHeuristic(previous); // ���� state�� heuristic ���� ���� 
		
		/*0~N-1 colomn�� ���ؼ� */
		for(int i=0;i<N;i++){		
			
			System.arraycopy(previous, 0, successor_temp, 0, N); /* temp�� ���� state�� ���� */
			
			/* i ��° colomn�� ���� 0~N-1�� �ٲٸ鼭 heuristic üũ  */
			for(int j=0;j<N;j++){
				
				successor_temp[i]=j;
				
				/* heuristic ���� ���� best��� �����Ǵ� heuristic ������ ������ ���� state�� ���� */
				if(min>this.getHeuristic(successor_temp)){
					
					min = this.getHeuristic(successor_temp);
					System.arraycopy(successor_temp, 0, successor_best, 0, N); /* best�� temp�� ���� */
				}
			}
		}
		
		/* heuristic ���� 0�� ��� */
		if(min==0){
			
			this.eTime = System.currentTimeMillis(); /* ���� �ð��� üũ�ϰ� solution state�� ��ȯ */
			return successor_best;
			
	    /* heuristic ���� ���� state�� ������ �۾��� ��� */
		}else if(min<this.getHeuristic(previous)){
			
			return hill_climbing(successor_best);    /* successor�� heuristic ���� ���� ���� state�� hill_climbing �ٽ� �õ�*/
		
		/* local optimum�� ���� ��� */
		}else{
			
			this.reset(); 							 /* state�� �ٽ� ���������� �ʱ�ȭ ��Ŵ */
			return hill_climbing(this.cur_location); /* restart */
		}	
	}
	
	/*
	 * local optimum�� �ɸ� ��� reset�����ִ� �޼ҵ�  
	 */
	private void reset(){
		for(int i = 0; i < N; i++) {
			Random random = new Random();
			this.cur_location[i] = random.nextInt(N);
		}
	}
}
