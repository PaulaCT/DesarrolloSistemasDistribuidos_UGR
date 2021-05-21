# Dependencias

require 'thrift'

require_relative 'calculadora'
require_relative 'calculadora_types'

class Servidor

	def inicializar()
		@log = {}
	end

	########## Operaciones básicas ##########

	# Función ping
	def ping()
		puts "Ping ~~~~"
	end

	# Función sumar
	def suma(num1, num2)
		val = num1 + num2
		return val
	end

	# Función restar
	def resta(num1, num2)
		val = num1 - num2
		return val
	end

	# Función multiplicar
	def multiplicacion(num1, num2)
		val = num1 * num2
		return val
	end

	# Función dividir
	def division(num1, num2)
		if num2 == 0
			x = InvalidOperation.new()
			x.why = "División entre 0"
			raise x
		end
		val = num1 / num2
		return val
	end

	########## Operaciones complejas ##########

	# Función determinante
	def determinante(matriz)
		puts "Vamos a calcular determinante"
		val = det(matriz, matriz.length)
		return val
	end

	# Operaciones auxiliares
	def det(matriz, tam)
		det = 0.0;
		if tam == 1
			det = matriz[0][0]
		else
			for j in 0..tam-1
				det = det + matriz[0][j] * cofactor(matriz, tam, 0, j)
			end
		end
		return det
	end

	def cofactor(matriz, tam, fila, columna)
		n = tam-1
		submatriz = Array.new(n) {Array.new(n)}
		x = 0
		y = 0
		for i in 0..tam-1
			for j in 0..tam-1
				if i != fila && j != columna
					submatriz[x][y] = matriz[i][j]
					y = y + 1
					if y >= n
						x = x + 1
						y = 0
					end
				end
			end
		end
		val = -1**(fila + columna) * det(submatriz, n)
		return val
	end

	# Función suma de vectores
	def sumavectores(vec1, vec2)
		tam = vec1.length
		val = Array.new(tam)
		for i in 0..tam-1
			val[i] = vec1[i] + vec2[i]
		end
		return val
	end

	########## Otras operaciones ##########

	def getStruct(key)
		print "getStruct(", key, ")\n"
		return @log[key]
	end

	def zip()
		print "zip\n"
	end

end

handler = Servidor.new()
processor = Calculadora::Processor.new(handler)
transport = Thrift::ServerSocket.new(8081)
transportFactory = Thrift::BufferedTransportFactory.new()
server = Thrift::SimpleServer.new(processor, transport, transportFactory)

puts "Starting the server..."
server.serve()
puts "done."