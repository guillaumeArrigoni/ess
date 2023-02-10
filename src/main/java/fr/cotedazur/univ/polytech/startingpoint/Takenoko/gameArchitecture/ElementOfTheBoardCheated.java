package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

public class ElementOfTheBoardCheated extends ElementOfTheBoard{

    public ElementOfTheBoardCheated(){
        super(new LoggerSevere(true)); //should never be use because the cheated ElementOfTheBoard can't call method throwing error
    }

    /**
     * Always able to grow bamboo
     * @param color of the bammboo
     */
    @Override
    public void placeBamboo(Color color) {
        return;
    }

    /**
     * Any color of bamboo can be give back
     * @param color of the bamboo
     */
    @Override
    public void giveBackBamboo(Color color) {
        return;
    }
}
