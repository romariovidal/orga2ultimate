; int apagar(char *image, int w, int h, int *cont)

%define sprite 		[ebp+8]
%define w 		[ebp+12]
%define h 		[ebp+16]
%define pCont 		[ebp+20]

%define fucsia 		0xFF00FF00
%define hacerRGB 	0xFFFFFF00
%define borrarFucsia	0x00000000
%define borrarRojo	0x00FFFFFF

extern printf

global apagar

section .data
formato db "Llamado con (%d)",10,0
mascaraFucsia db 0x00, 0xFF, 0x00, 0xFF, 0xFF, 0x00, 0xFF, 0x00
fucsiaCorrido db 0x00, 0xFF, 0x00, 0xFF


section .text

apagar:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	mov esi, sprite
	mov ecx, w
	mov edi, ecx
	

	;calculo la basura del ancho
	mov eax, ecx
	mov ecx, 0x3
	mul ecx

	and ecx, eax ; calculo el resto

	xor edx, edx ; inicializo en 0
	cmp ecx, 0
	je edxTieneLaBasura	; si el resto es 0 no agrego basura
	mov edx, 0x4
	sub edx, ecx	; calculo la basura, 4 - resto
	
edxTieneLaBasura:
;	movd mm6, edx	; mm6 tiene la basura

	mov eax, pCont
	mov eax, [eax]
	movd mm7, eax
	pshufw mm7, mm7, 00000000b	; mm7 = 0,d,0,d,0,d,0,d
	psllw mm7, 8
	movd mm7, eax
	pshufw mm7, mm7, 00001111b	; mm7 = 0,d,0,d,d,0,d,0
	psrlq mm7, 8
	pshufw mm7, mm7, 11010010b	; mm7 = 0,0,d,d,0,d,d,0

	movd mm5, [fucsiaCorrido]	; mm5 tiene la mascara para 1 pixel
	movq mm6, [mascaraFucsia]	; mm6 tiene la mascara
	mov ebx, h

cicloExterno:
	mov ecx, edi

cicloInterno:
	cmp ecx, 3	; verifico que me queden al menos 3 pixel
	jle ultimosPixel

	movq mm0, [esi]
	movq mm1, mm0
	psllq mm1, 16
	psrlq mm1, 8	; dejo los 2 pixel al medio
	movq mm4, mm1
;	psubd mm1, mm6
;	pxor mm2, mm2
;	pcmpeqd mm2, mm1	; 0 si fucsia, 1 si reemplazar
	pcmpeqd mm1, mm6

	pxor mm3, mm3
	pcmpeqd mm3, mm1

	pand mm3, mm7
	pand mm1, mm4
	por mm1, mm3


	
;	movq mm1, mm6
;	pand mm1, mm2	; 0 si no reemplazar, reemplazado
;	pxor mm2, mm2
;	pcmpeqd mm2, mm1	; 1 si no reemplazar, 0 si reemplazado
;	pand mm2, mm4	; valor anterior si no reemplazar, 0 si reemplazar
;	por mm1, mm2	; valor a guardar en el medio
	
	psrlq mm1, 8
	psrlq mm0, 48
	psllq mm0, 48
	por mm0, mm1
	movq [esi], mm0	; guarde lo que corresponde

	add esi, 6
	dec ecx
	loop cicloInterno

ultimosPixel:
	
	movd mm1, edx	; guardo la basura
	movd mm2, ebx	; guardo h

cicloUltimos:
	dec esi	; esto es para no traerme memoria que no me corresponde
	mov edx, [esi]
	movd ebx, mm5	; traigo la mascara
	mov dl, bl	; copio el byte que no me corresponde en la parte menos significativa
	sub ebx, edx
	jz reemplazar

finReemplazar:
	add esi, 4
	loop cicloUltimos
	
	movd edx, mm1	; recupero la basura
	movd ebx, mm2	; recupero h

	jmp finFila

reemplazar:
	mov byte [esi+1], 0
	mov byte [esi+2], al
	mov byte [esi+3], al

	jmp finReemplazar

finFila:
	add esi, edx
	
	dec ebx
	jnz cicloExterno

fin:
	mov esi, pCont
	mov eax, [esi]
	sub eax, 8
	mov [esi], eax

	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
