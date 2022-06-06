package cs350s22.component.ui.parser;

public abstract class Command {
    
    protected String[] commandText;
    protected A_ParserHelper parserHelper;
    protected Component component;
    protected String componentName;

    public Command(A_ParserHelper parserHelper, String[] commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText; 
    }

    abstract Component createComponent(String componentName); // creates component

    public void start(){ // starts component once created
        if(component != null){
            component.action();
        }
    } 

    
   
}