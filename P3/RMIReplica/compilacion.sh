#!/bin/sh -e
# ejecutar = Macro para compilación del programa

echo
echo "Lanzando el ligador de RMI … "
rmiregistry &

echo
echo "Compilando con javac ..."
javac *.java
sleep 2