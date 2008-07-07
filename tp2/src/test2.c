#include <stdio.h>


extern double generarRayo(unsigned int,unsigned int,unsigned int,unsigned int);



int main(){
	
	double resultado;


	unsigned int xI = 600;
	unsigned int yI = 521;
	unsigned int xF = 522;
	unsigned int yF = 508;

	resultado = generarRayo(xI,yI,xF,yF);

	printf("Dio: %.30f\n",resultado);

	return 0;
}
