package Model.Commands;

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

    public void deleteDrawCommand(double x,double y){
        try {
            int i = findFirstOccurance(x, y);
            if(i==-1){
                return;
            }
            Command rCom = Command.getCommandHistory().get(i);
            Command.getCommandHistory().set(i,new PlaceHolderCommand()); // remove
            Command.getCommandHistory().addLast(new DeleteDrawCommand(rCom,i));
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

}
