package cs350s22.startup;

import cs350s22.component.ui.parser.A_ParserHelper;
import cs350s22.component.ui.parser.Parser;
import cs350s22.component.ui.parser.ParserHelper;

import java.io.*;

//=================================================================================================================================================================================
public class Startup
{
   private final A_ParserHelper _parserHelper = new ParserHelper();
   
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   public Startup()
   {
      System.out.println("Welcome to your Startup class");
   }

   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] arguments) throws Exception
   {
      Startup startup = new Startup();

      // this command must come first. The filenames do not matter here
      startup.parse("@CONFIGURE LOG \"a.txt\" DOT SEQUENCE \"b.txt\" NETWORK \"c.txt\" XML \"d.txt\"");

      startup.parse("CREATE ACTUATOR LINEAR actuator0 ACCELERATION LEADIN 0.1 LEADOUT -0.2 RELAX 0.3 VELOCITY LIMIT 5 VALUE MIN 1 MAX 20 INITIAL 2 JERK LIMIT 3");
      // startup.parse("CREATE CONTROLLER FORWARDING myController2 WITH COMPONENTS actuator0");
      startup.parse("BUILD NETWORK WITH COMPONENT actuator0");
      // run your tests like this
      // startup.parse("SEND MESSAGE PING");
      metaTest(startup);

      // startup.parse("@EXIT");
   }
   
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   private void parse(final String parse) throws Exception
   {
      System.out.println("PARSE> "+ parse);
      
      Parser parser = new Parser(_parserHelper, parse);
      
      parser.parse();
   }

   private static void metaTest(Startup startup) throws Exception {
      startup.parse("@CLOCK");
      startup.parse("@CLOCK PAUSE");
      startup.parse("@CLOCK RESUME"); // comment this to see the program is paused
      startup.parse("@CLOCK SET RATE 1"); // comment @EXIT, then change to different rates to see different pace of updating time in console


      startup.parse("@CLOCK WAIT UNTIL 24444.5"); // this one doesn't work (this is a new command from v0.2, description for architecture binding is not provided)

      startup.parse("@CLOCK PAUSE");
      startup.parse("@CLOCK ONESTEP"); // one step is only valid when the clock is paused
      startup.parse("@CLOCK ONESTEP");
      startup.parse("@CLOCK ONESTEP 10");
      startup.parse("@CLOCK RESUME");

      startup.parse("@EXIT");

      // DON'T KNOW HOW TO TEST @RUN COMMAND SINCE IT REQUIRES .mvt file I DON'T KNOW DETAILS ABOUT THAT

   }
}
