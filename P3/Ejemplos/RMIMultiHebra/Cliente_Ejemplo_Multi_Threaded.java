// Código del cliente
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente_Ejemplo_Multi_Threaded implements Runnable {
	//...
	public String nombre_objeto_remoto = "un_nombre_para_obj_remoto";
	public String server;

	public Cliente_Ejemplo_Multi_Threaded (String server) {
		// Almacenamos el nombre del servidor
		this.server = server;
	}

	public void run() {
		System.out.println("Buscando el objeto remoto");
		try {
			Registry registry = LocateRegistry.getRegistry(server);
			// Invocación del objeto remoto, lo buscamos en el rmiregistry
			Ejemplo_I instancia_local = (Ejemplo_I) 
				registry.lookup(nombre_objeto_remoto);
			System.out.println("Invocando el objeto remoto");
			// Llamada a métodos remotos
			instancia_local.escribir_mensaje(Thread.currentThread().getName());
		} catch (Exception e) {
			System.err.println("Ejemplo exception: ");
			e.printStackTrace();
		} 
	}

	public static void main(String args[]) {
		// Instalación del gestor de seguridad
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		int n_hebras = Integer.parseInt(args[1]);
		// Creamos vector de hebras
		Cliente_Ejemplo_Multi_Threaded[] v_clientes = 
			new Cliente_Ejemplo_Multi_Threaded[n_hebras];
		Thread[] v_hebras = new Thread[n_hebras];

		for (int i = 0; i < n_hebras; i++) {
			// A cada hebra le pasamos el nombre del servidor
			v_clientes[i] = new Cliente_Ejemplo_Multi_Threaded(args[0]);
			v_hebras[i] = new Thread(v_clientes[i], "Cliente" + i);
			v_hebras[i].start();
		}
	}
}