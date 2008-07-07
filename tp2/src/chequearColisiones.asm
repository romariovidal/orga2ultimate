; void chequearColisiones(short unsigned* puntos, short unsigned longPuntos,
;                        short unsigned x1, short unsigned y1, byte* res);


%define puntos 	[ebp+8]
%define longPuntos 	[ebp+12]
%define x1	[ebp+16]
%define y1	[ebp+20]
%define res	[ebp+24]

extern printf

global chequearColisiones

section .data
formato1 db "Llamado1 con (%d)",10,0
formato2 db "Llamado2 con (%d)",10,0
formato3 db "Llamado3 con (%d)",10,0
formato4 db "Llamado4 con (%d)",10,0
formato5 db "Llamado5 con (%d)",10,0

section .text


chequearColisiones:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	mov esi, puntos
	mov edi, res
	mov ecx, longPuntos

	shr ecx, 3
	movq mm0, x1	; tengo en mm0 = 0, y1, 0, x1



ciclo:	
	; mis rectangulos en pantalla solo tienen 4 valores distintos seran a, b, c, d en orden de aparicion
	
	movq mm1, [esi]	; tengo en mm1 = ys2, xs2, ys1, xs1 o sea b, c, b, a
	psrlq mm1, 32	; mm1 = 0, 0, b, c
	movq mm2, [esi + 8]	; tengo en mm2 = ys4, xs4, ys3, xs3 o sea d, a, d, c
	psrlq mm2, 32	; mm2 = 0, 0, d, a
		
	pshufw mm3, mm0, 11101110b	; mm3 = 0, y1, 0, y1
	pshufw mm4, mm1, 11110111b	; mm4 = 0, 0, b, 0
	pshufw mm5, mm2, 01111111b	; mm5 = d, 0, 0, 0
	por mm4, mm5
	por mm3, mm4	; mm3 = d, y1, b, y1

	pshufw mm4, mm3, 00111001b	; mm4 = y1, d, y1, b
	psubw mm3, mm4	; mm3 = d-y1, y1-d, b-y1, y1-b

	pshufw mm4, mm1, 11000011b	; mm4 = 0, c, c, 0
	pshufw mm5, mm2, 00111100b	; mm5 = a, 0, 0, a
	por mm4, mm5	; mm4 = a, c, c, a
	pmaddwd mm4, mm3	; mm4 = a(d-y1)+c(y1-d), c(b-y1)+a(y1-b)

	pxor mm6, mm6
	movq mm7, mm4
	pcmpgtd mm7, mm6
	pxor mm6, mm6
	pcmpeqd mm6, mm6
	pxor mm7, mm6	; completa con 1 donde es negativa o 0 el area, termine area 1 y 3

	jmp adelante1
ciclo1:
	jmp ciclo

adelante1:

	pshufw mm4, mm1, 11111101b	; mm4 = 0, 0, 0, b
	pshufw mm5, mm2, 11011111b	; mm5 = 0, d, 0, 0
	por mm4, mm5
	pshufw mm5, mm4, 11001110b
	psubw mm4, mm5	; mm4 = 0, d-b, 0, b-d
	
	pshufw mm5, mm0, 11001100b	; mm5 = 0, x1, 0, x1
	pmaddwd mm4, mm5	; mm4 = x1(d-b), x1(b-d)

	pshufw mm5, mm1, 00001111b	; mm4 = c, c, 0, 0
	pshufw mm6, mm2, 11110000b	; mm5 = 0, 0, a, a
	por mm5, mm6
	pshufw mm3, mm3, 10010011b	; mm3 = y1-d, b-y1, y1-b, d-y1
	pmaddwd mm3, mm5	; c(y1-d)+c(b-y1), a(y1-b)+a(d-y1)

	paddd mm3, mm4	; c(y1-d)+x1(d-b)+c(b-y1), a(y1-b)+x1(b-d)+a(d-y1)
;	pxor mm4, mm4
;	pcmpgtd mm3, mm4	; completa con 1 donde es positiva el area, termine area 2 y 4

	pxor mm6, mm6
	pcmpgtd mm3, mm6
	pxor mm6, mm6
	pcmpeqd mm6, mm6
	pxor mm3, mm6	; completa con 1 donde es negativa o 0 el area, termine area 1 y 3


	jmp adelante2
ciclo2:
	jmp ciclo1

adelante2:

	pand mm7, mm3	; me quedo con 1 si tengo todas negativas o 0 - 1 con 2 y 3 con 4
	movq mm6, mm7
	psrlq mm7, 32
	pand mm7, mm6	; tengo en la parte alta 1 si todos son negativas o 0 si no


	movd ebx, mm7

;	push ebx
;	mov edx, formato5
;	push edx
;	call printf
;	add esp, 8

	cmp ebx, 0

	jne cambiar
cambie:
	add edi, 1
	add esi, 16
	loop ciclo2

	jmp fin

cambiar:
	mov al, 1
	mov [edi], al
	jmp cambie


fin:
	pop esi
	pop edi
	pop ebx
	pop ebp

	ret

