import java.util.Random;

public class GA_chess {
	
	static final int Tournament_k = 5; // Tournament selection시에 사용할 상수 k
 	
	int N;				   // 퀸의 개수(N*N)
	long sTime,eTime;      // sTime : 탐색의 시작시간 eTime : 탐색의 종료시간  
	int num_pop;		   // 한 세대의 individual수
	float cross_rate;      // crossover rate
	float mutation_rate;   // mutation rate
	int[][] Population;    // 현재 세대
	int[] FitnessForIndv;  // 현재 세대의 fitness를 저장
	int[][] NewPopulation; // 후세대
	Random random;		   

	/**
	 * GA_chess를 초기화 시키는 생성자
	 * location 배열을 0~N-1 사이의 랜덤 값으로 초기화시킴
	 * @param N
	 */
	public GA_chess(int N, int pop, float cross, float mut){
		this.N = N;
		this.num_pop = pop;
		this.cross_rate = cross;
		this.mutation_rate = mut;
		this.Population = new int[num_pop][N];
		this.NewPopulation = new int[num_pop][N];
		this.FitnessForIndv = new int[num_pop];
		this.random = new Random();			  
		
		for(int i = 0; i < num_pop; i++){
			for(int j = 0; j < N; j++){
			 this.Population[i][j] = random.nextInt(N);
			}
		}
		this.sTime = System.currentTimeMillis();  // 시간 측정을 위해 생성자 호출시의 시간을 저장
	}
	
	
	/*
	 * Genetic algorithm을 이용해 문제를 해결하는 주 메소드
	 */
	public int[] solve(){
		
		int res; // 답인 individual의 index를 저장
		
		while(true){
			
			res = setFitness();	// 답이 있을 경우 답을 가진 individual의 index, 답이 없는 경우 -1 
			if(res != -1){
				this.eTime = System.currentTimeMillis();
				return Population[res];
			}
			
			generateUsingCrossOver();   /* cross over */
			applyMutation();			/* mutation */
			Population = NewPopulation; /* copy */
		}
	}
			
	
	/* 
	 * 걸린시간을 반환
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	
	/**
	 * crossover와 일반 selection으로 만들어진 New Population에 mutation_rate만큼 mutation 적용 
	 * mutation 연산은 column 한개를 골라 random하게 바꾸는 것
	 */
	private void applyMutation(){
		
		int num_mutation = (int)(num_pop * mutation_rate); // mutation시킬 individual의 개수
		int target; // 변형시킬 individual
		int col;    // 변형시킬 column
						
		for(int i = 0; i < num_mutation; i++){
			target = random.nextInt(num_pop);  			    // random으로 individual 선택
			col = random.nextInt(N);		   				// random으로 column 선택
			NewPopulation[target][col] = random.nextInt(N); // random한 값으로 변경
		}
	}
	
	/**
	 * Population의 수 * crossover rate 만큼의 후세대를 crossover 연산을 통해 생성
	 */
	private void generateUsingCrossOver(){
		
		int generatedByCross = (int)(num_pop*cross_rate);  // crossover로 생성되는 individual의 개수
		int parent1,parent2;							   // parent1,parent2를 저장하는 변수
	
		/* 짝수개로 맞춰줌 */
		if(generatedByCross%2 != 0){
			generatedByCross -= 1; 
		}
		
		/* 전체 population의 cross_rate 만큼만 crossover 연산을 통해 생성 */
		for(int i = 0; i < generatedByCross; i += 2){
			parent1 = selectOne(Tournament_k);	 // parent1 선택
			parent2 = selectOne(Tournament_k);   // parent2 선택
			operateCrossOver(parent1,parent2,i); // crossover
		}
		
		/* 나머지 individual들은 tournament selection에 의해 선택된 individual을 그대로 가져옴*/
		for(int i = generatedByCross; i < num_pop; i++){
			
			parent1 = selectOne(Tournament_k); 
			
			/* tournament selection으로 선택된 individual 다음 세대로 복사 */
			for(int j = 0; j < N; j++){
				NewPopulation[i][j] = Population[parent1][j];
			}
		}
	}
	
	
	/**
	 * parent1, parent2를 이용해 crossover 연산을 진행해서 새로운 후대 생성해서 
	 * NewPopulation의 idx,idx+1번째 individual 생성
	 */
	private void operateCrossOver(int par1, int par2, int idx){
		
		int cross_point = random.nextInt(N-1);  // cross가 일어날 기준 column
		
		/* 0 ~ cross_point column까지는 par1이 gene을 물려줌 */
		for(int i = 0; i <= cross_point; i++){
			NewPopulation[idx][i] = Population[par1][i];
		}
		
		/* cross_point+1 ~ N-1 column까지는 par2가 gene을 물려줌 */
		for(int i = cross_point+1; i < N; i++){
			NewPopulation[idx][i] = Population[par2][i];
		}
		
		/* 위와 반대 */
		for(int i = 0; i <= cross_point; i++){
			NewPopulation[idx+1][i] = Population[par2][i];
		}
		
		for(int i = cross_point+1; i < N; i++){
			NewPopulation[idx+1][i] = Population[par1][i];
		}
	}
	
	
	/**
	 * Tournament selection with k 
	 * Population에 있는 individual 중 k개를 랜덤으로 골라서 가장 best인 individual 한개의 index 반환
	 */
	private int selectOne(int k){
		
		int best_indv = random.nextInt(num_pop); // 초기 best individual
		int indv;
		
		/* 위에서 best_indv를 초기화할 때 한개를 골랐으므로 k-1개만 고르면 됨 */
		for(int i = 0; i < k-1; i++){
			
			indv = random.nextInt(num_pop); // Population중에 랜덤으로 individual 한개를 뽑음
			
			/* 만약 지금 뽑은 individual의 fitness가 더 낮다면 */
			if(FitnessForIndv[best_indv] > FitnessForIndv[indv]){
				best_indv = indv; // best individual 교체
			}
		}
		
		return best_indv;
	}
	
	/**
	 * 현재 Population에 있는 모든 individual들의 fitness를 저장 
	 * fitness가 0인 경우가 있는 경우 solution이므로 individual의 index 반환
	 */
	private int setFitness(){
		
		/* 모든 individual에 대해 */
		for(int i = 0; i < num_pop; i++){
			
			FitnessForIndv[i] = getFitness(Population[i]);
		
			/* fitness가 0이라면 */
			if(FitnessForIndv[i] == 0){
				return i; 
			}
		}
		
		/* solution이 없는 경우 */
		return -1;
	}
	
	/**
	 * 현재 individual의 Fitness 값을 반환 
	 */
	private int getFitness(int individual[]){

		int Fitness=0; // Fitness 값을 저장할 변수
	
		/* 0~N-1번째 column에 대해  */
		for(int i = 0; i < N-1; i++)
		{
			/* i번째 column과 j번째 column을 체크 */
			for(int j= i + 1;j < N; j++){
				
				/* 퀸이 서로 공격하고 있다면 Fitness 값 증가*/
				if(individual[i] == individual[j] 
						|| j - i == Math.abs(individual[i] - individual[j])){
					Fitness++;
				}
			}
		}

		return Fitness;
	}
	
}
