package Model.Commands;

import java.util.LinkedList;
import java.util.Observable;

public class Command extends Observable implements Cloneable{

    private static LinkedList<Command> commandHistory = new LinkedList<>();

    private static LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public static LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public static LinkedList<Command> getCommandHistory() {
        return commandHistory;
    }

    final public void addCommand(Command c){
        commandHistory.addFirst(c);
        for(Command cmd : commandHistory){

        }
        setChanged();
        notifyObservers(commandHistory);
    }

    final public void undoCommand(){
        try {
            undoneCommandHistory.addFirst((Command) commandHistory.getFirst().clone());
        } catch (CloneNotSupportedException e) {
            System.out.println("This action can't be redone.");
        }
        commandHistory.removeFirst();
        setChanged();
        notifyObservers(commandHistory);
    }

    final public void redoCommand(){
        addCommand(undoneCommandHistory.pop());
        setChanged();
        notifyObservers(commandHistory);
    }

}
