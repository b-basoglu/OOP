public enum IgnotType {
    IRON(0),COPPER(1);
    private int value;
    IgnotType(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}