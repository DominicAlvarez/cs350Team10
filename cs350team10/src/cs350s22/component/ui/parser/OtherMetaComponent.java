package cs350s22.component.ui.parser;

import cs350s22.component.logger.LoggerMessage;
import cs350s22.component.logger.LoggerMessageSequencing;
import cs350s22.support.Filespec;

import java.io.IOException;

/**
 * @author Tianyang Liao
 * @course CSCD 350
 * @Description Other meta components including exit, run, and configure
 * @create 2022-06-04 15:19
 */
public class OtherMetaComponent extends Component{
    public OtherMetaComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
    }

    
    public void action() {
        if (commandText[0].toUpperCase().equals("@EXIT"))
            _exitHandler();
        else if (commandText[0].toUpperCase().equals("@RUN"))
            _runHandler();
        else
            _configureHandler();

    }

    private void _exitHandler() {
        if (commandText.length != 1) // check command length
            throw new RuntimeException("EXIT COMMAND DOES NOT SUPPORT ANY PARAMETERS!");

        parserHelper.exit();
    }


    private void _runHandler() {
        if (commandText.length != 2) // check command length
            throw new RuntimeException("RUN COMMAND ONLY SUPPORT ONE PARAMETER!");

        // not sure where to handle exception
        try {
            parserHelper.run(commandText[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _configureHandler() {
        if (commandText.length != 10) // check command length
            throw new RuntimeException("CONFIGURE COMMAND ONLY SUPPORT TEN PARAMETER!");

        // make sure every parameter is typed correctly
        String[] expectedCmd = {"LOG", "DOT", "SEQUENCE", "NETWORK", "XML"};
        int[] expectedIndex = {1, 3, 4, 6, 8};
        for (int i = 0; i < expectedIndex.length; i++) {
            if (!commandText[expectedIndex[i]].equals(expectedCmd[i]))
                throw new RuntimeException("CONFIGURE COMMAND INDEX " + expectedIndex[i] + " IS EXPECTED AS " + expectedCmd[i]);
        }

        // initialize logger message with provided parameters
        // again, not sure where to handle errors
        try {
            LoggerMessage.initialize(Filespec.make(commandText[2]));
            commandText[5] = commandText[5].replace("\"", ""); // get rid of quotation marks
            commandText[7] = commandText[7].replace("\"", "");
            LoggerMessageSequencing.initialize(Filespec.make(commandText[5]), Filespec.make(commandText[7]));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}