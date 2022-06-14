package cs350s22.component.ui.parser;


/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

 
public class GetCommand extends Command{

    public GetCommand(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        componentName = commandText[1];
        this.component = createComponent(componentName);
    }

    
    public Component createComponent(String componentName) {
        if(componentName.equals("SENSOR")){
            return new SensorComponent(parserHelper, commandText);
        }
        throw new RuntimeException("GET IS ONLY VALID FOR SENSOR");
    }
    
}