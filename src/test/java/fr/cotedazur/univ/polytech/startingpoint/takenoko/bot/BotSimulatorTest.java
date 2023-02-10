package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathirrigation.GenerateAWayToIrrigateTheBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BotSimulatorTest {
    BotRandom botRandom;
    Board board;
    Random r;
    MeteoDice meteoDice;
    RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    final String arg = "demo";

    BotSimulator bot;
    GestionObjectives gestionObjectives;

    @BeforeEach
    void setUp() {
        LogInfoDemo logInfoDemo = new LogInfoDemo(true);
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters, 1, 2, new LoggerSevere(true));
        gestionObjectives = new GestionObjectives(board, retrieveBoxIdWithParameters, new LoggerError(true));
        gestionObjectives.initialize(
                gestionObjectives.listOfObjectiveParcelleByDefault(),
                gestionObjectives.listOfObjectiveJardinierByDefault(),
                gestionObjectives.listOfObjectivePandaByDefault());
        r = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        botRandom = new BotRandom("testBot", board, r,  gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>(), logInfoDemo);
        board.getElementOfTheBoard().getStackOfBox().getStackOfBox().clear();
        for(int i = 0; i < 15; i++){
            board.getElementOfTheBoard().getStackOfBox().getStackOfBox().add(new HexagoneBox(Color.VERT, Special.CLASSIQUE));
        }
         bot = botRandom.createBotSimulator();
    }

    @Test
    void botRandomCreation(){
        assertEquals(botRandom.score, bot.score);
        assertEquals(botRandom.scorePanda, bot.scorePanda);
        assertEquals(botRandom.objectives, bot.objectives);
        assertEquals(botRandom.nbIrrigation, bot.nbIrrigation);
        assertEquals(botRandom.name, bot.name);
        assertEquals(botRandom.bambooEaten, bot.bambooEaten);
        assertTrue(bot.isLegal());
    }

    @Test
    void placeTile(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        assertTrue(bot.getBoard().isCoordinateInBoard(new int[]{1,-1,0}));
    }

    @Test
    void moveGardener(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.MOVE_GARDENER, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        assertEquals(1, bot.getBoard().getGardenerCoords()[0]);
        assertEquals(-1, bot.getBoard().getGardenerCoords()[1]);
        assertEquals(0, bot.getBoard().getGardenerCoords()[2]);
    }

    @Test
    void movePanda(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.MOVE_PANDA, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        assertEquals(1, bot.getBoard().getPandaCoords()[0]);
        assertEquals(-1, bot.getBoard().getPandaCoords()[1]);
        assertEquals(0, bot.getBoard().getPandaCoords()[2]);
    }

    @Test
    void takeIrrigation(){
        bot.setInstructions(new ActionLog(PossibleActions.TAKE_IRRIGATION));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        assertEquals(1, bot.getNbIrrigation());
    }

    @Test
    void irrigate(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,0,-1, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 2,-1,-1, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        GenerateAWayToIrrigateTheBox tmp;
        try{
            tmp = new GenerateAWayToIrrigateTheBox(bot.getBoard().getPlacedBox().get(1999902));
            ActionLogIrrigation ali = new ActionLogIrrigation(PossibleActions.PLACE_IRRIGATION, tmp.getPathToIrrigation());
            bot.setInstructions(ali);
            bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
            assertTrue(bot.getBoard().getPlacedBox().get(1999902).isIrrigate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void placeSpecialProtect(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.ADD_AUGMENT, 1009901,0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(Special.PROTEGER, bot.getBoard().getPlacedBox().get(1009901).getSpecial());
    }

    @Test
    void placeSpecialWater(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.ADD_AUGMENT, 1009901,1));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(Special.SOURCE_EAU, bot.getBoard().getPlacedBox().get(1009901).getSpecial());
    }

    @Test
    void placeSpecialCompost(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.ADD_AUGMENT, 1009901,2));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(Special.ENGRAIS, bot.getBoard().getPlacedBox().get(1009901).getSpecial());
    }

    @Test
    void drawObjective(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_OBJECTIVE, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(1, bot.getObjectives().size());
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_OBJECTIVE, 1));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(2, bot.getObjectives().size());
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_OBJECTIVE, 2));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        assertEquals(3, bot.getObjectives().size());
    }

    @Test
    void growBambooRain(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        bot.setInstructions(new ActionLog(PossibleActions.GROW_BAMBOO, 1009901));
        bot.playTurn(MeteoDice.Meteo.PLUIE, "demo");
        assertEquals(2, bot.getBoard().getPlacedBox().get(1009901).getHeightBamboo());
    }

}
