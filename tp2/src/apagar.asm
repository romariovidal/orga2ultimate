; int apagar(char *image, int w, int h, int *cont)

%define sprite 		[ebp+8]
%define w 		[ebp+12]
%define h 		[ebp+16]
%define pCont 		[ebp+20]

%define fucsia 		0xFF00FF00
%define hacerRGB 	0xFFFFFF00
%define borrarFucsia	0x00000000
%define borrarRojo		0x00FFFFFF

extern printf

global apagar

section .data
formato db "Llamado con (%d, %d, %d, %d)",10,0


section .text

apagar:
    push ebp
    mov ebp, esp
    push ebx
    push edi
    push esi

	mov esi, sprite
	dec esi		; Esta trampa es por lo de little-endian
	mov ecx, w
	mov ebx, h
	mov edi, pCont
	mov edi, [edi]

	;push edi
	;push ebx
	;push ecx
	;push esi 
	;push formato
	;call printf
	;add esp, 20
	
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
		jnz reemplazar

	regresar:
		add esi, 0x3
		dec ecx
		jnz cicloInterno

		add esi, edx ; agrego la basura
		dec edi
		jnz	cicloExterno
		jmp fin

reemplazar:
		mov eax, pCont
		mov eax, [eax]

		mov byte [esi+1], 0
		mov byte [esi+2], al
		mov byte [esi+3], al

	
		jmp regresar

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
