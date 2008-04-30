; int recortar(char *imagen, int paso, int wP, int w, int h, char *res)

global recortar

extern malloc
extern free

section .text


recortar:
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
