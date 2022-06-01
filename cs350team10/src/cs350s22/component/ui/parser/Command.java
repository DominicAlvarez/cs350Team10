package cs350s22.component.ui.parser;

public abstract class Command {
    protected String[] commandText;
    protected A_ParserHelper parserHelper;
    public Command(A_ParserHelper parserHelper, String[] commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText;   
    }

    abstract Component createComponent(String componentName);

    
   
}
