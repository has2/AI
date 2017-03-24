import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chess {

	public static void main(String[] args) throws IOException {
		
		int N = Integer.parseInt(args[0]); // N���� �޾ƿ�
		int[] result_location;             // Solution ���� �޾Ƽ� ������ �迭
		double result_time;				   // �ɸ� �ð��� �����ϴ� double�� ����
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
		w_output = new FileWriter(output);	/* ���ο� ������ �����ϰ� Filewriter w_output�� ���� */	

		/**
		 * DFS Ǯ��
		 */
		DFS_chess dfs = new DFS_chess(N);				//DFS_chess Ŭ���� ����
		w_output.write(">DFS\nLocation : ");			
		
		/* Solution�� ���� */
		if(dfs.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = dfs.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
				w_output.write("No solution");
		}
		
		

		
		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = dfs.getTime();
		w_output.write("\nTime : ");
		w_output.write(Double.toString(result_time) + "��\n\n");

		
		/**
		 * BFS Ǯ��
		 */		
		BFS_chess bfs = new BFS_chess(N);				//BFS_chess Ŭ���� ����
		w_output.write(">BFS\nLocation : ");

		/* Solution�� ���� */
		if(bfs.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = bfs.getLocation();
			for(int i = 0 ; i<N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
				w_output.write("No solution");
		}

		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = bfs.getTime();
		w_output.write("\nTime : ");
		w_output.write(Double.toString(result_time) + "��\n\n");

		/**
		 * DFID Ǯ��
		 */		
		DFID_chess dfid = new DFID_chess(N);				//DFID_chess Ŭ���� ����
		w_output.write(">DFID\nLocation : ");

		/* Solution�� ���� */
		if(dfid.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = dfid.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
			w_output.write("No solution");
		}

		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = dfid.getTime();
		w_output.write("\nTime : ");
		w_output.write(Double.toString(result_time) + "��\n\n");

		w_output.close();
	}
}
