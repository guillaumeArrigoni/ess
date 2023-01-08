package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class BotRandomTest {
    BotRandom botRandom;
    Board board;
    Random r;
    MeteoDice meteoDice;

    GestionObjectives gestionObjectives;
    @BeforeEach
    void setUp() {
        gestionObjectives = new GestionObjectives();
        gestionObjectives.initialize();
        r = mock(Random.class);
        board = new Board();
        meteoDice = mock(MeteoDice.class);
        botRandom = new BotRandom("testBot", board, r, meteoDice, gestionObjectives);
    }

    @Test
    void placeFirstTileDrawn() {
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile();
        verify(r, times(5)).nextInt(anyInt(),anyInt());
        assertEquals(2, board.getAllBoxPlaced().size());
    }

    @Test
    void placeSecondTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,1,1);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile();
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void placeThirdTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,2,2);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile();
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void playTurnWindPlaceBoxTwoTimes(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0,1,2,1,1);
        when(r.nextInt(anyInt())).thenReturn(0,0);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(10)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(3, board.getAllBoxPlaced().size());
    }

    @Test
    void playTurnWindPlaceBoxMoveGardener(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(0,1);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(6)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, board.getAllBoxPlaced().size());
        assertNotEquals(new int[]{0,0,0}, board.getGardenerCoords());

    }

    @Test
    void playTurnMoveGardenerImpossible(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(1,0);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(3)).nextInt(anyInt());
        assertEquals(3, board.getAllBoxPlaced().size());
    }

    @Test
    void playTurnDrawParcelObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(r.nextInt(anyInt())).thenReturn(2);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
        assertEquals(TypeObjective.PARCELLE, botRandom.getObjectives().get(0).getType());
        assertEquals(TypeObjective.PARCELLE, botRandom.getObjectives().get(1).getType());
    }

    @Test
    void playTurnDrawGardenerObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(1);
        when(r.nextInt(anyInt())).thenReturn(2);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
        assertEquals(TypeObjective.JARDINIER, botRandom.getObjectives().get(0).getType());
        assertEquals(TypeObjective.JARDINIER, botRandom.getObjectives().get(1).getType());
    }

/*TODO : Panda not implmenented
    @Test
    void playTurnDrawPandaObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(r.nextInt(anyInt())).thenReturn(2);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
    }
*/

    @Test
    void playTurnDrawRandomObjectiveTwice(){
        when(r.nextInt(anyInt())).thenReturn(2);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
    }

    @Test
    void playTurnDrawRandomObjectiveTooMuch(){
        when(r.nextInt(anyInt())).thenReturn(2,2,2,2,2,2,0);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        botRandom.playTurn();
        botRandom.playTurn();
        botRandom.playTurn();
        verify(r, times(7)).nextInt(anyInt());
        assertEquals(5, botRandom.getObjectives().size());
    }
}