package Board;
import java.util.*;

public class Position {
    private int _x;
    private int _y;
    private int _z;

    //These are the directions that a piece can move in
    private static final List<Integer> UP = Arrays.asList(0,1,-1);
    private static final List<Integer> DOWN = Arrays.asList(0,-1,1);
    private static final List<Integer> SW = Arrays.asList(-1,0,1);
    private static final List<Integer> SE = Arrays.asList(1,0,-1);
    private static final List<Integer> NW = Arrays.asList(-1,1,0);   
    private static final List<Integer> NE = Arrays.asList(1,-1,0);

    public Position(int x, int y, int z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getZ() {
        return _z;
    }

    public void setX(int x) {
        _x = x;
    }

    public void setY(int y) {
        _y = y;
    }

    public void setZ(int z) {
        _z = z;
    }

    public Position getPos() {
        return new Position(_x,_y,_z);
    }

    //Returns the position of the next piece in the given direction
    public Position getNexPosition(String direction){
        Position next_pos = new Position(0,0,0);
        if (direction.equals("UP")) {
            next_pos.setX(this.getX()+UP.get(0));
            next_pos.setY(this.getY()+UP.get(1));
            next_pos.setZ(this.getZ()+UP.get(2));
        } else if (direction.equals("DOWN")) {
            next_pos.setX(this.getX()+DOWN.get(0));
            next_pos.setY(this.getY()+DOWN.get(1));
            next_pos.setZ(this.getZ()+DOWN.get(2));
        } else if (direction.equals("SW")) {
            next_pos.setX(this.getX()+SW.get(0));
            next_pos.setY(this.getY()+SW.get(1));
            next_pos.setZ(this.getZ()+SW.get(2));
        } else if (direction.equals("SE")) {
            next_pos.setX(this.getX()+SE.get(0));
            next_pos.setY(this.getY()+SE.get(1));
            next_pos.setZ(this.getZ()+SE.get(2));
        } else if (direction.equals("NW")) {
            next_pos.setX(this.getX()+NW.get(0));
            next_pos.setY(this.getY()+NW.get(1));
            next_pos.setZ(this.getZ()+NW.get(2));
        } else if (direction.equals("NE")) {
            next_pos.setX(this.getX()+NE.get(0));
            next_pos.setY(this.getY()+NE.get(1));
            next_pos.setZ(this.getZ()+NE.get(2));
        }
        return next_pos;
    }
    
    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";
    }


    //Change equals to be based on the position of the piece
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) {
            return false;
        }
        Position pos = (Position) o;
        return _x == pos.getX() && _y == pos.getY() && _z == pos.getZ();
    }


    //Change hashcode to be based on the position of the piece
    @Override
    public final int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + _x + _y + _z;
        return result;
    }


}
