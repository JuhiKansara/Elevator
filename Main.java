import java.util.*;

import src.elevator.model.Building;
import src.elevator.model.Direction;
import src.elevator.model.Lift;
import src.elevator.model.LiftMovement;
import src.elevator.request.ExternalRequest;
import src.elevator.request.InternalRequest;

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