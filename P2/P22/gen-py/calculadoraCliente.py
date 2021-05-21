#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# Dependencias
from calculadora import Calculadora
from calculadora.ttypes import *

import numpy as np
import os

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

# ------------- MENÚ DE OPCIONES ------------------

# Función menú, muestra operaciones disponibles
def menu():
	print('')
	print('Qué operación desea realizar?')
	print('1. Sumar dos eneros')
	print('2. Restar dos enteros')
	print('3. Multiplicar dos enteros')
	print('4. Dividir dos enteros')
	print('5. Calcular el determinante de una matriz')
	print('6. Sumar dos vectores')
	print('7. Salir')
	eleccion = input()
	return eleccion

# ------------- LECTURA DE PARÁMETROS -------------

# Funciones para lectura de operandos
def dos_operandos():
	num1 = input('Introduzca el primer número: ')
	num2 = input('Introduzca el segundo número: ')
	return num1, num2

def un_archivo():
	file = raw_input('Introduzca el nombre del fichero: ')
	return file

# Funciones para lectura de ficheros
def read_matrix(file):
	try:
		f = open(file, "r")
	except OSError as err:
		print("Error al abrir fichero: " + err.strerror)
	else:
		tam = int(f.readline())
		matriz = []
		try:
			for i in range(0,tam):
				linea = f.readline().split(' ')
				entrada = []
				for j in range(0,tam):
					entrada.append(float(linea[j]))
				matriz.append(entrada)
			return matriz
		except ValueError:
			print("Fichero no válido: datos incorrectos")
		except IndexError:
			print("Fichero no válido: tamaño incorrecto")

def read_vector(file):
	try:
		f = open(file, "r")
	except OSError as err:
		print("Error al abrir fichero: " + err.strerror)
	else:
		tam = int(f.readline())
		vec1 = []
		vec2 = []
		auxiliar1 = f.readline().split(' ')
		auxiliar2 = f.readline().split(' ')
		try:
			for i in range(0,tam):
				vec1.append(float(auxiliar1[i]))
				vec2.append(float(auxiliar2[i]))
			return vec1, vec2
		except ValueError:
			print("Fichero no válido: datos incorrectos")
		except IndexError:
			print("Fichero no válido: tamaño incorrecto")

# ------------- LLAMADAS AL SERVIDOR -------------- 

def sumar(cliente):
	num1, num2 = dos_operandos()
	result = cliente.suma(num1, num2)
	print('Resultado: ' + str(num1) + ' + ' + str(num2) 
		+ ' = ' + str(result))

def restar(client):
	num1, num2 = dos_operandos()
	result = client.resta(num1, num2)
	print('Resultado: ' + str(num1) + ' - ' + str(num2) 
		+ ' = ' + str(result))

def multiplicar(client):
	num1, num2 = dos_operandos()
	result = client.multiplicacion(num1, num2)
	print('Resultado: ' + str(num1) + ' * ' + str(num2) 
		+ ' = ' + str(result))

def dividir(client):
	num1, num2 = dos_operandos()
	try:
		result = client.division(num1, num2)
		print('Resultado: ' + str(num1) + ' / ' + str(num2) 
			+ ' = ' + str(result))
	except TException:
		print('Operación no válida: división entre 0')

def determinante(client):
	fichero = un_archivo()
	matriz = read_matrix(fichero)
	result = client.determinante(matriz)
	print('Resultado: ' + str(result))

def sumavectores(client):
	fichero = un_archivo()
	vec1, vec2 = read_vector(fichero)
	result = client.sumavectores(vec1, vec2)
	print('Resultado: ' + str(vec1) + '\n\t + ' + str(vec2) 
		+ '\n\t = ' + str(result))

# ------------- MAIN ------------------------------

def main():

	# Creación del socket
	transport = TSocket.TSocket('localhost',8081)
	transport = TTransport.TBufferedTransport(transport)
	protocol = TBinaryProtocol.TBinaryProtocol(transport)

	# Creación del cliente
	client = Calculadora.Client(protocol)

	# Inicio de conexión
	transport.open()
	client.ping()
	print('     ~~~~ Pong')

	# Preguntamos por una opción
	eleccion = menu()

	# Mientras que no se decida salir
	while (eleccion != 7):
		switcher = {
			1: sumar,
			2: restar,
			3: multiplicar,
			4: dividir,
			5: determinante,
			6: sumavectores
		}

		# Llamamos al método correspondiente
		funcion = switcher.get(eleccion)
		funcion(client)

		# Volvemos a preguntar
		eleccion = menu()

	# Fin de conexión
	transport.close()

if __name__ == '__main__':
	try:
		main()
	except Thrift.TException as tx:
		print('%s' % tx.message)