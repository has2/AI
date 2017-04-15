import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chess {

	public static void main(String[] args) throws IOException {
		
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
		 * Hill climbing Ǯ��
		 */
		Hillclimbing_chess hillclimbing_chess = new Hillclimbing_chess(N);
		
		w_output.write(">Hill climbing\n");
		
		
		/* ���� ���� �� �߻��ϴ� StackOverflowError�� ���� ó���ϱ� ���� try~catch�� */
		try{
			
			hillclimbing_chess.solve();
			result_location = hillclimbing_chess.getLocation();
			elapsed_time = hillclimbing_chess.getTime();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			w_output.write("\nTotal Elapsed Time : "+ elapsed_time);
					
		}catch(StackOverflowError e){
			
			elapsed_time = (System.currentTimeMillis()-hillclimbing_chess.sTime)/1000.0f;
			
			w_output.write("No sulution");
			w_output.write("\nTotal Elapsed Time : "+ elapsed_time);
			
		}
		
		w_output.close();
	}
		
		
}
