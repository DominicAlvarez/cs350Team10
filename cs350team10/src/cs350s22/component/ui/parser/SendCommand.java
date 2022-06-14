package cs350s22.component.ui.parser;

/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

public class SendCommand extends Command{

    public SendCommand(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        componentName = commandText[1];
        this.component = createComponent(componentName);
    }

    
    public Component createComponent(String componentName) {
        switch(componentName){
            
            case "MESSAGE":
                return new MessageComponent(parserHelper, commandText);
            default:
                throw new RuntimeException("INVALID COMPONENT " + componentName + " IN SEND COMMAND");
            
        }
        
    }
    
}