; int salta(int *saltando, int *bajando, int*posY, int *velocidad, int *crecerVelocidad, int piso)

global salta

extern malloc
extern free

section .text


salta:
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
