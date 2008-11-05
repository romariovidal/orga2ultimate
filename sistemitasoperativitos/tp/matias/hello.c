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
#include <linux/tty.h>
#include <linux/kd.h>
#include <linux/console_struct.h>

#define MODULE_NAME "holahola"

/* Prototipos de las funciones de inicializacion y destruccion */
static int __init hello_init(void);
static void __exit hello_exit(void);
/* Informamos al kernel que inicialize el modulo usando hello_init
 * y que antes de quitarlo use hello_exit
 */

static struct proc_dir_entry *example_dir, *mi_archivo;

struct datos;

struct tty_driver *mi_driver;
#define TODOS_ON 0x07
#define RESTORE_LEDS 0xFF

module_init(hello_init);
module_exit(hello_exit);

int escribiendo(struct file *filp, const char __user *buff, unsigned long len, void *data){
    printk(KERN_ALERT "Me escribieron \n");

	return 0;
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
		printk(KERN_ALERT "GOLAZO CHAB'ON!\n");
	} 

	mi_archivo->write_proc = escribiendo;

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

