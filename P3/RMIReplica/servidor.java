import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class servidor {
	public static void main(String[] args) {
		// Instalamos el gestor de seguridad
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try{
			Registry reg;
			try {
				// Creamos un registro
				reg = LocateRegistry.createRegistry(1234);
			} catch (RemoteException e) {
				// O lanzamos la réplica si ya estuviera creado
				//System.out.println("Exception: " + e.getMessage());
				System.out.println("Lanzando réplica");
				reg = LocateRegistry.getRegistry(1234);
			}

			// Creamos una instancia del contador
			contador micontador = new contador(reg.list().length);
			String nombre_remoto = String.valueOf(reg.list().length);
			Naming.rebind(nombre_remoto, micontador);
			reg.rebind(nombre_remoto, micontador);
			System.out.println("Réplica " + nombre_remoto + " lanzada");

			System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}