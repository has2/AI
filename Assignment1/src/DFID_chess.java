import java.util.Stack;

/**
 * DFID�� ������ Ǫ�� Ŭ����
 */
class DFID_chess extends method_chess{

	int limit_depth = 0;	   // DFID���� �ʿ��� limit_depth ��
	Stack<Integer> fringe; // DFS �̿��� ���� fringe�� Stack���� ����
	
	/**
	 * DFS_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public DFID_chess(int N){
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); // fringe�� �ʱ�ȭ ��Ŵ
		for(int i = 0; i < N; i++) {
			this.fringe.push(i);
		}
		this.sTime = System.currentTimeMillis();
	}

	/*
	 * DFID�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
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
				
				/* expand�������� �ʰ� fringe�� ����ִ� ��� : 2���� ���̽� */
				}else if(fringe.empty()){
					
					/* limit_depth=N-1�� ���� ������ ��� ��带 �湮�߱� ������ false return�ϰ� ���� */
					if(limit_depth == N-1){
						this.eTime = System.currentTimeMillis();
						return false;
						
					/* limit_depth<N-1�� ���� limit_depth�� 1 ������Ű�� Ŭ���� �������� reset�ð� �ٽ� Ž��*/
					}else{
						limit_depth++;
						reset();
						continue;
					}
				}
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
		
		this.eTime = System.currentTimeMillis();
		return false;
	}
	
	/*
	 * canExpand �޼ҵ尡 DFID������ N������ expand ���ɼ��� üũ�ϴ� ���� �ƴ϶�
	 * ��� �þ�� limit_depth������ üũ���� 
	 */
	protected boolean canExpand(){
		return this.cur_depth != this.limit_depth ? true : false; 
	}
	
	
	/*
	 * Ŭ���� �������� reset��Ŵ 
	 */
	private void reset(){
		for(int i = 0; i < N; i++) {
			this.fringe.push(i);
			this.location[i] = 0;
		}
		this.cur_depth = 0;		
	}
}