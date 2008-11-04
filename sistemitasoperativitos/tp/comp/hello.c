/* hello.c
 *
 * "Hello, world" usando modulos de kernel
 *
 */
/* Headers para modulos de kernel */
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include  <linux/kd.h>    /* IOCTLs del teclado   */
#include  <linux/ioctl.h>    /* */
//#include "moduloDeNuestroKernel.c"

/* Prototipos de las funciones de inicializacion y destruccion */
static int __init hello_init(void);
static void __exit hello_exit(void);
/* Informamos al kernel que inicialize el modulo usando hello_init
 * y que antes de quitarlo use hello_exit
 */
module_init(hello_init);
module_exit(hello_exit);
/* Inicializacion */
static int __init hello_init() {
    printk(KERN_ALERT "Hola kernel!\n");

	printk(KERN_ALERT "Intentar'e imprimir como est'an los leds\n");

	
	//main();

    int fd;      /* fd de la consola (/dev/tty), para usar en ioctl()    */
    long int state; /* Entero para guardar el estado de los LEDs         */
    ioctl(fd, KDGETLED, &state);
	//int num = LED_NUM;
	printk(KERN_ALERT "Estado de la luz del num: %d\n", LED_NUM);
    /* Si devolvemos un valor distinto de cero significa que
     * hello_init fallo y el modulo no puede ser cargado.
     */
    return 0;
}
/* Destruccion */
static void __exit hello_exit() {
    printk(KERN_ALERT "Chau, kernel.\n");
}

/* Declaramos que este codigo tiene licencia GPL.
 * De esta manera no estamos "manchando" el kernel con codigo propietario.
 */
MODULE_LICENSE("GPL");

