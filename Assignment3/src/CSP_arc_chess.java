import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;

/**
 *	DFS�� ������ Ǫ�� Ŭ����
 */
class CSP_arc_chess extends method_chess{
	
	static final int ABLE   = 0;
    static final int FIXED  = 1;
	static final int UNABLE = 2;
	
	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����
		
	/**
	 * DFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public CSP_arc_chess(int N){
		
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
	int cnt=0;
	public boolean solve(){
		
		/* fringe�� ������� �� ���� �ݺ� */
		int cnt = 0;
		boolean loop = true;
		while(loop){
			cnt++;
			
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */
			
	
			/* consistent */
			if(isConsistent() && ArcConsistencyChecking()){
				
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("arc"+cnt);
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
	
	private boolean ArcConsistencyChecking(){
		
		if(cur_depth == N-1) return true;
	
		int[] new_domain_count = new int[N];
		int[][] new_domain = new int[N][N];
		getNewDomain(new_domain_count,new_domain);
		
	
		LinkedList<Integer> changed = new LinkedList<Integer>();
		
		for(int i = cur_depth + 1; i < N; i++)
			changed.offer(i);
		
		while(!changed.isEmpty()){
			int changedVar = changed.poll();
			for(int i= cur_depth+1; i < N; i++){
				if(i!=changedVar && SrcIsChanged(i,changedVar,new_domain,new_domain_count)){
					if(new_domain_count[i] == 0) return false;
					changed.offer(i);					
				}
			}		
		}
					
		domain_count = new_domain_count;
		domain = new_domain;
		return true;
	}
	
	private boolean SrcIsChanged(int src,int target,int new_domain[][],int new_domain_count[]){
		
		boolean isSrcChanged = false;
		
		for(int k = 0; k < N; k++){
			if(new_domain[src][k] == ABLE){
				int up = k+Math.abs(src-target);
				int down = k-Math.abs(src-target);

				for(int i = 0; i < N; i++){
					if((i != up) && (i != down) && (i != k) && (new_domain[target][i] == ABLE)){
						break;
					}

					if(i == N-1){
						new_domain[src][k] = UNABLE;
						new_domain_count[src]--;
						isSrcChanged = true;
					}
				}
			}
		}
		
		return isSrcChanged;
	}
	

}