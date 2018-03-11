package Model;

import Model.Commands.Command;
import Model.Commands.DrawCommand;

import java.util.List;
import java.util.Map;

abstract public class Application {
    protected Command commands;

    public Command getCommands() {
        return commands;
    }

    public Application(){
        commands = new Command();
    }

    public void clearApplication(){
        commands.clearCommands();
    }

    public abstract void addCommand(String command, Map params);

    public void addCommand(Command c){
        if(c != null){
            commands.addCommand(c);
        }
    }

    @Override
    public String toString(){
        String s = "";
        for(Command c : Command.getCommandHistory()){
            if(c instanceof DrawCommand){
                s += ((DrawCommand) c).getShape().getWidth() + " "
                        + ((DrawCommand) c).getShape().getHeight() + " "
                        + ((DrawCommand) c).getShape().getX() + " "
                        + ((DrawCommand) c).getShape().getY() + " "
                        + ((DrawCommand) c).getShape().getIsFilled() + " "
                        + ((DrawCommand) c).getShape().getRgbColorCode();
            }
        }
        return s;
    }

    public void undoCommand(){
        commands.undoCommand();
    }

    public void redoCommand(){
        commands.redoCommand();
    }

    public abstract void saveWorld(String fileName);

   public abstract void openWorld(String fileName);


}
