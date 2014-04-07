package singleton;

public class MainInvokationEvent extends MainEvent {

    private String[] arguments;

    public MainInvokationEvent(String[] arguments) {
        super(MainEvent.Type.INVOKATION);
        this.arguments = arguments;
    }

    public String[] getArguments() {
        return arguments;
    }

}
