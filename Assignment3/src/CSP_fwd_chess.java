import java.util.Arrays;
import java.util.Stack;


/**
 *	DFS�� ������ Ǫ�� Ŭ����
 */
class CSP_fwd_chess extends method_chess{
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����
	
	/**
	 * DFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public CSP_fwd_chess(int N){
		
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		this.domain = new int[N][N];
		this.domain_count = new int[N];
		
		for(int i = 0; i < N; i++) {
			domain_count[i] = N;
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	/*
	 * DFS�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	public boolean solve(){
		
		/* fringe�� ������� �� ���� �ݺ� */
		int cnt = 0;
		boolean loop = true;
		while(loop){
			cnt++;
			
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */

			/* consistent */
			if(isConsistent() && ForwardChecking()){
				
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("fwd"+cnt);
					return true;
				}
				
		
				/* expand */
				for(int i = 0; i < N; i++){
					fringe.push(i);
				}
				cur_depth++;
				continue;
			
			/* not consistent */
			}else{
				
				if(location[cur_depth] == 0){

					int up = 1; // ���° �θ���� �ö󰡾� �ϴ����� �����ϴ� ����

					/* �θ� ������ üũ�� �� cur_depth�� update ���ش� */
					
					try{
						
						while(location[cur_depth-up] == 0){
							++up;
						}
						
					}catch(ArrayIndexOutOfBoundsException e){
						return false;
					}
					cur_depth -= up;
					BacktrackDomain();
					
				}
			}		
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	

	
	private boolean ForwardChecking(){
		
		int[] new_domain_count = new int[N];
		int[][] new_domain = new int[N][N];
		
		
		getNewDomain(new_domain_count,new_domain);
		
		
		for(int i = cur_depth + 1; i < N; i++){
			if(new_domain_count[i]==0){
				return false;
			}
		}
		

		domain_count = new_domain_count;
		domain = new_domain;
		
		return true;
	}
	
}