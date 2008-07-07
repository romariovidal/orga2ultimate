#include <stdio.h>


extern double generarRayo(unsigned int,unsigned int,unsigned int,unsigned int);



int main(){
	
	double resultado;


	unsigned int xI = 4;
	unsigned int yI = 4;
	unsigned int xF = 7;
	unsigned int yF = 7;

	resultado = generarRayo(xI,yI,xF,yF);

	printf("Dio: %.30f\n",resultado);

	return 0;
}
