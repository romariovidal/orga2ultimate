#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <time.h>

int randomNumber() {
	int value=(rand()%2);
	
	return value;
}

int main(void){
        int     fd[2], res;
        pid_t   pidDelHijo;
        char    mensaje[] = "TomA mate";
		int 	i = 1;
        char    loMensajeado[80];

        pipe(fd);
      
		while((res = write(fd[1],mensaje, 1)) != 1)
			if (res == -1) perror("primer signal");
				else printf("ok\n");

	   	if((pidDelHijo = fork()) == -1)        {
                perror("fork");
                exit(1);
        }

	    if(pidDelHijo != 0){ 
			printf("Este es mi pid %d\n", pidDelHijo);
			srand(time(NULL));
		} else {
			printf("\t\t\t\tEste es mi pid %d\n", pidDelHijo);
			sleep(2);
			srand(time(NULL));
		}
		
		while(1){			

			while ((res = read(fd[0], loMensajeado, 1)) != 1)
				if (res == -1) perror("wait");

	        if(pidDelHijo != 0) 
				printf("Soy el proceso A");
			else
				printf("\t\t\t\tSoy el proceso B");
			printf(" entrando en la zona de exclusión, por vez %d\n", i);
			/* adentro de la exclusión mutua */
			sleep(1);
			i++;

	        if(pidDelHijo != 0) 
				printf("Soy el proceso A");
			else
				printf("\t\t\t\tSoy el proceso B");
			printf(" liberando en la zona de exclusión\n");

			while((res = write(fd[1], mensaje, 1)) != 1)
				if (res == -1) perror("signal");

			if(randomNumber())
				sleep(1);

		}
        
        return(0);
}


