#!/bin/sh -e
# ejecutar = Macro para ejecucion del servidor del programa
# en una sola maquina Unix de nombre localhost.

echo
echo "Lanzando el servidor"
java -cp . -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy servidor
sleep 2