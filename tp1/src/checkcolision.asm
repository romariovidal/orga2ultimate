; int checkcolision(int paredx, int paredy, int paredxw, int paredyh, int mariox, int marioy, int marioxw, int marioyh)

%define paredx 	[ebp+8]
%define paredy 	[ebp+12]
%define paredxw	[ebp+16]
%define paredyh	[ebp+20]
%define mariox	[ebp+24]
%define marioy	[ebp+28]
%define marioxw	[ebp+32]
%define marioyh	[ebp+36]


global checkcolision

section .text


checkcolision:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	mov ebx, paredx
	cmp ebx, marioxw
	ja noHayColision	; salto si paredx es mayor que marioxw

	mov ebx, paredxw
	cmp ebx, mariox
	jb noHayColision	; salto si paredxw es menor que mariox

	mov ebx, paredy
	cmp ebx, marioyh
	ja noHayColision	; salto si paredy es mayor que marioyh

	mov ebx, paredyh
	cmp ebx, marioy
	jb noHayColision	; salto si paredyh es menor que marioy

	mov eax, 1
	jmp fin

noHayColision:
	xor eax, eax	; devuelve 0 si no se solapan

fin:
	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
