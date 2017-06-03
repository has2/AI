import java.util.Random;

public class GA_chess {
	
	static final int Tournament_k = 5; // Tournament selection�ÿ� ����� ��� k
 	
	int N;				   // ���� ����(N*N)
	long sTime,eTime;      // sTime : Ž���� ���۽ð� eTime : Ž���� ����ð�  
	int num_pop;		   // �� ������ individual��
	float cross_rate;      // crossover rate
	float mutation_rate;   // mutation rate
	int[][] Population;    // ���� ����
	int[] FitnessForIndv;  // ���� ������ fitness�� ����
	int[][] NewPopulation; // �ļ���
	Random random;		   

	/**
	 * GA_chess�� �ʱ�ȭ ��Ű�� ������
	 * location �迭�� 0~N-1 ������ ���� ������ �ʱ�ȭ��Ŵ
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
		this.sTime = System.currentTimeMillis();  // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	
	/*
	 * Genetic algorithm�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	public int[] solve(){
		
		int res; // ���� individual�� index�� ����
		
		while(true){
			
			res = setFitness();	// ���� ���� ��� ���� ���� individual�� index, ���� ���� ��� -1 
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
	 * �ɸ��ð��� ��ȯ
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	
	/**
	 * crossover�� �Ϲ� selection���� ������� New Population�� mutation_rate��ŭ mutation ���� 
	 * mutation ������ column �Ѱ��� ��� random�ϰ� �ٲٴ� ��
	 */
	private void applyMutation(){
		
		int num_mutation = (int)(num_pop * mutation_rate); // mutation��ų individual�� ����
		int target; // ������ų individual
		int col;    // ������ų column
						
		for(int i = 0; i < num_mutation; i++){
			target = random.nextInt(num_pop);  			    // random���� individual ����
			col = random.nextInt(N);		   				// random���� column ����
			NewPopulation[target][col] = random.nextInt(N); // random�� ������ ����
		}
	}
	
	/**
	 * Population�� �� * crossover rate ��ŭ�� �ļ��븦 crossover ������ ���� ����
	 */
	private void generateUsingCrossOver(){
		
		int generatedByCross = (int)(num_pop*cross_rate);  // crossover�� �����Ǵ� individual�� ����
		int parent1,parent2;							   // parent1,parent2�� �����ϴ� ����
	
		/* ¦������ ������ */
		if(generatedByCross%2 != 0){
			generatedByCross -= 1; 
		}
		
		/* ��ü population�� cross_rate ��ŭ�� crossover ������ ���� ���� */
		for(int i = 0; i < generatedByCross; i += 2){
			parent1 = selectOne(Tournament_k);	 // parent1 ����
			parent2 = selectOne(Tournament_k);   // parent2 ����
			operateCrossOver(parent1,parent2,i); // crossover
		}
		
		/* ������ individual���� tournament selection�� ���� ���õ� individual�� �״�� ������*/
		for(int i = generatedByCross; i < num_pop; i++){
			
			parent1 = selectOne(Tournament_k); 
			
			/* tournament selection���� ���õ� individual ���� ����� ���� */
			for(int j = 0; j < N; j++){
				NewPopulation[i][j] = Population[parent1][j];
			}
		}
	}
	
	
	/**
	 * parent1, parent2�� �̿��� crossover ������ �����ؼ� ���ο� �Ĵ� �����ؼ� 
	 * NewPopulation�� idx,idx+1��° individual ����
	 */
	private void operateCrossOver(int par1, int par2, int idx){
		
		int cross_point = random.nextInt(N-1);  // cross�� �Ͼ ���� column
		
		/* 0 ~ cross_point column������ par1�� gene�� ������ */
		for(int i = 0; i <= cross_point; i++){
			NewPopulation[idx][i] = Population[par1][i];
		}
		
		/* cross_point+1 ~ N-1 column������ par2�� gene�� ������ */
		for(int i = cross_point+1; i < N; i++){
			NewPopulation[idx][i] = Population[par2][i];
		}
		
		/* ���� �ݴ� */
		for(int i = 0; i <= cross_point; i++){
			NewPopulation[idx+1][i] = Population[par2][i];
		}
		
		for(int i = cross_point+1; i < N; i++){
			NewPopulation[idx+1][i] = Population[par1][i];
		}
	}
	
	
	/**
	 * Tournament selection with k 
	 * Population�� �ִ� individual �� k���� �������� ��� ���� best�� individual �Ѱ��� index ��ȯ
	 */
	private int selectOne(int k){
		
		int best_indv = random.nextInt(num_pop); // �ʱ� best individual
		int indv;
		
		/* ������ best_indv�� �ʱ�ȭ�� �� �Ѱ��� ������Ƿ� k-1���� ���� �� */
		for(int i = 0; i < k-1; i++){
			
			indv = random.nextInt(num_pop); // Population�߿� �������� individual �Ѱ��� ����
			
			/* ���� ���� ���� individual�� fitness�� �� ���ٸ� */
			if(FitnessForIndv[best_indv] > FitnessForIndv[indv]){
				best_indv = indv; // best individual ��ü
			}
		}
		
		return best_indv;
	}
	
	/**
	 * ���� Population�� �ִ� ��� individual���� fitness�� ���� 
	 * fitness�� 0�� ��찡 �ִ� ��� solution�̹Ƿ� individual�� index ��ȯ
	 */
	private int setFitness(){
		
		/* ��� individual�� ���� */
		for(int i = 0; i < num_pop; i++){
			
			FitnessForIndv[i] = getFitness(Population[i]);
		
			/* fitness�� 0�̶�� */
			if(FitnessForIndv[i] == 0){
				return i; 
			}
		}
		
		/* solution�� ���� ��� */
		return -1;
	}
	
	/**
	 * ���� individual�� Fitness ���� ��ȯ 
	 */
	private int getFitness(int individual[]){

		int Fitness=0; // Fitness ���� ������ ����
	
		/* 0~N-1��° column�� ����  */
		for(int i = 0; i < N-1; i++)
		{
			/* i��° column�� j��° column�� üũ */
			for(int j= i + 1;j < N; j++){
				
				/* ���� ���� �����ϰ� �ִٸ� Fitness �� ����*/
				if(individual[i] == individual[j] 
						|| j - i == Math.abs(individual[i] - individual[j])){
					Fitness++;
				}
			}
		}

		return Fitness;
	}
	
}
