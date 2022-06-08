package cs350s22.component.ui.parser;

/**
 * @author Tianyang Liao
 * @course CSCD 350
 * @Description
 * @create 2022-06-04 14:59
 */
public class MetaCommand extends Command{

    public MetaCommand(A_ParserHelper parserHelper, String[] commandText){
        super(parserHelper, commandText);
        this.componentName = commandText[0];
        this.component = createComponent(componentName);
    }

    @Override
    Component createComponent(String componentName) {
        switch(componentName) {
            case "@CLOCK":
                return new ClockComponent(parserHelper, commandText);
            case "@EXIT":
            case "@RUN":
            case "@CONFIGURE":
                return new OtherMetaComponent(parserHelper, commandText);
            default:
                throw new RuntimeException("INVALID COMPONENT " + componentName + " IN META COMMAND");

        }
    }


}