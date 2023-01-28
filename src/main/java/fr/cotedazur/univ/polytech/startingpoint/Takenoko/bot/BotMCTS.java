package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.Node;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

public class BotMCTS extends Bot{
    Node node;
    List<ActionLog> instructions;
    /**
     * Constructor of the bot
     *
     * @param name                        the name of the bot
     * @param board                       the board of the game     the random generator
     * @param gestionObjectives
     * @param retrieveBoxIdWithParameters
     * @param bambooEated
     */
    public BotMCTS(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color, Integer> bambooEated) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo) {
        node = new Node(this.createBotSimulator(), 2, meteo);
        instructions = node.getBestInstruction();
        System.out.println("instructions : " + instructions.get(0) + " " + instructions.get(1));
        for(ActionLog instruction : instructions){
            doAction();
        }
    }

    @Override
    protected void doAction() {
        switch (instructions.get(0).getAction()) {
            case DRAW_AND_PUT_TILE -> placeTile();
            case MOVE_GARDENER -> moveGardener();
            case DRAW_OBJECTIVE -> drawObjective();
        }
        instructions.remove(0);
    }


    @Override
    protected void placeTile() {
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.drawTile());
        //Choose a random tile from the tiles drawn
        HexagoneBox placedTile = list.get(0);
        //Choose a random available space
        int[] placedTileCoords = instructions.get(0).getParameters();
        //Set the coords of the tile
        placedTile.setCoordinates(placedTileCoords);
        //Add the tile to the board
        board.addBox(placedTile);
        System.out.println(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }

    @Override
    protected void moveGardener() {
        board.setGardenerCoords(instructions.get(0).getParameters());
        System.out.println(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    @Override
    public void drawObjective() {
        switch(instructions.get(0).getParameters()[0]){
            case 0 -> {
                gestionObjectives.rollParcelleObjective(this);
                System.out.println(this.name + " a pioché un objectif de parcelle");
            }
            case 1 -> {
                gestionObjectives.rollPandaObjective(this);
                System.out.println(this.name + " a pioché un objectif de panda");
            }case 2 -> {
                gestionObjectives.rollJardinierObjective(this);
                System.out.println(this.name + " a pioché un objectif de jardinier");
            }
        }

    }


}
