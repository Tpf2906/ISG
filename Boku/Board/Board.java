package Board;
import java.util.*;
import java.io.*;
import java.lang.Math;


public class Board {
    // [(1,-1,1) -> 1]
    private LinkedHashMap<Position, Integer> _positions;
    // [1 -> (1,-1,1)]
    private LinkedHashMap<Integer,Position> _indexes;

    public Board() {
        _positions = new LinkedHashMap<Position, Integer>();
        _indexes = new LinkedHashMap<Integer,Position>();
        int x , y , z ;
        int counter = 0;
        x = -5;y = 5;z = 0;
        //Fill the board with empty positions
        for (int i = 0; i < 5; i++) {
            x = -5+i;y = 5-i;z = 0;
            for (int j = 0; j < 5+i ; j++) {
                this.setPosition(new Position(x, y, z), 0);
                this.setIndex(counter, new Position(x, y, z));
                y += 1;
                z -= 1;
                counter++;
            }
        }
        x = y = z = 0;
        for (int i = 0; i < 6; i++) {
            x = i;y = 0;z = -i;
            for (int j = 0; j < 10-i; j++) {
                this.setPosition(new Position(x, y, z), 0);
                this.setIndex(counter, new Position(x, y, z));
                counter++;
                y += 1;
                z -= 1;
            }
        } 
    
    }

    public Set<Position> getPositions() {
        return _positions.keySet();
    }

    public Collection<Integer> getPositionValues() {
        return _positions.values();
    }

    public Set<Integer> getIndexes() {
        return _indexes.keySet();
    }

    public Collection<Position> getIndexValues() {
        return _indexes.values();
    }

    public Position getIndex(int index) {
        return _indexes.get(index);
    }

    public void setPosition(Position pos, int value) {
        _positions.put(pos, value);
    }

    public void setIndex(int index, Position pos) {
        _indexes.put(index, pos);
    }

    public int getPosition(Position pos) {
        return _positions.get(pos);
    }

    public boolean isPositionEmpty(Position pos) {
        return _positions.get(pos) == 0;
    }

    public Set<Position> getEmptyPositions() {
        Set<Position> empty_positions = new HashSet<Position>();
        for (Position pos : _positions.keySet()) {
            if (isPositionEmpty(pos)) {
                empty_positions.add(pos);
            }
        }
        return empty_positions;
    }


    // Checks if there are 5 pieces in a row
    public int check_winner(){
        int[] upwards_positions = {0,5,6,11,12,13,18,19,20,21,26,27,28,29,30,35,36,37,38,39,40
                         ,45,46,47,48,49,54,55,56,57,62,63,64,69,70};
        int[] nw_positions = {26,27,28,29,30,35,36,37,38,39,40,45,46,47,48,49,50,54,55,56
                        ,57,58,59,62,63,64,65,66,67,69,70,71,72,73,74,75,76,77,78,79};
        int[] ne_positions = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,18,19,20,21,22,23,26,27,28
                        ,29,30,31,35,36,37,38,39,40,45,46,47,48,49};
        
        Position p ;
        Position p1 ;
        Position p2 ;
        Position p3 ;
        Position p4 ;

        for (int pos : upwards_positions) {
            p = _indexes.get(pos);
            p1 = p.getNexPosition("UP");
            p2 = p1.getNexPosition("UP");
            p3 = p2.getNexPosition("UP");
            p4 = p3.getNexPosition("UP");
            if (_positions.get(p) == _positions.get(p1) && _positions.get(p) == _positions.get(p2) && _positions.get(p) == _positions.get(p3)
             && _positions.get(p) == _positions.get(p4) && _positions.get(p) != 0) {
                return getPosition(p);
            }
        }

        for (int pos : nw_positions) {
            p = _indexes.get(pos);
            p1 = p.getNexPosition("NW");
            p2 = p1.getNexPosition("NW");
            p3 = p2.getNexPosition("NW");
            p4 = p3.getNexPosition("NW");
           if (_positions.get(p) == _positions.get(p1) && _positions.get(p) == _positions.get(p2) && _positions.get(p) == _positions.get(p3)
             && _positions.get(p) == _positions.get(p4) && _positions.get(p) != 0) {
                return getPosition(p);
            }
        }

        for (int pos : ne_positions) {
            p = _indexes.get(pos);
            p1 = p.getNexPosition("NE");
            p2 = p1.getNexPosition("NE");
            p3 = p2.getNexPosition("NE");
            p4 = p3.getNexPosition("NE");
           
            if (_positions.get(p) == _positions.get(p1) && _positions.get(p) == _positions.get(p2) && _positions.get(p) == _positions.get(p3)
             && _positions.get(p) == _positions.get(p4) && _positions.get(p) != 0) {
                return getPosition(p);
            }
        }
        return 0;
    }

    // Checks if a capture has been made
    public HashSet<Position> checkCapture(Position pos, int player){
        HashSet<Position> captured = new HashSet<Position>();
        HashSet<Position> check = new HashSet<Position>();
        Position backup_Position = pos;
        Position next_position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("UP");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        pos = backup_Position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("DOWN");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        pos = backup_Position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("SW");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        pos = backup_Position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("SE");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        pos = backup_Position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("NW");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        pos = backup_Position;
        for (int i = 0; i < 5; i++) {
            next_position = pos.getNexPosition("NE");
            if (player == -this.getPosition(pos)) {
                check.add(pos);
                pos = next_position;
            }else if (player == this.getPosition(pos)) {
                captured.addAll(check);
                check.clear();
                break;
            }else{check.clear();break;}
            
        }
        return captured;

    }

    // draws the board in the terminal
    public void draw_board() throws IOException{
        File file = new File("Board/board.txt");
        int[] in_order = {44,
                        34,53,
                        25,43,61,
                      17,33,52,68,
                     10,24,42,60,74,
                    4,16,32,51,67,79,
                      9,23,41,59,73,
                    3,15,31,50,66,78,
                      8,22,40,58,72,
                    2,14,30,49,65,77,
                      7,21,39,57,71,
                    1,13,29,48,64,76,
                      6,20,38,56,70,
                    0,12,28,47,63,75,
                      5,19,37,55,69,
                       11,27,46,62,
                        18,36,54,
                          26,45,
                            35};
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st, token;
        StringTokenizer tokenizer;
        int i = 0,value;
        while ((st = br.readLine()) != null){
            tokenizer = new StringTokenizer(st," ");
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                if (token.equals("---")){
                    System.out.print("   ");
                }
                else {
                    value = this.getPosition(this.getIndex(in_order[i]));
                    if (value == 0){System.out.print(token);}
                    else{
                        if (value < 0) {System.out.print(value + " ");}
                        else{System.out.print(" " + value + " ");}
                    }
                    i++;
                }
            }
            System.out.println();
        }
        br.close();
    }

    // checks if the board is full
    public boolean isTerminal() {
        return this.getEmptyPositions().size() == 0;
    }

    // TO DO
    public int evaluate() {
        int score = 0;
        int ai_in_a_row = 0;   
        int human_in_a_row = 0;
        int[] upwards_positions = {0,5,11,18,26,35,45,54,62,69,75};
        int[] nw_positions = {4,3,2,1,0,5,11,16,26,35};
        int[] ne_positions = {35,45,54,62,69,79,78,77,76,75};
        Position p;
        for (int pos : upwards_positions) {
            p = this.getIndex(pos);
            ai_in_a_row = 0;
            human_in_a_row = 0;
            if (this.getPosition(p) == 1) {
                ai_in_a_row++;
            }else if (this.getPosition(p) == -1) {
                human_in_a_row++;
            }
            while (true) {
                p = p.getNexPosition("UP");
                if (_positions.containsKey(p) == false) {
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                    break;
                }
                if (this.getPosition(p) == 1) {
                    ai_in_a_row++;
                    score -= Math.pow(human_in_a_row,2);
                    human_in_a_row = 0;
                }
                else if (this.getPosition(p) == -1) {
                    human_in_a_row++;
                    score += Math.pow(ai_in_a_row,2);
                    ai_in_a_row = 0;
                }
                else{
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                }
            }
        }

        for (int pos : nw_positions) {
            p = this.getIndex(pos);
            ai_in_a_row = 0;
            human_in_a_row = 0;
            if (this.getPosition(p) == 1) {
                ai_in_a_row++;
            }else if (this.getPosition(p) == -1) {
                human_in_a_row++;
            }
            while (true) {
                p = p.getNexPosition("NW");
                if (_positions.containsKey(p) == false) {
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                    break;
                }
                if (this.getPosition(p) == 1) {
                    ai_in_a_row++;
                    score -= Math.pow(human_in_a_row,2);
                    human_in_a_row = 0;
                }
                else if (this.getPosition(p) == -1) {
                    human_in_a_row++;
                    score += Math.pow(ai_in_a_row,2);
                    ai_in_a_row = 0;
                }
                else{
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                }
            }
        }

        for (int pos : ne_positions) {
            p = this.getIndex(pos);
            ai_in_a_row = 0;
            human_in_a_row = 0;
            if (this.getPosition(p) == 1) {
                ai_in_a_row++;
            }else if (this.getPosition(p) == -1) {
                human_in_a_row++;
            }
            while (true) {
                p = p.getNexPosition("NE");
                if (_positions.containsKey(p) == false) {
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                    break;
                }
                if (this.getPosition(p) == 1) {
                    ai_in_a_row++;
                    score -= Math.pow(human_in_a_row,2);
                    human_in_a_row = 0;
                }
                else if (this.getPosition(p) == -1) {
                    human_in_a_row++;
                    score += Math.pow(ai_in_a_row,2);
                    ai_in_a_row = 0;
                }
                else{
                    score += Math.pow(ai_in_a_row,2);
                    score -= Math.pow(human_in_a_row,2);
                    ai_in_a_row = 0;
                    human_in_a_row = 0;
                }
            }
        }


        return score;
    }
}
