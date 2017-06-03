import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chess {

	static final int Num_Population = 40;
	static final float Crossover_rate = 0.6f;
	static final float Mutation_rate = 0.3f;
	
	public static void main(String[] args) throws IOException  {
				
		int N = Integer.parseInt(args[0]); // N���� �޾ƿ�
		int[] result_location;             // Solution ���� �޾Ƽ� ������ �迭
		double elapsed_time;			   // �ɸ� �ð��� �����ϴ� double�� ����
		File output;					 
		FileWriter w_output;			 
		
		/* 
		 * ������ ���� ��θ� �޾ƿ��µ� ���� ��ΰ� C:\Users\Lee Sang Hwa\Desktop 
		 * �̷� ������ ������ ���� ���� �ֱ� ������ �̾ ���������
		 */
		String Path = args[1];			 
		for(int i = 2; i < args.length; i++){
			Path += " " + args[i];
		}
		
		Path += "\\" + "result" + args[0] + ".txt";	 /* Path�� ��Ÿ���� �����ڴ� "\\"�̹Ƿ� ���ϸ� �տ� ������ */
		output = new File(Path);			         /* ���� ��θ� output�� ���� */
				
		/* ������ �̹� �����Ѵٸ� ����  */
		if(output.exists()){
			output.delete();
		}
		
		output.createNewFile();				
		w_output = new FileWriter(output);			/* ���ο� ������ �����ϰ� Filewriter w_output�� ���� */
		
		
		/**
		 * Genetic algorithm Ǯ��
		 */
		
		w_output.write(">Genetic Algorithm\n");
		
		if(N != 2 && N!= 3){
			
			GA_chess ga = new GA_chess(N,Num_Population,Crossover_rate,Mutation_rate);
			result_location = ga.solve();
			elapsed_time = ga.getTime();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		}else{
			
			w_output.write("no solution");
			elapsed_time =0.0f;
		}
		w_output.write("\nTotal Elapsed Time : "+ elapsed_time);
			
		w_output.close();
	}
		
		
}
