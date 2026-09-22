package src.elevator.request;

import src.elevator.model.Building;
import src.elevator.model.Direction;

public class ExternalRequest{
    private int requestFromFloorNum;
    private Direction direction;
    private Building building;

    public ExternalRequest(int requestFromFloorNum, Direction direction, Building building){
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
