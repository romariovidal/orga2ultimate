#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t buff_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t  buff_lleno_cond  = PTHREAD_COND_INITIALIZER;
pthread_cond_t  buff_vacio_cond  = PTHREAD_COND_INITIALIZER;

#define MAX 3

char buffer[MAX];
int in, out, n;

void *almacenar(void *val);
void *retirar(void *val);

void testAlmacenar(void * v);
void testRetirar(void * v);

void verBuffer();

main()
{
   	pthread_t thread1, thread2, thread3, thread4, thread5, thread6, 
	thread7, thread8, thread9, thread10, thread11, thread12, thread21,
	thread22, thread23, thread24, thread25, thread26,thread27,
	thread28, thread29, thread30, thread31, thread32;
	
	n = 0;
	in = 0;
	out = 0;
	char * mensaje1 ="a";
	char * mensaje2 ="b";
	char * mensaje3 ="c";
	char * mensaje4 ="d";
	char * mensaje5 ="e";
	char * mensaje6 ="f";
	char * mensaje7 ="g";
	char * mensaje8 ="h";
	char * mensaje9 ="i";
	char * mensaje10 ="j";
	char * mensaje11 ="k";
	char * mensaje12 ="l";

//   pthread_create( &thread1, NULL, &almacenar, mensaje1);
//   pthread_create( &thread2, NULL, &retirar, mensaje2);

	char * mensajeS21 = (char *) malloc(sizeof(char));
	char * mensajeS22 = (char *) malloc(sizeof(char));
	char * mensajeS23 = (char *) malloc(sizeof(char));
	char * mensajeS24 = (char *) malloc(sizeof(char));
	char * mensajeS25 = (char *) malloc(sizeof(char));
	char * mensajeS26 = (char *) malloc(sizeof(char));
	char * mensajeS27 = (char *) malloc(sizeof(char));
	char * mensajeS28 = (char *) malloc(sizeof(char));
	char * mensajeS29 = (char *) malloc(sizeof(char));
	char * mensajeS30 = (char *) malloc(sizeof(char));
	char * mensajeS31 = (char *) malloc(sizeof(char));
	char * mensajeS32 = (char *) malloc(sizeof(char));


    pthread_create( &thread21, NULL, (void *)&testRetirar, mensajeS21);

	pthread_create( &thread1, NULL, (void *)&testAlmacenar, mensaje1);
	pthread_create( &thread2, NULL, (void *)&testAlmacenar, mensaje2);
	pthread_create( &thread3, NULL, (void *)&testAlmacenar, mensaje3);
	pthread_create( &thread4, NULL, (void *)&testAlmacenar, mensaje4);
	pthread_create( &thread5, NULL, (void *)&testAlmacenar, mensaje5);

    pthread_create( &thread22, NULL, (void *)&testRetirar, mensajeS22);
    pthread_create( &thread23, NULL, (void *)&testRetirar, mensajeS23);
    pthread_create( &thread24, NULL, (void *)&testRetirar, mensajeS24);
    pthread_create( &thread25, NULL, (void *)&testRetirar, mensajeS25);

	pthread_create( &thread6, NULL, (void *)&testAlmacenar, mensaje6);

    pthread_create( &thread26, NULL, (void *)&testRetirar, mensajeS26);
    pthread_create( &thread27, NULL, (void *)&testRetirar, mensajeS27);

	pthread_create( &thread7, NULL, (void *)&testAlmacenar, mensaje7);
	pthread_create( &thread8, NULL, (void *)&testAlmacenar, mensaje8);
	pthread_create( &thread9, NULL, (void *)&testAlmacenar, mensaje9);
	pthread_create( &thread10, NULL, (void *)&testAlmacenar, mensaje10);
	pthread_create( &thread11, NULL, (void *)&testAlmacenar, mensaje11);
	pthread_create( &thread12, NULL, (void *)&testAlmacenar, mensaje12);

    pthread_create( &thread28, NULL, (void *)&testRetirar, mensajeS28);
    pthread_create( &thread29, NULL, (void *)&testRetirar, mensajeS29);
    pthread_create( &thread30, NULL, (void *)&testRetirar, mensajeS30);
    pthread_create( &thread31, NULL, (void *)&testRetirar, mensajeS31);
    pthread_create( &thread32, NULL, (void *)&testRetirar, mensajeS32);

	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);
	pthread_join(thread3, NULL);
	pthread_join(thread4, NULL);
	pthread_join(thread5, NULL);
	pthread_join(thread6, NULL);
	pthread_join(thread7, NULL);
	pthread_join(thread8, NULL);
	pthread_join(thread9, NULL);
	pthread_join(thread10, NULL);
	pthread_join(thread11, NULL);
	pthread_join(thread12, NULL);
	pthread_join(thread21, NULL);
	pthread_join(thread22, NULL);
	pthread_join(thread23, NULL);
	pthread_join(thread24, NULL);
	pthread_join(thread25, NULL);
	pthread_join(thread26, NULL);
	pthread_join(thread27, NULL);

	pthread_join(thread28, NULL);
	pthread_join(thread29, NULL);
	pthread_join(thread30, NULL);
	pthread_join(thread31, NULL);
	pthread_join(thread32, NULL);

	free(mensajeS21);
	free(mensajeS22);
	free(mensajeS23);
	free(mensajeS24);
	free(mensajeS25);
	free(mensajeS26);
	free(mensajeS27);
	free(mensajeS28);
	free(mensajeS29);
	free(mensajeS30);
	free(mensajeS31);
	free(mensajeS32);

   	exit(0);
}


void verBuffer(){
	printf("| ");
	int i;
	for(i=0; i<MAX; i++)
		printf("%c | ", buffer[i]);

	printf("\n");

}

void testAlmacenar(void * v){	
	almacenar(v);
}

void testRetirar(void * v){
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

	pthread_mutex_unlock( &buff_mutex );

	verBuffer();

	printf(">>>Mandando Signal a consumidor\n");
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

	printf("\t\t\t\t\t\t<<<Mandando Signal a productor\n");
	pthread_cond_signal( &buff_vacio_cond );
}

