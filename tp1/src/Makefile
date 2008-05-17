all:
	nasm -felf apagar2.asm
	nasm -felf blit.asm
	nasm -felf checkcolision.asm
	nasm -felf generarFondo.asm
	nasm -felf recortar.asm
	nasm -felf salta.asm
	gcc main.c -o runner apagar2.o blit.o checkcolision.o generarFondo.o recortar.o salta.o `sdl-config --cflags --libs`

clean:
	rm runner apagar2.o  blit.o  checkcolision.o  generarFondo.o  recortar.o  salta.o
 
