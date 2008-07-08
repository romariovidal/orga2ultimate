; void generarFondo (char* screen, int ws, int hs, char* piso, int wp, int hp);

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

generarFondo:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	mov eax, altoPantalla
	mov edx, altoPiso
	movd mm4, edx	; en mm4 tengo el alto del piso
	mov edi, pScreen ; esi apunta al byte actual que vamos a pintar
	sub eax, edx
	;eax contiene el alto del cielo
	
	mov ecx, anchoPantalla
	mov esi, ecx	; tengo en esi anchoPantalla
	mul ecx
	mov ecx, 0x3
	mul ecx
	mov ecx, 0x8
	div ecx	; calculo el total de veces que necesito iterar para pintar el cielo usando registros de mmx 
	mov ecx, eax

	pxor mm0, mm0

cicloCielo:
	movq [edi], mm0
	add edi, 8
	loop cicloCielo
	
	cmp edx, 0
	je dibujarPiso
	mov ecx, edx
	xor al, al

cicloResto:
	mov byte[edi], al
	inc edi
	loop cicloResto

dibujarPiso:

	;Inicio contador de veces que aparecerá
	mov eax, esi
	xor edx, edx
	mov ecx, anchoPiso
	div ecx
	movd mm6, eax	; en mm6 tengo las veces A Copiar
	
	;si necesito pintasr medio piso tengo el resto en edx	

	;calculo la basura del ancho
	mov eax, ecx
	mov ecx, 0x3
	mul ecx

	; en eax tengo el tam de la imagen en bytes

	and ecx, eax ; calculo el resto

	xor ebx, ebx ; inicializo en 0
	cmp ecx, 0
	je sumarBasuraSprite	; si el resto es 0 no agrego basura
	mov ebx, 0x4
	sub ebx, ecx	; calculo la basura, 4 - resto
	
sumarBasuraSprite:
	mov edx, eax	; en edx tengo el tam de la imagen en bytes
	add edx, ebx ; tengo el tam de la imagen con la basura
	movd mm5, edx	; en mm5 tengo el ancho del piso con basura anchoPisoConBasura

;	movd ecx, mm4
;	mov cantFilasAPintar, ecx

	; en mm4 tengo la cantidad de filas a pintar

	mov esi, pPiso
	mov edx, esi
	movd ebx, mm6	; traigo las veces a copiar

	mov ecx, 0x7
	and ecx, eax ; calculo el resto
	movd mm3, ecx	; en mm3 tengo el resto del Piso

	; en eax tengo el tam de la imagen en bytes

	shr eax, 3
	mov ecx, eax

pintarPiso:
	movq mm0, [esi]
	movq [edi], mm0
	add esi, 8
	add edi, 8
	loop pintarPiso

	movd ecx, mm3

	cmp ecx, 0
	je noHayResto	

	cld
	rep movsb

noHayResto:
	dec ebx
	cmp ebx, 0
	je finFila
	
	mov ecx, eax
	mov esi, edx
	jmp pintarPiso

finFila:
	movd ecx, mm4	; cantFilasAPintar
	dec ecx
	cmp ecx, 0
	je finPiso
	
	movd mm4, ecx	; cantFilasAPintar
	movd ecx, mm5	; anchoPisoConBasura
	add edx, ecx
	mov esi, edx
	movd ebx, mm6	; vecesACopiar
	mov ecx, eax
	jmp pintarPiso

finPiso:

	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
