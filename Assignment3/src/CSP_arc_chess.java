import java.util.Stack;
import java.util.LinkedList;

/**
 *	CSP Arc consistency class
 */
class CSP_arc_chess extends method_chess{
	
		
	/**
	 * CSP_arc_chess�� �ʱ�ȭ��Ű�� ������
	 * location �迭�� 0~N-1�� �ʱ�ȭ��Ű�� fringe�� 0~N-1�� �־���
	 * @param N ü������ column�� ����
	 */
	public CSP_arc_chess(int N){
		
		this.N = N;
		this.location = new int[N];
		this.fringe = new Stack<Integer>(); 
		this.domain = new int[N][N];		// �� column���� ���� �� �ִ� value���� ABLE���� UNABLE������ ����
		this.domain_count = new int[N];		// �� column	���� ���� �� �ִ� value���� ������ ����
		
		for(int i = 0; i < N; i++) {
			domain_count[i] = N;			/* �ʱ� domain count�� N���� �ʱ�ȭ */
			fringe.push(i);
		}
		this.sTime = System.currentTimeMillis(); // �ð� ������ ���� ������ ȣ����� �ð��� ����
	}
	
	/*
	 * Arc consistency�� �̿��� ������ �ذ��ϴ� �� �޼ҵ�
	 */
	int cnt=0;
	public boolean solve(){
		
		
		int cnt = 0;	// �湮�� node�� ������ �����ϱ� ���� ����
		
		/* fringe�� ������� �� ���� �ݺ� */		
		boolean loop = true;
		while(loop){
			
			cnt++;
			location[cur_depth] = fringe.pop(); /* fringe���� ���� ���� ���� column�� ����(i��° column�� cur_depth i�� �ǹ���) */
				
			/* ���� assign�� ������ consistent �ϰ�  ArcConsistencyChecking�ڿ��� consistent �ϴٸ� */
			if(isConsistent() && ArcConsistencyChecking()){
				
				/* ������ column�� assign�� ���̶�� solution�̹Ƿ� true ��ȯ�ϰ� ���� */
				if(cur_depth == N-1){
					this.eTime = System.currentTimeMillis();
					System.out.println("CSP with arc consistency iterate count : "+cnt);
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
				/* Backtrack�ÿ�  domain[][]�� domain_count[]�� ���� ���·� �������� method ȣ�� */
				BacktrackDomain();					
			}		
		}

		this.eTime = System.currentTimeMillis();
		return false;
	}
	
	/*
	 * ArcConsistencyChecking�� �ϰ� �� �ڿ��� consistent�ϸ� true�� ��ȯ�ϰ� inconsistent�� false ��ȯ
	 */
	private boolean ArcConsistencyChecking(){
		
		int[] new_domain_count = new int[N];		// update��  domain_count�� �ӽ�����
		int[][] new_domain = new int[N][N];			// update��  domain�� �ӽ�����
		
		getNewDomain(new_domain_count,new_domain);	// update
		
		LinkedList<Integer> changed = new LinkedList<Integer>(); // domain�� ��ȭ�� ���� column���� ��ȣ�� �����ϴ� queue
		
		/* 
		 * cur_depth��° column�� assign�� �߱� ������ 
		 * cur_depth+1��° column���� N-1��° column�� 
		 * ���� domain�� ��� 1���� �پ������Ƿ� ���� queue�� �־��� 
		 */
		for(int i = cur_depth + 1; i < N; i++)
			changed.offer(i);
		
		/* queue�� �������� �ݺ� */
		while(!changed.isEmpty()){
	
			int changedVar = changed.poll();
			
			/* queue���� �Ѱ��� ������ �װͰ� �����ִ� arc�� �˻� */
			for(int i= cur_depth+1; i < N; i++){
				
				/* cur_depth+1~N-1������ column i�� ���� changedVar�� domain�� ������ ��  i�� domain�� update�Ǿ����� Ȯ�� */
				if(i!=changedVar && SrcIsChanged(i,changedVar,new_domain,new_domain_count)){
					
					/* update�� domain�� count�� 0�̶�� */
					if(new_domain_count[i] == 0) return false;
					
					/* domain�� �������Ƿ� queue�� �־��� */
					changed.offer(i);					
				}
			}		
		}
		
		/* consistent �ϴٸ� update�� domain�� ���� */
		domain_count = new_domain_count;
		domain = new_domain;
		return true;
	}
	
	/**
	 * column source,target�� ���� target�� ���� source column�� domain�� ����Ǿ��ٸ� true �ƴҽ� false return
	 * @param src				: �˻��� column ��ȣ 
	 * @param target			: domain�� ��ȭ�� �־��� column ��ȣ
	 * @param new_domain		: update�� ���� parameter
	 * @param new_domain_count	: update�� ���� parameter
	 * @return
	 */
	private boolean SrcIsChanged(int src,int target,int new_domain[][],int new_domain_count[]){
		
		boolean isSrcChanged = false;
		
		/* src�� value 0~N-1�� ���� */
		for(int k = 0; k < N; k++){
			
			/* value�� ABLE�� ���¶�� */
			if(new_domain[src][k] == ABLE){
				int up = k+Math.abs(src-target);	// src�� �밢�� ���� �� 
				int down = k-Math.abs(src-target);  // src�� �밢�� �Ʒ��� ��
				
				/* target�� value 0~N-1�� ���� */
				for(int i = 0; i < N; i++){
					
					/* src�� �밢�� ��,�밢�� �Ʒ����� �ƴϸ鼭 ���������� ���� value�� ���� ABLE�� ���� ������ src�� value k�� ������ ABLE�� ���� */
					if((i != up) && (i != down) && (i != k) && (new_domain[target][i] == ABLE)){
						break;
					}
					
					/* src�� value k�� UNABLE�� �� */
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