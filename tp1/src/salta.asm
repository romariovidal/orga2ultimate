; int salta(int *saltando, int *bajando, int*posY, int *velocidad, int *crecerVelocidad, int piso)

%define saltando	[ebp+8]
%define bajando 	[ebp+12]
%define posY		[ebp+16]
%define velocidad	[ebp+20]
%define crecerVelocidad	[ebp+24]
%define piso		[ebp+28]

;Variables locales
%define c1	2
%define c2	20
%define vel_ini	10

global salta

section .text


salta:
	push ebp
	mov ebp, esp
	push ebx
	push edi
	push esi

	; veo si esta saltando
	mov eax, saltando
	mov ebx, [eax]
	cmp ebx, 1
	jne noEstaSaltando

	; guardo posY = posY - velocidad / c1
	xor edx, edx
	mov ebx, velocidad
	mov eax, [ebx]
	mov ecx, c1
	div ecx
	mov edx, posY
	mov edi, [edx]
	sub edi, eax

	mov eax, [ebx]	; pongo la velocidad en eax

	; veo si esta creciendo la velocidad
	mov edx, crecerVelocidad
	mov esi, [edx]
	cmp esi, 1
	jne noCreceVelocidad

	; incremento velocidad
	inc eax

	; veo si llegue a la vel maxima
	cmp eax, c2
	jb cambieVelocidad ; salto si la velocidad es menor a la vel maxima
	
	; guardo 0 en crecerVelocidad
	xor esi, esi
	mov [edx], esi

	jmp cambieVelocidad ; salto porque ya crecio la velocidad y no tengo que decrementar

noCreceVelocidad:
	; decremento la velocidad
	dec eax

cambieVelocidad:
	cmp edi, 0
	jg noLlegueAlTecho	; salto si edi es mayor que 0 (usando signo)
	
	xor edi, edi	; pongo la posY en 0
	jmp llegueAlTecho

noLlegueAlTecho:
	cmp eax, 0	; veo si la velocidad es 0
	jne velNoEsCero

llegueAlTecho:
	; pasa si llegue al techo o la vel es 0
	mov eax, 1	; aca tenia la velocidad, le pongo 1, reseteo
	mov edx, bajando
	mov [edx], eax	; pongo bajando en 1
	xor ecx, ecx
	mov edx, saltando
	mov [edx], ecx	; pongo en 0 saltando
	
velNoEsCero:
	mov edx, posY
	mov [edx], edi	; actualizo en memoria posY
	mov [ebx], eax	; actualizo la velocidad en memoria

; --------------- Aca termina la primera parte --------------

noEstaSaltando: 
	; veo si esta bajando
	mov eax, bajando
	mov ebx, [eax]
	cmp ebx, 1
	jne noEstaBajando

	; guardo posY = posY + velocidad / c1
	xor edx, edx
	mov ebx, velocidad
	mov eax, [ebx]
	mov ecx, c1
	div ecx
	mov edx, posY
	mov edi, [edx]
	add edi, eax

	; actualizo el valor de la velocidad
	mov eax, [ebx]
	inc eax

	; veo si llegue o me pase del piso
	cmp edi, piso
	jl noLlegueAlPiso	; salto si posY es menor que el piso
	
	mov edi, piso	; pongo el valor del piso a posY
	xor esi, esi
	mov ecx, bajando
	mov[ecx], esi	; pongo bajando en 0
	mov eax, vel_ini	; pongo vel ini al valor de la velocidad
	inc esi
	mov ecx, crecerVelocidad
	mov [ecx], esi	; pongo 1 en crecerVelocidad

noLlegueAlPiso:
	mov [ebx], eax	; actualizo la velocidad en memoria
	mov [edx], edi	; actualizo la posY en memoria

noEstaBajando:

	pop esi
	pop edi
	pop ebx
	pop ebp

	ret
