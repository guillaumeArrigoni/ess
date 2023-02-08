package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.Node;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
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
    public BotMCTS(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color, Integer> bambooEated, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, logInfoDemo);
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        if (MeteoDice.Meteo.VENT == meteo || meteo == MeteoDice.Meteo.NO_METEO) {
            node = new Node(this.createBotSimulator(), 2, meteo, arg);
        } else {
            node = new Node(this.createBotSimulator(), 3, meteo, arg);
        }
        instructions = node.getBestInstruction();
        /*for (ActionLog instruction : instructions) {
            System.out.println(instruction.getAction() + " " + Arrays.toString(instruction.getParameters()));
        }*/
        for(int i = 0; i < instructions.size(); i++){
            doAction(arg);
        }
    }

    @Override
    protected void doAction(String arg) {
        switch (instructions.get(0).getAction()) {
            case DRAW_AND_PUT_TILE -> placeTile(arg);
            case MOVE_GARDENER -> moveGardener(arg);
            case DRAW_OBJECTIVE -> drawObjective(arg);
            default -> movePanda(arg);
        }
        instructions.remove(0);
    }


    @Override
    protected void placeTile(String arg){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = instructions.get(0).getParameters()[3];
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        //Choose a random available space
        int[] placedTileCoords = new int[]{instructions.get(0).getParameters()[0],instructions.get(0).getParameters()[1],instructions.get(0).getParameters()[2]};
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        if (arg.equals("demo")) System.out.println(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }

    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.get(0).getParameters());
        if (arg.equals("demo")) System.out.println(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.get(0).getParameters(),this);
        if (arg.equals("demo")) System.out.println(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()));
    }

    @Override
    public void drawObjective(String arg) {
        switch(instructions.get(0).getParameters()[0]){
            case 0 -> {
                gestionObjectives.rollParcelleObjective(this, arg);
                if (arg.equals("demo")) System.out.println(this.name + " a pioché un objectif de parcelle");
            }
            case 1 -> {
                gestionObjectives.rollPandaObjective(this, arg);
                if (arg.equals("demo"))System.out.println(this.name + " a pioché un objectif de panda");
            }case 2 -> {
                gestionObjectives.rollJardinierObjective(this, arg);
                if (arg.equals("demo")) System.out.println(this.name + " a pioché un objectif de jardinier");
            }
        }
    }

    @Override
    public TypeObjective choseTypeObjectiveToRoll(String arg){
        return null;
    }
}
