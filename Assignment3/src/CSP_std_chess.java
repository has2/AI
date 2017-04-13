import java.util.Stack;


/**
 *	DFS�� ������ Ǫ�� Ŭ����
 */
class CSP_std_chess extends method_chess{

	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����
		
	/**
	 * DFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public CSP_std_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		for(int i = 0; i < N; i++) {
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	/*
	 * DFS�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	public boolean solve(){
		
		int cnt = 0;
		/* fringe�� ������� �� ���� �ݺ� */
		boolean loop = true;
		while(loop){
			cnt++;
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */
			
			/* consistent */
			if(isConsistent()){
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("std"+cnt);
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
						
						while(location[cur_depth-up] == 0) ++up;
						
					}catch(ArrayIndexOutOfBoundsException e){
						
						return false;
						
					}
					cur_depth -= up;
				}
			}		
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	
}