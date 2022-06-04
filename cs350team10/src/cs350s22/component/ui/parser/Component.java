package cs350s22.component.ui.parser;



public abstract class Component {
    
    protected String[] commandText;
    protected A_ParserHelper parserHelper;

    public Component(A_ParserHelper parserHelper, String[] commandText){

        this.parserHelper = parserHelper;
        this.commandText = commandText;

    }

    abstract void action();
}
