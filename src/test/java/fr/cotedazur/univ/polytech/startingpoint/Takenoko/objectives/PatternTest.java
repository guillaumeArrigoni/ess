package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternTest {

    private static Pattern pattern1;
    private static Pattern pattern2;
    private static Pattern pattern3;
    private static Pattern pattern4;
    private static Pattern pattern5;
    private static Pattern pattern6;

    @BeforeAll
    public static void setUp(){
        pattern1 = Pattern.PLANTER_SUR_SOURCE_EAU;
        pattern2 = Pattern.PLANTER_TROIS_JAUNES;
        pattern3 = Pattern.POSER_TRIANGLE;
        pattern4 = Pattern.POSER_LIGNE;
        pattern5 = Pattern.MANGER_DEUX_BAMBOUS;
        pattern6 = Pattern.MANGER_TROIS_BAMBOUS;
    }

    @Test
    void getForme() {
        assertNull(pattern1.getForme());
        assertNull(pattern2.getForme());
        assertEquals("TRIANGLE", pattern3.getForme());
        assertEquals("LIGNE", pattern4.getForme());
        assertNull(pattern5.getForme());
        assertNull(pattern6.getForme());
    }

    @Test
    void getNbBambou() {
        assertEquals(1, pattern1.getNbBambou());
        assertEquals(3, pattern2.getNbBambou());
        assertEquals(0, pattern3.getNbBambou());
        assertEquals(0, pattern4.getNbBambou());
        assertEquals(2, pattern5.getNbBambou());
        assertEquals(3, pattern6.getNbBambou());
    }

    @Test
    void getHauteurBambou() {
        assertEquals(4, pattern1.getHauteurBambou());
        assertEquals(3, pattern2.getHauteurBambou());
        assertEquals(0, pattern3.getHauteurBambou());
        assertEquals(0, pattern4.getHauteurBambou());
        assertEquals(1, pattern5.getHauteurBambou());
        assertEquals(1, pattern6.getHauteurBambou());
    }

    @Test
    void getSpecial() {
        assertEquals(Special.SourceEau, pattern1.getSpecial());
        assertNull(pattern2.getSpecial());
        assertNull(pattern3.getSpecial());
        assertNull(pattern4.getSpecial());
        assertNull(pattern5.getSpecial());
        assertNull(pattern6.getSpecial());
    }
}