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

#define MODULE_NAME "MY_LEDS"
#define ERROR -1

/* Prototipos de las funciones de inicializacion y destruccion */
static int __init hello_init(void);
static void __exit hello_exit(void);
/* Informamos al kernel que inicialize el modulo usando hello_init
 * y que antes de quitarlo use hello_exit
 */

static struct proc_dir_entry *myDir;
static struct proc_dir_entry *myFile;

struct datos;

int fd; /* File descriptor for console (/dev/tty/) */
struct tty_driver *myDriver;
#define LEDS_PRENDIDOS 0x07
#define LEDS_APAGADOS 0xFF

module_init(hello_init);
module_exit(hello_exit);

int usandoLaIOCTL(int valor){
/* Definidas en linux/kd.h
 * 0x1 LED_SCR el de Scroll Lock
 * 0x2 LED_NUM el de Numeric lock
 * 0x4 LED_CAP el de Caps Lock
 */
	/* Para los leds*/
	(myDriver->ioctl) (vc_cons[fg_console].d->vc_tty, 
		NULL, KDSETLED,valor);

	/* Para los flags, no la luz */

	return 0;
}


int escribiendo(struct file *filp, const char __user *buff, unsigned long len, void *data){

	if(len>2){
		printk(KERN_ALERT "Ponga un numero entre 0 y 7\n");
		return len;
	}	
	switch (buff[0]){
		case '0':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [0|0|0]\n");
			usandoLaIOCTL(0);
			break;
		case '1':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [0|0|1]\n");
			usandoLaIOCTL(1);
			break;
		case '2':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [0|1|0]\n");
			usandoLaIOCTL(2);
			break;
		case '3':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [0|1|1]\n");
			usandoLaIOCTL(3);
			break;
		case '4':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [1|0|0]\n");
			usandoLaIOCTL(4);
			break;
		case '5':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [1|0|1]\n");
			usandoLaIOCTL(5);
			break;
		case '6':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [1|1|0]\n");
			usandoLaIOCTL(6);
			break;
		case '7':
			printk(KERN_ALERT "[CAP|NUM|SCR] -> [1|1|1]\n");
			usandoLaIOCTL(7);
			break;
		default:
			printk(KERN_ALERT "Ponga un numero entre 0 y 7\n");
			break;
	}

	return len;
}

/* Inicializacion */
static int __init hello_init() {
	int ret = 0;
    printk(KERN_ALERT "Hola kernel \n");
    printk(KERN_INFO "Hola kernel!\n");

	myDir = proc_mkdir(MODULE_NAME, NULL);
	if(myDir == NULL)
	{
		printk(KERN_ALERT "Fallo!\n");
		ret = -ENOMEM;
		goto no_dir;
	} else {
		printk(KERN_ALERT "DONE!\n");
	}

	myFile = create_proc_entry("ledControl.led", 0644, myDir);
	if(myFile == NULL){
		printk(KERN_ALERT "No se ha podido crear el archivo\n");
		ret = -ENOMEM;
		goto no_file;		
	} else {
		printk(KERN_ALERT "El modulo se ha cargado exitosamente, felicitaciones!\n");
	} 

	myFile->write_proc = escribiendo;

	myDriver = vc_cons[fg_console].d->vc_tty->driver;

	return 0;

no_file:
	remove_proc_entry("ledControl.led", myDir);
no_dir:
	remove_proc_entry(MODULE_NAME, NULL);
    return ret;
}
/* Destruccion */
static void __exit hello_exit() {
    printk(KERN_ALERT "Chau, kernel.\n");

	remove_proc_entry("ledControl.led", myDir);
	remove_proc_entry(MODULE_NAME, NULL);

}

MODULE_LICENSE("GPL");

