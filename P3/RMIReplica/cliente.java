import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class cliente {
	public static void main(String[] args) {
		// Crea e instala el gestor de seguridad
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// Crea el stub para el cliente especificando el nombre del servidor
			Registry mireg = LocateRegistry.getRegistry("localhost", 1234);

			// Seleccionamos una réplica al azar
			icontador micontador = (icontador)mireg.lookup(mireg.list()[0]);

			// Y dejamos que se nos asigne al registrarnos
			String midni = args[0];
			int mireplica = micontador.registro(Integer.parseInt(midni), mireg);

			// Si hemos tenido éxito
			if (mireplica != -1) {
				// Trabajaremos en nuestra réplica
				if (mireplica != 0) micontador = (icontador)mireg.lookup(mireg.list()[mireplica]);

				// Inicio de donaciones
				boolean salir = false;
				Scanner scan = new Scanner(System.in);

				while (!salir){
					System.out.println("");
					System.out.println("Qué desea hacer?");
					System.out.println("1. Donar");
					System.out.println("2. Consultar mis donaciones");
					System.out.println("3. Consultar las recaudaciones de esta réplica");
					System.out.println("4. Consultar el total recaudado");
					System.out.println("5. Consultar el cliente más generoso");
					System.out.println("6. Salir");
					System.out.println("");
					String opcion = scan.nextLine();

					switch(opcion){
						case "1": {
							System.out.println("Cuánto desea donar?");
							String midonacion = scan.nextLine();
							if (micontador.donar(Integer.parseInt(midni), Integer.parseInt(midonacion)))
								System.out.println("Donación exitosa.");
							else System.out.println("Se ha producido un error al donar.");
						}; break;
						case "2": {
							int misdonaciones = micontador.donacionesCliente(Integer.parseInt(midni));
							System.out.println("Has donado " + misdonaciones + " en total.");
						}; break;
						case "3": {
							int rec_replica = micontador.recaudadoReplica();
							System.out.println("Se ha recaudado " + rec_replica + " en esta réplica.");
						}; break;
						case "4": {
							int rec = micontador.recaudado(mireg);
							System.out.println("Se ha recaudado " + rec + " en total.");
						}; break;
						case "5": {
							String reply = micontador.clienteMasGeneroso(mireg);
							System.out.println(reply);
						}; break;
						case "6":
						default: salir = true; break;
					}
				}

			} else {
				System.out.println("Ya existe un cliente con mi dni");
			}

		} catch(NotBoundException | RemoteException e) {
			System.err.println("Exception del sistema: " + e);
		}
		System.exit(0);
	}
}