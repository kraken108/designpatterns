package Model.Commands;

import Model.Shapes.Shape;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * A parent class for all commands which extends the observable class
 * so that the controller can update the view automatically when a change has occured.
 * Implements clonable for the design pattern prototype.
 */
public interface Command {

    void undoCommand();
    void redoCommand();
    void performCommand(LinkedList<Command> commands, Map<String,Object> params);

}
