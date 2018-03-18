package Model;

import Model.Commands.Command;
import Model.Commands.CommandFactory;
import Model.Commands.DrawCommand;
import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;

import java.util.*;

/**
 * Application describes how a general application should work.
 */
abstract public class Application extends Observable implements ApplicationI {

    CommandFactory cFac;

    protected static LinkedList<Command> commandHistory = new LinkedList<>();

    protected static LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public static LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public Application(){

    }

    public static LinkedList<Command> getCommandHistory() {
        return commandHistory;
    }
    /**
     * Clears all the commands in both commandhistory and undonecommandhistory
     * and notifies the observes.
     */
    final public void clearCommands(){
        commandHistory.clear();
        undoneCommandHistory.clear();
    }

    /**
     * Undoes a command by removing it from the commandhistory list
     * and saves that command to the undone command list so that you can redo it.
     * Notifies observer.
     */
    @Override
    public void undoCommand(){
        try {
            Command temp = commandHistory.get(commandHistory.size()-1);
            System.out.println(temp);
            undoneCommandHistory.addLast((Command) commandHistory.getLast());
            commandHistory = temp.undoCommand((LinkedList<Command>)commandHistory.clone());
            commandHistory.removeLast();
            setChanged();
            notifyObservers(commandHistory);
        } catch (IndexOutOfBoundsException|NoSuchElementException e) {
            System.out.println("Bad action.");
        }
    }

    /**
     * Redos command by taking them from undoneCommandHistory and putting them in
     * commandhistory at their correct index again.
     */
    @Override
    public void redoCommand() {
        try {
            Command temp = undoneCommandHistory.getLast();
            commandHistory = temp.redoCommand(commandHistory);
            undoneCommandHistory.removeLast(); //remove
            setChanged();
            notifyObservers(commandHistory);
        } catch (NoSuchElementException e) {
            System.out.println("Cant redo more");
        }
    }

    @Override
    public void addCommand(String name, Map<String, Object> params) {
        Command c = cFac.createCommand(name,params);
            commandHistory = c.performCommand((LinkedList<Command> )commandHistory.clone(),params);
        setChanged();
        notifyObservers(commandHistory);
    }

    /**
     * @param x mouseclick X
     * @param y mouseclick Y
     * @returns the integer for the first found object
     * Find the first occurance of an object in the commandhistory list.
     */
     static public int findFirstOccurance(double x,double y){
        Shape testShape;
        int i  = 0;
        for(Command c: reverseCommandList(getCommandHistory())){
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

    /**
     * @param list input list to reverse
     * @return a reversed list
     * Returns a reversed list so that you can take the element on top on the canvas
     */
    public static LinkedList<Command> reverseCommandList(LinkedList<Command> list){
        LinkedList<Command> rList = new LinkedList<>();
        while(!list.isEmpty())
            rList.addLast(list.pop());
        return rList;
    }
    /**
     * Constructor
     */
    public Application(Observer o) {
        cFac =(CommandFactory) FactoryProducer.getInstance().createFactory("Command");
        addObserver(o);
    }

    /**
     * Clears the history of all commands in this application.
     */
    public void clearApplication() {
        clearCommands();
    }

    /**
     * Saves the application to file.
     * @param fileName The filename to write application data to.
     */
    public abstract void saveWorld(String fileName);

    /**
     * Loads an application from file to the model.
     * @param fileName The filename to read from.
     */
    public abstract void openWorld(String fileName);


}
