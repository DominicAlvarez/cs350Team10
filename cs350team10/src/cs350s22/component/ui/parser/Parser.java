package cs350s22.component.ui.parser;

import java.io.IOException;
import java.text.ParseException;



public class Parser implements ParserConstants{

    protected A_ParserHelper parserHelper;
    protected String commandText;
    protected Token jj_nt;
    protected Token token;
    protected ParserTokenManager token_source;
    protected Command cmd;
    

    public Parser(A_ParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText;
     
    }

   public final void disable_tracing(){} // not sure what do with these methods
   public final void enable_tracing(){}

    public void parse() throws IOException, ParseException{
        String[] splitCommand = this.commandText.split(" ");
        cmd = setCommand(parserHelper, splitCommand);
    }

    private Command setCommand(A_ParserHelper parserHelper, String[] command){
        String type = command[0].toUpperCase();
        if(type.equals("CREATE")){
            return new CreateCommand(parserHelper, command);
        }
        
        return null;
    }

    
    
}
