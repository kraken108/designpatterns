package Model;


import Model.Commands.*;
import Model.Factory.FactoryProducer;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;
import Model.Shapes.Shape;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * DrawApplication is an implementation of Application. Describing application specific methods.
 */
public class DrawApplication extends Application {

    /**
     * Get available shapes from the model.
     * @return A list of all available shapes.
     */
    public String[] getAvailableShapes(){
        return FactoryProducer.getInstance().createFactory("SHAPE").getShapes();
    }

    private DrawCommand drawCommand = new DrawCommand();
    private DeleteDrawCommand deleteDrawCommand = new DeleteDrawCommand();
    private EditDrawCommand editDrawCommand = new EditDrawCommand();

    private LinkedList<Command> commandHistory = new LinkedList<>();

    private LinkedList<Command> undoneCommandHistory = new LinkedList<>();

    public  LinkedList<Command> getUndoneCommandHistory() {
        return undoneCommandHistory;
    }

    public  LinkedList<Command> getCommandHistory() {
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
    public void undoCommand(){
        try {
            Command temp = commandHistory.get(commandHistory.size()-1);
            undoneCommandHistory.addLast((Command) commandHistory.getLast());
            commandHistory.removeLast();
            /*if(temp instanceof DeleteDrawCommand){
                commandHistory.set(((DeleteDrawCommand) temp).getIndex(),
                        (DrawCommand)((DeleteDrawCommand) temp).getDeletedCommand());
            }else if(temp instanceof EditDrawCommand){
                commandHistory.set(((EditDrawCommand) temp).getIndex(),new DrawCommand(((EditDrawCommand) temp).getPrevShape()));
            }
            setChanged();
            notifyObservers(commandHistory);*/
        } catch (IndexOutOfBoundsException|NoSuchElementException  e) {
            System.out.println("Bad action.");
        }
    }

    /**
     * Redos command by taking them from undoneCommandHistory and putting them in
     * commandhistory at their correct index again.
     */
    public void redoCommand() {
        try {
            System.out.println(":)))))"+undoneCommandHistory.size());
            Command temp = undoneCommandHistory.getLast();
            undoneCommandHistory.removeLast(); //remove
           /* if(temp instanceof DeleteDrawCommand){
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
            notifyObservers(commandHistory);*/
        } catch (NoSuchElementException e) {
            System.out.println("Cant redo more");
        }
    }
    /**
     * @param c the iinput command
     * Adds a command to the commandhistory list and then ontifies observers.
     */
    final public void addCommand(Command c){
        commandHistory.addLast(c);
    }



    /**
     * @param x mouseclick X
     * @param y mouseclick Y
     * @returns the integer for the first found object
     * Find the first occurance of an object in the commandhistory list.
     */
    final protected int findFirstOccurance(double x,double y){
        Shape testShape;
        int i  = 0;
        for(Command c: reverseCommandList((LinkedList<Command>) getCommandHistory())){
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
     * Get a Map representation of all shapes stored in the model.
     * @return
     */
    public List<Map> getShapes(){
        List<Map> shapes = new ArrayList<>();
        for(Command c : getCommandHistory()){
            if(c instanceof DrawCommand){
                Shape s = ((DrawCommand) c).getShape();
                Map<String,Object> map = new HashMap<>();
                map.put("NAME",s.getClass().getSimpleName());
                map.put("X",s.getX());
                map.put("Y",s.getY());
                map.put("WIDTH",s.getWidth());
                map.put("HEIGHT",s.getHeight());
                map.put("FILLED",s.getIsFilled());
                map.put("COLOR",s.getColor());
                shapes.add(map);
            }
        }
        return shapes;
    }

    /**
     * Empty constructor
     */
    public DrawApplication(){
    }

    /**
     * Adds a new command to the application
     * @param command Name of command to be added.
     * @param params  Attributes the command should possess.
     */
    @Override
    public void addCommand(String command, Map params) {
        super.addCommand(FactoryProducer.getInstance().createFactory("COMMAND").createCommand(command,params));
    }

    /**
     * Edits a draw command.
     * @param x x value
     * @param y y value
     * @param shape the shape
     * @param size the size
     * @param fill is it filled
     * @param color color of shape
     */
    public void editDrawCommand(double x, double y,String shape,int size, boolean fill,String color){
        editDrawCommand.editDrawCommand(x,y,shape,size,fill,color);
    }

    /**
     * Removes a draw command at position
     * @param x x value
     * @param y y value
     */
    public void deleteDrawCommand(double x, double y){
        deleteDrawCommand.deleteDrawCommand(x,y);
    }

    /**
     * Constructor taking an observer, sends it to commands.
     * @param o The observer
     */
    public DrawApplication(Observer o) {
        super.commands.addObserver(o);
        drawCommand.addObserver(o);
        deleteDrawCommand.addObserver(o);
        editDrawCommand.addObserver(o);
    }

    /**
     * Saves application to file.
     * @param fileName The filename to write application data to.
     */
    @Override
    public void saveWorld(String fileName){
        Document s = new DrawDocument();
        try {
            s.saveDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an application from file.
     * @param fileName The filename to read from.
     */
    @Override
    public void openWorld(String fileName){
        Document s = new DrawDocument();
        try {
            System.out.println("Trying to open: " + fileName);
            s.openDocument(fileName,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param list input list to reverse
     * @return a reversed list
     * Returns a reversed list so that you can take the element on top on the canvas
     */
    private LinkedList<Command> reverseCommandList(LinkedList<Command> list){
        LinkedList<Command> rList = new LinkedList<>();
        while(!list.isEmpty())
            rList.addLast(list.pop());
        return rList;
    }

    /**
     * Generates a String representation of draw application.
     * @return String represenation of draw application.
     */
    @Override
    public String toString(){
        String s = "";
        for(Command c : getCommandHistory()){
            if(c instanceof DrawCommand){
                s += ((DrawCommand) c).getShape().getWidth() + " "
                        + ((DrawCommand) c).getShape().getHeight() + " "
                        + ((DrawCommand) c).getShape().getX() + " "
                        + ((DrawCommand) c).getShape().getY() + " "
                        + ((DrawCommand) c).getShape().getIsFilled() + " "
                        + ((DrawCommand) c).getShape().getColor();
            }
        }
        return s;
    }
}
