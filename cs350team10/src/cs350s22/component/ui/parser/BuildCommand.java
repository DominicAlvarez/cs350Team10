package cs350s22.component.ui.parser;

public class BuildCommand extends Command {
    

    /**
  @author: Carlos Alvarez 
  {@summary} : CSCD 350 project Team 10
 **/
    
    public BuildCommand(A_ParserHelper parserHelper, String[] commandText){
        super(parserHelper, commandText);
        this.componentName = commandText[1];
        this.component = createComponent(componentName);
    }

    
    public Component createComponent(String componentName) {
        switch(componentName){
            
            case "NETWORK":
                return new NetworkComponent(parserHelper, commandText);

            default:
                throw new RuntimeException("INVALID COMPONENT " + componentName);
        }
    }
        
    }
    

