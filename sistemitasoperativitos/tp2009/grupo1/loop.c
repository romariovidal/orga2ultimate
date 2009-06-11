#include <stdio.h>
#define IDGRUPO 1 /* Completar con su numero de grupo */
int main() {
    int i, c;
    while(1) {
        c = 48 + i;
        //printf("%d, --> %d\n", c, i);
        printf("%d", c);
        i++;
        i = i % IDGRUPO;
		//sleep(1);
    }
    return 0;
}

