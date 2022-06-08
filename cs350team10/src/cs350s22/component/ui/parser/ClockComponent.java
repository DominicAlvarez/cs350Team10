package cs350s22.component.ui.parser;

import cs350s22.support.Clock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tianyang Liao
 * @course CSCD 350
 * @Description meta component clock
 * @create 2022-06-04 15:10
 */
public class ClockComponent extends Component {
    private Clock clock;

    public ClockComponent(A_ParserHelper parserHelper, String[] commandText) {
        super(parserHelper, commandText);
        clock = Clock.getInstance();
    }

    @Override
    void action() {
        if (commandText.length == 1) {
            System.out.println(clock.getTime()); // cmd 7 in meta cmd
        } else if (commandText[1].equals("PAUSE") || commandText[1].equals("RESUME")) {
            _pauseResumeHandler();
        } else if (commandText[1].equals("ONESTEP")) {
            _oneStepHandler();
        } else if (commandText[1].equals("SET") && commandText[2].equals("RATE")) {
            _setRateHandler();
        } else if (commandText[1].equals("WAIT")) {
            _waitHandler();
        } else {
            throw new RuntimeException("WRONG CLOCK COMMAND INPUT");
        }


    }

    private void _pauseResumeHandler() {
        if (commandText.length != 2) {
            throw new RuntimeException("PAUSE OR RESUME COMMAND DOES NOT SUPPORT ANY OTHER PARAMETERS");
        }

        if (commandText[1].equals("PAUSE"))
            clock.isActive(false);
        else
            clock.isActive(true);
    }

    private void _oneStepHandler() {
        if (commandText.length == 2) {
            clock.onestep();
        } else if (commandText.length == 3) {
            if (!isNumeric(commandText[2], 1))
                throw new RuntimeException("PARAMETER FOR ONESTEP COMMAND SHALL BE A INTEGER!");
            clock.onestep(Integer.parseInt(commandText[2]));

        } else {
            throw new RuntimeException("ONESTEP COMMAND ONLY SUPPORTS ZERO OR ONE PARAMETER!");
        }

    }

    private void _setRateHandler() {
        if (commandText.length != 4) {
            throw new RuntimeException("SET RATE COMMAND ONLY SUPPORTS ONE PARAMETER!");
        }

        if (!isNumeric(commandText[3], 1))
            throw new RuntimeException("PARAMETER FOR SET RATE COMMAND SHALL BE A INTEGER!");

        clock.setRate(Integer.parseInt(commandText[3]));

    }


    private void _waitHandler() {
        if (commandText.length != 4) {
            throw new RuntimeException("WAIT COMMAND ONLY SUPPORTS TWO PARAMETERS!");
        }

        if (!isNumeric(commandText[3], 2)) {
            throw new RuntimeException("WAIT COMMAND ONLY SUPPORTS FLOATING NUMBER INPUT AS LAST PARAMETER!");
        }

        if (commandText[2].equals("FOR")) {
            clock.waitFor(Double.parseDouble(commandText[3]));
        } else if (commandText[2].equals("UNTIL")) {
            clock.waitUntil(Double.parseDouble(commandText[3]));
        } else {
            throw new RuntimeException("WAIT COMMAND SHALL CONTAIN FOR OR UNTIL!");
        }

    }

    public static boolean isNumeric(String str, int option) { // check if the given string is a number (NOT include negative number)
        if (option == 1) { // check whether it's a integer
            if (str == null) {
                return false;
            }
            int sz = str.length();
            for (int i = 0; i < sz; i++) {
                if (Character.isDigit(str.charAt(i)) == false) {
                    return false;
                }
            }
        } else { // check whether it's a floating number
            Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        }
        return true;
    }


}