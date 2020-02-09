import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Solutiontest {

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
        
        
        if (input[0]==2){input=adjustPlayer(input);}
        move = max_Move(input);
        System.out.print(move);
        scanner.close();
    }
    

	private static int max_Move (int[] input) {
		int depth=3;
		int max=0;
		int[] temp = new int[20];
		int[] board = new int[20];
	    ArrayList<int[]> moveset= new ArrayList<int[]>();
		ListIterator<int[]> iter;
		temp=input;
		ArrayList<int[]> tree = new ArrayList<int[]>();
		tree.add(input);
		for(int i=1; i<=depth; i++) {
			System.out.println("depth="+i);
			
				//select best node
				iter = tree.listIterator();
				while (iter.hasNext()) { 
			         
			            temp=iter.next();
			            if(i%2==0) {adjustPlayer(temp);}
			            System.out.println(convertArrayToString(temp) + " "+max);
			            moveset.addAll(movemaker2(temp, i));        
			}
			//save 
			tree.clear();
            if(i%2==0) {
            	iter = moveset.listIterator();
            	while (iter.hasNext()) { 
			         
		          temp=iter.next();
		          tree.add(adjustPlayer(temp));
            }
            }
            else {
    			tree.addAll(moveset);
            }		
		}
		iter = tree.listIterator();
		while (iter.hasNext()) { 
            
            temp=iter.next();
            System.out.println(convertArrayToString(temp) + " "+max);
            if((temp[8]-input[8])>max){
                board=temp;
                max=temp[8]-input[8];
            }
        } 
		//
    	return board[18];
    	
    }
    
	private static ArrayList<int[]> movemaker2(int[] input, int depth) {
        int move = 0;
        float max=-1000;
        int score=0;
        float value;
        ArrayList<int[]> moveset= new ArrayList<int[]>();
        
        for(int i=6; i>=1; i--) {
            int[] board = input.clone();
            if(board[i+1]!=0) {
                board = getScore(i,board); 
                if ((input[i+1]+i)==7) {
                    board=search(board);
                }
                board[15]=board[8]-input[8];
                //System.out.println(convertArrayToString(board)+" move="+i);
                value=prioritize(board, input);
                //value=board[16];
                score=board[8]-input[8];
                //System.out.println("score="+board[15]+ "     i= " + i);
               // System.out.println("value="+value+ " \n ");
                if (value>=max) {
                	max=value;
                	move=i;
                	if (depth==1){board[18]=move;}
                	moveset.add(board);
                	}
                //check treeboard if so add path to it
            }
        }
		return moveset;
    }
    
        private static int[] adjustPlayer(int[] input) {
        int[] temp = new int[20];
        temp[8]=input[1];
        temp[1]=input[8];
        for(int i=2; i<8;i++) {
            temp[i]= input[i+7];
        }
        for(int i=9; i<14;i++) {
            temp[i]= input[i-7];
        }
        return temp;
    }
        
    private static float prioritize(int[] board,int[] input) { 
    	int   points, oppstone, stonesminus;
    	float value,state, overflow, anticap;
    	points = board[15];
    	stonesminus = (board[2]+board[3]+board[4]+board[5]+board[6]+board[7]+board[8])-(input[2]+input[3]+input[4]+input[5]+input[6]+input[7]);
    	oppstone = (board[9]+board[10]+board[11]+board[12]+board[13]+board[14])-(input[9]+input[10]+input[11]+input[12]+input[13]+input[14]);
    	//maybe get points early stall late?
    	//prevent captures
    	//even out ur board
    	state=(48-board[8]+board[1])/48;
    	//cap = 0;
    	value= state*(points*10) + stonesminus*3 ;
    	//System.out.println("points=" + points + "  stone- = "+stonesminus);
    	return value;
    }
    
    //makes decision based on move summary
    private static int movemaker(int[] input) {
        int move = 0;
        float max=-1000;
        int score=0;
        float value;
        
        for(int i=6; i>=1; i--) {
            int[] board = input.clone();
            if(board[i+1]!=0) {
                board = getScore(i,board); 
                if ((input[i+1]+i)==7) {
                    board=search(board);
                }
                board[15]=board[8]-input[8];
                //System.out.println(convertArrayToString(board)+" move="+i);
                value=prioritize(board, input);
                //value=board[16];
                score=board[8]-input[8];
                System.out.println("score="+board[15]+ "     i= " + i);
                System.out.println("value="+value+ " \n ");
                if (value>=max) {max=value;move=i;}
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
            for (int i=6; i>=1; i--) {
                temp = tempclone.clone();
                if((temp[i+1]+i)==7) {
                    temp=(getScore(i, temp));
                    //temp[20]+=1;
                    //temp[15+depth]=i;
                    q.add(temp);
                    //tree.add(temp);
                    //System.out.println("\nadded to q: "+ convertArrayToString(temp)+" i= " +i +" temp "+temp[i+1]+"\n");
                }
                else {
                    temp=getScore(i, temp);
                    tree.add(temp);
                    //System.out.println(i+" added to tree: " + convertArrayToString(temp)+" i= " +i +" temp "+temp[i+1]);
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
