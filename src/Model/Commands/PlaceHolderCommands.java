package Model.Commands;

import java.util.LinkedList;
import java.util.Map;

public class PlaceHolderCommands implements Command {


    @Override
    public LinkedList<Command> undoCommand(LinkedList<Command> commands) {
        return commands;
    }

    @Override
    public LinkedList<Command> redoCommand(LinkedList<Command> commands) {
        return commands;
    }

    @Override
    public LinkedList<Command> performCommand(LinkedList<Command> commands, Map<String, Object> params) {
        return commands;
    }
}
