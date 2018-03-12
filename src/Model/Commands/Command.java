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
        commandHistory.addLast(c);
        setChanged();
        notifyObservers(commandHistory);
    }

    final protected static int findFirstOccurance(double x,double y){
        Shape testShape;
        int i  = 0;
        for(Command c: reverseCommandList((LinkedList<Command>) Command.getCommandHistory().clone())){
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

    final public void undoCommand(int index){
        try {
        Command temp = commandHistory.get(commandHistory.size()-index);
            undoneCommandHistory.addLast((Command) commandHistory.getLast().clone());
            //commandHistory.set(commandHistory.size()-index,new PlaceHolderCommand()); //remove
            commandHistory.removeLast();
        if(temp instanceof DeleteDrawCommand){
            commandHistory.set(((DeleteDrawCommand) temp).getIndex(),
                    (DrawCommand)((DeleteDrawCommand) temp).getDeletedCommand());
        }else if(temp instanceof EditDrawCommand){
            commandHistory.set(((EditDrawCommand) temp).getIndex(),new DrawCommand(((EditDrawCommand) temp).getPrevShape()));
        }
        setChanged();
        notifyObservers(commandHistory);
        } catch (IndexOutOfBoundsException|NoSuchElementException |CloneNotSupportedException e) {
            System.out.println("Bad action.");
        }
    }

    final public void redoCommand() {
        try {
            System.out.println(":)))))"+undoneCommandHistory.size());
            Command temp = undoneCommandHistory.getLast();
            undoneCommandHistory.removeLast(); //remove
            if(temp instanceof DeleteDrawCommand){
                System.out.println("del");
                //commandHistory.set(((DeleteDrawCommand) temp).getIndex(),new PlaceHolderCommand()); //remove
                commandHistory.removeLast();
            }else if (temp instanceof EditDrawCommand){
                System.out.println("edit");
                ((DrawCommand)commandHistory.get(((EditDrawCommand) temp).getIndex())).setShape(((EditDrawCommand) temp).getNewShape());
            }
            else {
                addCommand(temp);
            }
            setChanged();
            notifyObservers(commandHistory);
        } catch (NoSuchElementException e) {
            System.out.println("Cant redo more");
        }
    }


    private static LinkedList<Command> reverseCommandList(LinkedList<Command> list){
        System.out.println("l1: "+commandHistory.size());
        LinkedList<Command> rList = new LinkedList<>();
        while(!list.isEmpty())
            rList.addLast(list.pop());
        System.out.println("l2: "+commandHistory.size());
        return rList;
    }
}
