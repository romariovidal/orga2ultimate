#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>

int main(void)
{
        int     fd[2];
        pid_t   pidHijo;
        char    entrada[] = "saber de la vida\n";
        char    salida[80];

        pipe(fd);
        
        if((pidHijo = fork()) == -1)
        {
                perror("fork");
                exit(1);
        }

        if(pidHijo != 0)
        {
		// Aca entra el padre
		printf("Soy el padre!\n");		    
		// cierro la salida del pipe
		close(fd[0]);				
		printf("Ya cerre mi salida, nene\n");
		// Lee el entrada
		write(fd[1], entrada, strlen(entrada));
		printf("Ya te pase mi conocimiento hijo\n");
		exit(0);
        }
        else
        {
		// Aca entra hijo 
		printf("Soy el hijo!\n");
		// cierro la entrada del pipe
		close(fd[1]);
		printf("Ya cerre mi entrada, pa\n");
		// Manda el "entrada" atreaces de la salida del pipe
		read(fd[0], salida, sizeof(salida));
		printf("Papa ya me dio su %s\n", salida);
		exit(0);		
        }        
        return(0);
}


