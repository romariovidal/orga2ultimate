; int blit(char *image, int w, int h)

%define imagen  [ebp+8]
%define w       [ebp+12]
%define h       [ebp+16]

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
	dec esi		; Esta trampa es por lo de little-endian
	
	;calculo la basura del ancho
        mov eax, w
        mov ecx, 0x3
        mul ecx

        mov edi, eax ; en edi tengo el tam de la imagen en bytes

        mov ecx, 0x4
        div ecx

        add edi, edx    ; le agrego los bytes basura ;en edx queda la basura


	;Reemplazo

		mov edi, h
cicloExterno:
		mov ecx, w

cicloInterno:
		mov eax,[esi]
		and eax, hacerRGB
		sub eax, fucsia
		jz reemplazar

	regresar:
		add esi, 0x3
		dec ecx
		jnz cicloInterno

		add esi, edx ; agrego la basura
		dec edi
		jnz	cicloExterno
		jmp fin

reemplazar:
		or eax, azul
		mov [esi], eax
	
		jmp regresar

fin:
    pop esi
    pop edi
    pop ebx
    pop ebp

    ret