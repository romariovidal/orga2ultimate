#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t buff_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t  buff_lleno_cond  = PTHREAD_COND_INITIALIZER;
pthread_cond_t  buff_vacio_cond  = PTHREAD_COND_INITIALIZER;

#define MAX 3

char *buffer[MAX];
int in, out, n;

void *almacenar(void *val);
void *retirar(void *val);

main()
{
   pthread_t thread1, thread2;
	
	n = 0;
	in = 0;
	out = 0;
	char *mensaje1 = "hola";
	char *mensaje2;
   pthread_create( &thread1, NULL, &almacenar, mensaje1);
   pthread_create( &thread2, NULL, &retirar, mensaje2);
   pthread_join( thread1, NULL);
   pthread_join( thread2, NULL);

   exit(0);
}

void *almacenar(void *val)
{
	pthread_mutex_lock( &buff_mutex );	
	if(n == MAX) then
	{
		pthread_cond_wait( &buff_vacio_cond, &buff_mutex );
   }
	char *mensaje = (char *)val;	
	buffer[in] = mensaje;
	if(in == MAX)
		in = 0;
	else
		in++;
	n++;
	pthread_mutex_unlock( &buff_mutex );
	pthread_cond_signal( &buff_lleno_cond );
	
}

void *retirar(void *val)
{
	pthread_mutex_lock( &buff_mutex );	
	if(n == 0) then
	{
		pthread_cond_wait( &buff_lleno_cond, &buff_mutex );
   }
	val = buffer[out];
	if(out == MAX)
		out = 0;
	else
		out++;
	n--;
	pthread_mutex_unlock( &buff_mutex );
	pthread_cond_signal( &buff_vacio_cond );

}

