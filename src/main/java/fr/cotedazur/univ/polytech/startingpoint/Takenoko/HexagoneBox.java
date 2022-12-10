package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.generateID;
import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.separateID;

public class HexagoneBox {

    private ArrayList<Integer> coordinates ;
    /**
     * Form : 1*1 000 000 + x*10 000 + y*100 + z
     * Example : 1020301 -> x = 02, y = 03, z = 01
     */
    private int id ;
    private Color color;
    private Special special;
    private boolean irrigate;
    private int heightBamboo;



    /**
     *      1
     *  6       2
     *  5       3
     *      4
     * @param z : 5-4 (or 1-2) edge
     * @param y : 6-5 (or 2-3) edge
     * @param x : 1-6 (or 3-4) edge
     * @param color : the color of the box
     * @param special : the particularity of the box
     */
    public HexagoneBox (int x, int y, int z, Color color, Special special){
        this.coordinates = new ArrayList<Integer>(Arrays.asList(x, y, z));
        this.id = generateID(x,y,z);
        this.color = color;
        this.special = special;
        this.irrigate = true;
        this.heightBamboo = 0;
    }

    public HexagoneBox (Color color, Special special){
        this.coordinates = new ArrayList<Integer>(Arrays.asList(null, null, null));
        this.id = -1;
        this.color = color;
        this.special = special;
        this.irrigate = true;
        this.heightBamboo = 0;
    }

    public ArrayList<Integer> getCoordinates(){
        return this.coordinates;
    }
    
    public int getId(){
        return this.id;
    }
    
    public Color getColor(){
        return this.color;
    }

    public Special getSpecial() {
        return special;
    }

    public boolean isIrrigate() {
        return irrigate;
    }

    public int getHeightBamboo() {
        return heightBamboo;
    }

    public void setId(int id){
        this.id = id;
        int[] tempoCoordinates = separateID(id);
        this.coordinates = new ArrayList(Arrays.asList(tempoCoordinates));
    }
}
