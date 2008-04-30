; int apagar(char *image, int w, int h, int *cont)

global apagar

extern malloc
extern free

section .text


apagar:
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
