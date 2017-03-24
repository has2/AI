abstract class method_chess {
	int N;				// ���� ����(N*N)
	long sTime,eTime;   // sTime : Ž���� ���۽ð� eTime : Ž���� ����ð�  
	int[] location; 	// 0~N-1 column�� ���� ��ġ�� ����
	int cur_depth=0;    // ���� Ž������ ����� ���̸� ����
	
	
	/**
	 * ������ Ž�� ����� ���� NQueen������ �ذ��ϴ� �޼ҵ�
	 */
	public abstract boolean solve();
		
	/* 
	 * 0~N-1 column�� �ִ� queen�� ��ġ�� ��ȯ
	 */
	public int[] getLocation(){
		return this.location;
	}
	
	
	/* 
	 * �ɸ��ð��� ��ȯ
	 */
	public double getTime(){
		return (this.eTime-this.sTime)/1000.0f;
	}
	
	
	/* 
	 * expand ���������� üũ���� 
	 */
	protected boolean canExpand(){
		return cur_depth != N-1 ? true : false;  /*expand���� -> true, expand�Ұ� -> false*/
	}
	
	
	/* 
	 * goal state������ üũ 
	 */
	protected boolean isGoal(){
		
		/*
		 * üũ�� �� cur_depth�� N-1�� �ƴϸ� ������ goal state�� �ƴ�
		 */
		if(cur_depth!=N-1){			
			return false;
		}
		
		/*0~N-1 column�� �ִ� ������ üũ��*/
		for(int i = 0; i < N-1; i++)
		{
			
			/*���� row�� �ִ� ���� �ִ��� üũ*/
			for(int j = i+1; j < N; j++){
				if(location[i] == location[j]){
					return false;
				}
			}
			
			/*�ϵ��� ���� �밢���� ���� �ִ����� üũ*/
			for(int j = i+1; j < N; j++){
				if((location[i] + j - i >= N)){
					break;
				}
				
				if(location[j] == location[i] + j - i){
					return false;
				}
			}
			
			/*������ ���� �밢���� ���� �ִ����� üũ*/
			for(int j = i+1; j < N; j++){
				if(location[i] + i - j < 0){
					break;
				}
				
				if(location[j] == location[i] + i - j){
					return false;
				}
			}
		}
		return true;  /*���� ��� ������ ��������Ƿ� goal*/
	}
	
}
