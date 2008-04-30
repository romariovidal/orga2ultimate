; void generarFondo (char *screen, int ws, int hs, char *piso, int wp, int wh)

global generarFondo

extern malloc
extern free

section .text


generarFondo:
    push ebp
    mov ebp, esp
    push ebx
    push edi
    push esi

; Nucleo de la funcion

    pop esi
    pop edi
    pop ebx
    pop ebp

    ret
