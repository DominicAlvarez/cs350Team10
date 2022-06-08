package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.support.Identifier;
import cs350s22.test.ActuatorPrototype;


/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10


  NTS: fix redundancy in the code.
 **/


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

    
    public void action() {

        setType(commandText[2]);
        setID(commandText[3]);
        int index = setOptionalComponents(commandText[4]);
        
        while(index < commandText.length){
            switch(commandText[index])
            {
                case "ACCELERATION":

                    index++;
                    if(commandText[index].equals("LEADIN"))
                    {
                        index++;
                        leadin = setDoubleValues(index);
                        index++;
                        if(commandText[index].equals("LEADOUT"))
                        {
                            index++;
                            leadout = setDoubleValues(index);
                            index++;
                            
            
                        }else
                        {
                        throw new RuntimeException("EXPECTED LEADOUT");
                        }   
            
                    }else
                    {
                        throw new RuntimeException("EXPECTED LEADIN");
                    }

                    break;
                
                case "RELAX":

                    index++;
                    relax = setDoubleValues(index);
                    index++;
                    break;
                
                case "VELOCITY":

                    index++;
                    if(commandText[index].equals("LIMIT"))
                    {
                        index++;
                        velocityLimit = setDoubleValues(index);
                        index++;
                        
            
                    }else
                    {
                        throw new RuntimeException("EXPECTED VELOCITY LIMIT");
                    }
                    break;
                
                case "VALUE":
                    index++;
                    if(commandText[index].equals("MIN"))
                    {
                        index++;
                        min= setDoubleValues(index);
                        index++;
                        if(commandText[index].equals("MAX"))
                        {
                            index++;
                            max = setDoubleValues(index);
                            index++;
                            
            
                        }else
                        {
                        throw new RuntimeException("EXPECTED MAX");
                        }   
            
                    }else
                    {
                        throw new RuntimeException("EXPECTED MIN");
                    }
                    break;
                
                case "INITIAL":

                    index++;
                    initial = setDoubleValues(index);
                    index++;
                    break;

                case "JERK":

                    index++;
                    if(commandText[index].equals("LIMIT"))
                    {
                        index++;
                        jerkLimit = setDoubleValues(index);
                        index++;
                        
            
                    }else
                    {
                        throw new RuntimeException("EXPECTED JERK LIMIT");
                    }
                    break;
                    
                
                default: 
                    throw new RuntimeException("MISSING kEYWORDS");
            }
        }

        

        ActuatorPrototype actuatorPrototype = new ActuatorPrototype(id, groups, leadin, leadout, relax, velocityLimit, initial, min, max, jerkLimit, sensors);
        

        _actuatorTable.add(id, actuatorPrototype);
        
    }

    private void setType(final String type)
    {
        if(type.equals("LINEAR") || type.equals("ROTARY"))
        {
            this.actuatorType = type;
        }
            
        else
        {
            throw new RuntimeException("Expected LINEAR or ROTARY");
        }

    }

    private void setID(final String type)
    { 
        if(!type.equals("ACCELERATION") && !type.equals("GROUPS") && !type.equals("GROUP") && !type.equals("SENSOR") && !type.equals("SENSORS"))
        {
            this.id = Identifier.make(type);
        }
        else{
            throw new RuntimeException("Must enter an Id for the actuator.");
        }
    }

    private int setOptionalComponents(final String comp)
    {
        int i = 4;
        if(commandText[i].equals("GROUPS") || commandText[i].equals("GROUP"))
        {
            i++;
            while(!commandText[i].equals("SENSOR") && !commandText[i].equals("SENSORS") && !commandText[i].equals("ACCELERATION"))
            {
                this.groups.add(Identifier.make(commandText[i]));
                i++;
            }
        }
        if(commandText[i].equals("SENSOR") || commandText[i].equals("SENSORS"))
        {
            i++;
            while(!commandText[i].equals("ACCELERATION"))
            {
                Identifier temp = Identifier.make(commandText[i]);
                if(_sensorsTable.contains(temp)){
                    sensors.add(_sensorsTable.get(temp));
                } // we might need to add a else statement if sensor is not in table.
                i++;
            }
        }
        return i;

    }

    private double setDoubleValues(final int index)
    {
        try{
            double val = Double.parseDouble(commandText[index]);
            return val;
        }catch(Exception ex){
            throw new RuntimeException("Expected a value of type double");
        }
    }

    
}
