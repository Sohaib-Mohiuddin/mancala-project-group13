import java.util.*;

public class Solution {

    @SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
    	
		int[] input = new int[20];
    	Scanner scanner = new Scanner(System.in);
    	int move;
    	int j=0;
    	
    	for (int i=0; i<5; i++) {
    		Scanner lineScanner = new Scanner(scanner.nextLine());
			while (lineScanner.hasNextInt()) {
				input[j]=(lineScanner.nextInt());
				j++;
			}
    	}
    	
    	
    	
    	move = movemaker(input);
    	System.out.print("move chosen: "+move);
    	scanner.close();
    }
    
    //makes decision based on move summary
	private static int movemaker(int[] input) {
		int move = 0;
		int max=0;
		int score=0;
		
		for(int i=1; i<=6; i++) {
			int[] board = input.clone();
			if(board[i+1]!=0) {
				board = getScore(i,board); 
				if ((input[i+1]+i)==7) {
					board=search(board);
				}
				board[15]=board[8]-input[8];
				System.out.println(convertArrayToString(board)+" move="+i);
				score=board[8]-input[8];
				System.out.println("score="+board[15]+ "	 i= " + i);
				if (score>=max) {max=score;move=i;}
				//check treeboard if so add path to it
			}
		}
		
		return move;
	}
	private static int[] search(int[] board) {
		int max=0;
		int[] temp = new int[20];
		int[] maxboard = new int[20];
		maxboard=board;
		ArrayList<int[]> tree = new ArrayList<int[]>();
		tree=makeTree(board);
		Iterator<int[]> iter = tree.iterator(); 
		 while (iter.hasNext()) { 
	         
	            temp=iter.next();
	            //System.out.println(convertArrayToString(temp) + " "+max);
	            if((temp[8]-board[8])>max){
	            	maxboard=temp;
	            	max=temp[8]-board[8];
	            }
	        } 
		return maxboard;
	}
	
	private static ArrayList<int[]> makeTree(int[] board){
		int depth=1;
		int node=0;
		boolean var=false;
		int[] temp = new int[20];
		int[] tempclone = new int[20];
		temp=board;
		ArrayList<int[]> tree = new ArrayList<int[]>();
		ArrayList<int[]> q = new ArrayList<int[]>();
		q.add(temp);
		while(var!=true){
			tempclone = q.remove(node);
			temp = tempclone.clone();
			for (int i=1; i<=6; i++) {
				temp = tempclone.clone();
				if((temp[i+1]+i)==7) {
					temp=(getScore(i, temp));
					//temp[20]+=1;
					//temp[15+depth]=i;
					q.add(temp);
					//tree.add(temp);
					System.out.println("\nadded to q: "+ convertArrayToString(temp)+" i= " +i +" temp "+temp[i+1]+"\n");
				}
				else {
					temp=getScore(i, temp);
					tree.add(temp);
					System.out.println(i+" added to tree: " + convertArrayToString(temp)+" i= " +i +" temp "+temp[i+1]);
				}
			}var = q.isEmpty();
			depth++;
		}
		return tree;
	}
		
	//return board after change
	private static int[] getScore(int i, int[] board) {
		//int score;
		int endpoint = i + board[i+1];
		if (endpoint>13) {
			endpoint = 13%endpoint;
			for (int k = 2; k <14 ; k++) {
				board[k]++;
			}
		}
		
		if(endpoint>1 && endpoint<6 && board[endpoint+i]==0) {
			board = updateboard(i, board, endpoint);
			board[8]=board[8]+board[13-i];
			return board;
		}

		board = updateboard(i, board, endpoint);
		return board;		
	}

	private static int[] updateboard(int i, int[] board, int ep) { //bug adds to opponent's Mancala which is now mine
		for (int j=i+1;j<=ep;j++) {
			board[j+1]++;
		}
		board[i+1]=0;
		return board;
	}
	

    public static String convertArrayToString(int[] temp) {
        return Arrays.toString(temp);
    }
}
