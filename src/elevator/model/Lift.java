package src.elevator.model;

public class Lift{
    private int currentFloor;
    private LiftMovement movement;
    private Building building;

    public Lift(int currentFloor, LiftMovement movement){
        if(movement == null){
            throw new IllegalArgumentException("Movement must be MOVING_UP, MOVING_DOWN or IDLE.");
        }
        this.currentFloor = currentFloor;
        this.movement = movement;
    }

    public int getCurrentFloor(){
        return currentFloor;
    }
    public LiftMovement getMovement(){
        return movement;
    }
    public Building getBuilding(){
        return building;
    }

    public boolean isValidDestination(int floor){
        if(building == null){
            throw new IllegalStateException("Lift has not been assigned to a building yet.");
        }
        return floor >= building.getMinFloor() && floor <= building.getMaxFloor();
    }

    void assignToBuilding(Building building){
        if(building == null){
            throw new IllegalArgumentException("Building must not be null.");
        }
        if(this.building != null){
            throw new IllegalStateException("This lift is already assigned to a building.");
        }
        if(!building.isValidFloor(this.currentFloor)){
            throw new IllegalArgumentException("Floor " + this.currentFloor + " is outside building range " + building.getMinFloor() + " to " + building.getMaxFloor());
        }
        this.building = building;
    }
}
