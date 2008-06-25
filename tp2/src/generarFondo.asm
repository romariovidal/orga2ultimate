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

;var locales
%define anchoPisoConBasura	[ebp-4]
%define cantFilasAPintar [ebp-8]
%define vecesACopiar [ebp-12]
%define restoPiso [ebp-16]

generarFondo:
	push ebp
	mov ebp, esp
	sub esp, 16
	push ebx
	push edi
	push esi

	mov eax, altoPantalla
	mov edx, altoPiso
	mov edi, pScreen ; esi apunta al byte actual que vamos a pintar
	sub eax, edx
	;eax contiene el alto del cielo
	
	mul dword anchoPantalla
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
	mov eax, anchoPantalla
	xor edx, edx
	div dword anchoPiso
	mov ebx, eax
	mov vecesACopiar, ebx
	
	;si necesito pintasr medio piso tengo el resto en edx	

	;calculo la basura del ancho
	mov eax, anchoPiso
	mov ecx, 0x3
	mul ecx

	mov ebx, eax ; en ebx tengo el tam de la imagen en bytes

	and ecx, eax ; calculo el resto

	xor eax, eax ; inicializo en 0
	cmp ecx, 0
	je sumarBasuraSprite	; si el resto es 0 no agrego basura
	mov eax, 0x4
	sub eax, ecx	; calculo la basura, 4 - resto
	
sumarBasuraSprite:
	mov edx, ebx	; en edx tengo el tam de la imagen en bytes
	add edx, eax ; tengo el tam de la imagen con la basura
	mov anchoPisoConBasura, edx

	mov eax, ebx	; en eax tengo el tam de la imagen en bytes

	mov ecx, altoPiso
	mov cantFilasAPintar, ecx
	mov esi, pPiso
	mov edx, esi
	mov ebx, vecesACopiar

	mov ecx, 0x7
	and ecx, eax ; calculo el resto
	mov restoPiso, ecx
	shr eax, 3
	mov ecx, eax

pintarPiso:
	movq mm0, [esi]
	movq [edi], mm0
	add esi, 8
	add edi, 8
	loop pintarPiso

	mov ecx, restoPiso

	cmp ecx, 0x0
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
	mov ecx, cantFilasAPintar
	dec ecx
	cmp ecx, 0
	je finPiso
	
	mov cantFilasAPintar, ecx
	add edx, anchoPisoConBasura
	mov esi, edx
	mov ebx, vecesACopiar
	mov ecx, eax
	jmp pintarPiso

finPiso:

	pop esi
	pop edi
	pop ebx
	add esp, 16
	pop ebp

	ret
