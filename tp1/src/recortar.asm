; int recortar(char *imagen, int paso, int wP, int w, int h, char *res)

%define imagen 	[ebp+8]
%define paso 	[ebp+12]
%define wP		[ebp+16]
%define w		[ebp+20]
%define h		[ebp+24]
%define res		[ebp+28]

;Variables locales
%define basuraAnchoSprite	[ebp-4]
%define anchoImgBytes		[ebp-8]
%define comienzoFilaCopia 	[ebp-12]

global recortar

section .text


recortar:
    push ebp
    mov ebp, esp
    sub esp, 12
    push ebx
    push edi
    push esi

	; esi tendrá el puntero a la imagen.
	; ebx a res
	mov esi, imagen 

	; busco el tam de cada sprite en bytes
		mov eax, wP
		mov ecx, 0x3
		mul ecx

		mov edi, eax ; en edi tengo el tam de la imagen en bytes

		mov ecx, 0x4
		div ecx

		mov ecx, 0x4
		sub ecx, edx	; calculo la basura, 4 - resto
;		add edi, ecx	; le agrego los bytes basura
		mov basuraAnchoSprite, ecx

	;Ya obtuve el tamaño

	;Idem pero para la imagen
		mov eax, w
		mov ecx, 0x3
		mul ecx

		mov ebx, eax ; en edi tengo el tam de la imagen en bytes

		mov ecx, 0x4
		div ecx

		mov ecx, 0x4
		sub ecx, edx	; calculo la basura, 4 - resto
		add ebx, ecx	; le agrego los bytes basura
;		mov ebx, eax
		mov anchoImgBytes, ebx
	;Ya obtuve el tamaño


	;Obtengo el lugar donde empieza el sprite buscado
	mov ecx, paso
	cmp ecx, 0
	jz posicionado
	

avanzarDeSprite:
	add esi, edi
	dec ecx
	jnz avanzarDeSprite
	;Ya estoy parado al comienzo de mi sprite
		
posicionado:
	mov ebx, res
	mov edi, h

cicloExterno:
    	mov ecx, wP   ;ecx contiene el ancho de la imagen en pixeles
	mov comienzoFilaCopia, esi

cicloInterno:
    mov eax, [esi]  ;traigo el dibujo de memoria    
    mov [ebx], eax

    add esi, 0x3
    add ebx, 0x3
    dec ecx

    jnz cicloInterno ;sigo la linea
	
	add ebx, basuraAnchoSprite
	mov esi, comienzoFilaCopia
	add esi, anchoImgBytes
	dec edi
	jnz cicloExterno
	

    pop esi
    pop edi
    pop ebx
	add esp, 12
    pop ebp

    ret
