; int blit(char *image, int w, int h)

global blit

extern malloc
extern free

section .text


blit:
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
