; int checkcolision(int paredx, int paredy, int paredxw, int paredyh, int marioRx, int marioRy, int marioRxw, int marioRyh)

global checkcolision

extern malloc
extern free

section .text


checkcolision:
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
