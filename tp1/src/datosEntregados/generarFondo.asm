; void generarFondo (char *screen, int ws, int hs, char *piso, int wp, int wh)

global generarFondo

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

;var locales
%define punteroAPiso	[ebp-4]
%define cantPintadasSuelo [ebp-8]

generarFondo:
    push ebp
    mov ebp, esp
	sub esp, 8
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

	dec eax	;contador de cantidad de filas restantes a pintar
	jnz cicloGrande


dibujarPiso:
	;edx tendrá la cantidad de veces a copiar el dibujo
	;esi tendrá el puntero a la pantalla
	;edi tendrá el puntero a la imagen

	;Inicio contador de veces que aparecerá
	mov eax, anchoPantalla
	mov ecx, 0x3
	mul ecx
	mov ebx, eax
	
	mov eax, anchoPiso
	mul ecx
	mov edi, eax
	inc edi

	mov eax, ebx
	div edi
	
	mov edx, eax
	inc eax ;Esto es porque decremento antes del salto
	mov cantPintadasSuelo, eax
	;Fin de contador de veces que aparecerá.

	mov ebx, altoPiso 	; cantidad de iteraciones de un cacho
    mov edi, pPiso  ;edi tiene el puntero al comienzo del dibujo

	mov punteroAPiso, esi 	;guardo el punto donde empecé a dibujar.
	jmp cicloExterno

cicloMasExterno:
	mov esi, punteroAPiso
	dec esi	
	mov eax, anchoPiso      ;eax contiene el ancho del piso en píxeles
	mov	ecx, 0x3
	mul ecx
	add esi, eax
	inc esi
	mov punteroAPiso, esi
	mov edi, pPiso
	mov ebx, altoPiso 	; cantidad de iteraciones de un cacho

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

	jnz cicloInterno

	mov eax, anchoPantalla
	mov ecx, anchoPiso
	sub eax, ecx	; Busco donde tengo que empezar la próxima linea
	mov ecx, 0x3	; Lo paso a bytes
	mul ecx
	add esi, eax
	;parece que tengo que avanzar 2 el piso, porque mide menos..
	inc edi ; Avanzo lo que me falta hasta completar la palabra
	inc edi ; Debería cambiar esto por el resto de la división

	dec ebx	;contador de cantidad de filas restantes a pintar
	jnz cicloExterno

	mov eax, cantPintadasSuelo
	dec eax
	mov cantPintadasSuelo, eax
	cmp eax, 0
	jnz cicloMasExterno
fin:
    pop esi
    pop edi
    pop ebx
	add esp, 8
    pop ebp

    ret
