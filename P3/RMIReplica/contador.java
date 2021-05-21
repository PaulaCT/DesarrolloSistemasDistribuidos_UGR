//import icontador.icontador;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class contador extends UnicastRemoteObject implements icontador {
	private int suma;
	private int replica;
	private ArrayList<Integer> clientes;
	private ArrayList<Integer> donaciones_clientes;

	public contador(int r) throws RemoteException {
		suma = 0;
		replica = r;
		clientes = new ArrayList();
		donaciones_clientes = new ArrayList();
	}

	public boolean esCliente(int dni) throws RemoteException {
		return clientes.contains(dni);
	}

	// Registra a un cliente por su DNI
	public int registro(int dni, Registry reg) throws RemoteException, NotBoundException {
		
		// Se registra el cliente en la réplica más vacía
		icontador otra;
		int index;
		int clientes_otra = 0;

		index = (replica + 1) % 2;
		otra = (icontador) reg.lookup(reg.list()[index]);

		// Si el cliente ya estuviera registrado en alguna réplica 
		// Opción comentada: el cliente puede iniciar sesión varias veces
		/*if (esCliente(dni)) return replica;
		else if (otra.esCliente(dni)) return index;*/
		// Opción no comentada: el cliente solo puede iniciar sesión una vez
		if (esCliente(dni) || otra.esCliente(dni)) return -1;

		// Si no, registramos al cliente en la réplica con menor número
		clientes_otra = otra.clientesRegistrados();
		if (clientes_otra < clientesRegistrados()) {
			otra.registrarEnReplica(dni);
			return index;
		} else registrarEnReplica(dni);

		// Ha tenido éxito, devolvemos la réplica
		return replica;
	}

	public void registrarEnReplica(int dni) throws RemoteException {
		clientes.add(dni);
		donaciones_clientes.add(0);
		System.out.println("El cliente " + dni + " ha sido registrado en " + replica + ".");
		System.out.print("Clientes registrados: ");
		for (int i=0; i<clientesRegistrados(); i++){
			System.out.print(clientes.get(i) +  " ");
		}
		System.out.println("");
	}

	public boolean donar (int cliente, int donacion) throws RemoteException {
		if (esCliente(cliente)){
			suma = suma + donacion;
			int indice = clientes.indexOf(cliente);
			donaciones_clientes.set(indice, donaciones_clientes.get(indice) + donacion);
			System.out.println("El cliente " + cliente + " ha donado " + donacion + ".");
			// En este servicio
			System.out.println("Ya van " + suma + "€ donados en réplica " + replica + ".");
			return true;
		}
		return false;
	}

	public int clientesRegistrados() throws RemoteException {
		return clientes.size();
	}

	public int recaudadoReplica() throws RemoteException {
		return suma;
	}

	public int recaudado(Registry reg) throws RemoteException, NotBoundException {
		icontador otra;
		int index;

		index = (replica + 1) % 2;
		otra = (icontador) reg.lookup(reg.list()[index]);

		return suma + otra.recaudadoReplica();
	}

	public int donacionesCliente(int dni) throws RemoteException {
		if (esCliente(dni)){
			int indice = clientes.indexOf(dni);
			int donaciones = donaciones_clientes.get(indice);
			if (donaciones != 0) return donaciones;
			else System.out.println("El cliente " + dni + " no ha realizado ninguna donacion.");
		}
		System.out.println(dni + " no es cliente.");
		return -1;
	}

	public int indexGeneroso() throws RemoteException {
		int max = 0;
		int indice = -1;
		for (int i = 0; i < clientesRegistrados(); i++){
			if (donaciones_clientes.get(i) > max){
				max = donaciones_clientes.get(i);
				indice = i;
			}
		}
		return indice;
	}

	public int whoIs(int indice) throws RemoteException {
		return clientes.get(indice);
	}

	public String clienteMasGeneroso(Registry reg) throws RemoteException, NotBoundException {
		icontador otra;
		int index;

		index = (replica + 1) % 2;
		otra = (icontador) reg.lookup(reg.list()[index]);

		int otra_generoso = otra.whoIs(otra.indexGeneroso());
		int otra_cantidad = otra.donacionesCliente(otra_generoso);

		int generoso = whoIs(indexGeneroso());
		int cantidad = donacionesCliente(generoso);

		if (otra_cantidad > cantidad){
			generoso = otra_generoso;
			cantidad = otra_cantidad;
		}

		String reply = "El cliente " + generoso + " ha sido el cliente más generoso donando un total de " + cantidad + "!";
		return reply;
	}
}