package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;

import cs350s22.component.ui.CommandLineInterface;
import cs350s22.message.A_Message;
import cs350s22.message.actuator.MessageActuatorReportPosition;
import cs350s22.message.actuator.MessageActuatorRequestPosition;
import cs350s22.message.ping.MessagePing;
import cs350s22.support.Identifier;

public class MessageComponent extends Component{

    CommandLineInterface cli;
    List<Identifier> ids;
    List<Identifier> groups;

    public MessageComponent(A_ParserHelper parserHelper, String[] commandText) {
        
        super(parserHelper, commandText);
        cli = parserHelper.getCommandLineInterface();
        ids = new ArrayList<Identifier>();
        groups = new ArrayList<Identifier>();

    }

    
    public void action() {
        String command = commandText[2];
        if(command.equals("PING")){

            A_Message msg = new MessagePing();
            cli.issueMessage(msg);

        }
        else{

            int count = 2;
            A_Message msg;
            if(command.equals("ID") || command.equals("IDS")){
                while(!commandText[count].equals("GROUP") && !commandText[count].equals("GROUPS") && !commandText[count].equals("POSITION")){
                    count++;
                    ids.add(Identifier.make(commandText[count]));
                }
            }
            if(commandText[count].equals("GROUP") || commandText[count].equals("GROUPS")){
                while(!commandText[count].equals("POSITION")){
                    count++;
                    groups.add(Identifier.make(commandText[count]));
                }
                count++;
            }
            
            else if(commandText[count].equals("POSITION")){

                count++;

                if(commandText[count].equals("REQUEST")){
                        double value = Double.valueOf(commandText[commandText.length - 1]);
                        if(ids.size() > 0){
                            msg = new MessageActuatorRequestPosition(ids, value);
                            cli.issueMessage(msg);
                        }

                        if(groups.size() > 0){
                            msg = new MessageActuatorRequestPosition(groups, value, 1);
                            cli.issueMessage(msg);
                        }
                }

                else if(commandText[count].equals("REPORT")){
                    if(ids.size() > 0){
                        msg = new MessageActuatorReportPosition(ids);
                        cli.issueMessage(msg);
                    }

                    if(groups.size() > 0){
                        msg = new MessageActuatorReportPosition(groups, 1);
                        cli.issueMessage(msg);
                    }
                }

                else{
                    throw new RuntimeException("INVALID INPUT AFTER POSITION " + commandText[count]);
                }

            }

            else{
                throw new RuntimeException("EXPECTED POSITION (REQUEST | REPORT)");
            }
        }
        
        
    }
    
}
