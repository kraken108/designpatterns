package Model.Commands;

public class DeleteDrawCommand extends DrawCommand {

    private Command deletedCommand;
    private int index;

    public void deleteDrawCommand(double x,double y){
        try {
            index = findFirstOccurance(x, y);
            deletedCommand = Command.getCommandHistory().get(index);
            Command.getCommandHistory().remove(index);
            Command.getCommandHistory().add(0,);
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

}
