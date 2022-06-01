package cs350s22.component.ui.parser;

public class CreateCommand extends Command{
    protected String componentName;
    protected Component component;
    public CreateCommand(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        componentName = commandText[1];
        component = createComponent(componentName);

    }

    @Override
    public Component createComponent(String componentName) {
        if(componentName.equals("ACTUATOR")){
            return new ActuatorComponent(commandText);
        }
        return null;
        
    }

    

    
    
}
