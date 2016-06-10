import java.awt.Color;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

/**
 * Created by bal_mcsheldon on 5/12/2016.
 */

public class myShip extends BasicSpaceship {
        public enum shipState{
            ROTATE, THRUST, BRAKE, IDLE
        }

        private Point middle;
        private shipState state;
        private boolean inCircle;



        @Override
        public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
        {

            state = shipState.ROTATE;
            middle = new Point((worldWidth / 2), (worldHeight / 2));
            inCircle = false;
            return new RegistrationData("Jim", new Color(124, 201, 255), 0);

        }


        @Override
        public ShipCommand getNextCommand(BasicEnvironment env) {
            ObjectStatus ship = env.getShipStatus();
            ShipCommand command = new IdleCommand(.1);


            switch(state){
                case ROTATE:
                    //int angleToMiddle = ship.getPosition().getAngleTo(middle);
                    command = new RotateCommand(ship.getPosition().getAngleTo(middle) - ship.getOrientation());
                    state = shipState.THRUST;
                    break;

                case THRUST:
                    command = new ThrustCommand('B', 4, 0.6);
                    state = shipState.IDLE;
                    break;
                case BRAKE:
                    if(ship.getPosition().isCloseTo(middle, 40.0)){
                        inCircle = true;
                        command = new BrakeCommand(0.01);
                        state = shipState.IDLE;
                   }
                    else {
                        state = shipState.IDLE;
                    }
                    break;
                case IDLE:
                    if(ship.getPosition().isCloseTo(middle, 200.0)){
                        state = shipState.BRAKE;
                    }
                    else if(ship.getPosition().isCloseTo(middle, 1000.0) && inCircle == true){
                        inCircle = false;
                        state = shipState.ROTATE;
                    }
                    //command = new IdleCommand(0.1);
                    break;
                default:
                    break;
            }
            return command;
    }
}



