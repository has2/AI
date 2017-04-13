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
		
		
		int N = Integer.parseInt(args[0]); // N값을 받아옴
		int[] result_location;             // Solution 값을 받아서 저장할 배열
		double result_time;				   // 걸린 시간을 저장하는 double형 변수
		File output;					 
		FileWriter w_output;			 
		
		/* 
		 * 파일의 절대 경로를 받아오는데 절대 경로가 C:\Users\Lee Sang Hwa\Desktop 
		 * 이런 식으로 공백이 있을 수도 있기 때문에 이어서 붙혀줘야함
		 */
		String Path = args[1];			 
		for(int i = 2; i < args.length; i++){
			Path += " " + args[i];
		}
		
		Path += "\\" + "result" + args[0] + ".txt";	 /* Path를 나타내는 구분자는 "\\"이므로 파일명 앞에 붙혀줌 */
		output = new File(Path);			         /* 파일 경로를 output에 연결 */
				
		/* 파일이 이미 존재한다면 삭제  */
		if(output.exists()){
			output.delete();
		}
		
		output.createNewFile();				
		w_output = new FileWriter(output);	/* 새로운 파일을 생성하고 Filewriter w_output을 연결 */	

		/**
		 * CSP_standard 풀이
		 */
		CSP_std_chess cspstd = new CSP_std_chess(N);				
		w_output.write(">Standard CSP\nLocation : ");			
		
		/* Solution이 존재 */
		if(cspstd.solve()){
			
			/* 퀸의 칼럼 위치를 받은 뒤 파일에 출력 */
			result_location = cspstd.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution이 없을 때 */
		}else{
				cspstd.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* 걸린시간을 받아서 파일에 출력 */
		result_time = cspstd.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "초\n\n");
		
		/**
		 * CSP_standard 풀이
		 */
		CSP_fwd_chess cspfwd = new CSP_fwd_chess(N);				//DFS_chess 클래스 생성
		w_output.write(">CSP with Forward Checking\nLocation : ");			
		
		/* Solution이 존재 */
		if(cspfwd.solve()){
			
			/* 퀸의 칼럼 위치를 받은 뒤 파일에 출력 */
			result_location = cspfwd.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution이 없을 때 */
		}else{
				cspfwd.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* 걸린시간을 받아서 파일에 출력 */
		result_time = cspfwd.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "초\n\n");
		
		
		/**
		 * Arc 풀이
		 */
		CSP_arc_chess csparc = new CSP_arc_chess(N);				//DFS_chess 클래스 생성
		w_output.write(">CSP with Arc Consistency\nLocation : ");			
		
		/* Solution이 존재 */
		if(csparc.solve()){
			
			/* 퀸의 칼럼 위치를 받은 뒤 파일에 출력 */
			result_location = csparc.getLocation();
			for(int i = 0; i < N; i++){
				w_output.write(result_location[i] + " ");
			}
			
		/* Solution이 없을 때 */
		}else{
				csparc.eTime = System.currentTimeMillis();
				w_output.write("No solution");
		}
		
		
		/* 걸린시간을 받아서 파일에 출력 */
		result_time = csparc.getTime();
		w_output.write("\nTotal Elapsed Time : ");
		w_output.write(Double.toString(result_time) + "초\n\n");

		w_output.close();
	}
}
