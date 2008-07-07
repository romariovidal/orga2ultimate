; void generarRayo( unsigned int xI, unsigned int yI, unsigned int xF, unsigned int yF,
;			 void* screen, unsigned int anchoPantalla)


%define xI 		[ebp+8]
%define yI		[ebp+12]
%define xF		[ebp+16]
%define yF 		[ebp+20]

global generarRayo

;section .data
;formato db "Llamado con (%d, %d, %d, %d)",10,0

section .text

generarRayo:
    push ebp
    mov ebp, esp
    push ebx
    push edi
    push esi

	finit
	;alpha= sen^-1(opuesto/tangente)
	;opuesto= (xF,yF), (xF,yI)
	;tangente= sqrt((xI-xF)²+(yI-yF)²)

	fild dword xI
	fild dword yI
	fild dword xF
	fild dword yF

	fld st0
	fsub st0, st3 	; Tenemos el  opuesto

	fxch st0, st4

	fsubp st2, st0 	; Resto las y y pop
	fsubp st2, st0 	; Resto las x y pop

	fmul st0,st0	; Elevo al cuadrado los x
	fxch
	fmul st0,st0	; Elevo al cuadrado los y

	faddp st1, st0	; Sumo los cuadrados y pop

	fsqrt 	; Ya tenemos la tangente

	fdivp st1, st0	; dividimos opuesto/tangente

	;Me preparo para hacer arctan
	fld st0
	fmul st0, st0

	fld1

	fsubrp st1, st0

	fsqrt		;en st0 tengo sqrt(1-x^2) y en st1 tengo x

	fpatan		; arctan(x/sqrt(1-x^2) )
	
    pop esi
    pop edi
    pop ebx
    pop ebp

    ret
