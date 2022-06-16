package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;
import cs350s22.component.A_Component;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.component.sensor.mapper.A_Mapper;
import cs350s22.component.sensor.reporter.A_Reporter;
import cs350s22.component.sensor.watchdog.A_Watchdog;
import cs350s22.support.Identifier;
import cs350s22.test.MySensor;


/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

public class SensorComponent extends Component
{

    protected String type;
    protected Identifier id;
    protected List<Identifier> groups;
    protected SymbolTable<A_Reporter> _reporterTable;
    protected SymbolTable<A_Mapper> _mapperTable;
    protected SymbolTable<A_Watchdog> _watchdogTable;
    protected SymbolTable<A_Sensor> _sensortable;
    protected List<A_Reporter> reporters;
    protected List<A_Watchdog> watchdogs;
    protected A_Mapper mapper;
    protected MySensor mySensor;
    

    public SensorComponent(A_ParserHelper parserHelper, String[] commandText) 
    {
        super(parserHelper, commandText);
        type = null;
        id = null;
        groups = new ArrayList<>();
        _reporterTable = parserHelper.getSymbolTableReporter();
        _mapperTable = parserHelper.getSymbolTableMapper();
        _watchdogTable = parserHelper.getSymbolTableWatchdog();
        _sensortable = parserHelper.getSymbolTableSensor();
        reporters = new ArrayList<A_Reporter>();
        watchdogs = new ArrayList<A_Watchdog>();
        mapper = null;
        mySensor = null;

        
    }

    
     public void action() 
     {
         // Sensor is the only that has 3 types of commands so must implement this way
        if(commandText[0].equals("CREATE")){
            createSensor();
        }
        else if(commandText[0].equals("SET")){
            setSensor();
        }
        else{
            getSensor();
        }
    }




    public void createSensor(){
        
        if(commandText[2].equals("SPEED") || commandText[2].equals("POSITION"))
        {
            id = Identifier.make(commandText[3]);
            int i = 4;
            while(i < commandText.length){
                if(commandText[i].equals("GROUPS") || commandText[i].equals("GROUP"))
                {   
                    groups = new ArrayList<Identifier>();
                    i++;
                    while(i < commandText.length && !commandText[i].equals("REPORTER") && !commandText[i].equals("REPORTERS") && !commandText[i].equals("WATCHDOG") && !commandText[i].equals("WATCHDOGS") && !commandText[i].equals("MAPPER"))
                    {
                         // must increment i to be in the correct position after while loop ends
                        groups.add(Identifier.make(commandText[i]));
                        i++; // add an Identifier to groups
                    }
                    
                }
                else if(commandText[i].equals("REPORTER") || commandText[i].equals("REPORTERS"))
                {   
                    
                    while(i < commandText.length - 1 && !commandText[i + 1].equals("WATCHDOG") && !commandText[i + 1].equals("WATCHDOGS") && !commandText[i + 1].equals("MAPPER")){
                        i++; // must increment i to be in the correct position after while loop ends
                        reporters.add(_reporterTable.get(Identifier.make(commandText[i]))); // add an Identifier to groups
                    }
                    i++;
                }
                
                
                else if(commandText[i].equals("WATCHDOGS") || commandText[i].equals("WATCHDOG"))
                {   
                    
                    while(i < commandText.length - 1 && !commandText[i + 1].equals("MAPPER"))
                    {
                        i++; // must increment i to be in the correct position after while loop ends
                        watchdogs.add(_watchdogTable.get(Identifier.make(commandText[i]))); // add an Identifier to groups
                    }
                    i++;
                
                }
                else if (commandText[i].equals("MAPPER"))
                {
                    i++;
                    mapper = _mapperTable.get(Identifier.make(commandText[i]));
                    i++;
                }
                
                else
                {
                    throw new RuntimeException("NOT A CORRECT COMPONENT/KEYWORD");
                }
            }
            
            // ensures we use the correct constructor
            if(mapper != null){
                mySensor = new MySensor(id, groups, reporters, watchdogs, mapper);
                
            }
            else if(groups != null || watchdogs != null || reporters != null){
    
                mySensor = new MySensor(id, groups, reporters, watchdogs);
            }
            else{
                mySensor = new MySensor(id);
            }

            _sensortable.add(id, mySensor);

        }else
        {
            throw new RuntimeException("EXPECTED SPEED or POSITION. GOT: " + commandText[2]);
        }

        
    }

    public void setSensor(){
        
        if(_sensortable.contains(Identifier.make(commandText[2]))) // checking sensor to make sure it is created 
        {   
            A_Sensor sensor = _sensortable.get(Identifier.make(commandText[2]));
            List<A_Component> networkComponents = parserHelper.getNetwork().getMasterController().getComponents();
            boolean inNetwork = false;
        
            for(A_Component component : networkComponents) 
            {
                if(component.getID().compareTo(sensor.getID()) == 0) // checks if sensor is directly connected to the netowrk
                {
                    inNetwork = true;
                }
            }

            if(inNetwork){ // if the sensor is directly connected to the network
                if(commandText[3].equals("VALUE"))
                {
                    Double value = Double.valueOf(commandText[4]);
                    _sensortable.get(sensor.getID()).setValue(value);
                }
                else
                {
                    throw new RuntimeException("EXPECTED keyword VALUE in the command entered");
                }
            }
        }
        else
        {
            throw new RuntimeException("SENSOR CANNOT BE SET UNTIL CREATED");
        }
    }

    public void getSensor()
    {
       

        if(_sensortable.contains(Identifier.make(commandText[2]))) // Makes sure this sensor exists
        {   
            A_Sensor sensor = _sensortable.get(Identifier.make(commandText[2]));
            System.out.println("Sensor: " + sensor.getID().toString() + " Value is : " + sensor.getValue());
        }
        else
        {
            throw new RuntimeException("CANNOT GET SENSOR UNTIL CREATED");
        }
    }
    
}
