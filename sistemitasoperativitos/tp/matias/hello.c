/* hello.c
 *
 * "Hello, world" usando modulos de kernel
 *
 */
/* Headers para modulos de kernel */
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/proc_fs.h>
#include <linux/init.h>
#include <linux/tty.h>			/* fg_console, MAX_NR_CONSOLES*/
#include <linux/vt_kern.h>		/* fg_console, MAX_NR_CONSOLES*/
#include <linux/kd.h>			/* KDSETLED */
#include <linux/console_struct.h>	/* vc_cons*/

#define MODULE_NAME "holahola"
#define ERROR -1

/* Prototipos de las funciones de inicializacion y destruccion */
static int __init hello_init(void);
static void __exit hello_exit(void);
/* Informamos al kernel que inicialize el modulo usando hello_init
 * y que antes de quitarlo use hello_exit
 */

static struct proc_dir_entry *example_dir, *mi_archivo;

struct datos;

int fd; /* File descriptor for console (/dev/tty/) */
struct tty_driver *mi_driver;
#define TODOS_ON 0x07
#define RESTORE_LEDS 0xFF

module_init(hello_init);
module_exit(hello_exit);

int usandoLaIOCTL(int valor){
/* Definidas en linux/kd.h
 * 0x1 LED_SCR el de Scroll Lock
 * 0x2 LED_NUM el de Numeric lock
 * 0x4 LED_CAP el de Caps Lock
 */
	(mi_driver->ioctl) (vc_cons[fg_console].d->vc_tty, 
		NULL, KDSETLED,valor);
	return 0;
}


int escribiendo(struct file *filp, const char __user *buff, unsigned long len, void *data){
    printk(KERN_ALERT "Me escribieron %s - y len es %lu\n", buff, len);
    printk(KERN_ALERT "Len es %lu\n", len);
    printk(KERN_ALERT "Pos 0 es %c\n", buff[0]);
    printk(KERN_ALERT "Pos Pos 1s %c\n", buff[1]);
    printk(KERN_ALERT "Y???\n");
	//char c = buff[0];
	if(len>2){
		printk(KERN_ALERT "Escribi'o mal las opciones, debe poner un entero entre 0 y 7 - Sale por len\n");
		return len;
	}	
	switch (buff[0]){
		case '0':
			printk(KERN_ALERT "Apagando todo\n");
			usandoLaIOCTL(0);
			break;
		case '1':
			printk(KERN_ALERT "LED_SCR on\n");
			usandoLaIOCTL(1);
			break;
		case '2':
			printk(KERN_ALERT "LED_NUM on\n");
			usandoLaIOCTL(2);
			break;
		case '3':
			printk(KERN_ALERT "LED_NUM + LED_SCR on\n");
			usandoLaIOCTL(3);
			break;
		case '4':
			printk(KERN_ALERT "LED_CAP on\n");
			usandoLaIOCTL(4);
			break;
		case '5':
			printk(KERN_ALERT "LED_CAP + LED_SCR on \n");
			usandoLaIOCTL(5);
			break;
		case '6':
			printk(KERN_ALERT "LED_CAP + LED_NUM on\n");
			usandoLaIOCTL(6);
			break;
		case '7':
			printk(KERN_ALERT "LED_CAP + LED_NUM + LED_SCR on\n");
			usandoLaIOCTL(7);
			break;
		default:
			printk(KERN_ALERT "Escribi'o mal las opciones, debe poner un entero entre 0 y 7\n");
			break;
	}

	return len;
}

/* Inicializacion */
static int __init hello_init() {
	int ret = 0;
    //printk(KERN_ALERT "Hola kernel %x \n", fg_console);
    printk(KERN_ALERT "Hola kernel \n");
    printk(KERN_INFO "Hola kernel!\n");
    /* Si devolvemos un valor distinto de cero significa que
     * hello_init fallo y el modulo no puede ser cargado.
     */

	example_dir = proc_mkdir(MODULE_NAME, NULL);
	if(example_dir == NULL)
	{
		printk(KERN_ALERT "Fallo!\n");
		ret = -ENOMEM;
		goto no_dir;
	} else {
		printk(KERN_ALERT "DONE!\n");
	}

	mi_archivo = create_proc_entry("tommyPuto", 0644, example_dir);
	if(mi_archivo == NULL){
		printk(KERN_ALERT "Fallo el archivo\n");
		ret = -ENOMEM;
		goto no_file;		
	} else {
		printk(KERN_ALERT "GOLAZO CHAB'ON! Que puto\n");
	} 

	mi_archivo->write_proc = escribiendo;

	mi_driver = vc_cons[fg_console].d->vc_tty->driver;

	return 0;

no_file:
	remove_proc_entry("tommyPuto", example_dir);
no_dir:
	remove_proc_entry(MODULE_NAME, NULL);
    return ret;
}
/* Destruccion */
static void __exit hello_exit() {
    printk(KERN_ALERT "Chau, kernel.\n");

	remove_proc_entry("tommyPuto", example_dir);
	remove_proc_entry(MODULE_NAME, NULL);
//	(mi_driver->ioctl) (vc_cons[fg_console].d->vc_tty, NULL, 
//				KDSETLED, RESTORE_LEDS);
//
}

/* Declaramos que este codigo tiene licencia GPL.
 * De esta manera no estamos "manchando" el kernel con codigo propietario.
 */
MODULE_LICENSE("GPL");

