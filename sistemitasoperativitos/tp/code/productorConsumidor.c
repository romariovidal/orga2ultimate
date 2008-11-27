#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t buff_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t  buff_lleno_cond  = PTHREAD_COND_INITIALIZER;
pthread_cond_t  buff_vacio_cond  = PTHREAD_COND_INITIALIZER;

#define MAX 4

char buffer[MAX];
int in, out, n;

void *almacenar(void *val);
void *retirar(void *val);

void producir(void * v);
void consumir(void * v);

void verBuffer();

main()
{
   	pthread_t t_01, t_02, t_03, t_04, t_05, t_06, t_07, t_08, t_09, t_10, 
					 t_21, t_12, t_13, t_14, t_15, t_16, t_17, t_18, t_19, t_20;
	
	n = 0;
	in = 0;
	out = 0;
	char * mensaje1 ="1";
	char * mensaje2 ="2";
	char * mensaje3 ="3";
	char * mensaje4 ="4";
	char * mensaje5 ="5";
	char * mensaje6 ="6";
	char * mensaje7 ="7";
	char * mensaje8 ="8";
	char * mensaje9 ="9";
	char * mensaje10 ="0";

	char * salida1 = (char *) malloc(sizeof(char));
	char * salida2 = (char *) malloc(sizeof(char));
	char * salida3 = (char *) malloc(sizeof(char));
	char * salida4 = (char *) malloc(sizeof(char));
	char * salida5 = (char *) malloc(sizeof(char));
	char * salida6 = (char *) malloc(sizeof(char));
	char * salida7 = (char *) malloc(sizeof(char));
	char * salida8 = (char *) malloc(sizeof(char));
	char * salida9 = (char *) malloc(sizeof(char));
	char * salida10 = (char *) malloc(sizeof(char));

   pthread_create( &t_11, NULL, (void *)&consumir, salida1);

	pthread_create( &t_01, NULL, (void *)&producir, mensaje1);
	pthread_create( &t_02, NULL, (void *)&producir, mensaje2);
	pthread_create( &t_03, NULL, (void *)&producir, mensaje3);
	pthread_create( &t_04, NULL, (void *)&producir, mensaje4);
	pthread_create( &t_05, NULL, (void *)&producir, mensaje5);
	pthread_create( &t_06, NULL, (void *)&producir, mensaje6);

   pthread_create( &t_12, NULL, (void *)&consumir, salida2);
   pthread_create( &t_13, NULL, (void *)&consumir, salida3);
   pthread_create( &t_14, NULL, (void *)&consumir, salida4);
   pthread_create( &t_15, NULL, (void *)&consumir, salida5);

   pthread_create( &t_16, NULL, (void *)&consumir, salida6);
   pthread_create( &t_17, NULL, (void *)&consumir, salida7);

	pthread_create( &t_07, NULL, (void *)&producir, mensaje7);
	pthread_create( &t_08, NULL, (void *)&producir, mensaje8);
	pthread_create( &t_09, NULL, (void *)&producir, mensaje9);
	pthread_create( &t_10, NULL, (void *)&producir, mensaje10);

   pthread_create( &t_18, NULL, (void *)&consumir, salida8);
   pthread_create( &t_19, NULL, (void *)&consumir, salida9);
   pthread_create( &t_20, NULL, (void *)&consumir, salida10);

	pthread_join(t_1, NULL);
	pthread_join(t_2, NULL);
	pthread_join(t_3, NULL);
	pthread_join(t_4, NULL);
	pthread_join(t_5, NULL);
	pthread_join(t_6, NULL);
	pthread_join(t_7, NULL);
	pthread_join(t_8, NULL);
	pthread_join(t_9, NULL);
	pthread_join(t_10, NULL);
	pthread_join(t_11, NULL);
	pthread_join(t_12, NULL);
	pthread_join(t_11, NULL);
	pthread_join(t_12, NULL);
	pthread_join(t_13, NULL);
	pthread_join(t_14, NULL);
	pthread_join(t_15, NULL);
	pthread_join(t_16, NULL);
	pthread_join(t_17, NULL);
	pthread_join(t_18, NULL);
	pthread_join(t_19, NULL);
	pthread_join(t_20, NULL);

	free(salida1);
	free(salida2);
	free(salida3);
	free(salida4);
	free(salida5);
	free(salida6);
	free(salida7);
	free(salida8);
	free(salida9);
	free(salida10);

   exit(0);
}


void verBuffer(){
	printf("[");
	int i;
	for(i=0; i<MAX; i++)
		printf("%c|", buffer[i]);

	printf("]\n");

}

void producir(void * v){	
	almacenar(v);
}

void consumir(void * v){
	retirar(v);
}

void *almacenar(void *val)
{
	pthread_mutex_lock( &buff_mutex );	
	if(n == MAX)
	{
		pthread_cond_wait( &buff_vacio_cond, &buff_mutex );
	   }
	char *mensaje = (char *)val;	
	buffer[in] = *mensaje;
	in++;
	if(in == MAX)
		in = 0;
	n++;
	verBuffer();

	pthread_mutex_unlock( &buff_mutex );
	pthread_cond_signal( &buff_lleno_cond );
	
}

void *retirar(void *val)
{
	pthread_mutex_lock( &buff_mutex );	
	if(n == 0) 
	{
		pthread_cond_wait( &buff_lleno_cond, &buff_mutex );
   }
	*((char *)val) = buffer[out];
	buffer[out] = 0;
	out++;
	if(out == MAX)
		out = 0;
	n--;
	pthread_mutex_unlock( &buff_mutex );
	verBuffer();
	pthread_cond_signal( &buff_vacio_cond );
}

