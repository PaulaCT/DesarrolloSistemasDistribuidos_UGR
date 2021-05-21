// Implementa la interfaz remota
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Ejemplo implements Ejemplo_I {

	public Ejemplo() {
		super();
	}

	public synchronized void escribir_mensaje (String mensaje) {
		// Implementación de la interfaz
		System.out.println("\nEntra Hebra " + mensaje);

		// Buscamos los procesos 0, 10, 20...
		if (mensaje.endsWith("0")) {
			try {
				System.out.println("Empezamos a dormir");
				Thread.sleep(5000);
				System.out.println("Terminamos de dormir");
			}
			catch (Exception e) {
				System.err.println("Ejemplo exception: ");
				e.printStackTrace();
			}
		}
		System.out.println("Sale Hebra " + mensaje);
	}

	public static void main (String[] args) {
		// Instalación del gestor de seguridad
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			// Creamos una instancia
			Ejemplo_I prueba = new Ejemplo();
			// Selección de puerto anónimo para atender peticiones
			Ejemplo_I stub = (Ejemplo_I) UnicastRemoteObject.exportObject(prueba, 0);
			Registry registry = LocateRegistry.getRegistry();
			String nombre_objeto_remoto = "un_nombre_para_obj_remoto";
			// La exportamos y le damos un nombre con el que identificarla en el
			// RMI registry
			registry.rebind(nombre_objeto_remoto, stub);
			System.out.println("Ejemplo bound");
		} catch (Exception e) {
			System.err.println("Ejemplo exception: ");
			e.printStackTrace();
		}
	}
}
