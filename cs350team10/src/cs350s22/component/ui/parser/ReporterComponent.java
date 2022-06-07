package cs350s22.component.ui.parser;

import java.util.List;

import cs350s22.component.A_Component;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.component.sensor.reporter.A_Reporter;
import cs350s22.support.Identifier;

public class ReporterComponent extends Component{
    protected List<A_Component> components;
    protected SymbolTable<A_Actuator> _actuatorTable;
    protected SymbolTable<A_Sensor> _sensorTable;
    protected SymbolTable<A_Reporter> _reporterTable;


    
    public ReporterComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        _actuatorTable = parserHelper.getSymbolTableActuator();
        _sensorTable = parserHelper.getSymbolTableSensor();
    }

    @Override
    void action() {
       if(commandText[3].equals("CHANGE")){
           int i = 3;
           while(i < commandText.length){
               Identifier temp = Identifier.make(commandText[i]);
               A_Component comp = null;
               A_Reporter cm = null;
               if(!commandText[i].equals("NOTIFY") || !commandText[i].equals("ID")
               || !commandText[i].equals("DELTA")){
                   if(_reporterTable.contains(temp)){
                    cm = _reporterTable.get(temp);
                   }
                   else if(_actuatorTable.contains(temp)){
                       comp = _actuatorTable.get(temp);
                   }
                   else if(_sensorTable.contains(temp)){
                       comp = _sensorTable.get(temp);
                   }
                   else{
                    throw new RuntimeException("INVALID COMPONENT");
                }
                   
               }
               components.add(comp);
               i++;
           }
       }
       else if(commandText[3].equals("FREQUENCY")){
               int i = 3;
               while(i < commandText.length){
                   Identifier temp = Identifier.make(commandText[i]);
                   A_Component comp = null;
                   A_Reporter cm = null;
                   if(!commandText[i].equals("NOTIFY") || !commandText[i].equals("IDS")
                   || !commandText[i].equals("FREQUENCY")){
                    if(_reporterTable.contains(temp)){
                        cm = _reporterTable.get(temp);
                    }
                    else if(_actuatorTable.contains(temp)){
                        comp = _actuatorTable.get(temp);
                    }
                    else if(_sensorTable.contains(temp)){
                        comp = _sensorTable.get(temp);
                    }
                    else{
                        throw new RuntimeException("INVALID COMPONENT");
                    }
                   }
                   components.add(comp);
                   i++;
               } // end while
           }
       else{
        throw new RuntimeException("INVALID DEPENDECY SEQUENCER COMMAND");
       }
    
  
    }
}
