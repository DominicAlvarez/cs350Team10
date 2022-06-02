package cs350s22.component.ui.parser;

import java.util.List;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.support.Identifier;
import cs350s22.test.ActuatorPrototype;

public class ActuatorComponent extends Component {

    protected SymbolTable<A_Actuator> _actuatorTable;
    protected String actuatorType;
    protected Identifier id;
    protected List<Identifier> groups;
    protected List<A_Sensor> sensors;
    protected double leadin;
    protected double leadout;
    protected double relax;
    protected double velocityLimit;
    protected double min;
    protected double max;
    protected double initial;
    protected double jerkLimit;


    public ActuatorComponent(String[] commandText){
        super(commandText);
        store();
    }

    @Override
    void store() {
        System.out.println("YES");
        for(int i = 2; i < commandText.length; ++i){
            
            String cmd = commandText[i];
            switch(cmd){

                case "LINEAR":
                    actuatorType = "LINEAR";
                    break;

                case "ROTARY":
                    actuatorType = "ROTARY";
                    break;

                case "GROUP":
                    break;
                
                case "GROUPS":
                    break;
                
                case "SENSOR":
                    break;
                
                case "SENSORS":
                    break;
                
                case "ACCELERATION":
                    break;

                case "VELOCITY LIMIT":
                    break;
                
                case "VALUE":
                    break;
                
                case "JERK LIMIT":
                    break;
                
                default:
                    throw new RuntimeException("INVALID COMMAND");
                    
            }
        }

        
        ActuatorPrototype actuatorPrototype = new ActuatorPrototype(id, groups, leadin, leadout, relax, velocityLimit, initial, min, max, jerkLimit, sensors);

    }

}
