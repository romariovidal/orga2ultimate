; void generarRayo( unsigned int xpI, unsigned int ypI, unsigned int xpF, unsigned int ypF,
;			 void* screen, unsigned int anchoPantalla)


%define sprite 		[ebp+8]
%define w 		[ebp+12]
%define h 		[ebp+16]
%define pCont 		[ebp+20]

%define fucsia 		0xFF00FF00
%define hacerRGB 	0xFFFFFF00
%define borrarFucsia	0x00000000
%define borrarRojo	0x00FFFFFF

extern printf

global generarRayo

section .data
formato db "Llamado con (%d, %d, %d, %d)",10,0


section .text

generarRayo:
    push ebp
    mov ebp, esp
    push ebx
    push edi
    push esi


    pop esi
    pop edi
    pop ebx
    pop ebp

    ret
