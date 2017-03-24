import java.util.Stack;


/**
 *	DFS�� ������ Ǫ�� Ŭ����
 */
class DFS_chess extends method_chess{

	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����
		
	/**
	 * DFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public DFS_chess(int N){
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
		
		/* fringe�� ������� �� ���� �ݺ� */
		boolean loop = true;
		while(loop){
			
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */
			
			/* ���� ���� state�� goal state�� ����ð��� ����ϰ� �޼ҵ带 ���� */		
			if(isGoal()){						
						
				this.eTime = System.currentTimeMillis();
				return true;
				
			/* goal state�� �ƴ� ��� */	
			}else{
				
				/* expand������ ��� : ���� �湮���� node�� expand�� �ڿ� cur_depth�� 1 ������Ŵ */
				if(canExpand()){							
					for(int i = 0; i < N; i++){
						fringe.push(i);
					}
					cur_depth++;
					continue;
				}

				/* expand�������� �ʰ� fringe�� ����ִ� ��� : ��� node�� �湮�������� ���� ���� ����̹Ƿ� false�� return �ϰ����� */												 
				if(fringe.empty()){
								
					this.eTime = System.currentTimeMillis();
					return false;
				}
				
                
				/*
				 * expand �������� �ʰ� location[cur_depth]�� 0�� ��� : �� ���� DFS�� �������� �� leaf���� �����ؼ� expand�� �Ұ��� ������
				 * fringe���� ���� ���������Ƿ� cur_depth���� ���� ������ �ٽ� �ö󰡰� �ȴ�. �� ����� ��ġ�� ����ؼ� cur_depth�� 
				 * update ���ִ� �ڵ��̴�.
				 */
				if(location[cur_depth] == 0){
					
					int up = 1; // ���° �θ���� �ö󰡾� �ϴ����� �����ϴ� ����
					
					/* �θ� ������ üũ�� �� cur_depth�� update ���ش� */
					while(location[cur_depth-up] == 0) ++up;
					cur_depth -= up;
				}
			}								
		}
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	
}