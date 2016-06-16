import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;

/**
 * Created by bal_mcsheldon on 6/16/2016.
 */
public class HungryHungryBaubles extends BasicSpaceship{
    public enum shipState{
        ROTATE, THRUST, BRAKE, IDLE
    }

    private Point middle;
    private shipState state;
    private boolean gotBauble;



    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {

        state = shipState.ROTATE;
        middle = new Point((worldWidth / 2), (worldHeight / 2));
        gotBauble = false;
        return new RegistrationData("Jim", new Color(124, 201, 255), 0);

    }


    @Override
    public ShipCommand getNextCommand(BasicEnvironment env) {
        ObjectStatus ship = env.getShipStatus();
        ShipCommand command = new IdleCommand(.1);
        BasicGameInfo info = env.getGameInfo();
        Point goldenBauble = info.getObjectiveLocation();

        switch(state){
            case ROTATE:
                //int angleToMiddle = ship.getPosition().getAngleTo(middle);
                gotBauble = false;
                command = new RotateCommand(ship.getPosition().getAngleTo(goldenBauble) - ship.getOrientation());
                state = shipState.THRUST;
                break;

            case THRUST:
                command = new ThrustCommand('B', 4, 0.6);
                state = shipState.IDLE;
                break;
            case BRAKE:
                command = new BrakeCommand(.1);
                state = shipState.ROTATE;
                break;
            case IDLE:
               if(ship.getPosition().isCloseTo(goldenBauble, 60.0)){
                    gotBauble = true;
                    command = new IdleCommand(4);
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
