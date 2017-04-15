import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chess {

	public static void main(String[] args) throws IOException {
		
		int N = Integer.parseInt(args[0]); // N값을 받아옴
		int[] result_location;             // Solution 값을 받아서 저장할 배열
		double elapsed_time;			   // 걸린 시간을 저장하는 double형 변수
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
		w_output = new FileWriter(output);			/* 새로운 파일을 생성하고 Filewriter w_output을 연결 */
		
		
		/**
		 * Hill climbing 풀이
		 */
		Hillclimbing_chess hillclimbing_chess = new Hillclimbing_chess(N);
		
		w_output.write(">Hill climbing\n");
		
		
		/* 답이 없을 때 발생하는 StackOverflowError를 따로 처리하기 위한 try~catch문 */
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
