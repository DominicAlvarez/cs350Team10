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
      
      
      startup.parse("@EXIT");
      

      
      
      
      // run your tests like this
      
      
   }
   
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   private void parse(final String parse) throws Exception{
      System.out.println("PARSE> "+ parse);
      
      Parser parser = new Parser(_parserHelper, parse);
      parser.parse();
       _parserHelper.schedule(parse);
   }
}