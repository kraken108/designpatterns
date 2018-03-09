package Model.Commands;

import java.util.LinkedList;

abstract public class Command implements Cloneable{

    private static LinkedList<Command> commandHistory = new LinkedList<>();

    private static LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public static LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public static LinkedList<Command> getCommandHistory() {
        return commandHistory;
    }

    private static void addCommand(Command c){
        commandHistory.add(0,c);
    }

    private static void undoCommand(){
        try {
            undoneCommandHistory.add((Command) commandHistory.getFirst().clone());
        } catch (CloneNotSupportedException e) {
            System.out.println("This action can't be redone.");
        }
        commandHistory.removeFirst();
    }
}
