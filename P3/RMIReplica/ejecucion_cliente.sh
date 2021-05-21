#!/bin/sh -e
# ejecutar = Macro para ejecucion del cliente del programa
# en una sola maquina Unix de nombre localhost.


echo
echo "Lanzando el cliente"
java -cp . -Djava.rmi.server.codebase=file:./ -Djava.security.policy=server.policy cliente 49087324