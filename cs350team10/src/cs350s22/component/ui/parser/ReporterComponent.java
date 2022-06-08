package cs350s22.component.ui.parser;

import java.util.ArrayList;
import java.util.List;


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
    protected Identifier reporterId;
    protected String type;


    
    public ReporterComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        _actuatorTable = parserHelper.getSymbolTableActuator();
        _sensorTable = parserHelper.getSymbolTableSensor();
        _reporterTable = parserHelper.getSymbolTableReporter();
        ids = new ArrayList<>();
        groups = new ArrayList<>();
        type = null;
        reporterId = null;
    }

    @Override
    void action() {

        type = commandText[2];
        
        if(type.equals("CHANGE") || type.equals("FREQUENCY")){
            if(!commandText[3].equals("NOTIFY")){
                reporterId = Identifier.make(commandText[3]);
            }
            else{
                throw new RuntimeException("NEED ID FOR REPORTER");
            }

            if(commandText[4].equals("NOTIFY")){
                int index = setOptionalComponents(commandText[5]);
                A_Reporter reporter;
                int value;
                switch(commandText[index]){
                    case "FREQUENCY":
                    
                        try{
                            value = Integer.parseInt(commandText[index + 1]);
                        }catch(Exception ex)
                        {
                            throw new RuntimeException("NEED A INTEGER VALUE");
                        }
                        
                        if(groups.size() < 1){
                            reporter = new ReporterFrequency(ids, value);
                        }
                        else{
                            reporter = new ReporterFrequency(ids, groups, value);
                        }
                        break;
                    
                    default:
                        try{
                            value = Integer.parseInt(commandText[index + 1]);
                        }catch(Exception ex)
                        {
                            throw new RuntimeException("NEED A INTEGER VALUE");
                        }
                        if(groups.size() < 1){
                            reporter = new ReporterChange(ids, value);
                        }
                        else{
                            reporter = new ReporterChange(ids, groups, value);
                        }
                        break;
                }
                        _reporterTable.add(reporterId, reporter);
            }
            
        }
        else{
            throw new RuntimeException("NOT A VALID TYPE");
        }
    
    }

    private int setOptionalComponents(final String comp)
    {
        int i = 5;
        if(commandText[i].equals("GROUPS") || commandText[i].equals("GROUP"))
        {
            i++;
            while(!commandText[i].equals("FREQUNCY") && !commandText[i].equals("DELTA") && !commandText[i].equals("IDS") && !commandText[i].equals("ID"))
            {
                this.groups.add(Identifier.make(commandText[i]));
                i++;
            }
        }
        if(commandText[i].equals("ID") || commandText[i].equals("IDS"))
        {
            i++;
            while(!commandText[i].equals("FREQUNCY") && !commandText[i].equals("DELTA") && !commandText[i].equals("GROUPS") && !commandText[i].equals("GROUP"))
            {
                this.ids.add(Identifier.make(commandText[i]));
                i++;
            }
        }
        return i;

    }
}
