; void recortar(char *imagen, int paso, int wp, int w, int h, char *res)

%define imagen 	[ebp+8]
%define paso 	[ebp+12]
%define wP	[ebp+16]
%define w	[ebp+20]
%define h	[ebp+24]
%define res	[ebp+28]

global recortar

section .text

recortar:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	; esi tendrá el puntero a la imagen.
	mov esi, imagen 

	;calculo la basura del ancho para generar el sprite
	mov eax, wP
	mov ecx, 0x3
	mul ecx

	and ecx, eax ; calculo el resto

	xor ebx, ebx ; inicializo en 0
	cmp ecx, 0
	je sumarBasuraSprite	; si el resto es 0 no agrego basura
	mov ebx, 0x4
	sub ebx, ecx	; calculo la basura, 4 - resto
	
sumarBasuraSprite:
	mov edi, eax	; en edi tengo el tam del sprite en bytes
	movd mm7, ebx ; guardo la basura del sprite en mm7

	;Idem pero para la imagen
	mov eax, w
	mov ecx, 0x3
	mul ecx

	and ecx, eax ; calculo el resto

	xor ebx, ebx ; inicializo en 0
	cmp ecx, 0
	je noHayBasura	; si el resto es 0 no agrego basura
	mov ebx, 0x4
	sub ebx, ecx	; calculo la basura, 4 - resto

	add eax, ebx	; le agrego los bytes basura

noHayBasura:
	movd mm6, eax	; guardo en mm6 el ancho de la imagen en bytes anchoImgBytes
	;Ya obtuve el tamaño

	mov edx, edi	; en edx tengo el tam del sprite en bytes

	;Obtengo el lugar donde empieza el sprite buscado
	mov ecx, paso
	cmp ecx, 0
	jz posicionado

avanzarDeSprite:
	add esi, edx
	loop avanzarDeSprite
	;Ya estoy parado al comienzo de mi sprite
		
posicionado:
	mov edi, res
	mov ebx, h

	mov ecx, 0x7
	and ecx, edx
	movd mm5, ecx	; tengo en mm5 el resto del sprite restoSprite

cicloExterno:
    	mov ecx, edx
	shr ecx, 3
	movd mm4, esi ; guardo el comienzo del sprite comienzoFilaCopia

cicloInterno:
	movq mm0, [esi]
	movq [edi], mm0

	add esi, 8
	add edi, 8
	loop cicloInterno
	
	movd ecx, mm5	; restoSprite
	cmp ecx, 0
	je noHayResto

	cld
	rep movsb

noHayResto:
	movd ecx, mm7	; basuraAnchoSprite
	add edi, ecx
	movd esi, mm4	; comienzoFilaCopia
	movd ecx, mm6	; anchoImgBytes
	add esi, ecx
	dec ebx
	jnz cicloExterno

	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
