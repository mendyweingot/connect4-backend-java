package src;
import java.util.*;

import src.controller.*;
import src.restServices.requestObjects.*;
import src.restServices.responseObjects.*;

public class app {
    public static void main(String[] args){
        System.out.println();
    }    

    public static void printBoard(int[][] board){
        System.out.println("\n\n");
        for (int i = 0; i < board.length; i++){
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j] + "|");
            }            
            System.out.println();
        }
        System.out.println("\n\n");
    }
}
