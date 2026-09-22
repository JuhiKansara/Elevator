package src.elevator.request;

import src.elevator.model.Lift;

public class InternalRequest{
    private int requestToFloorNum;
    private Lift lift;

    public InternalRequest(int requestToFloorNum, Lift lift){
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
