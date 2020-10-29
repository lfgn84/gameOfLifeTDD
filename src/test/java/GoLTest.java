import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GoLTest {

    GoL gameOfLife;
    @BeforeEach
    @Disabled
    void setup(){

    }

    @Test
    @DisplayName("Testing one point and get every one back to see that point")
    void shouldReturnAnIntOFHowManyActiveCells() {
        gameOfLife = new GoL(3, 3);
        gameOfLife.setAlive(1, 1);
        assertEquals(1, gameOfLife.countAliveNeighbours(new Point(1, 0), gameOfLife.getBoardList()));
        assertEquals(1, gameOfLife.countAliveNeighbours(new Point(1, 2), gameOfLife.getBoardList()));
        assertEquals(1, gameOfLife.countAliveNeighbours(new Point(2, 2), gameOfLife.getBoardList()));
    }

    @Test
    @DisplayName("Testing several points and get back neigbours sees the points")
    void shouldReturnAnIntOFHowManyActiveCellsWhenTestingSevralPoints() {
        gameOfLife = new GoL(3, 3);
        gameOfLife.setAlive(0, 1);
        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(2, 1);
        assertEquals(3, gameOfLife.countAliveNeighbours(new Point(1, 0), gameOfLife.getBoardList()));
        assertEquals(2, gameOfLife.countAliveNeighbours(new Point(2, 2), gameOfLife.getBoardList()));
    }

    @Test
    @DisplayName("Testing several points and killing one point and get back neigbours sees the points")
    void shouldReturnAnIntOFHowManyActiveCellsAfterKillinCells() {
        gameOfLife = new GoL(3, 3);
        gameOfLife.setAlive(0, 1);
        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(2, 1);
        assertEquals(3, gameOfLife.countAliveNeighbours(new Point(1, 0), gameOfLife.getBoardList()));
        assertEquals(2, gameOfLife.countAliveNeighbours(new Point(2, 2), gameOfLife.getBoardList()));
        gameOfLife.setAlive(0, 1);
        gameOfLife.setAlive(1, 1);
        gameOfLife.setDead(2, 1);
        assertEquals(2, gameOfLife.countAliveNeighbours(new Point(1, 0), gameOfLife.getBoardList()));
        assertEquals(1, gameOfLife.countAliveNeighbours(new Point(2, 2), gameOfLife.getBoardList()));
    }


    @Test
    @DisplayName("Testing so we get back an string with grid of live and dead cells")
    void shouldReturnAnStringOFHowManyActiveCellsWhenTestingSevralPoints() {
        gameOfLife = new GoL(3, 3);
        gameOfLife.setAlive(0, 1);
        gameOfLife.setAlive(1, 1);
        gameOfLife.setAlive(2, 1);
        String Line = ".*.\n" + ".*.\n" + ".*.\n";
        assertEquals(Line, gameOfLife.printBoard());
        Line = "...\n" + "***\n" + "...\n";
        gameOfLife.step();
        assertEquals(Line, gameOfLife.printBoard());
    }

    @Test
    @DisplayName("Verifing point creation in list and removal")
    void shouldReturnTrueWhenVerifyAPoint() {
        gameOfLife = new GoL(3, 3);
        gameOfLife.setAlive(1, 1);
        assertTrue(gameOfLife.checkIfPointExistsInList(1, 1));
        gameOfLife.setDead(1, 1);
        assertFalse(gameOfLife.checkIfPointExistsInList(1, 1));
    }
}