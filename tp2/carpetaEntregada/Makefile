all:
	nasm -felf apagar.asm
	nasm -felf blit.asm
	nasm -felf chequearColisiones.asm
	nasm -felf generarFondo.asm
	nasm -felf recortar.asm
	nasm -felf generarRayo.asm
	gcc main.c -o runner apagar.o blit.o chequearColisiones.o generarFondo.o recortar.o generarRayo.o `sdl-config --cflags --libs`

clean:
	rm runner apagar.o  blit.o  chequearColisiones.o  generarFondo.o  recortar.o  generarRayo.o
 
