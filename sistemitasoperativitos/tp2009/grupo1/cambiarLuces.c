/* cambiarLuces.c
 *
 * Cambiador de luces del teclado usando un modulo del kernel
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

#define MODULE_NAME "lasluces"
#define ERROR -1

/* Prototipos de las funciones de inicializacion y destruccion */
static int __init luces_init(void);
static void __exit luces_exit(void);

static struct proc_dir_entry *mi_directorio, *mi_archivo;
struct tty_driver *mi_driver;

/* Informamos al kernel que inicialize el modulo usando luces_init
 * y que antes de sacarlo use luces_exit
 */
module_init(luces_init);
module_exit(luces_exit);

int usandoLaIOCTL(int valor){
/* Definidas en linux/kd.h
 * 0x1 LED_SCR el de Scroll Lock
 * 0x2 LED_NUM el de Numeric lock
 * 0x4 LED_CAP el de Caps Lock
 */
	/* Para los leds*/
	(mi_driver->ioctl) (vc_cons[fg_console].d->vc_tty, 
		NULL, KDSETLED,valor);

	/* Para los flags, no la luz */
//	(mi_driver->ioctl) (vc_cons[fg_console].d->vc_tty, 
//		NULL, KDSKBLED,valor);
	return 0;
}


int escribiendo(struct file *filp, const char __user *buff, unsigned long len, void *data){
    printk(KERN_ALERT "Me escribieron %s - y len es %lu\n", buff, len);
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
static int __init luces_init() {
	int ret = 0;
    printk(KERN_ALERT "Estamos cargando el modulo para usted\n");

	mi_directorio = proc_mkdir(MODULE_NAME, NULL);
	if(mi_directorio == NULL)
	{
		printk(KERN_ALERT "Fallo!, no pude crear el directorio.\n");
		ret = -ENOMEM;
		goto no_dir;
	} else {
		printk(KERN_ALERT "DONE!, primer paso completado con 'exito\n");
	}

	mi_archivo = create_proc_entry("escribaAqui", 0644, mi_directorio);
	if(mi_archivo == NULL){
		printk(KERN_ALERT "Fallo la creaci'on del archivo\n");
		ret = -ENOMEM;
		goto no_file;		
	} else {
		printk(KERN_ALERT "GOLAZO! Pudiste crear el archivo\n");
	} 

	mi_archivo->write_proc = escribiendo;
	mi_driver = vc_cons[fg_console].d->vc_tty->driver;

	printk(KERN_ALERT "Si ves esto es que ya podes usarme.\n");
	return 0;

no_file:
	remove_proc_entry("escribaAqui", mi_directorio);
no_dir:
	remove_proc_entry(MODULE_NAME, NULL);
    return ret;
}
/* Destruccion */
static void __exit luces_exit() {
    printk(KERN_ALERT "Desmontando el m'odulo.\n");

	remove_proc_entry("escribaAqui", mi_directorio);
	remove_proc_entry(MODULE_NAME, NULL);
}

MODULE_LICENSE("GPL");

