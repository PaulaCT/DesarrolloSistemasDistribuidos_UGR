/** Realizado por Paula Cumbreras Torrente     **/
/** DNI: 49087324B                             **/
/** Diseño de Sistemas Distribuidos Grupo 2    **/
/** Práctica 2 - Apache Thrift                 **/
/**                                            **/

service Calculadora{
	void ping(),
	i32 suma(1:i32 num1, 2:i32 num2),
	i32 resta(1:i32 num1, 2:i32 num2),
	i32 multiplicacion(1:i32 num1, 2:i32 num2),
	i32 division(1:i32 num1, 2:i32 num2),
	double determinante(1:list<list<double>> matriz),
	list<double> sumavectores(1:list<double> vec1, 2:list<double> vec2),
}

/**                                            **/
/** thrift --gen py -r calculadora.thrift      **/
/** thrift --gen rb -r calculadora.thrift      **/
/**                                            **/
