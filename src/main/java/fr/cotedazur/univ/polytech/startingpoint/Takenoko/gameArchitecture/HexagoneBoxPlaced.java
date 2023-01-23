package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class HexagoneBoxPlaced extends HexagoneBox  {

    private int[] coordinates ;
    /**
     * Form : 1*1 000 000 + x*10 000 + y*100 + z
     * Example : 1020301 -> x = 02, y = 03, z = 01
     */
    private int id ;
    private int heightBamboo;
    private HashMap<Integer,int[]> AdjacentBox;
    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;
    private ArrayList<Crest> listOfCrestAroundBox;

    /**
     *      1
     *  6       2
     *  5       3
     *      4
     * @param z : 5-4 (or 1-2) edge
     * @param y : 6-5 (or 2-3) edge
     * @param x : 1-6 (or 3-4) edge
     */
    public HexagoneBoxPlaced (int x, int y, int z, HexagoneBox boxNotPlaced,RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        super(boxNotPlaced);
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        this.coordinates = new int[]{x,y,z};
        this.id = generateID(this.coordinates);
        this.heightBamboo = 0;
        getAllAdjacenteBox();
        updateRetrieveBox();
        generateCrestAroundBox();
    }

    public HexagoneBoxPlaced (int x, int y, int z, Color color,Special special,RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        this(x,y,z,new HexagoneBox(color,special),retrieveBoxIdWithParameters,board);
    }

    private void updateRetrieveBox() {
        retrieveBoxIdWithParameters.setBoxColor(this.id,super.color);
        retrieveBoxIdWithParameters.setBoxHeight(this.id, this.heightBamboo);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(this.id,super.irrigate);
        retrieveBoxIdWithParameters.setBoxSpeciality(this.id, super.special);
    }

    public int[] getCoordinates(){
        return this.coordinates;
    }

    public int getId(){
        return this.id;
    }

    public int getHeightBamboo() {
        return heightBamboo;
    }

    public void growBamboo() {
        retrieveBoxIdWithParameters.setBoxHeightDelete(this.id,this.heightBamboo);
        if (this.heightBamboo < 4) this.heightBamboo++;
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
    }

    public Optional<Color> eatBamboo() {
        retrieveBoxIdWithParameters.setBoxHeightDelete(this.id,this.heightBamboo);
        Optional<Color> bambooEatedColor = Optional.empty();
        if (this.heightBamboo > 0) {
            this.heightBamboo--;
            bambooEatedColor = Optional.of(super.color);
        }
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
        return bambooEatedColor;
    }

    public HashMap<Integer, int[]> getAdjacentBox() {
        return this.AdjacentBox;
    }

    public int[] getAdjacentBoxOfIndex(int index){
        return this.AdjacentBox.get(index);
    }

    public void setSpecial(Special special) {
        super.special = special;
        retrieveBoxIdWithParameters.setBoxSpeciality(this.id,super.special);
    }

    public void setIrrigate(boolean irrigate) {
        super.irrigate = irrigate;
        retrieveBoxIdWithParameters.setBoxIsIrrigated(this.id,super.irrigate);
    }

    public void setHeightBamboo(int heightBamboo) {
        this.heightBamboo = heightBamboo;
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
    }


    /**
     * A method to get the 6 possible adjacente box of a box
     *      6       1
     *  5       x       2
     *      4       3
     * with x the box in question and 1,2,3,4,5,6 the adjacente box
     */
    private void getAllAdjacenteBox(){
        this.AdjacentBox = new HashMap<Integer,int[]>();
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        int z = this.coordinates[2];
        this.AdjacentBox.put(1,new int[]{x+1,y-1,z});
        this.AdjacentBox.put(2,new int[] {x+1,y,z-1});
        this.AdjacentBox.put(3,new int[] {x,y+1,z-1});
        this.AdjacentBox.put(4,new int[] {x-1,y+1,z});
        this.AdjacentBox.put(5,new int[] {x-1,y,z+1});
        this.AdjacentBox.put(6,new int[] {x,y-1,z+1});
    }

    @Override
    public String toString() {
        return "Box of id : " + id +
                ", color : " + super.color +
                " and is " + super.special;
    }


    private void generateCrestAroundBox(){
        ArrayList<Crest> listOfCoordiante = new ArrayList<>();
        int x = this.coordinates[0]*10;
        int y = this.coordinates[1]*10;
        listOfCoordiante.add(new Crest(x+5,y-5,1));
        listOfCoordiante.add(new Crest(x+5,y,2));
        listOfCoordiante.add(new Crest(x,y+5,3));
        listOfCoordiante.add(new Crest(x-5,y+5,1));
        listOfCoordiante.add(new Crest(x-5,y,2));
        listOfCoordiante.add(new Crest(x,y-5,3));
        this.listOfCrestAroundBox =  listOfCoordiante;
    }

    public ArrayList<Crest> getListOfCrestAroundBox() {
        return listOfCrestAroundBox;
    }

    private void setAutoIrrigation(){
        if (board.getCrestGestionnaryAlreadyIrrigated().contains(this.id)){
            board.getCrestGestionnaryAlreadyIrrigated().removeAll(Arrays.asList(this.id));
            super.irrigate = true;
        } else {
            //TODO change below to false when add irrigation option to the game
            super.irrigate = board.getAllIrrigated();
        }
    }

    private void initiateLacIrrigation(){
        if (super.color == Color.Lac){
            for (int i=0; i<this.listOfCrestAroundBox.size();i++){
                this.board.placeIrrigation(listOfCrestAroundBox.get(i));
            }
        }
    }

    public void launchIrrigationChecking(){
        initiateLacIrrigation();
        setAutoIrrigation();
    }
}
