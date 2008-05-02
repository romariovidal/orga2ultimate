; void generarFondo (char *screen, int ws, int hs, char *piso, int wp, int wh)

global generarFondo

extern malloc
extern free

section .text
;datos de pantalla
%define pScreen   	[ebp+8]
%define anchoPantalla   [ebp+12]
%define altoPantalla   	[ebp+16]

;datos de piso
%define pPiso   	[ebp+20]
%define anchoPiso	[ebp+24]
%define altoPiso	[ebp+28]

;colores de cielo
%define colorCieloR	0xFF
%define colorCieloG	0x96
%define colorCieloB	0x00			



generarFondo:
    push ebp
    mov ebp, esp
    push ebx
    push edi
    push esi

	mov eax, altoPantalla
	mov edx, altoPiso
	mov esi, pScreen ; esi apunta al byte actual que vamos a pintar
	sub eax, edx
	;eax contiene el alto del cielo
		
cicloGrande:
	mov ecx, anchoPantalla
	;ecx contiene el ancho de la pantalla en pixeles	

cicloChico:
	mov byte [esi], colorCieloR ;pinto de Rojo
	inc esi

	mov byte [esi], colorCieloG ;pinto de Verde
	inc esi

	mov byte [esi], colorCieloB ;pinto de Azul
	inc esi

	dec ecx; tiene la cantidad de pixeles que faltan pintar
	jnz cicloChico
	;AVANZAR LO QUE FALTE HASTA EL PROXIMO MULTIPLO DE 4 (BASURA)

	dec eax	;contador de cantidad de filas restantes a pintar
	jnz cicloGrande


dibujarPiso:
	push 0x3	;Cantidad de veces que debería dibujar

	mov ebx, altoPiso 	; cantidad de iteraciones de un cacho
    mov edi, pPiso  ;edi tiene el puntero al comienzo del dibujo

	mov edx, esi 	;en edx guardo el punto donde empecé a dibujar.
	;jmp cicloExterno

cicloMasExterno:
	mov esi, edx 	;en edx guardo el punto donde empecé a dibujar.
    mov eax, anchoPiso      ;eax contiene el ancho del piso en píxeles
	mov	ecx, 0x3
	mul ecx
	add esi, eax
	mov edi, pPiso
	mov ebx, altoPiso 	; cantidad de iteraciones de un cacho
	pop eax
	dec eax
	dec eax
	push eax
;	jnz cicloMasExterno
;	pop eax
;	inc eax
;	push eax

cicloExterno:
    mov eax, anchoPiso      ;eax contiene el ancho del piso en píxeles
	mov	ecx, 0x3
	mul ecx
	mov ecx, eax	;ecx contiene el ancho de la pantalla en bytes	

cicloInterno:
	mov eax, [edi]  ;traigo el dibujo de memoria	
	mov [esi], eax

	add edi, 0x3
	add esi, 0x3
	sub ecx, 0x3

	;mov byte al, [edi]
	;mov byte [esi], al
	;inc edi
	;inc esi
	;dec ecx

	jnz cicloInterno
	;AVANZAR LO QUE FALTE HASTA EL PROXIMO MULTIPLO DE 4 (BASURA)

	mov eax, anchoPantalla
	mov ecx, anchoPiso
	sub eax, ecx	; Busco donde tengo que empezar la próxima linea
	mov ecx, 0x3	; Lo paso a bytes
	mul ecx
	add esi, eax
	;parece que tengo que avanzar 2 el piso, porque mide menos..
	inc edi 		; Esto no se porque pero hace cosas copadas.... Tipo, hace aparecer la mitad en colores
	inc edi 		; Esto no se porque pero hace cosas copadas.... Tipo, hace aparecer la mitad en colores

	dec ebx	;contador de cantidad de filas restantes a pintar
	jnz cicloExterno

	pop eax
	dec eax
	push eax
	jnz cicloMasExterno
fin:
	pop ebx 
    pop esi
    pop edi
    pop ebx
    pop ebp

    ret
