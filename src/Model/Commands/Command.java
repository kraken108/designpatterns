package Model.Commands;

import Model.Shapes.Shape;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;

public class Command extends Observable implements Cloneable{

    protected static LinkedList<Command> commandHistory = new LinkedList<>();

    protected static LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public static LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public static LinkedList<Command> getCommandHistory() {
        return commandHistory;
    }

    final public void clearCommands(){
        commandHistory.clear();
        undoneCommandHistory.clear();
        setChanged();
        notifyObservers(commandHistory);
    }

    final public void addCommand(Command c){
        commandHistory.addFirst(c);
        setChanged();
        notifyObservers(commandHistory);
    }

    final protected static int findFirstOccurance(double x,double y){
        Shape testShape;
        int i  = 0;
        for(Command c: Command.getCommandHistory()){
            if(c instanceof DrawCommand) {
                testShape = ((DrawCommand) c).getShape();
                if (x >= testShape.getX() && x <= testShape.getX() + testShape.getWidth()) {
                    if (y >= testShape.getY() && y <= testShape.getY() + testShape.getHeight()) {
                        return i;
                    }
                }
            }
            i++;
        }
        return -1;
    }

    final public void undoCommand(){
        try {
            undoneCommandHistory.addFirst((Command) commandHistory.getFirst().clone());

        Command temp = commandHistory.getFirst();
        commandHistory.removeFirst();
        if(temp instanceof DeleteDrawCommand){
            commandHistory.add(((DeleteDrawCommand) temp).getIndex(),
                    (DrawCommand)((DeleteDrawCommand) temp).getDeletedCommand());
        }else if(temp instanceof EditDrawCommand){
            ((DrawCommand) commandHistory.get(((EditDrawCommand) temp).getIndex())).setShape(((EditDrawCommand) temp).getPrevShape());
        }
        setChanged();
        notifyObservers(commandHistory);
        } catch (NoSuchElementException |CloneNotSupportedException e) {
            System.out.println("This action can't be redone.");
        }
    }

    final public void redoCommand() {
        try {
            Command temp = undoneCommandHistory.pop();
            if(temp instanceof DeleteDrawCommand){
                commandHistory.remove(((DeleteDrawCommand) temp).getIndex());
            }else if (temp instanceof EditDrawCommand){
                ((DrawCommand)commandHistory.get(((EditDrawCommand) temp).getIndex())).setShape(((EditDrawCommand) temp).getNewShape());
            }
            addCommand(temp);
            setChanged();
            notifyObservers(commandHistory);
        } catch (NoSuchElementException e) {
            System.out.println("Cant redo more");
        }
    }

}
