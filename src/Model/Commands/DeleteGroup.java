package Model.Commands;

import Model.Application;
import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DeleteGroup implements Command {

    private LinkedList<DeleteDrawCommand> groupCommands;

    public DeleteGroup(){
        groupCommands=new LinkedList<>();
    }

    public DeleteGroup(LinkedList<DeleteDrawCommand> groupCommands) {
        this.groupCommands = groupCommands;
    }

    public LinkedList<Command> undoCommand(LinkedList<Command> commands) {
        for(DeleteDrawCommand c:groupCommands){
            commands.set(c.getIndex(),c.getDeletedCommand());
        }
        return commands;
    }

    @Override
    public LinkedList<Command> redoCommand(LinkedList<Command> commands) {
        for(DeleteDrawCommand c:groupCommands){
            commands.set(c.getIndex(),this);
        }
        commands.addLast(this);
        return commands;
    }

    @Override
    public LinkedList<Command> performCommand(LinkedList<Command> commands, Map<String, Object> params) {
        System.out.println("ja körs iasf");
        Shape testShape;
        CommandFactory cmdF= (CommandFactory)FactoryProducer.getInstance().createFactory("COMMAND");
        int i  = 0;
        for(Command c: commands){
            if(c instanceof DrawCommand) {
                testShape = ((DrawCommand) c).getShape();
                if (((double)params.get("XBEGIN")) <= testShape.getX() && (testShape.getX()<=((double)params.get("XEND")))) {
                    if (((double)params.get("YBEGIN")) <= testShape.getY() && testShape.getY() <= ((double)params.get("YEND"))) {
                        Map<String,Object> nParams = new HashMap<>();
                        nParams.put("INDEX",i);
                        nParams.put("REMOVEDCOMMAND",c);
                        groupCommands.add((DeleteDrawCommand)cmdF.createCommand("DELETE",nParams));
                        commands.set(i,new PlaceHolderCommands());
                    }
                }
            }
            i++;
        }
        commands.addLast(this);
        return commands;
    }

    public LinkedList<DeleteDrawCommand> getGroupCommands() {
        return groupCommands;
    }

    public void setGroupCommands(LinkedList<DeleteDrawCommand> groupCommands) {
        this.groupCommands = groupCommands;
    }
}
