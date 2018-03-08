package Model;

import java.util.ArrayList;

public class World {
    private ArrayList<Command> commands;

    public World(){
        commands = new ArrayList<>();
    }

    public void addCommand(Command c){
        if(c != null){
            commands.add(c);
        }

    }


}
