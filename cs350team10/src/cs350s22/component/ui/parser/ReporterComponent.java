package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import cs350s22.component.A_Component;
import cs350s22.component.actuator.A_Actuator;
import cs350s22.component.sensor.A_Sensor;
import cs350s22.component.sensor.reporter.A_Reporter;
import cs350s22.component.sensor.reporter.ReporterChange;
import cs350s22.component.sensor.reporter.ReporterFrequency;
import cs350s22.support.Identifier;

public class ReporterComponent extends Component{
    protected SymbolTable<A_Actuator> _actuatorTable;
    protected SymbolTable<A_Sensor> _sensorTable;
    protected SymbolTable<A_Reporter> _reporterTable;
    protected List<Identifier> ids;
    protected List<Identifier> groups;
    protected Identifier reporterID;
    protected String type = null;


    
    public ReporterComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        _actuatorTable = parserHelper.getSymbolTableActuator();
        _sensorTable = parserHelper.getSymbolTableSensor();
        _reporterTable = parserHelper.getSymbolTableReporter();
        ids = new ArrayList<>();
        groups = new ArrayList<>();
    }

    @Override
    void action() {
        type = commandText[2];
        if(commandText[2].equals("CHANGE") || commandText[2].equals("FREQUENCY")){
            if(!commandText[4].equals("NOTIFY")){
                reporterID= Identifier.make(commandText[3]);
            }
            else{
                throw new RuntimeException("NEED ID FOR REPORTER");
            }
            if(commandText[3].equals("NOTIFY")){
                int index = setOptionalComponents(commandText[5]);
                A_Reporter reporter;
                int val;
                switch(commandText[index]){
                    case "FREQUENCY":
                        try{
                            val = Integer.parseInt(commandText[index + 1]);
                        }catch(Exception ex){
                            throw new RuntimeException("NEED INTEGER VALUE");
                        }
                        if(groups.size() < 1){
                            reporter = new ReporterFrequency(ids, val);
                        }else{
                            reporter = new ReporterFrequency(ids, groups, val);
                        }
                        break;
                    default:
                        try{
                            val = Integer.parseInt(commandText[index + 1]);
                        }catch(Exception ex){
                            throw new RuntimeException("NEED INTEGER VALUE");
                        }
                        if(groups.size() < 1){
                            reporter = new ReporterChange(ids, val);
                        }else{
                            reporter = new ReporterChange(ids, groups, val);
                    }   
                }
                _reporterTable.add(reporterID, reporter);
            }
        }
                else{
                    throw new RuntimeException("INVALID REPORTER TYPE");
                }
             
           
        }
    private int setOptionalComponents(final String comp){
        int i = 5;
        if(commandText[i].equals("GROUPS") || commandText[i].equals("GROUP")){
            i++;
            while(!commandText[i].equals("IDS") && !commandText[i].equals("GROUPS")){
                this.groups.add(Identifier.make(commandText[i]));
                i++;
            }
        }
        if(commandText[i].equals("IDS")|| commandText[i].equals("ID")){
            i++;
            while(!commandText[i].equals("FREQUENCY")|| !commandText[i].equals("DELTA")){
                this.ids.add(Identifier.make(commandText[i]));
                i++;
            }
        }
        
          return i;

    }
}
