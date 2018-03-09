package Model.Application;

import Model.Commands.Command;

import java.util.ArrayList;

abstract public class Application {
    private ArrayList<Command> commands;

    public Application(){
        commands = new ArrayList<>();
    }

    public void addCommand(Command c){
        if(c != null){
            commands.add(c);
        }
    }

    public abstract void saveWorld(String fileName);

    public abstract void openWorld(String fileName);



    public ArrayList<Command> getCommands(){
        return commands;
    }
}
