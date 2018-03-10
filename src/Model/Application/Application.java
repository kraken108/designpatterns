package Model.Application;

import Model.Commands.Command;

import java.util.ArrayList;

abstract public class Application {
    protected Command commands;

    public Command getCommands() {
        return commands;
    }

    public Application(){
        commands = new Command();
    }

    public void addCommand(Command c){
        if(c != null){
            commands.addCommand(c);
        }
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
