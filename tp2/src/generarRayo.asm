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

	mov edi, screen
	mov eax, anchoP
	mov ecx, 0x3

	mul ecx

	mov ecx, eax ;cant de píxeles por fila

	finit
	;alpha= arcsen(opuesto/tangente)
	;opuesto= (xF,yF), (xF,yI)
	;tangente= sqrt((xI-xF)²+(yI-yF)²)

	fild dword xI
	fild dword yI
	fild dword xF
	fild dword yF	;tam=4 elems

	fld st0			; tam= 5 elems
	; Pila:
	; st0 = yF
	; st1 = yF
	; st2 = xF
	; st3 = yI
	; st4 = xI
	fsub st0, st3 	; Tenemos el  opuesto
	fabs

	fxch st0, st4

	; Pila:
	; st0 = xI
	; st1 = yF
	; st2 = xF
	; st3 = yI
	; st4 = |yF-yI|

	fsubp st2, st0 	; Resto las y y pop. tam=4
	fsubp st2, st0 	; Resto las x y pop. tam=3
	; Pila:
	; st0 = xF -xI
	; st1 = yI - yF
	; st2 = |yF-yI|

	fmul st0,st0	; Elevo al cuadrado los x
	fxch
	fmul st0,st0	; Elevo al cuadrado los y
	; Pila:
	; st0 = (yI - yF)^2
	; st1 = (xF -xI)^2
	; st2 = |yF-yI|

	faddp st1, st0	; Sumo los cuadrados y pop. tam=2

	fsqrt 	; Ya tenemos la tangente
	; Pila:
	; st0 = sqrt((yI - yF)^2+(xF -xI)^2) <-tangente
	; st1 = |yF-yI|

	fld	st0			; tam = 3
	fxch st0, st2	; me guardo la tangente para más adelante

	fdivrp st1, st0	; dividimos opuesto/tangente. tam=2
	; Pila:
	; st0 = |yF-yI|/sqrt((yI - yF)^2+(xF -xI)^2) <- op/hip
	; st1 = sqrt((yI - yF)^2+(xF -xI)^2) <-tangente

	;Me preparo para hacer arctan
	fld st0			; tam=3
	fmul st0, st0	; 

	fld1			; tam=4
	fxch st1, st0

	fsubp st1, st0	; tam=3

	fsqrt		;en st0 tengo sqrt(1-x^2) y en st1 tengo x

	fpatan		; arctan(x/sqrt(1-x^2) ) ; tam = 2 

	;ESTADO DE LA PILA
	;	st0 = angulo
	;	st1 =  tangente u opuesto u liñita que une los 2 puntos (su tamaño)

	fldz

	;ESTADO DE LA PILA
	;	st0 = 0 <-- será x
	;	st1 = angulo
	;	st2 =  tangente u opuesto u liñita que une los 2 puntos (su tamaño)


	fld dword [CUARENTASOBRECINCO]
	fld dword [CUATROSOBRECIENTOVEINTIOCHO]

	;ESTADO DE LA PILA
	; 	st0 = 4/128
	;	st1 = 40.0/5.0
	;	st2 = 0 <-- será x
	;	st3 = angulo
	;	st4 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

	fldpi
	fmulp st1, st0

	;ESTADO DE LA PILA
	; 	st0 = 4*PI/128
	;	st1 = 40.0/5.0 o largo/5
	;	st2 = 0 <-- será x
	;	st3 = angulo
	;	st4 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

elCiclonQueNoEsCuervo:


	;CALCULANDO Y	
		fld st0
		fmul st0,st3
		fsin 

		fld st1
		fadd st0, st0	
		fadd st0, st0	
		fmul st0, st4
		fcos
		
		;ESTADO DE LA PILA
		;	st0 = cos(4Pi/128 * 4 * x)
		;	st1 = sin(4PI/128 * x)
		; 	De acá para abajo no se toca :-P
		; 	st2 = 4*PI/128
		;	st3 = 40.0/5.0 o largo/5
		;	st4 = 0 <-- será x
		;	st5 = angulo
		;	st6 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fmulp st1, st0
		fmul st0, st2
		;fsub st0,st0
		;ESTADO DE LA PILA
		;	st0 = cos(4Pi/128 * 4 * x) * sin(4PI/128) *40/5 <-- esto es y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
	;FIN CALCULANDO Y
	;CALCULANDO XF		
		fld st4	;angulo
		fcos
		fmul st0, st4 ;x
		
		fld st5	;angulo
		fsin
		fmul st0, st2; y
		;ESTADO DE LA PILA
		;	st0 = sin(angulo) * y
		;	st1 = cos(angulo) * x
		;	st2 = y
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5
		;	st5= 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		faddp st1, st0	; xf
		fistp dword nuestroxF
		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
		
	;FIN CALCULANDO XF	
	;CALCULANDO YF	
		fld st4 ;cargo el ángulo  
		fcos
		fmul st0, st1 ;cos * y
		fld st5 ;cargo el ángulo
		fsin
		fmul st0, st5 	;sen * x
		;ESTADO DE LA PILA
		;	st0 = sin(angulo) * x
		;	st1	= cos(angulo) * y
		;	st2 = y
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5
		;	st5= 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
	
		fsubp st1, st0
		fistp dword nuestroyF

		; prueba
		;fist dword nuestroyF
		fxch st3,st0
		;fist dword nuestroxF
		fxch st3,st0

		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
	;FIN CALCULANDO YF

	;jmp testPuto

	;DEJAMOS LA FPU POR UN RATO
	;VAMOS A PINTARRAJEAR UN PUNTITO EN LA PANTALLA

		mov eax, nuestroyF
		mov esi, nuestroxF
	
		;add eax, 1000b	
		add eax, yI
		;mov ecx, anchoP
		mov ecx, 3000
		mul ecx		; ancho de pantalla
		;add eax, yI

		;mov eax, 1000000		

		add eax, esi ; 
		add eax, esi ; 
		add eax, esi ; 
		add eax, xI
		mov ebx, edi ; copio el puntero de la pantalla
		
		add ebx, eax ; en ebx debo pintarrajear un píxel
		
		mov byte [ebx+0], 0xff ; azul
		mov byte [ebx+1], 0x00 ; verde
		mov byte [ebx+2], 0x00 ; rojo
	; TE PINTAMOS LA CARA
	; VOLVEMOS A LA FPU	
		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5 = 8
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

testPuto:
	;CALCULAR Y
		fsub st0, st0
		fadd st0, st3
	 
		fld st2 ; pongo un 8
		fmul st0, st0 ; 64
		fadd st0, st0 ;	128
		fadd st0, st0 ;	256
		
		fxch st0, st1		
		;ESTADO DE LA PILA
		;	st0 = x
		;	st1 = 256
		; 	De acá para abajo no se toca :-P
		; 	st2 = 4*PI/128
		;	st3 = 40.0/5.0 o largo/5 = 8
		;	st4 = 0 <-- será x
		;	st5 = angulo
		;	st6 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fprem 	; st0 = st0 mod st1

		fmul st0, st2
		fmul st0, st3
		;ESTADO DE LA PILA
		;	st0 = (x mod 256) 4PI/128 * 8
		;	st1 = 256
		; 	De acá para abajo no se toca :-P
		; 	st2 = 4*PI/128
		;	st3 = 40.0/5.0 o largo/5 = 8
		;	st4 = 0 <-- será x
		;	st5 = angulo
		;	st6 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fld st0
		fsin
		fxch st1, st0

		fxch st2, st0
		fsub st0, st0
		fxch st2, st0		
		;ESTADO DE LA PILA
		;	st0 = (x mod 256) 4PI/128 * 8
		;	st1 = sin ((x mod 256) 4PI/128 * 8)
		;	st2 = 0
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5 = 8
		;	st5 = 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fadd st0, st0
		fadd st2, st0
	
		fsin
		fxch st2, st0
		fadd st0, st0
		fcos
		;ESTADO DE LA PILA
		;	st0 = cos(((x mod 256) 4PI/128 * 8 )*2 *2)
		;	st1 = sin ((x mod 256) 4PI/128 * 8)
		;	st2 = sin (((x mod 256) 4PI/128 * 8 )*2)
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5 = 8
		;	st5 = 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fmulp st1, st0
		fmulp st1, st0
		;ESTADO DE LA PILA
		;	st0 = cos(...) * sin (...) * sin (...)
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5 = 8
		;	st3 = 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fmul st0, st1  ; st0 * largo/5
		fld st0			
		fadd st0, st0  ; st0 * largo/5 *2
		fadd st0, st0  ; st0 * largo/5 *4
		faddp st1, st0 ; st0 * largo/5 *5
		;ESTADO DE LA PILA
		;	st0 = largo * cos(...) * sin (...) * sin (...) <--esto es y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5 = 8
		;	st3 = 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
	;FIN CALCULAR Y
	;CALCULANDO XF		
		fld st4	;angulo
		fcos
		fmul st0, st4 ;x
		
		fld st5	;angulo
		fsin
		fmul st0, st2; y
		;ESTADO DE LA PILA
		;	st0 = sin(angulo) * y
		;	st1 = cos(angulo) * x
		;	st2 = y
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5
		;	st5= 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		faddp st1, st0	; xf
		fistp dword nuestroxF
		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
		
	;FIN CALCULANDO XF	
	;CALCULANDO YF	
		fld st4 ;cargo el ángulo  
		fcos
		fmul st0, st1 ;cos * y
		fld st5 ;cargo el ángulo
		fsin
		fmul st0, st5 	;sen * x
		;ESTADO DE LA PILA
		;	st0 = sin(angulo) *
		;	st1	= cos(angulo)
		;	st2 = y
		; 	De acá para abajo no se toca :-P
		; 	st3 = 4*PI/128
		;	st4 = 40.0/5.0 o largo/5
		;	st5= 0 <-- será x
		;	st6 = angulo
		;	st7 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
	
		fsubp st1, st0
		fistp dword nuestroyF

		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
	;FIN CALCULANDO YF
	;DEJAMOS LA FPU POR UN RATO
	;VAMOS A PINTARRAJEAR UN PUNTITO EN LA PANTALLA

		mov eax, nuestroyF
		mov esi, nuestroxF
	
		;add eax, 1000b	
		add eax, yI
		;mov ecx, anchoP
		mov ecx, 3000
		mul ecx		; ancho de pantalla
		;add eax, yI

		;mov eax, 1000000		

		add eax, esi ; 
		add eax, esi ; 
		add eax, esi ; 
		add eax, xI
		mov ebx, edi ; copio el puntero de la pantalla
		
		add ebx, eax ; en ebx debo pintarrajear un píxel
		
		mov byte [ebx+0], 0x00
		mov byte [ebx+1], 0xff
		mov byte [ebx+2], 0xff
	; TE PINTAMOS LA CARA
	; VOLVEMOS A LA FPU	
		;ESTADO DE LA PILA
		;	st0 = y
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= 0 <-- será x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)

		fsub st0, st0
		fld1 
		faddp st1, st0
		;ESTADO DE LA PILA
		;	st0 = 1
		; 	De acá para abajo no se toca :-P
		; 	st1 = 4*PI/128
		;	st2 = 40.0/5.0 o largo/5
		;	st3= x
		;	st4 = angulo
		;	st5 = tangente u opuesto u liñita que une los 2 puntos (su tamaño)
		fadd st3, st0
		
		fsubp st5, st0
		;ESTADO DE LA PILA
		; 	De acá para abajo no se toca :-P
		; 	st0 = 4*PI/128
		;	st1 = 40.0/5.0 o largo/5
		;	st2 = x + 1
		;	st3 = angulo
		;	st4 = tangente - 1

		fldz

		fcomip st0, st5 ; comparo. Si  st0<st5 es decir 0<tangente entonces carryF = 1

		jc elCiclonQueNoEsCuervo

fin:
    pop esi
    pop edi
    pop ebx
	add esp, 8
    pop ebp

    ret
