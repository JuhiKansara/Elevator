package src.elevator.model;

import java.util.ArrayList;
import java.util.List;

public class Building{
    private int minFloor;
    private int maxFloor;
    private List<Lift> lifts = new ArrayList<>();

    public Building(int minFloor, int maxFloor){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public boolean isValidFloor(int floor){
        return floor >= minFloor && floor <= maxFloor;
    }

    public int getMinFloor(){
        return minFloor;
    }
    public int getMaxFloor(){
        return maxFloor;
    }

    public void addLift(Lift lift){
        if(lift == null){
            throw new IllegalArgumentException("Lift must not be null.");
        }
        lift.assignToBuilding(this);
        lifts.add(lift);
    }
    public List<Lift> getLifts(){
        return lifts;
    }
}
