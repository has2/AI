import java.util.Stack;


/**
 *	Standard CSP class
 */
class CSP_std_chess extends method_chess{

	/**
	 * CSP_std_chess�� �ʱ�ȭ��Ű�� ������
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
	 * Standard CSP�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	public boolean solve(){
		
		int cnt = 0;	// �湮�� node�� ������ �����ϱ� ���� ����
		
		/* fringe�� ������� �� ���� �ݺ� */
		boolean loop = true;
		while(loop){
			
			cnt++;
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */
			
			/* ���� assign�� ������ consistent �ϴٸ� */
			if(isConsistent()){
				
				/* ������ column�� assign�� ���̶�� solution�̹Ƿ� true ��ȯ�ϰ� ���� */
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("Standard CSP iterate count : "+cnt);
					return true;
				}
				
				/* ������ column�� �ƴ϶�� expand�ϰ� cur_depth 1 ���� */
				for(int i = 0; i < N; i++){
					fringe.push(i);
				}
				cur_depth++;
				continue;
			
			/* ���� assign�� ������ consistent���� �ʰ� ���� depth���� ��� ������ �Ҵ��غ� ���(Backtracking �ʿ�) */
			}else if(location[cur_depth] == 0){

				
				try{					
					
					/* ancestor���� üũ�� �� cur_depth�� update ���ش� */
					while(location[--cur_depth] == 0){}
				
				/* ArrayIndexOutOfBoundsException�� Solution�� �������� �ʴ� 2,3�϶� �߻��ϴµ� �� ��Ȳ�� ó���ϱ� ���� catch�� */
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("No solution");
					return false;

				}			
			}
		}

		this.eTime = System.currentTimeMillis();
		return false;
	}
	
}