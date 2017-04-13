import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chess {

	public static void main(String[] args) throws IOException {
		
//		CSP_fwd_chess cspfwd = new CSP_fwd_chess(2);
//		cspfwd.solve();
//		for(int a : cspfwd.getLocation())
//			System.out.print(a+" ");
//		
//		
//		CSP_std_chess cspstd = new CSP_std_chess(2);
//		cspstd.solve();
//		for(int a : cspstd.getLocation())
//			System.out.print(a+" ");
		
		
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
		 * CSP_standard Ǯ��
		 */
		CSP_std_chess cspstd = new CSP_std_chess(N);				
		w_output.write(">Standard CSP\nLocation : ");			
		
		/* Solution�� ���� */
		if(cspstd.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = cspstd.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
				cspstd.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = cspstd.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "��\n\n");
		
		/**
		 * CSP_standard Ǯ��
		 */
		CSP_fwd_chess cspfwd = new CSP_fwd_chess(N);				//DFS_chess Ŭ���� ����
		w_output.write(">CSP with Forward Checking\nLocation : ");			
		
		/* Solution�� ���� */
		if(cspfwd.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = cspfwd.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
				cspfwd.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = cspfwd.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "��\n\n");
		
		
		/**
		 * Arc Ǯ��
		 */
		CSP_arc_chess csparc = new CSP_arc_chess(N);				//DFS_chess Ŭ���� ����
		w_output.write(">CSP with Arc Consistency\nLocation : ");			
		
		/* Solution�� ���� */
		if(csparc.solve()){
			
			/* ���� Į�� ��ġ�� ���� �� ���Ͽ� ��� */
			result_location = csparc.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution�� ���� �� */
		}else{
				csparc.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* �ɸ��ð��� �޾Ƽ� ���Ͽ� ��� */
		result_time = csparc.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "��\n\n");

		w_output.close();
	}
}
