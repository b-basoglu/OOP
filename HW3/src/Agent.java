public class Agent extends MazeObject implements Comparable{
    
    private final int id;
    private int collectedGold;
    public Agent(Position position, int id) {
        super(position, MazeObjectType.AGENT);
        this.id = id;
        this.collectedGold = 0;
    }

    public int getId() {
        return id;
    }

    public int getCollectedGold() {
        return collectedGold;
    }

    public void setCollectedGold(int collectedGold) {
        this.collectedGold = collectedGold;
    }

    @Override
    public int compareTo(Object o) {
        int rhst = ((Agent)o).getId();
        if(this.getId()>rhst)
            return 1;
        else if(this.getId()<rhst)
            return -1;
        else return 0;

    }
}
