#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>

int main(void)
{
        int     fd[2], res;
        pid_t   pidDelHijo;
        char    mensaje[] = "Tom'a este mensaje, o mejor tom'a mate!\n";
        char    loMensajeado[80];

        pipe(fd);
        
        if((pidDelHijo = fork()) == -1)
        {
                perror("fork");
                exit(1);
        }

        if(pidDelHijo != 0)
        {
				/* Este es el proceso hijo */
				printf("Hijo en el paso  1\n");
                /* El hijo cierra la entrada del pipe */
                close(fd[0]);
				printf("Hijo en el paso  2\n");
                /* Manda el "mensaje" atreaces de la salida del pipe */
                write(fd[1], mensaje, strlen(mensaje));
				printf("Hijo en el paso  3\n");
				close(fd[1]);
				printf("Hijo en el paso  4\n");
                exit(0);
        }
        else
        {
				/* Este es el proceso padre */
				printf("Padre en el paso 1\n");
                /* El padre cierra la salida del pipe */
                close(fd[1]);

				printf("Padre en el paso 2\n");
                /* Lee la entrada del pipe */
                res = read(fd[0], loMensajeado, sizeof(loMensajeado));
				printf("Padre en el paso 3\n");
                printf("Mensaje recibido: %s\n", loMensajeado);
				printf("Padre en el paso 4\n");
	
				exit(0)
        }
        
        return(0);
}


