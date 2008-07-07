; void generarRayo( unsigned int xI, unsigned int yI, unsigned int xF, unsigned int yF,
;			 void* screen, unsigned int anchoPantalla)


%define xI 		[ebp+8]
%define yI		[ebp+12]
%define xF		[ebp+16]
%define yF 		[ebp+20]
%define screen 	[ebp+24]
%define anchoP	[ebp+30]

%define nuestroxF 	[ebp-4]
%define nuestroyF	[ebp-8]

global generarRayo

section .data
CUARENTASOBRECINCO DD 	8.0;
CUATROSOBRECIENTOVEINTIOCHO DD 0.03125;
DOSCINCUENTISEIS DD 256.0;

;formato db "Llamado con (%d, %d, %d, %d)",10,0

section .text

generarRayo:
    push ebp
    mov ebp, esp
	sub esp, 8
    push ebx
    push edi
    push esi


fin:
    pop esi
    pop edi
    pop ebx
	add esp, 8
    pop ebp

    ret
