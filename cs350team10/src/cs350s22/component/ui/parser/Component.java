package cs350s22.component.ui.parser;

/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

public abstract class Component {
    
    protected String[] commandText;
    protected A_ParserHelper parserHelper;

    public Component(A_ParserHelper parserHelper, String[] commandText){

        this.parserHelper = parserHelper;
        this.commandText = commandText;

    }

    abstract void action();
}
