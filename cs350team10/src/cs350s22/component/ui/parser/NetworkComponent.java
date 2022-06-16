package cs350s22.component.ui.parser;
import java.util.ArrayList;
import java.util.List;

import cs350s22.component.A_Component;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.controller.A_Controller;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.support.Identifier;


/**
  @author: Carlos Alvarez 
  {@summary} : CSCD 350 project Team 10
 **/

public class NetworkComponent extends Component{

   
    protected List<A_Component> components;
    protected SymbolTable<A_Actuator> _actuatorTable;
    protected SymbolTable<A_Sensor> _sensorTable;
    protected SymbolTable<A_Controller> _controllerTable;

    public NetworkComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        components = new ArrayList<A_Component>();
        _actuatorTable = parserHelper.getSymbolTableActuator();
        _controllerTable = parserHelper.getSymbolTableController();
        _sensorTable = parserHelper.getSymbolTableSensor();
        
    }

   
    public void action() {
        switch(commandText[2]){
            case "WITH":
                if(commandText[3].equals("COMPONENTS") || commandText[3].equals("COMPONENT")){
                    int i = 4;
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
                            throw new RuntimeException("INVALID COMPONENT " + temp);
                        }

                        components.add(comp);
                        i++;
                    } // end of while
                    parserHelper.getControllerMaster().addComponents(components);
                }
                else{
                    throw new RuntimeException("Expecting COMPONENT");
                }
                parserHelper.getNetwork().writeOutput();
                break;
            
            default:
                throw new RuntimeException("Expecting WITH");
            }
            
           
            
        
    }


    
}
