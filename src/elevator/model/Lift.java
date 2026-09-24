package src.elevator.model;

import java.util.TreeSet;

public class Lift{
    private int currentFloor;
    private LiftMovement movement;
    private Building building;
    private TreeSet<Integer> stopsAbove;
    private TreeSet<Integer> stopsBelow;

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

    public void addStop(int floor){
        if(!isValidDestination(floor)){
            throw new IllegalArgumentException("Floor " + floor + " is not a valid destination for this lift.");
        }
        if(floor > currentFloor) stopsAbove.add(floor);
        else if (floor < currentFloor) stopsBelow.add(floor);
        else throw new IllegalArgumentException("The requested floor is the same as the current floor.");
    }

    Integer getNextStop(){
        if(movement == LiftMovement.MOVING_UP){
            if(!stopsAbove.isEmpty()) return stopsAbove.pollFirst();
            if(!stopsBelow.isEmpty()) {
                movement = LiftMovement.MOVING_DOWN;
                return stopsBelow.pollLast();
            }
            movement = LiftMovement.IDLE;
            return null;
        } else if (movement == LiftMovement.MOVING_DOWN) {
            if(!stopsBelow.isEmpty()) return stopsBelow.pollLast();
            if(!stopsAbove.isEmpty()){
                movement = LiftMovement.MOVING_UP;
                return stopsAbove.pollFirst();
            }
            movement = LiftMovement.IDLE;
            return null;
        } else {
            if(!stopsAbove.isEmpty()) return stopsAbove.pollFirst();
            if(!stopsBelow.isEmpty()) {
                movement = LiftMovement.MOVING_DOWN;
                return stopsBelow.pollLast();
            }
            movement = LiftMovement.IDLE;
            return null;
        }
    }
}
