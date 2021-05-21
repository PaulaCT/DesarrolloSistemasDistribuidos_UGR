import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;

public interface icontador extends Remote {
	public boolean esCliente(int dni) throws RemoteException;
	public int registro(int dni, Registry reg) throws RemoteException, NotBoundException;
	public void registrarEnReplica(int dni) throws RemoteException;
	public boolean donar (int cliente, int donacion) throws RemoteException;
	public int clientesRegistrados() throws RemoteException;
	public int recaudadoReplica() throws RemoteException;
	public int recaudado(Registry reg) throws RemoteException, NotBoundException;
	public int donacionesCliente(int dni) throws RemoteException;
	public int indexGeneroso() throws RemoteException;
	public int whoIs(int indice) throws RemoteException;
	public String clienteMasGeneroso(Registry reg) throws RemoteException, NotBoundException;
}