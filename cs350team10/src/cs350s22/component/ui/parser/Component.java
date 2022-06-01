package cs350s22.component.ui.parser;



public abstract class Component {
    protected String[] commandText;

    public Component(String[] commandText){
        this.commandText = commandText;
    }

    abstract void store();
}
