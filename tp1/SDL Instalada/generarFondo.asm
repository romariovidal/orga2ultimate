;void generarFondo(char* screen, int ws, int hs, char* piso, int wp, int hp);

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


generarFondo:
	push ebp
	mov ebp, esp
	push esi
	push edi
	push ebx
	mov eax, altoPantalla
	mov edx, altoPiso
	mov esi, pScreen ; esi apunta al byte actual que vamos a pintar
	sub eax, edx
	;eax contiene el alto del cielo
		
cicloGrande:
	mov ecx, anchoPantalla
	;ecx contiene el ancho de la pantalla en pixeles	

cicloChico:
	mov db [esi], colorCieloR ;pinto de Rojo
	inc esi

	mov db [esi], colorCieloG ;pinto de Verde
	inc esi

	mov db [esi], colorCieloB ;pinto de Azul
	inc esi

	dec ecx; tiene la cantidad de pixeles que faltan pintar
	jnz cicloChico
	;AVANZAR LO QUE FALTE HASTA EL PROXIMO MULTIPLO DE 4 (BASURA)

	dec eax	;contador de cantidad de filas restantes a pintar
	jnz cicloGrande

dibujarPiso:


	pop ebx
	pop edi
	pop esi
