package cs350s22.component.ui.parser;

import java.io.IOException;
import java.text.ParseException;



public class Parser{

    protected A_ParserHelper parserHelper;
    protected String commandText;
    protected Command cmd;
    

    public Parser(A_ParserHelper parserHelper, String parse){
        this.parserHelper = parserHelper;
        this.commandText = parse;
     
    }

    public void parse() throws IOException, ParseException{
        String[] splitCommand = this.commandText.split(" ");
        cmd = setCommand(parserHelper, splitCommand);
        if(cmd != null){
            cmd.start();
        }
    }

    private Command setCommand(A_ParserHelper parserHelper, String[] command){
        String type = command[0].toUpperCase();
        if(type.equals("CREATE")){
            return new CreateCommand(parserHelper, command);
        }
        else if(type.equals("BUILD")){
            return new BuildCommand(parserHelper, command);
        }
        else if(type.equals("SEND")){
            return new SendCommand(parserHelper, command);
        }
        else if(type.equals("SET")){
            return new SetCommand(parserHelper, command);
        }
        else if(type.equals("GET")){
            return new GetCommand(parserHelper, command);
        }
        else if(type.contains("@")){
            return new MetaCommand(parserHelper, command);
        }
        
        throw new RuntimeException("NOT A VALID COMMAND");
    }

    
    
}