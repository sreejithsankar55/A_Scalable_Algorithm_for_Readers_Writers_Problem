import java.rmi.*;

public interface Distributed extends Remote{
    public void readingControl(String i,String val) throws RemoteException;
    public void stopReading(String i) throws RemoteException;
    public void writing(String input) throws RemoteException;
    public void reading(String i) throws RemoteException;
    public void lock() throws RemoteException;
    public void release() throws RemoteException;
}
