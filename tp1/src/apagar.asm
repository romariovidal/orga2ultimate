; int apagar(char *image, int w, int h, int *cont)

%define sprite 		[ebp+8]
%define w 		[ebp+12]
%define h 		[ebp+16]
%define pCont 		[ebp+20]

extern printf

global apagar

section .data
formato db "Llamado con (%d, %d, %d, %d)",10,0


section .text

apagar:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi
	mov eax, 16
	
	; Principio Nucleo de la funcion
	mov edi, sprite 	;puntero a char (apunta a la imagen)
	mov ecx, w		;contiene el ancho del sprite
	mov ebx, h		;contiene el alto del sprite
	mov esi, pCont
	mov esi, [esi] 		;esi contiene el valor del contador

	push esi
	push ebx
	push ecx		
	push edi 
	push formato
	call printf
	add esp, 20
	
	jmp fin
	
ciclo_fila:
	xor eax, eax		;inicializo eax en 0
	mov byte eax, [edi]	;muevo el primer byte apuntado por edi a la parte baja de eax
	shl eax, 16		;shifteo a izquierda para tener espacio en eax para guardar 16 bits en ax
	inc edi			;avanzo en puntero al sprite
	mov ax, [edi]		;muevo 2 bytes a ax
	and eax, 0x00ffffff	;le paso una mascara para que quede en cero la parte alta de eax (teoricamente ya esta en cero porque la inicilize en cero)
	cmp eax, 0x00ff00ff 	;comparo eax con rosa
	je  pasarDePixel	;si es rosa me paso de pixel (o sea, avanzo el puntero dos lugares)
	dec edi			;si no es rosa entonces retrocedo el puntero al sprite para reescribir el valor del pixel
	mov byte [edi], 0	;pongo 0 en la componente roja
	inc edi			;avanzo puntero
	mov eax, esi		;pongo el valor del contador en eax
	mov byte [edi], al	;muevo la parte baja de eax a lo apuntado por edi (son los segundos 8 bits que faltan para llenar el pixel)
	inc edi			;avanzo puntero
	mov byte [edi], al	;muevo la parte baja de eax a lo apuntado por edi (son los terceros 8 bits que faltan para llenar el pixel)
	inc edi			;avanzo puntero para dejar apuntando al proximo pixel a procesar
	loop ciclo_fila		;salto a ciclo_fila y decremento ecx
	jmp pasarDeFila		;si ecx es cero entonces tengo que procesar la proxima fila

pasarDePixel:
	add edi, 2		;avanzo el puntero 2 lugares para procesar el proximo pixel
	loop ciclo_fila

pasarDeFila:
	dec ebx 		; ebx tiene la cantidad de filas que me faltan procesar
	cmp ebx, 0		; comparo con cero
	je  fin			;si es cero, entonces no me faltan procesar mas filas
	mov ecx, w 		; reseteo la cantidad de columnas

	mov edx, ecx		; edx tiene la cantidad de columnas
	shl edx, 1
	add edx, ecx 		; edx tiene la cantidad de columnas por 3
	and edx, 0x00ffffff
	add edx, 4 		; edx tiene la cantidad de bytes de la fila, incluyendo la basura
	mul edx			; ahora tengo en eax la cantidad de bytes procesados, incluyendo los bytes basura
	and edx, 0x11000000 	; ahora en edx tengo la cantidad de bytes de la fila modulo 4
	add edi, edx 		; ahora tengo a edi apuntando a la fila que tengo que procesar
	jmp ciclo_fila

fin:
	mov esi, pCont
	mov eax, [esi]
	sub eax, 8
	mov [esi], eax
	
	; Fin Nucleo de la funcion

final:	
	pop esi
	pop edi
	pop ebx
	pop ebp
	
	ret
