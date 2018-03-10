package Model.Application;

import Model.Command;
import Model.FileHandler.Document;
import Model.FileHandler.DrawDocument;

import java.io.FileNotFoundException;
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

    public abstract void saveWorld(String fileName);

   public abstract void openWorld(String fileName);


}
