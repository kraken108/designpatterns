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
        System.out.println("del len: "+Command.getCommandHistory().size());
        try {
            index = findFirstOccurance(x, y);
            if(index==-1){
                System.out.println("kuken");
                return;
            }
            System.out.println("runko runkovich");
            Command rCom = Command.getCommandHistory().get(index);
            Command.getCommandHistory().remove(index);
            Command.getCommandHistory().add(0,new DeleteDrawCommand(rCom,index));
            System.out.println(":))");
            setChanged();
            notifyObservers(commandHistory);
        }catch(IndexOutOfBoundsException e){
            System.out.println("no object found");
        }
    }

}
