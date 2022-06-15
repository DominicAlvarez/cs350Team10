package cs350s22.startup;

import cs350s22.component.logger.LoggerActuator;
import cs350s22.component.ui.parser.A_ParserHelper;
import cs350s22.component.ui.parser.Parser;
import cs350s22.component.ui.parser.ParserHelper;
import cs350s22.support.Filespec;

//=================================================================================================================================================================================
public class Startup
{
   private final A_ParserHelper _parserHelper = new ParserHelper();
   
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   public Startup()
   {
      System.out.println("Welcome to your Startup class");
      LoggerActuator.initialize(Filespec.make("blah"));
      
   }

   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] arguments) throws Exception
   {
      Startup startup = new Startup();
      

      startup.parse("@CONFIGURE LOG \"a.txt\" DOT SEQUENCE \"b.txt\" NETWORK \"c.txt\" XML \"d.txt\"");
      
      //startup.parse("CREATE ACTUATOR LINEAR myActuator1 ACCELERATION LEADIN 0.1 LEADOUT -0.2 RELAX 0.3 VELOCITY LIMIT 5 VALUE MIN 1 MAX 10 INITIAL 2 JERK LIMIT 3");
      
      startup.parse("CREATE MAPPER myMapper1 EQUATION SCALE 1");

      startup.parse("CREATE REPORTER CHANGE myReporter1 NOTIFY IDS myActuator1 myActuator2 DELTA 3");

      startup.parse("CREATE SENSOR POSITION mySensor8 GROUP myGroup1 REPORTERS myReporter1 MAPPER myMapper1");

      startup.parse("BUILD NETWORK WITH COMPONENT mySensor8");

      startup.parse("SET SENSOR mySensor8 VALUE 35");
      
      startup.parse("GET SENSOR mySensor8 VALUE");

      startup.parse("@EXIT");
      

      
      
      
      // run your tests like this
      
      
   }
   
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   private void parse(final String parse) throws Exception{
      System.out.println("PARSE> "+ parse);
      
      //Parser parser = new Parser(_parserHelper, parse);
      //parser.parse();
       _parserHelper.schedule(parse);
   }
}