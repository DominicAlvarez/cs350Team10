package cs350s22.component.ui.parser;

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
            
            // case "MAPPER":
            //     break;
            
            // case "REPORTER":
            //     break;

            // case "SENSOR":
            //     break;
            
            // case "WATCHDOG":
            //     break;
            
            default:
                throw new RuntimeException("INVALID COMPONENT" + componentName + " IN CREATE COMMAND");
        }
        
        
        
    }
    
}
