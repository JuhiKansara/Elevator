import java.util.*;

enum Direction{
    UP, DOWN
}
enum LiftMovement{
    MOVING_UP, MOVING_DOWN, IDLE
}

class ExternalRequest{
    private int requestFromFloorNum;
    private Direction direction;
    private Building building;

    ExternalRequest(int requestFromFloorNum, Direction direction, Building building){
        if(direction == null){
            throw new IllegalArgumentException("Direction must be UP or DOWN.");
        }
        if(building == null){
            throw new IllegalArgumentException("Please provide the building...it cannot be null.");
        }
        if(!building.isValidFloor(requestFromFloorNum)){
            throw new IllegalArgumentException("Floor " + requestFromFloorNum + " is outside building range " + building.getMinFloor() + " to " + building.getMaxFloor());
        }
        this.requestFromFloorNum = requestFromFloorNum;
        this.direction = direction;
        this.building = building;
    }

    public int getRequestFromFloorNum(){
        return requestFromFloorNum;
    }
    public Direction getDirection(){
        return direction;
    }
    public Building getBuilding(){
        return building;
    }
}

class InternalRequest{
    private int requestToFloorNum;
    private Lift lift;

    InternalRequest(int requestToFloorNum, Lift lift){
        if(lift == null){
            throw new IllegalArgumentException("Must provide valid lift.");
        }
        if(!lift.isValidDestination(requestToFloorNum)){
            throw new IllegalArgumentException("The destination floor " + requestToFloorNum + " is not in the range of " + lift.getBuilding().getMinFloor() + " to " + lift.getBuilding().getMaxFloor());
        }
        this.requestToFloorNum = requestToFloorNum;
        this.lift = lift;
    }

    public int getRequestToFloorNum(){
        return requestToFloorNum;
    }
    public Lift getLift(){
        return lift;
    }
}

class Lift{
    private int currentFloor;
    private LiftMovement movement;
    private Building building;
    
    Lift(int currentFloor, LiftMovement movement){
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

    boolean isValidDestination(int floor){
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

class Building{
    private int minFloor;
    private int maxFloor;
    private List<Lift> lifts = new ArrayList<>();

    Building(int minFloor, int maxFloor){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    boolean isValidFloor(int floor){
        return floor >= minFloor && floor <= maxFloor;
    }

    public int getMinFloor(){
        return minFloor;
    }
    public int getMaxFloor(){
        return maxFloor;
    }

    void addLift(Lift lift){
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

class Main {
    public static void main(String[] args) {
        Building A = new Building(-1,14);
        System.out.println(A.isValidFloor(15));

        Lift l1 = new Lift(-1,LiftMovement.MOVING_UP);
        A.addLift(l1);

        ExternalRequest req1 = new ExternalRequest(14,Direction.DOWN,A);
        System.out.println(req1.getRequestFromFloorNum());
        System.out.println(req1.getDirection());

        InternalRequest req2 = new InternalRequest(2,l1);
        System.out.println(req2.getRequestToFloorNum());
        System.out.println(req2.getLift());
    }
}