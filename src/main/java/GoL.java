import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class GoL {
    private List<Optional<Point>> boardList = new ArrayList<>();
    private final OptionalInt maxX;
    private final OptionalInt maxY;

    public GoL(int width, int height) {
        this.maxX = OptionalInt.of(width);
        this.maxY = OptionalInt.of(height);
    }

    public List<Optional<Point>> getBoardList() {
        return boardList;
    }

    public String printBoard() {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < maxX.getAsInt(); i++) {
            for (int j = 0; j < maxY.getAsInt(); j++) {
                if (checkIfPointExistsInList(i, j))
                    line.append(PointState.ALIVE);
                else
                    line.append(PointState.DEAD);
            }
            line.append("\n");
        }
        return line.toString();
    }

    public void setAlive(int x, int y) {
        if (x > maxX.getAsInt() || y > maxY.getAsInt() || x < 0 || y < 0)
            throw new IllegalArgumentException();
        if (!checkIfPointExistsInList(x, y))
            boardList.add(Optional.of(new Point(x, y)));
    }

    public boolean checkIfPointExistsInList(int x, int y) {
        return boardList.stream()
                .map(Optional::get)
                .filter(point -> point.getX() == x)
                .filter(point -> point.getY() == y)
                .count() == 1;
    }

    public void setDead(int x, int y) {
        boardList.remove(boardList.stream()
                .filter(point -> point.get().getX() == x)
                .filter(point -> point.get().getY() == y)
                .map(Optional::get)
                .findFirst());
    }

    public long countAliveNeighbours(Point point, List<Optional<Point>> templist) {
        int x = point.getX();
        int y = point.getY();
        long count = 0;
        Point aboveLeft = new Point(x - 1, y - 1);
        Point aboveMiddle = new Point(x - 1, y);
        Point aboveRight = new Point(x - 1, y + 1);
        Point aSideLeft = new Point(x, y - 1);
        Point aSideRight = new Point(x, y + 1);
        Point underLeft = new Point(x + 1, y - 1);
        Point underMiddle = new Point(x + 1, y);
        Point underRight = new Point(x + 1, y + 1);

        Point[] positionArray = {aboveLeft, aboveMiddle, aboveRight, aSideLeft, aSideRight, underLeft, underMiddle, underRight};

        for (Point value : positionArray) {
            count += neighbourCheck(templist, value);
        }

        return count;
    }

    private long neighbourCheck(List<Optional<Point>> templist, Point tempPoint) {
        return templist.stream()
                .map(Optional::get)
                .filter(point -> point.getX() == tempPoint.getX()
                        && point.getY() == tempPoint.getY())
                .count();
    }

    public void step() {
        List<Optional<Point>> newboardList = new ArrayList<>();
        for (int i = 0; i < maxX.getAsInt(); i++) {
            CheckPointsInEachColumnInGrid(newboardList, i);
        }
        this.boardList = newboardList;
    }

    private void CheckPointsInEachColumnInGrid(List<Optional<Point>> newboardList, int i) {
        for (int j = 0; j < maxY.getAsInt(); j++) {
            Point point = new Point(i, j);
            int aliveNeighboursOnPoint = (int) countAliveNeighbours(point, boardList);

            if (checkIfPointExistsInList(point.getX(), point.getY())
                    && (aliveNeighboursOnPoint == 2 || aliveNeighboursOnPoint == 3))
                newboardList.add(Optional.of(point));
            else if (aliveNeighboursOnPoint == 3)
                newboardList.add(Optional.of(point));
        }
    }
}