; int apagar(char *image, int w, int h, int *cont)

global apagar

extern malloc
extern free

%define image 		[ebp+8]
%define w 		[ebp+12]
%define h 		[ebp+16]
%define contador 	[ebp+20]

section .text

apagar:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi
	
	; Principio Nucleo de la funcion
	mov edi, image 	; puntero a char (apunta a la imagen)
	mov ecx, w	; contiene el ancho del sprite
	mov ebx, h	; contiene el alto del sprite
	mov eax, contador 
	mov esi, [eax] ; esi contiene el valor del contador
	
	
ciclo_fila:
	mov eax, [edi]
	and eax, 0xfff0
	cmp eax, 0xf0f0 
	je  pasarDePixel
	mov byte [edi], 0
	inc edi
	mov byte al, [esi]
	mov byte [edi], al
	inc edi
	mov byte [edi], al
	inc edi
	loop ciclo_fila

	jmp pasarDeFila

pasarDePixel:
	add edi, 3
	loop ciclo_fila

pasarDeFila:
	dec ebx
	cmp ebx, 0
	je  fin
	mov ecx, w
	jmp ciclo_fila

fin:
	sub esi, 8
	mov ebx, contador
	mov [ebx], esi
	; Fin Nucleo de la funcion
	
	pop esi
	pop edi
	pop ebx
	pop ebp
	
	ret
