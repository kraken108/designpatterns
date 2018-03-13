package Model.Commands;

import Model.Factory.FactoryProducer;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that deletes a drawcommand
 */
public class DeleteDrawCommand extends Command {

    private Command deletedCommand;
    private int index;

    public Command getDeletedCommand() {
        return deletedCommand;
    }

    public int getIndex() {
        return index;
    }

    public DeleteDrawCommand(Command c, int index){
        deletedCommand = c;
        this.index = index;
    }

    public DeleteDrawCommand(){
        index = 0;
        deletedCommand = null;
    }

    /**
     * @param x Mouseclikc x
     * @param y Mouse clikc Y
     * Deletes a command by removing the draw command and placing a placeholder so that the index is
     *  kept if you want to revert.
     */
    public void deleteDrawCommand(double x,double y){
        try {
            int i = findFirstOccurance(x, y);
            if(i==-1){
                return;
            }
            CommandFactory cmd = (CommandFactory)FactoryProducer.getInstance().createFactory("COMMAND");
            Map<String,Object> params = new HashMap();
            params.put("REMOVEDCOMMAND",Command.getCommandHistory().get(i));
            params.put("INDEX",i);
            Command.getCommandHistory().set(i,cmd.createCommand("PLACEHOLDER",null)); // remove
            Command.getCommandHistory().addLast(cmd.createCommand("DELETE",params));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

}
