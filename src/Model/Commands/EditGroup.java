package Model.Commands;

import Model.Factory.FactoryProducer;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EditGroup implements Command {
    private LinkedList<EditDrawCommand> groupCommands;

    public EditGroup(){
        groupCommands=new LinkedList<>();
    }

    public EditGroup(LinkedList<EditDrawCommand> groupCommands) {
        this.groupCommands = groupCommands;
    }

    @Override
    public LinkedList<Command> undoCommand(LinkedList<Command> commands) {
        for(EditDrawCommand c:groupCommands){
            commands.set(c.getIndex(),new DrawCommand(c.getPrevShape()));
        }
        return commands;
    }

    @Override
    public LinkedList<Command> redoCommand(LinkedList<Command> commands) {
        for(EditDrawCommand c:groupCommands){
            commands.set(c.getIndex(),new DrawCommand(c.getNewShape()));
        }
        commands.addLast(this);
        return commands;
    }


    @Override
    public LinkedList<Command> performCommand(LinkedList<Command> commands, Map<String, Object> params) {
        Shape testShape;
        CommandFactory cmdF= (CommandFactory) FactoryProducer.getInstance().createFactory("COMMAND");
        int i  = 0;
        for(Command c: commands){
            if(c instanceof DrawCommand) {
                testShape = ((DrawCommand) c).getShape();
                if (((double)params.get("XBEGIN")) <= testShape.getX() && (testShape.getX()<=((double)params.get("XEND")))) {
                    if (((double)params.get("YBEGIN")) <= testShape.getY() && testShape.getY() <= ((double)params.get("YEND"))) {
                        Shape newShape = ShapeFactory.createShape((String)params.get("SHAPE"), testShape.getX(), testShape.getY(), (int) params.get("SIZE"),(int) params.get("SIZE"),(boolean) params.get("FILL"),(String) params.get("COLOR"));
                        ((DrawCommand) c).setShape(newShape);
                        Map<String,Object> eParams = new HashMap();
                        eParams.put("OLDSHAPE",testShape);
                        eParams.put("INDEX",i);
                        eParams.put("NEWSHAPE",newShape);
                        groupCommands.add((EditDrawCommand)cmdF.createCommand("EDIT",eParams));
                    }
                }
            }
            i++;
        }
        commands.addLast(this);
        return commands;
    }
}
