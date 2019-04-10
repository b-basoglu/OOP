
import java.util.Random;

/**
 * This class can represent both direction and position of the objects on map which are
 * bullets, soldiers and zombies
 * It has 3 main component which are x,y which represent respectively horizontal,vertical
 * position and length
 * This position can be normalized
 * This position can be generated randomly
 */
public class Position {
    private double x;
    private double y;
    
    private double length;

    /**
     * Constructor
     * @param x horizontal position of the objects
     * @param y vertical position of the objects
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        
        this.calculateLength();
    }

    /**
     * Horizontal position getter
     * @return x
     */
    public double getX() {
        return x;
    }
    /**
     * Vertical position getter
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates distance between other and this position
     * @param other position of target
     * @return distance between two position
     */
    public double distance(Position other) {
        return Math.sqrt(Math.pow(this.x-other.getX(), 2)+Math.pow(this.y-other.getY(), 2));
    }

    /**
     * Adds other position with this position
     * @param other position
     * @return sum of two position
     */
    public Position add(Position other) {
        Position position = new Position(this.getX(),this.getY());
        position.x += other.x;
        position.y += other.y;

        position.calculateLength();
        return position;
    }

    /**
     * Multiply other position with this position
     * @param constant position
     * @return multiplication of two position
     */
    public Position mult(double constant) {
        Position position = new Position(this.getX(),this.getY());
        position.x *= constant;
        position.y *= constant;

        position.calculateLength();
        return position;
    }


    /**
     * Calculate length of this position
     */
    private void calculateLength() {
        this.length = Math.sqrt(Math.pow(x, 2.0)+Math.pow(y, 2.0));
    }

    /**
     * Normalization of this position
     */
    public void normalize() {
        this.x /= this.length;
        this.y /= this.length;
        
        this.length = 1.0;
    }

    /**
     * Generation of random direction
     * @param normalize to be normalized
     * @return normalized position of normalize
     */
    public static Position generateRandomDirection(boolean normalize) {
        Random random = new Random();
        double x = -1+random.nextDouble()*2;
        double y = -1+random.nextDouble()*2;
        
        Position result = new Position(x, y);
        if (normalize)
            result.normalize();
        return result;
    }

    /**
     * Returns copy of this position
     * @return copy of this position
     */
    @Override
    protected Object clone() {
        return new Position(x, y); 
    }

    /**
     * Compare whether this position is equal to obj
     * @param obj compared one
     * @return boolean: whether two position equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    /**
     * Hash this position
     * @return hashed value
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    /**
     * Converts this position into string
     * @return string version of position
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
    
}
