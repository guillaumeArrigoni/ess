package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActionTest {
    static Random r;

    static Board board;


    @BeforeEach
    void setUp() {
        r = mock(Random.class);
        board = new Board();
    }

    //DrawTile
    @Test
    void drawYellowTile() {
        when(r.nextInt(0, 3)).thenReturn(0);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Jaune, hexagoneBox.getColor());
    }

    @Test
    void drawGreenTile() {
        when(r.nextInt(0, 3)).thenReturn(1);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Vert, hexagoneBox.getColor());
    }

    @Test
    void drawRedTile() {
        when(r.nextInt(0, 3)).thenReturn(2);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Rouge, hexagoneBox.getColor());
    }

    //GetMovesForGardenerOrPanda
    @Test
    void getMovesForGardenerOrPandaTopRight() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{1, -1, 0});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(-1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaRight() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{1, 0, -1});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomRight() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{0, 1, -1});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(0,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomLeft() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{-1, 1, 0});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaLeft() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{-1, 0, 1});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaTopLeft() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{0, -1, 1});
        board.addBox(hexagoneBox);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(0,coords[0]);
        assertEquals(-1,coords[1]);
        assertEquals(1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaGlobal() {
        HexagoneBox hexagoneBox = new HexagoneBox(Color.Jaune, Special.Classique);
        HexagoneBox hexagoneBox2 = new HexagoneBox(Color.Jaune, Special.Classique);
        HexagoneBox hexagoneBox3 = new HexagoneBox(Color.Jaune, Special.Classique);
        HexagoneBox hexagoneBox4 = new HexagoneBox(Color.Jaune, Special.Classique);
        HexagoneBox hexagoneBox5 = new HexagoneBox(Color.Jaune, Special.Classique);
        HexagoneBox hexagoneBox6 = new HexagoneBox(Color.Jaune, Special.Classique);
        hexagoneBox.setCoordinates(new int[]{1, -1, 0});
        hexagoneBox2.setCoordinates(new int[]{1, 0, -1});
        hexagoneBox3.setCoordinates(new int[]{0, 1, -1});
        hexagoneBox4.setCoordinates(new int[]{-1, 1, 0});
        hexagoneBox5.setCoordinates(new int[]{-1, 0, 1});
        hexagoneBox6.setCoordinates(new int[]{0, -1, 1});
        board.addBox(hexagoneBox);
        board.addBox(hexagoneBox2);
        board.addBox(hexagoneBox3);
        board.addBox(hexagoneBox4);
        board.addBox(hexagoneBox5);
        board.addBox(hexagoneBox6);
        assertEquals(6,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
    }
}
