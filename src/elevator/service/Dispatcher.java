package src.elevator.service;

import src.elevator.model.Building;
import src.elevator.model.Direction;
import src.elevator.model.Lift;
import src.elevator.model.LiftMovement;
import src.elevator.request.ExternalRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dispatcher {
    private Building building;
    private List<ExternalRequest> pendingRequests = new ArrayList<>();

    public Dispatcher(Building building){
        if(building == null){
            throw new IllegalArgumentException("Building must not be null.");
        }
        this.building = building;
    }

    void retryPending(){
        Iterator<ExternalRequest> it = pendingRequests.iterator();
        while(it.hasNext()){
            ExternalRequest request = it.next();
            Lift lift = selectLift(request);
            if (lift != null){
                lift.addStop(request.getRequestFromFloorNum());
                it.remove();
            }
        }
    }

    public void handle(ExternalRequest request){
        Lift lift = selectLift(request);
        if (lift == null){
            pendingRequests.add(request);
        }
        else {
            lift.addStop(request.getRequestFromFloorNum());
        }
    }

    Lift selectLift(ExternalRequest request){
        Lift bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;
        for(Lift lift : building.getLifts()){
            boolean movingTowardSameDirection = (request.getDirection() == Direction.UP && lift.getMovement() == LiftMovement.MOVING_UP) || (request.getDirection() == Direction.DOWN && lift.getMovement() == LiftMovement.MOVING_DOWN);
            if(movingTowardSameDirection){
                return lift;
            }
            if(lift.getMovement() == LiftMovement.IDLE){
                int distance = Math.abs(lift.getCurrentFloor() - request.getRequestFromFloorNum());
                if(distance < bestDistance){
                    bestDistance = distance;
                    bestMatch = lift;
                }
            }
        }
        return bestMatch;
    }
}
