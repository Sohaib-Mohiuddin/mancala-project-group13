package project1;

import java.util.*;

public class Solution {

    @SuppressWarnings("resource")
	public static void main(String[] args) {
    	
		int[] input = new int[15];
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

	private static int movemaker(int[] input) {
		int move = 0;
		int max=0;
		int score=0;
		int[][] movelist = new int[6][3];
		int k=0;
		
		for(int i=1; i<=6; i++) {
			int[] board = input.clone();
			if(board[i+1]!=0) {
				board = exact(board);
				board[0]=input[0];
				while (board[0]==input[0]) {
					board=exact(board);
					System.out.println(Arrays.toString(board));
					movelist[i-1][k]=board[14];
					k++;		
				}
			}
			score= board[8]-input[8];
			if (score>max) {max=score;move=i;}
		}
		
		return move;
	}

	private static int[] exact(int[] board) {
		int max=0;
		int score=0;
		int move=0;
		for (int i=1; i<=6; i++) {
			if(board[i+1]!=0) {
				board = getScore(i, board);
				score= board[14];
				if (score>max) {max=score;move=i;}
			}
		}
		board[14]= move;
		return board;
		
	}

	private static int[] getScore(int i, int[] board) {
		//int score;
		int endpoint = i + board[i+1];
		if (endpoint>13) {
			endpoint = 13%endpoint;
			for (int k = 0; k < board.length; k++) {
				board[k]++;
			}
		}
		
		if(endpoint!=8) {
			board[0]=-1*(board[0]-3);
		}
		
		if(endpoint>1 && endpoint<6 && board[13-endpoint]==0) {
			board = updateboard(i, board, endpoint);
			board[8]=board[8]+board[13-i];
			return board;
		}

		board = updateboard(i, board, endpoint);
		return board;		
	}

	private static int[] updateboard(int i, int[] board, int ep) {
		for (int j=i+1;j<=ep;j++) {
			board[j+1]++;
		}
		board[i+1]=0;
		return board;
	}
}
