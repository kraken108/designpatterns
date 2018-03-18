package Model.Commands;

import Model.Application;
import Model.Factory.FactoryProducer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

/**
 * A class that deletes a drawcommand
 */
public class DeleteDrawCommand extends Observable implements Command {

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


    @Override
    public LinkedList<Command> undoCommand(LinkedList<Command> commands) {
        commands.set(this.getIndex(),
                (DrawCommand)this.getDeletedCommand());
        return commands;
    }

    @Override
    public LinkedList<Command> redoCommand(LinkedList<Command> commands) {
        commands.removeLast();
        return commands;
    }

    @Override
    public LinkedList<Command> performCommand(LinkedList<Command> commands,Map<String, Object> params) {
        try {
            int i = Application.findFirstOccurance((double)params.get("X"),(double) params.get("Y"));
            if(i==-1){
                System.out.println("nofindy");
                return commands;
            }
            CommandFactory cmd = (CommandFactory)FactoryProducer.getInstance().createFactory("COMMAND");
            Map<String,Object> rParams = new HashMap();
            rParams.put("REMOVEDCOMMAND",commands.get(i));
            rParams.put("INDEX",i);
            commands.set(i,new PlaceHolderCommands()); // remove
            commands.addLast(cmd.createCommand("DELETE",rParams));
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("no object found");
        }
     return commands;
    }
}
