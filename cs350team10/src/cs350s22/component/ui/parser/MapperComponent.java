package cs350s22.component.ui.parser;

import java.io.IOException;
import cs350s22.component.sensor.mapper.A_Mapper;
import cs350s22.component.sensor.mapper.MapperEquation;
import cs350s22.component.sensor.mapper.MapperInterpolation;
import cs350s22.component.sensor.mapper.function.equation.EquationNormalized;
import cs350s22.component.sensor.mapper.function.equation.EquationPassthrough;
import cs350s22.component.sensor.mapper.function.equation.EquationScaled;
import cs350s22.component.sensor.mapper.function.interpolator.A_Interpolator;
import cs350s22.component.sensor.mapper.function.interpolator.InterpolationMap;
import cs350s22.component.sensor.mapper.function.interpolator.InterpolatorLinear;
import cs350s22.component.sensor.mapper.function.interpolator.InterpolatorSpline;
import cs350s22.component.sensor.mapper.function.interpolator.loader.MapLoader;
import cs350s22.support.Filespec;
import cs350s22.support.Identifier;


/**
  @author: Julian Diaz 
  {@summary} : CSCD 350 project Team 10
 **/

 
public class MapperComponent extends Component{

    protected Identifier id;
    protected String type;
    protected SymbolTable<A_Mapper> _mapperTable;


    public MapperComponent(A_ParserHelper parserHelper, String[] commandText)
    {
        super(parserHelper, commandText);
        id = null;
        type = null;
        _mapperTable = parserHelper.getSymbolTableMapper();
        
    }

    
    public void action() 
    {
        
        setID();
        setType();
        
    }

    public void setID()
    {
        String id = this.commandText[2];

        if(!id.equals("EQUATION") && !id.equals("INTERPOLATION"))
        {
            this.id = Identifier.make(id);
        }

        else
        {
            throw new RuntimeException("MUST ENTER AN ID");
        }
    }

    public void setType() 
    {

        String type = commandText[3];

        switch(type)
        {
            case "EQUATION":
                MapperEquation mapperEquation;
                switch(commandText[4])
                {
                    case "PASSTHROUGH":
                        EquationPassthrough equationPassthrough = new EquationPassthrough();
                        mapperEquation = new MapperEquation(equationPassthrough);
                        _mapperTable.add(id, mapperEquation);
                        break;
                    
                    case "SCALE":
                        try
                        {

                            Double val = Double.valueOf(commandText[5]);
                            EquationScaled equationScaled = new EquationScaled(val);
                            mapperEquation = new MapperEquation(equationScaled);
                            _mapperTable.add(id, mapperEquation);

                            
                        }catch(Exception ex)
                        {
                            throw new RuntimeException("NOT A CORRECT VALUE");
                        }
                        break;
                    
                    case "NORMALIZE":

                        try
                        {
                            Double val = Double.valueOf(commandText[5]);
                            Double val2 = Double.valueOf(commandText[6]);
                            EquationNormalized equationNormalized = new EquationNormalized(val, val2);
                            mapperEquation = new MapperEquation(equationNormalized);
                            _mapperTable.add(id, mapperEquation);
                        }catch(Exception ex)
                        {
                            throw new RuntimeException("EXPECTED 2 Values of type double got: " + commandText[5] + " "  + commandText[6]);
                        }
                        break;
                    
                    default:
                        throw new RuntimeException("EXPECTED PASSTHROUGH or SCALE or NORMALIZE. Got " + commandText[4]);
                }
                break; // break for EQUATION
            
            case "INTERPOLATION":
                String filename = commandText[5];
                MapLoader mapLoader = new MapLoader(Filespec.make(filename));
                InterpolationMap interpolationMap;
                try 
                {
                    interpolationMap = mapLoader.load();
                } catch (IOException e) 
                {
                    throw new RuntimeException("Not a valid filename");
                }
                A_Interpolator interpolator;
                switch(commandText[4])
                {
                    case "LINEAR":
                        interpolator = new InterpolatorLinear(interpolationMap);
                        break;
                    
                    case "SPLINE":
                        interpolator = new InterpolatorSpline(interpolationMap);
                        break;
                    
                    default:
                        throw new RuntimeException("EXPECTED LINEAR or SPLINE got: " + commandText[4]);
                }
                MapperInterpolation mapperInterpolation = new MapperInterpolation(interpolator);
                _mapperTable.add(id, mapperInterpolation);
                break; // break for INTERPOLATION
            
            default:
                throw new RuntimeException("NOT A VALID TYPE EXPECTED EQUATION OR INTERPOLATION GOT: " + type);
        }

    }


    
}