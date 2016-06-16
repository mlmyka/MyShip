import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.Color;
import java.util.List;

/**
 * Created by bal_mcsheldon on 6/13/2016.
 */
public class Survivor extends BasicSpaceship {
    public enum shipState{
        ROTATE, THRUST, BRAKE, IDLE, RADAR, RADARRESULTS,STEER, FIRETORPEDO, ALLSTOP
    }
    private shipState state;
    RadarResults res;


    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {

        state = shipState.THRUST;
        return new RegistrationData("Jim", new Color(124, 201, 255), 0);

    }


   @Override
   /**
    * switch statements to find next command
    */
    public ShipCommand getNextCommand(BasicEnvironment env) {
        ObjectStatus ship = env.getShipStatus();
        ShipCommand command = new IdleCommand(.1);


        switch(state){
            case ROTATE:
                int i = 0;
               //gets the angle to the first asteroid in the radar and rotates to face it
                command = new RotateCommand(ship.getPosition().getAngleTo(res.get(i).getPosition()) - ship.getOrientation());
                state = shipState.FIRETORPEDO;
                break;

            case THRUST:
                command = new ThrustCommand('B', 4, 0.6);
                state = shipState.RADAR;
                break;
            case BRAKE:
                    command = new BrakeCommand(0.01);
                    state = shipState.IDLE;
                break;
            case IDLE:
                command = new IdleCommand(0.1);
                state = shipState.THRUST;
                break;
            case RADAR:
                //sweeps the area for any object
                command = new RadarCommand(4);
                state = shipState.RADARRESULTS;
                break;
            case RADARRESULTS:
                command = new IdleCommand(.1);
                res = env.getRadar();
                //checks if there is any asteroids near my ship
                if(res.getByType("Asteroid").isEmpty()){
                    state = shipState.RADAR;
                }
                else{

                    state = shipState.ROTATE;

                }

                break;

            case STEER:
                break;
            case FIRETORPEDO:
                command = new FireTorpedoCommand('F');
                state = shipState.RADAR;
                break;
            case ALLSTOP:
                command = new AllStopCommand();
                break;
            default:
                break;
        }
        return command;
    }
}


