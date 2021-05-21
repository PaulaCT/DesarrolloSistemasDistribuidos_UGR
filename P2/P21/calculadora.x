/**** Realizado por Paula Cumbreras Torrente ****/
/**** DNI: 49087324B ****************************/
/**** Diseño de Sistemas Distribuidos Grupo 2 ***/
/**** Práctica 2 - Sun RPC **********************/
/************************************************/

/*
 * 	Distinguimos entre divisiones posibles y 
 *	divisiones entre 0
 */

union div_res switch (int errno) {
	case 0: 
		int res;	/* se devuelve el resultado */
	default: 
		void;		/* se produce un error */
};

/*
 * 	Programa calculadora: contiene las operaciones 
 *	de suma, resta, multiplicación y división
 */

program CALCULADORA {
	version CALCULADORA_V {
		int SUMA (int a, int b) = 1;
		int RESTA (int a, int b) = 2;
		int MULTI (int a, int b) = 3;
		div_res DIV (int a, int b) = 4;
	} = 1;
} = 0x20000001;

program CALCULADORA_COMPLEJA {
	version CALCULADORA_CV {
		string SUMA_V (string fichero) = 1;
		string DETERM (string fichero) = 2;
	} = 1;
} = 0x20000002;

/************************************************/

/* rpcgen -NCa calculadora.x */
/* rpcgen -Na calculadora.x */
