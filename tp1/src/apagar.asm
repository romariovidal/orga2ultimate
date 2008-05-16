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
	mov edi, sprite 	; puntero a char (apunta a la imagen)
	mov ecx, w		; contiene el ancho del sprite
	mov ebx, h		; contiene el alto del sprite
	mov esi, pCont
	mov esi, [esi] 		; esi contiene el valor del contador

	push esi
	push ebx
	push ecx
	push edi 
	push formato
	call printf
	add esp, 20
	
	jmp fin
	
ciclo_fila:
	xor eax, eax
	mov byte eax, [edi]
	shl eax, 16
	inc edi
	mov ax, [edi]
	and eax, 0x00ffffff
	cmp eax, 0x00ff00ff 
	je  pasarDePixel
	dec edi
	mov byte [edi], 0
	inc edi
	mov eax, esi
	mov byte [edi], al
	inc edi
	mov byte [edi], al
	inc edi
	loop ciclo_fila
	jmp pasarDeFila

pasarDePixel:
	add edi, 2
	loop ciclo_fila

pasarDeFila:
	dec ebx ; ebx tiene la cantidad de filas que me faltan
	cmp ebx, 0
	je  fin
	mov ecx, w 	; reseteo la cantidad de columnas

	mov edx, ecx	; edx tiene la cantidad de columnas
	shl edx, 1
	add edx, ecx 	; edx tiene la cantidad de columnas por 3
	and edx, 0x00ffffff
	add edx, 4 	; edx tiene la cantidad de bytes de la fila, incluyendo la basura
	mul edx		; ahora tengo en eax la cantidad de bytes procesados, incluyendo los bytes basura
	and edx, 0x11000000 ; ahora en edx tengo la cantidad de bytes de la fila modulo 4
	add edi, edx 	; ahora tengo a edi apuntando a la fila que tengo que procesar
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
