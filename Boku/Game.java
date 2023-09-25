import java.io.Console;
import java.util.*;
import Board.*;

public class Game {

    private static final int human = -1;
    private static final int AI = 1;
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        LinkedHashMap<String,Position> map = get_letter_map(board);
        HashMap<Position,Integer> test = new HashMap<Position,Integer>();
        test.put(new Position(AI, human, AI), 5);
        boolean ai_turn = false;
        boolean capture = false;
        int moves = 0;
        int player;
        Position human_forbiden_pos = null;
        Position ai_forbiden_pos = null;
        // check if the player wants to play first or second
        System.out.println("Enter 1 for player 1 and 2 for player 2:");
        String input = get_input();
        player = Integer.parseInt(input);
        if (player == 1) {
            ai_turn = true;
        }
        while (true) {
            // max number of moves is 80
            if (moves == 80) {
                System.out.println("Draw");
                break;
            }
            else if (ai_turn) {
                System.out.println("AI's turn");
                Position best_move = best_move(board, human_forbiden_pos, ai_forbiden_pos);
                System.out.println(best_move);
                board.setPosition(best_move,AI);
                if (board.check_winner() != 0) {
                    System.out.println("AI wins");
                    break;
                }
                board.draw_board();
                moves++;
                ai_turn = false;
            } else {
                ai_turn = true;
                System.out.println("Human's turn");
                System.out.println("Enter the letter of the position you want to play:");
                String letter = get_input();
                Position p = map.get(letter);
                if (capture){
                    board.setPosition(p,0);
                    capture = false;
                    human_forbiden_pos = p;
                }
                else {
                    board.setPosition(p,human);
                    human_forbiden_pos = null;
                    moves++;	
                    if (board.check_winner() != 0) {
                        System.out.println("Human wins");
                        break;
                    } 
                    if (board.checkCapture(p, human).size() != 0) {
                        capture = true;
                        ai_turn = false;
                    }   
                }
            }
        }
    }


    public static LinkedHashMap<String,Position> get_letter_map(Board board){
        String[] letters = {"A6","B7","C8","D9","E10","A5","B6","C7","D8","E9","F10","A4","B5","C6","D7","E8","F9","G10",
            "A3","B4","C5","D6","E7","F8","G9","H10","A2","B3","C4","D5","E6","F7","G8","H9","I10",
            "A1","B2","C3","D4","E5","F6","G7","H8","I9","J10","B1","C2","D3","E4","F5","G6","H7","I8","J9",
            "C1","D2","E3","F4","G5","H6","I7","J8","D1","E2","F3","G4","H5","I6","J7",
            "E1","F2","G3","H4","I5","J6","F1","G2","H3","I4","J5"};
        LinkedHashMap<String,Position> map = new LinkedHashMap<String,Position>();
        Set<Position> pos = board.getPositions();
        int i = 0;
        for (Position p : pos) {
            map.put(letters[i],p);
            i++;
        }
        return map;
    }

    public static String get_input() {
        Console console = System.console();
        String input = console.readLine();
        return input;
    }

       public static Position best_move(Board board,Position human_forbiden_pos, Position ai_forbiden_pos){
        Position best_move = null;
        Set<Position> empty = board.getEmptyPositions();
        int best_score = -100000;
        int score;
        int alpha = -100000;
        int beta = 100000;
        for (Position pos : empty) {
            board.setPosition(pos,AI);
            score = minimax(board,3,false,alpha,beta,human_forbiden_pos,ai_forbiden_pos);
            board.setPosition(pos,0);
            if (score > best_score) {
                best_score = score;
                best_move = pos;
            }
            alpha = Math.max(alpha,score);
            if (beta <= alpha) {
                break;
            }
        }

        return best_move;
    }
    
    public static int minimax(Board board,int depth, boolean isMaximizing,int alpha,int beta,
     Position human_forbiden_pos, Position ai_forbiden_pos){
        Set<Position> captured_Positions = new HashSet<Position>();
        int score = 0, best_score;
        int result = board.check_winner();
        if (result != 0) {
            return result;
        }
        if (depth == 0 || board.isTerminal()) {
            result = board.evaluate();
            return result;
        }
        if (isMaximizing) {
            best_score = -100000;
            Set<Position> empty_positions = board.getEmptyPositions();
            for (Position pos : empty_positions) {
                if (pos.equals(ai_forbiden_pos)) {
                    continue;
                }
                board.setPosition(pos,AI);
                captured_Positions = board.checkCapture(pos, AI);
                if (captured_Positions.size() > 0){
                    for (Position capturedPosition : captured_Positions){
                        board.setPosition(capturedPosition,0);
                        score = minimax(board,depth-1,false,alpha,beta,null,capturedPosition);
                        board.setPosition(capturedPosition,human);
                        if (score > best_score) {
                            best_score = score;
                        }
                        alpha = Math.max(alpha,score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                else {
                    score = minimax(board,depth-1,false,alpha,beta,null,null);
                }
                
                board.setPosition(pos,0);
                if (score > best_score) {
                    best_score = score;
                }
                alpha = Math.max(alpha,score);
                if (beta <= alpha) {
                    break;
                }
            }
            return best_score;
        } else {
            best_score = 100000;
            Set<Position> empty_positions = board.getEmptyPositions();
            for (Position pos : empty_positions) {
                board.setPosition(pos,human);
                captured_Positions = board.checkCapture(pos, human);
                if (captured_Positions.size() > 0){
                    for (Position capturedPosition : captured_Positions){
                        board.setPosition(capturedPosition,0);
                        score = minimax(board,depth-1,true,alpha,beta,capturedPosition,null);
                        board.setPosition(capturedPosition,AI);
                        if (score < best_score) {
                            best_score = score;
                        }
                        beta = Math.min(beta,score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                else {
                    score = minimax(board,depth-1,true,alpha,beta,null,null);
                }
                board.setPosition(pos,0);
                if (score < best_score) {
                    best_score = score;
                }
                beta = Math.min(beta,score);
                if (beta <= alpha) {
                    break;
                }
            }
            return best_score;
        }
    }
}