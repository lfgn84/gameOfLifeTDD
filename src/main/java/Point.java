class Point{
    private final int x;
    private final int y;
    private boolean state;
    public Point(int x, int y, boolean state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
}
