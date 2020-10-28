import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;


public class GoL {
    private List<Optional<Point>> boardList = new ArrayList<>();

    public GoL(int width, int height) {
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                boardList.add(Optional.of(new Point(i, j, false)));
            }
        }
    }

    public List<Optional<Point>> getBoardList() {
        return boardList;
    }

    private String accept(Point point) {
        if (!point.isState())
            return ".";
        return "*";
    }

    public void setAlive(int x, int y) {
        boardList.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x)
                .filter(p -> p.get().getY() == y)
                .forEach(p -> p.get().setState(true));
    }

    public void setDead(int x, int y) {
        boardList.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x)
                .filter(p -> p.get().getY() == y)
                .forEach(p -> p.get().setState(false));
    }

    public String printBoard() {
        StringBuilder line = new StringBuilder();
        OptionalInt opX = boardList.stream().mapToInt(p -> p.get().getX()).max();
        for (int i = 0; i < opX.getAsInt() + 1; i++) {
            int finalI = i;
            line.append(boardList.stream()
                    .filter(point ->
                            point.get().getX() == finalI)
                    .map(point -> {
                        return accept(point.get());
                    }).collect(Collectors.joining())).append("\n");
        }
        return line.toString();
    }
    public long countAliveNeighbours(Point point, List<Optional<Point>> templist) {
        int x = point.getX();
        int y = point.getY();
        long count;



        long under1 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x - 1 && p.get().getY() == y - 1)
                .filter(p -> p.get().isState())
                .count();
        long under2 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x && p.get().getY() == y - 1)
                .filter(p -> p.get().isState())
                .count();
        long under3 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x + 1 && p.get().getY() == y - 1)
                .filter(p -> p.get().isState())
                .count();

        long aSide1 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x - 1 && p.get().getY() == y)
                .filter(p -> p.get().isState())
                .count();
        long aSide2 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x + 1 && p.get().getY() == y)
                .filter(p -> p.get().isState())
                .count();

        long above1 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x - 1 && p.get().getY() == y + 1)
                .filter(p -> p.get().isState())
                .count();
        long above2 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x && p.get().getY() == y + 1)
                .filter(p -> p.get().isState())
                .count();
        long above3 = templist.stream()
                .filter(Optional::isPresent)
                .filter(p -> p.get().getX() == x + 1 && p.get().getY() == y + 1)
                .filter(p -> p.get().isState())
                .count();

        count = above1 + above2 + above3 + aSide1 + aSide2 + under1 + under2 + under3;


        return count;
    }

    public void step() {
        List<Optional<Point>> newboardList = new ArrayList<>();

        OptionalInt opX = boardList.stream().mapToInt(p -> p.get().getX()).max();
        OptionalInt opY = boardList.stream().mapToInt(p -> p.get().getY()).max();
        for (int i = 0; i < opX.getAsInt() + 1; i++) {
            for (int j = 0; j < opY.getAsInt() + 1; j++) {
                newboardList.add(Optional.of(new Point(i, j, false)));
            }
        }
        newboardList.stream()
                .map(Optional::get)
                .forEach(point -> {
                    int aliveNeighbours = (int) countAliveNeighbours(point, boardList);
                    if (boardList.stream()
                            .filter(Optional::isPresent)
                            .filter(p -> p.get().getX() == point.getX())
                            .filter(p -> p.get().getY() == point.getY())
                            .filter(p -> p.get().isState())
                            .count() == 1
                    ) {
                        point.setState(aliveNeighbours == 2 || aliveNeighbours == 3);
                    } else {
                        point.setState(aliveNeighbours == 3);
                    }
                });

        this.boardList = newboardList;
    }
}
