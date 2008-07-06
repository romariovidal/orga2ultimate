; int blit(char *image, int w, int h)

%define imagen  [ebp+8]
%define w       [ebp+12]
%define h       [ebp+16]

section .data

mascaraFucsia db 0x00, 0xFF, 0x00, 0xFF, 0xFF, 0x00, 0xFF, 0x00
fucsiaCorrido db 0x00, 0xFF, 0x00, 0xFF

%define fucsia 		0xFF00FF00
%define hacerRGB 	0xFFFFFF00
%define borrarFucsia	0x00000000
%define azul		0x0096FF00

global blit

section .text

blit:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	mov esi, imagen

	;calculo la basura del ancho para generar el sprite
	mov ebx, w
	mov eax, ebx
	mov ecx, 0x3
	mul ecx

	and ecx, eax ; calculo el resto

	xor edi, edi ; inicializo en 0
	cmp ecx, 0
	je ediTieneLaBasura	; si el resto es 0 no agrego basura
	mov edi, 0x4
	sub edi, ecx	; calculo la basura, 4 - resto
	
ediTieneLaBasura:
	movd mm6, edi	; mm6 tiene la basura

	movd mm5, [fucsiaCorrido]	; mm5 tiene la mascara para 1 pixel

	; en ebx dejo el ancho de la imagen en pixel
	mov edi, h

	movq mm7, [mascaraFucsia]
	
cicloExterno:
	mov ecx, ebx
	
cicloInterno:
	cmp ecx, 3	; verifico que me queden al menos 3 pixel
	jle ultimosPixel

	movq mm0, [esi]
	movq mm2, mm0
	psllq mm2, 16
	psrlq mm2, 8	; dejo los 2 pixel al medio
	movq mm1, mm7
	pcmpeqd mm1, mm2
	pandn mm1, mm2
	psrlq mm1, 8
	psrlq mm0, 48
	psllq mm0, 48
	por mm0, mm1
	movq [esi], mm0

	add esi, 6
	dec ecx
	loop cicloInterno

ultimosPixel:
	dec esi	; esto es para no traerme memoria que no me corresponde
	mov eax, [esi]
	movd edx, mm5	; traigo la mascara
	mov dl, al	; copio el byte que no me corresponde en la parte menos significativa
	sub edx, eax
	jz reemplazar

finReemplazar:
	add esi, 4
	loop ultimosPixel
	
	jmp finFila

reemplazar:
	mov dl, al	; el byte que no me corresponde
	mov [esi], edx
	jmp finReemplazar	

finFila:
	movd eax, mm6	; traigo la basura	
	add esi, eax
	
	dec edi
	jnz cicloExterno
	
fin:
	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
