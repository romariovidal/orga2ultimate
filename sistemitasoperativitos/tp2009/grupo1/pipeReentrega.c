#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
//#include <cstdlib.h>

int randomNumber() {
	int value=(rand()%3+1);
	
	return value;
}

int main(void){
        int     fd[2], res;
        pid_t   pidDelHijo;
        char    mensaje[] = "TomA mate";
		int 	longMsg = 9;
		int 	i = 0;
        char    loMensajeado[80];

        pipe(fd);
      
		printf("Bla\n"); 

		perror("BLA");
		while(res = write(fd[1],mensaje, 1) != 1)
			if (res == -1) perror("primer signal");
				else printf("ok\n");

		printf("BLA2");
	   	if((pidDelHijo = fork()) == -1)        {
                perror("fork");
                exit(1);
        }
		printf("Este es %d\n", pidDelHijo);
		//fork();
		
		while(1){			

			while (res = read(fd[0], loMensajeado, 1) != 1)
				if (res == -1) perror("wait");

	        if(pidDelHijo != 0) 
				printf("Soy el proceso A");
			else
				printf("\t\t\t\tSoy el proceso B");
			printf(" entrando en la zona de exclusión, por vez %d\n", i);
			// adentro de la exclusión mutua
			sleep(1);
			i++;

	        if(pidDelHijo != 0) 
				printf("Soy el proceso A");
			else
				printf("\t\t\t\tSoy el proceso B");
			printf(" liberando en la zona de exclusión\n");

			while(res=write(fd[1], mensaje, 1) != 1)
				if (res == -1) perror("signal");

			sleep(1);

		}

/*
        if(pidDelHijo != 0)        {
                close(fd[0]);
                write(fd[1], mensaje, strlen(mensaje));
				close(fd[1]);
                exit(0);
        }else{
                close(fd[1]);
                res = read(fd[0], loMensajeado, sizeof(loMensajeado));
                printf("Mensaje recibido: %s\n", loMensajeado);
				exit(0);
        }*/
        
        return(0);
}


