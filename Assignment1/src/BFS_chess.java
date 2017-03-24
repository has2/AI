import java.util.LinkedList;

/**
 *	DFS�� ������ Ǫ�� Ŭ����
 */
class BFS_chess extends method_chess{

	LinkedList<Integer> fringe; // BFS �̿��� ���� fringe�� Queue�� ����(LinkedList �̿�)
	
	/**
	 * BFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public BFS_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new LinkedList<Integer>(); // fringe�� �ʱ�ȭ (�ʱ���¸� �����ϱ� ���� -1�� ���� ���� �־���)
		for(int i = -1; i < N; i++) {
			fringe.offer(i);
		}
		this.sTime = System.currentTimeMillis(); // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	/*
	 * BFS�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */	
	public boolean solve(){
		
		/* fringe�� ������� �� ���� �ݺ� */
		boolean loop = true;
		while(loop){
			
				
			/* fringe���� ���� ���� ���� column�� ���� (i��° column�� cur_depth i�� �ǹ���) */
			location[cur_depth] = fringe.poll();
					
			/* cur_depth�� 0�� ���� ���� ó������ */
			if(cur_depth == 0){
				
				/*
				 * location[cur_depth](���� column���� ���õ� ���� ��ġ)���� -1�̶�� �ʱ�����̹Ƿ�
				 * �ѹ� �� dequeue�� ���༭ ù state�� ����
				 */
				if(location[cur_depth] == -1){
					location[cur_depth] = fringe.poll();
					
				
				/* location[cur_depth](���� column���� ���õ� ���� ��ġ)���� 0�� ���� depth 0 ������ ��ġ�� ������ depth 1�� �Ѿ��*/				 
				}else if(location[cur_depth] == 0){
					cur_depth++;
					location[cur_depth] = 0;
				}
				
			/*
			 * cur_depth�� 1�̻��� �� location[cur_depth](���� column���� ���õ� ���� ��ġ)���� 0�� ���� cur_depth������ ��ġ�� ������
			 * ���� depth�� �Ѿ�� 0�� �� ���� cur_depth���� �� ����Ʈ���� N-1��° �������� Ž���� ������ ������ Ʈ���� 0��° ������ �Ѿ�� ��찡 �ִ�.
			 * �� ��� ��� �θ� ������ ������ �־�� �ϹǷ� reArrange��� ����Լ��� �� �۾��� �����Ѵ�.
			 */
			}else if(location[cur_depth] == 0){
				reArrange(cur_depth-1);
			}
			
			/* ���� ���� state�� goal state�� ����ð��� ����ϰ� �޼ҵ带 ����*/	
			if(isGoal()){		
				this.eTime = System.currentTimeMillis();
				return true;
				
			/* goal state�� �ƴ� ��� */	
			}else{
				
				/* expand ������ ��� : ���� �湮���� node�� expand�� �ڿ� �ݺ� */
				if(canExpand()){
					for(int i = 0; i < N; i++){
						fringe.offer(i);
					}
					continue;
				}
				
				/* expand �������� �ʰ� fringe�� ����ִ� ��� : ��� node�� �湮�������� ���� ���� ����̹Ƿ� false�� return �ϰ����� */				
				if(fringe.isEmpty()){
					this.eTime = System.currentTimeMillis();
					return false;
				}
			}		
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
		
	/*
	 * location[cur_depth]�� ���� N-1 -> 0���� �Ѿ� �� ��
	 * �θ� ������ ������Ʈ ���ִ� recursive �޼ҵ�
	 */
	protected void reArrange(int dep){
		
		/* �θ����� ���� N-1�̾��� ��� */
		if(location[dep] == N-1){
			
			/* �θ��� ���� 0���� �ٲ���  */
			location[dep] = 0; 
			
			/* ���� ��Ʈ���� N-1�̶�� */
			if(dep == 0){
				
				/* depth�� ������Ŵ  */
				cur_depth++; 
				location[cur_depth] = 0;
				return;
			}
			
			/* �θ��� �θ���� ��������� Ȯ�� */
			reArrange(dep-1);
		
	    /* �θ����� ���� N-1�� �ƴ� ��� */
		}else{
			location[dep]++;
		}		
	}
	
}