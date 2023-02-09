package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxSimulation;

import java.util.ArrayList;

public class GenerateOptimizePathForSeveralBoxSimulation extends GenerateOptimizePathForSeveralBox{

    /**
     * Extend the method GenerateOptimizePathForSeveralBox in order to accept HexagoneBoxSimulation
     * @param boxs : the list of HexagoneBoxSimulation use to generate the path
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    public GenerateOptimizePathForSeveralBoxSimulation(ArrayList<HexagoneBoxSimulation> boxs) throws CrestNotRegistered, CloneNotSupportedException {
        super(new ArrayList<HexagoneBoxPlaced>(),false);
        super.boxToIrrigate = convertListOfBoxSimulationToBoxPlaced(boxs);
        super.crestGestionnary = boxs.get(0).getBoard().getCrestGestionnary();
        super.setupPathForEachBox();
        super.setupGeneralWayForEachBox();
        super.generatePath(super.boxToIrrigate,super.boxToIrrigate.size());
    }

    /**
     * Method use to convert a list of HexagoneBoxSimulation to HexagoneBoxPlaced
     * @param list : the list to convert
     * @return : the list converted
     */
    private ArrayList<HexagoneBoxPlaced> convertListOfBoxSimulationToBoxPlaced(ArrayList<HexagoneBoxSimulation> list){
        ArrayList<HexagoneBoxPlaced> listToReturn= new ArrayList<>();
        for (HexagoneBoxSimulation box : list){
            listToReturn.add(box);
        }
        return listToReturn;
    }
}