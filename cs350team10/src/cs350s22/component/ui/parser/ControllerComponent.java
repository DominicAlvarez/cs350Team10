package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;



import cs350s22.component.A_Component;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.controller.A_Controller;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.support.Identifier;
import cs350s22.test.MyControllerSlaveForwarding;
import cs350s22.test.MyControllerSlaveNonforwarding;

public class ControllerComponent extends Component {

    protected SymbolTable<A_Controller> _controllerTable;
    protected SymbolTable<A_Sensor> _sensorTable;
    protected SymbolTable<A_Actuator> _actuatorTable;
    protected String controllerType;
    protected Identifier id;
    protected List<Identifier> groups;
    protected Identifier dependencySequencerID;
    protected List<A_Component> components;


    public ControllerComponent(A_ParserHelper parserHelper, String[] commandText){
        super(parserHelper, commandText);
        _controllerTable = parserHelper.getSymbolTableController();
        _sensorTable = parserHelper.getSymbolTableSensor();
        _actuatorTable = parserHelper.getSymbolTableActuator();
        controllerType = null;
        id = null;
        groups = new ArrayList<Identifier>();
        dependencySequencerID = null;
        components = new ArrayList<A_Component>();
        
    }

    
    public void action() {

        // set the controller type
        
        String cmd = commandText[2];
        controllerType = cmd.equals("FORWARDING") || cmd.equals("NONFORWARDING") ? cmd : null;
        if(controllerType == null){
            throw new RuntimeException("INVALID CONTROLLER TYPE");
        }
        
        // set Id while ensuring it has not been skipped.
        if(!commandText[3].equals("GROUP") && !commandText[3].equals("GROUPS") && !commandText[3].equals("DEPENDECY") && !commandText[3].equals("WITH")){
            id = Identifier.make(commandText[3]);
        }
        else{
            throw new RuntimeException("NO ID WAS ENTERED");
        }

        for(int i = 4; i < commandText.length; ++i){
            
            cmd = commandText[i];
            switch(cmd){

                case "GROUP":
                    groups.add(Identifier.make(commandText[i + 1]));
                    i++;
                    break;
                
                case "GROUPS":
                    while(!cmd.equals("SENSOR") && !cmd.equals("SENSORS") && !cmd.equals("ACCELERATION")){
                        i++;
                        groups.add(Identifier.make(commandText[i + 1]));
                    }
                    break;
                
                case "DEPENDECY":
                    if(commandText[i + 1].equals("SEQUENCER")){
                        i += 2;
                        dependencySequencerID = Identifier.make(commandText[i]);
                        break;
                        
                    }

                case "WITH":
                    if(commandText[i + 1].contains("COMPONENT")){
                        i += 2 ;
                        while(i < commandText.length){

                            Identifier temp = Identifier.make(commandText[i]);
                            A_Component comp = null;
                            if(_actuatorTable.contains(temp)){
                                comp = _actuatorTable.get(temp);
                                
                            }
                            else if(_controllerTable.contains(temp)){
                                comp = _controllerTable.get(temp);
                                
                            }
                            else if(_sensorTable.contains(temp)){
                                comp = _sensorTable.get(temp);
                                
                            }
                            else{
                                throw new RuntimeException("INVALID COMPONENT");
                            }

                            components.add(comp);
                            i++;
                        } // end of while
                    }
                    else{
                        throw new RuntimeException("INVALID DEPENDECY SEQUENCER COMMAND");
                    }
                    break;
                
                    
                default:
                    throw new RuntimeException("INVALID COMMAND");
            }
        
        }
       if(controllerType.equals("FORWARDING")){
           MyControllerSlaveForwarding controller = new MyControllerSlaveForwarding(id);
           controller.addComponents(components);
           _controllerTable.add(id, controller);
       }
       else{
           MyControllerSlaveNonforwarding controller = new MyControllerSlaveNonforwarding(id);
           controller.addComponents(components);
           _controllerTable.add(id, controller);
       }
       
        
    }
    
}