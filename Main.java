import src.elevator.model.*;
import src.elevator.request.*;
import src.elevator.service.Dispatcher;

public class Main {
    public static void main(String[] args) {
        Building building = new Building(-1, 14);

        Lift l1 = new Lift(5, LiftMovement.IDLE);
        building.addLift(l1);

        Dispatcher dispatcher = new Dispatcher(building);

        new InternalRequest(10,l1);

        int maxTicks = 15;
        for(int tick = 1; tick <= maxTicks; tick++){
            boolean anyArrival = false;

            for(Lift lift : building.getLifts()){
                boolean arrived = lift.moveOneFloor();
                if(arrived) anyArrival = true;
                System.out.println("Tick " + tick + ": Lift at floor " + lift.getCurrentFloor()
                        + " [" + lift.getMovement() + "]"
                        + (arrived ? " <- ARRIVED" : ""));
            }

            // Inject a new request mid-simulation, to test the "pass-by" logic
            if(tick == 2){
                dispatcher.handle(new ExternalRequest(2, Direction.DOWN, building));
                System.out.println("Tick " + tick + ": New request for floor 2 (DOWN)");
            }

            if(anyArrival){
                dispatcher.retryPending();
            }
        }
    }
}