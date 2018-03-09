package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

abstract public class Command extends Observable implements Cloneable{

    private LinkedList<Command> commandHistory = new LinkedList<>();

    private LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public LinkedList<Command> getCommandHistory() {
        return commandHistory;
    }

    public void addCommand(Command c){
        commandHistory.add(0,c);
        notifyObservers();
    }

    public void undoCommand(){
        try {
            undoneCommandHistory.add((Command) commandHistory.getFirst().clone());
        } catch (CloneNotSupportedException e) {
            System.out.println("This action can't be redone.");
        }
        commandHistory.removeFirst();
    }
}
