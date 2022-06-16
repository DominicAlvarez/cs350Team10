package cs350s22.component.ui.parser;

/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

public class CreateCommand extends Command{

    public CreateCommand(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        componentName = commandText[1];
        this.component = createComponent(componentName);
    }

    
    public Component createComponent(String componentName) {
        switch(componentName){


            case "ACTUATOR":
                return new ActuatorComponent(parserHelper, commandText);
                
            
            case "CONTROLLER":
                return new ControllerComponent(parserHelper, commandText);
            
            case "MAPPER":
                return new MapperComponent(parserHelper, commandText);
            
             case "REPORTER":
                return new ReporterComponent(parserHelper, commandText);

            case "SENSOR":
                return new SensorComponent(parserHelper, commandText);
            
             case "WATCHDOG":
                return new WatchdogComponent(parserHelper, commandText);
            
            default:
                throw new RuntimeException("INVALID COMPONENT " + componentName + " IN CREATE COMMAND");
        }
        
        
        
    }
    
}
