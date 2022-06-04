package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.support.Identifier;
import cs350s22.test.ActuatorPrototype;

public class ActuatorComponent extends Component {

    protected SymbolTable<A_Actuator> _actuatorTable;
    protected SymbolTable<A_Sensor> _sensorsTable;
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


    public ActuatorComponent(A_ParserHelper parserHelper, String[] commandText){
        super(parserHelper, commandText);
       _actuatorTable = parserHelper.getSymbolTableActuator();
       _sensorsTable = parserHelper.getSymbolTableSensor();
       actuatorType = null;
       id = null;
       groups = new ArrayList<Identifier>();
       sensors = new ArrayList<A_Sensor>();
       leadin = 0;
       leadout = 0;
       relax = 0;
       velocityLimit = 0;
       min = 0;
       max = 0;
       initial = 0;
       jerkLimit = 0;
    }

    
    void action() {
        
        for(int i = 2; i < commandText.length; ++i){
            
            String cmd = commandText[i];
            switch(cmd){

                case "LINEAR":
                    actuatorType = "LINEAR";
                    id = Identifier.make(commandText[i + 1]);
                    i++;
                    break;

                case "ROTARY":
                    actuatorType = "ROTARY";
                    id = Identifier.make(commandText[i + 1]);
                    i++;
                    break;

                case "GROUP":
                    groups.add(Identifier.make(commandText[i + 1]));
                    i++;
                    break;
                
                case "GROUPS":
                    while(!cmd.equals("SENSOR") || !cmd.equals("SENSORS") || !cmd.equals("ACCELERATION")){
                        i++;
                        groups.add(Identifier.make(commandText[i + 1]));
                    
                    }
                    break;
                
                case "SENSOR":
                    sensors.add(_sensorsTable.get(Identifier.make(commandText[i + 1])));
                    i++;
                    break;
                
                case "SENSORS":
                    while(!cmd.equals("ACCELERATION")){
                        i++;
                        sensors.add(_sensorsTable.get(Identifier.make(commandText[i + 1])));
                    }
                    break;
                
                case "ACCELERATION":
                    if(commandText[i + 1].equals("LEADIN")){
                        leadin = Double.valueOf(commandText[i + 2]);
                        i += 3;
                        if(commandText[i].equals("LEADOUT")){
                            leadout = Double.valueOf(commandText[i + 1]);
                            i++;
                        }
                        break;
                    }
                    else{
                        throw new RuntimeException("NOT A VALID COMMAND AFTER ACCELERATION");
                    }
                
                case "RELAX":
                    relax = Double.valueOf(commandText[i + 1]);
                    i++;
                    break;
                case "VELOCITY":
                    velocityLimit = Double.valueOf(commandText[i + 2]);
                    i+=2;
                    break;
                
                case "VALUE":
                if(commandText[i + 1].equals("MIN")){
                    min = Double.valueOf(commandText[i + 2]);
                    i += 3;
                    if(commandText[i].equals("MAX")){
                        max = Double.valueOf(commandText[i + 1]);
                        i++;
                    }
                    break;
                }
                else{
                    throw new RuntimeException("NOT A VALID COMMAND AFTER VALUE");
                }
                
                case "INITIAL":
                    initial = Double.valueOf(commandText[i + 1]);
                    i++;
                    break;
                
                case "JERK":
                    jerkLimit = Double.valueOf(commandText[i + 2]);
                    i+=2;
                    break;
                    
                default:
                    throw new RuntimeException("INVALID COMMAND");
                    
            }
        }

        
        ActuatorPrototype actuatorPrototype = new ActuatorPrototype(id, groups, leadin, leadout, relax, velocityLimit, initial, min, max, jerkLimit, sensors);
        

        _actuatorTable.add(id, actuatorPrototype);
        
    }

}
