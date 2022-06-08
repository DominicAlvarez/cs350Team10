package cs350s22.component.ui.parser;

import cs350s22.component.sensor.watchdog.A_Watchdog;
import cs350s22.component.sensor.watchdog.WatchdogAcceleration;
import cs350s22.component.sensor.watchdog.WatchdogBand;
import cs350s22.component.sensor.watchdog.WatchdogHigh;
import cs350s22.component.sensor.watchdog.WatchdogLow;
import cs350s22.component.sensor.watchdog.WatchdogNotch;
import cs350s22.component.sensor.watchdog.mode.A_WatchdogMode;
import cs350s22.component.sensor.watchdog.mode.WatchdogModeAverage;
import cs350s22.component.sensor.watchdog.mode.WatchdogModeInstantaneous;
import cs350s22.component.sensor.watchdog.mode.WatchdogModeStandardDeviation;
import cs350s22.support.Identifier;


/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

public class WatchdogComponent extends Component{

    protected String type;
    protected Identifier id;
    protected String mode;
    protected SymbolTable<A_Watchdog> _watchdogTable;
    protected A_WatchdogMode watchdogMode;
    protected int index; // keep track of what index we need to look at depending on mode given
    protected A_Watchdog watchdog;
    public WatchdogComponent(A_ParserHelper parserHelper, String[] commandText) 
    {
        super(parserHelper, commandText);
        type = null;
        id = null;
        mode = null;
        _watchdogTable = parserHelper.getSymbolTableWatchdog();
        watchdogMode = null;
        index = -1;
        watchdog = null;
        
        
    }

    
    public void action() {
        
        if(commandText.length >= 7){ // checking the minimum amount of info to create a watchdog
            
            type = commandText[2];
            
            if(!commandText[3].equals("MODE") && !commandText[3].equals(mode) && !commandText[3].equals("THREHOLD")){
                id = Identifier.make(commandText[3]);
            }
            else{
                throw new RuntimeException("CANNOT USE KEYWORD " + commandText[3] + " AS AN ID");
            }
            
            mode = commandText[5];
            watchdogMode = getMode(mode);
            

            /* =============ACCELERATION, BAND, and NOTCH have similar reuirements since they all require low and high values======== */
            if(type.equals("ACCELERATION") || type.equals("BAND") || type.equals("NOTCH")){
            
                if(commandText[index].equals("THRESHOLD") && commandText[index + 1].equals("LOW") && commandText[index + 3].equals("HIGH")){
                    
                    double lowVal = Double.valueOf(commandText[index + 2]);
                    double highVal = Double.valueOf(commandText[index + 4]);
                    if(index + 7 > commandText.length){ // check to see if there could possibly be a grace value
                        switch(type){
                            case "ACCELERATION":
                                watchdog  = new WatchdogAcceleration(lowVal, highVal, watchdogMode);
                                break;
                            
                            case "BAND":
                                watchdog = new WatchdogBand(lowVal, highVal,watchdogMode);
                                break;
                            
                            default:
                                watchdog = new WatchdogNotch(lowVal, highVal, watchdogMode);
                        }
                        
                    }
                    else{
                        if(commandText[index + 5].equals("GRACE")){
                            int grace = Integer.parseInt(commandText[index + 6]);
                            switch(type){
                                case "ACCELERATION":
                                    watchdog  = new WatchdogAcceleration(lowVal, highVal, watchdogMode, grace);
                                    break;
                                
                                case "BAND":
                                    watchdog = new WatchdogBand(lowVal, highVal,watchdogMode, grace);
                                    break;
                                
                                default:
                                    watchdog = new WatchdogNotch(lowVal, highVal, watchdogMode, grace);
                            }
                        }
                        else{
                            throw new RuntimeException("UNKOWN VALUE ENTERED EXPECTED GRACE.");
                        }
                        
                    }
                }
                else{
                    throw new RuntimeException("Either missing THRESHOLD LOW or HIGH Keyword.");
                }
            }
            /*================LOW and HIGH only require one value with no keywords after threshold unlike the other watchdog types ================== */
            else if(type.equals("LOW") || type.equals("HIGH")){
                if(commandText[index].equals("THRESHOLD")){
                    double value = Double.parseDouble(commandText[index + 1]);
                    if(index + 4 > commandText.length){ // no grace value becuase we checked the index of threshold + (the number where the grace value would be) to see if there could possibly be a grace value
                        switch(type){
                            case "LOW":
                            watchdog = new WatchdogLow(value, watchdogMode);
                            break;
                            
                            default: // we know this would be high because we already checked in the else if statement if the type was LOW or HIGH
                            watchdog = new WatchdogHigh(value, watchdogMode);
                        }
                    }

                    else{ // there is possibly a grace value
                        if(commandText[index + 2].equals("GRACE")){
                            int grace = Integer.parseInt(commandText[index + 3]);
                            switch(type){
                                case "LOW":
                                watchdog = new WatchdogLow(value, watchdogMode, grace);
                                break;
                                
                                default: // we know this would be high because we already checked in the else if statement if the type was LOW or HIGH
                                watchdog = new WatchdogHigh(value, watchdogMode, grace);
                            }

                        }
                    }

                }
                else{
                    throw new RuntimeException("EXPECTED THRESHOLD");
                }
            }

            else{
                throw new RuntimeException("NOT A VALID WATCHDOG TYPE");
            }
        
        }
        else
        {
            throw new RuntimeException("Not enough information has been passed to create watchdog.");
        }

        _watchdogTable.add(id, watchdog);
        
    }

    public A_WatchdogMode getMode(String mode){

        // instantaneous
        if(mode.equals("INSTANTANEOUS")){
            index = 6;
            return new WatchdogModeInstantaneous();
        }

        // average mode 
        else if(mode.equals("AVERAGE")){
            
            if(!commandText[6].equals("THRESHOLD")){
                index = 7;
                int avgVal = Integer.parseInt(commandText[6]);
                return new WatchdogModeAverage(avgVal);
            }

            index = 6;
            return new WatchdogModeAverage();
        }

        // standard deviation mode
        else if(mode.equals("STANDARD")){
            if(commandText[6].equals("DEVIATION")){
                if(!commandText[7].equals("THRESHOLD")){
                    index = 8;
                    int stdev = Integer.parseInt(commandText[7]);
                    return new WatchdogModeStandardDeviation(stdev);
                }

                index = 7;
                return new WatchdogModeStandardDeviation();
                
            }
            else{
                throw new RuntimeException("Did you mean STANDARD DEVIATION?");
            }
        }
        else{
            throw new RuntimeException("NOT A VALID WATCHDOG MODE.");
        }
    }

    


    
    
    
}
