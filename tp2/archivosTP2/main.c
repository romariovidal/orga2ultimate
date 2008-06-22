#include "include/SDL.h"
#include "math.h"

extern void chequearColisiones(unsigned short *puntos, short unsigned longPuntos, short unsigned x, short unsigned y, char *resV);

extern void generarRayo (unsigned int posInicial_x, unsigned int posInicial_y, unsigned int posFinal_x, unsigned int posFinal_y, char* screen, unsigned int anchoP);

extern void generarFondo (char *screen, int ws, int hs, char *piso, int wp, int wh);

extern int recortar(char *imagen, int paso, int wP, int w, int h, char *res);

extern int blit(char *image, int w, int h);

extern int apagar(char *image, int w, int h, int *cont);


int salta(int *saltando, int *bajando, int*posY, int *velocidad, int *crecerVelocidad, int piso)
{
	if (saltando[0]==1)
	{	
		posY[0]=posY[0]-(velocidad[0] >> 2);
		if (crecerVelocidad[0]==1)
		{
			velocidad[0]++;
			if (velocidad[0]==30) crecerVelocidad[0]=0;
		}else
		{
			velocidad[0]--;
		}
		if (posY[0] <= 0) posY[0]=0;
		if (posY[0] <= 0 || velocidad[0]==0) 
		{
			
			saltando[0]=0;
			bajando[0]=1;
			velocidad[0]=1;
		}
	}
	if (bajando[0]==1)
	{
		posY[0]=posY[0]+(velocidad[0] >> 2);
		velocidad[0]++;
		if (posY[0] >= piso) 
		{
			posY[0]=piso;
			bajando[0]=0;
			velocidad[0]=20;
			crecerVelocidad[0]=1;
		}
	}
}

typedef struct mouse
{
	int x;
	int y;
	int boton;
} Mouse;

int main(int argc, char *argv[]) 
{

	SDL_Surface *screen, *res, *resCoin, *resQ, *piso, *coin, *pipe, *marioSpriteW, *marioSpriteWL, *marioStandL;
	SDL_Surface *marioJump, *quest, *cloud, *peach, *victoria, *marioJumpL, *coins, *estupe, *bs, *hspt, *hsptrx, *go, *marioStandR;
	SDL_Rect marioR, marioRE, areaCoin, areaCoinE, questR, pared, cloudR, peachR, victoriaR, pipeR, coinsR, estupeR, bsR, hsptR, hsptRR, goR;
	
	SDL_Event event;

	unsigned short puntos [208];
	char resV[26];
	char resP[1];
	unsigned short puntosP [8];

	int hsp = 0;
	int kk = 0;

	// Para las monedas

	int coinsRx;
	int coinsRy;
	int mE;
	int mMar=0;
	int mMar2=0;
	int mPe=0;
	int moN=0;

	int done=0, t3=0, t4=0;
	int i=0, j=0, c=0, f = 0, k=0, wP=0, m=0;
	int antX, antY, antPX, antPY, dec=0;

	int paso[1], t1[1], t2[1];
	int saltando[1], velocidad[1], bajando[1], crecerVelocidad[1], marioRy[1];
	int saltaMoneda[1], bajaMoneda[1], monedaY[1], velocidadMoneda[1], crecerVelMon[1], cont[1];

	// Para el Rayo
	unsigned int posInicial_x, posInicial_y;
	unsigned int posFinal_x, posFinal_y;

	int hsT1, hsT2, hspF=4;


	Mouse mouse;
	
	char *rojo = 0;
	char *verde = 0;
	char *azul = 0;

	char *rojoS = 0;
	char *verdeS = 0;
	char *azulS = 0;

	double ang;

	int largo = 40; 
	int x, y, x_1, y_1;
	int angulo = 0;
	float angulo2;
	int bpp = 3;
	Uint8 *p;

	Uint8 *keys;

	atexit(SDL_Quit);

	for (i = 0; i < 26; i++)
	{
		resV[i]=0;
	}

	// Iniciar SDL
	if (SDL_Init(SDL_INIT_VIDEO) < 0) {
		printf("No se pudo iniciar SDL: %s\n",SDL_GetError());
		exit(1);
	}

	// Activamos modo de video
	screen = SDL_SetVideoMode(1000,600,24,SDL_SWSURFACE);
	if (screen == NULL) {
		printf("No se puede inicializar el modo gráfico: \n",SDL_GetError());
		exit(1);
	}

	// Cargamos una Grafico para recortar a Mario del Sprite, utilizamos marioStand porque ajustan las medidas
	res = SDL_LoadBMP("imagenes/marioStandR.bmp");
    if ( res == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }
	
	// Cargamos gráfico de Suelo
	piso = SDL_LoadBMP("imagenes/piso.bmp");
    if ( piso == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	pipe = SDL_LoadBMP("imagenes/pipe.bmp");
    if ( pipe == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	coin = SDL_LoadBMP("imagenes/coin.bmp");
    if ( coin == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	marioStandR = SDL_LoadBMP("imagenes/marioStandR.bmp");
    if ( marioStandR == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	marioStandL = SDL_LoadBMP("imagenes/marioStandL.bmp");
    if ( marioStandL == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	hsptrx = SDL_LoadBMP("imagenes/hsptr.bmp");
    if ( hsptrx == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }


	marioSpriteW = SDL_LoadBMP("imagenes/marioSpriteW.bmp");
    if ( marioSpriteW == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }
	
	marioSpriteWL = SDL_LoadBMP("imagenes/marioSpriteWL.bmp");
    if ( marioSpriteWL == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	marioJump = SDL_LoadBMP("imagenes/marioJump.bmp");
    if ( marioJump == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	marioJumpL = SDL_LoadBMP("imagenes/marioJumpL.bmp");
    if ( marioJumpL == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	resCoin = SDL_LoadBMP("imagenes/mon.bmp");
    if ( resCoin == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	quest = SDL_LoadBMP("imagenes/quest.bmp");
	if ( quest == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	resQ = SDL_LoadBMP("imagenes/questB.bmp");
	if ( resQ == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	cloud = SDL_LoadBMP("imagenes/cloud.bmp");
	if ( cloud == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	peach = SDL_LoadBMP("imagenes/PrincessToadstool.bmp");
	if ( peach == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	coins = SDL_LoadBMP("imagenes/coins.bmp");
	if ( coins == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	estupe = SDL_LoadBMP("imagenes/estupe.bmp");
	if ( estupe == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	bs = SDL_LoadBMP("imagenes/bs.bmp");
	if ( bs == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	hspt = SDL_LoadBMP("imagenes/hspt.bmp");
	if ( hspt == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	go = SDL_LoadBMP("imagenes/go.bmp");
	if ( go == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }

	victoria = SDL_LoadBMP("imagenes/victoria.bmp");
	if ( victoria == NULL ) {
        printf("No pude cargar gráfico: %s\n", SDL_GetError());
        exit(1);
    }



	// Variable del paso del tiempo para las animaciones
	paso[0]=0;
	

	// Variables para saltar
	saltando[0]=0;
	bajando[0]=0;
	velocidad[0]=20;
	crecerVelocidad[0]=1;
	
	// Variables para saltar la moneda
	cont[0]=255;
	saltaMoneda[0]=0;
	bajaMoneda[0]=0;
	monedaY[0]=screen->h-300;
	velocidadMoneda[0]=20;
	crecerVelMon[0]=1;
	
	// Seteamos las posiciones del primer jugador
	marioR.x=200;
	marioR.y=screen->h-piso->h-res->h;
	marioR.h = res->h;
	marioR.w = res->w;
	
	// Es
	estupeR.x = 0;
	estupeR.y = 531;
	estupeR.h = estupe->h;
	estupeR.w = estupe->w;
	
	// Bs
	
	bsR.x = 0;
	bsR.y = 430;
	bsR.h = estupe->h;
	bsR.w = 107;

	// La moneda
	areaCoin.x=510;
	areaCoin.y=screen->h-300;
	areaCoin.w=resCoin->w;
	areaCoin.h=resCoin->h;

	hsptRR.x = 0;
	hsptRR.y = 430;
	hsptRR.w = hsptrx->w;
	hsptRR.h = hsptrx->h;

	hsptR.h = hspt->h;
	hsptR.w = hspt->w;

	// La quest
	questR.x=500;
	questR.y=screen->h-300;
	questR.w=64;
	questR.h=64;

	puntos[0]=questR.x;
	puntos[1]=questR.y;
	puntos[2]=questR.x+questR.w;
	puntos[3]=questR.y;
	puntos[4]=questR.x+questR.w;
	puntos[5]=questR.y+questR.h;
	puntos[6]=questR.x;
	puntos[7]=questR.y+questR.h;
	

	// Una pequeña pared
	pared.y=screen->h-100;
	pared.x=700;
	pared.h=piso->h;
	pared.w=piso->w;


	puntos[8]=pared.x;
	puntos[9]=pared.y;
	puntos[10]=pared.x+pared.w;
	puntos[11]=pared.y;
	puntos[12]=pared.x+pared.w;
	puntos[13]=pared.y+pared.h;
	puntos[14]=pared.x;
	puntos[15]=pared.y+pared.h;
	
	for (i = 0; i < 8; i++)
	{
		puntosP[i]=puntos[i+8];
	}
	resP[0]=0;


	goR.x = 500;
	goR.y = 150;
	goR.h = go->h;
	goR.w = go->w;

	// Una nube
	cloudR.y = 50;
	cloudR.x = 300;
	cloudR.w = cloud->w;
	cloudR.h = cloud->h;

	// Las moneditas
	coinsR.y = 400;
	coinsR.x = 150;
	coinsR.w = coins->w;
	coinsR.h = coins->h;

	// Ultimo cartel
	victoriaR.x=0;
	victoriaR.y=0;
	victoriaR.w=victoria->w;
	victoriaR.h=victoria->h;
	
	// Variables para medir el tiempo
	t1[0] = SDL_GetTicks();
	t3=t1[0];
	hsT1 = SDL_GetTicks();

	// Pipe
	pipeR.y=screen->h-114;
	pipeR.x=700;

	// Para el mouse
	mouse.boton = -1;
	mouse.x = 0;
	mouse.y = 0;

	

	posFinal_x = 0;

	// Comenzamos el Ciclo del Juego
	while(done == 0)
	{
		angulo = 0;
		
		// Vamos a hacer un rayito...
		if (mouse.boton == 1 && mMar2 != 5 && !( (keys[SDLK_LEFT]) || (keys[SDLK_RIGHT]) ) )
		{
			chequearColisiones(puntos, 208, mouse.x, mouse.y, resV);
			// Aca tengo que chequear las colisiones...
			if (resV[1]==1 && moN > 1)
			{
				f = 1;
				mPe = 5;
			}
					
			mE = 0;
			for (i = 2; i < 26; i++)
			{
				if (resV[i]==1) mE++;
			}
			
			if (resV[0]==1 && saltaMoneda[0]!=1 && bajaMoneda[0]!=1)
			{
				saltaMoneda[0]=1;
				cont[0]=255;
				resV[0]=0;
			}
			
			posFinal_x = mouse.x*3;
			posFinal_y = mouse.y;
			if (resV[1]==1 && moN < 4 || (mE < 24) && resV[1]==1)
			{
				mMar = 5;
				mPe = 5;
			}
			y = (posFinal_y)*3000;
			x = posFinal_x;

			p = (Uint8 *)screen->pixels + y + x;
			p[0]=255;
			p[1]=255;
			p[2]=255;

			// Punto para medir el rayo...
			if (mouse.x >= marioR.x)
			{
				posFinal_x = posFinal_x - (posFinal_x-(marioR.x*3+90)) + (posFinal_x-(marioR.x*3+90))/3;
			}
			if (mouse.x < marioR.x)
			{
				posFinal_x = posFinal_x - (int)(posFinal_x-(marioR.x*3)) + ((int)(posFinal_x-(marioR.x*3)))/3;
			}
			if (resV[0]==1) moN++;
			
			posInicial_y = marioR.y;
			posInicial_x = marioR.x*3;
			
			if(mouse.x > marioR.x)
			{
				generarRayo(posInicial_x + 90, posInicial_y+ 35, posFinal_x, posFinal_y, screen->pixels, screen->w);
			}else
			{
				generarRayo(posInicial_x, posInicial_y+ 35, posFinal_x, posFinal_y, screen->pixels, screen->w);
			}
		}

		


		// Mostramos todo.
		SDL_Flip(screen);

		if (f==1 && marioR.x>=865)
		{
			SDL_BlitSurface(victoria, NULL, screen, &victoriaR);
			goto fin;
		}


		

		// Generamos el fondo
		generarFondo(screen->pixels, 1000, 600, piso->pixels, piso->w, piso->h);
		
		// Dibujamos la pared
		if (mPe != 5)
		{
			blit(pipe->pixels, 64, 64);
			SDL_BlitSurface(pipe, NULL, screen, &pipeR);
		}
		
		// Dibujamos la nube
		blit(cloud->pixels, 128, 48);
		SDL_BlitSurface(cloud, NULL, screen, &cloudR);

		cloudR.x = 100;
		cloudR.y = 150;

		// Dibujamos la nube
		blit(cloud->pixels, 128, 48);
		SDL_BlitSurface(cloud, NULL, screen, &cloudR);
		cloudR.x = 850;
		cloudR.y = 130;
		

		// Dibujamos la nube
		blit(cloud->pixels, 128, 48);
		SDL_BlitSurface(cloud, NULL, screen, &cloudR);

		cloudR.x = 300;
		cloudR.y = 50;
		
		
		// Dibujamos la princesa
		peachR.y = screen->h-148;
		peachR.x = 900;
		peachR.h = peach->h;
		peachR.w = peach->w;
		blit(peach->pixels, 56, 96);
		if (f==1) SDL_BlitSurface(peach, NULL, screen, &peachR);

		// Mostramos las moneditas...

		c = 16;
		m = 2;
		coinsRx = coinsR.x;
		coinsRy = coinsR.y;
		for (j = 0; j < 3; j++)
		{
			for (i = 0; i < 8; i++)
			{
				if (resV[m]==0)
				{
					blit(coins->pixels, 16, 28);
					SDL_BlitSurface(coins, NULL, screen, &coinsR);

					puntos[c]=coinsR.x;
					puntos[c+1]=coinsR.y;
					puntos[c+2]=coinsR.x+coinsR.w;
					puntos[c+3]=coinsR.y;
					puntos[c+4]=coinsR.x+coinsR.w;
					puntos[c+5]=coinsR.y+coinsR.h;
					puntos[c+6]=coinsR.x;
					puntos[c+7]=coinsR.y+coinsR.h;
				}
				coinsR.x = coinsR.x + 25;
				c += 8;
				m++;
			}
			coinsR.x = coinsRx;
			coinsR.y = coinsR.y - 35;
		}
		coinsR.y = coinsRy;

		


		// Creando una animacion
		t2[0] = SDL_GetTicks();
		if (t2[0] - t1[0] >= 40)
		{
			if (paso[0] > 2)
			{paso[0]=0;}
			else {paso[0]++;}
			t1[0]=t2[0];
		}
		
		wP=32;
		// Guardo la posicion anterior
		antPX = areaCoin.x;
		antPY = areaCoin.y;

		// borramos
		areaCoinE.x=antPX;
		areaCoinE.y=antPY;
		areaCoinE.w=coin->w;
		areaCoinE.h=coin->h;
		SDL_FillRect(screen,&areaCoinE,SDL_MapRGB(screen->format,0,0,0));
		
		// La hago saltar si mario pega contra el bloque
		salta(saltaMoneda, bajaMoneda, monedaY, velocidadMoneda, crecerVelMon, screen->h-300);
		areaCoin.y = monedaY[0];
		// La animo
		recortar(coin->pixels, paso[0], 32, coin->w, coin->h, resCoin->pixels);
		
		if (bajaMoneda[0]==1) apagar(resCoin->pixels, areaCoin.w, areaCoin.h, cont);
		blit(resCoin->pixels, areaCoin.w, areaCoin.h);
		SDL_BlitSurface(resCoin, NULL, screen, &areaCoin);

		if (paso[0]!=3)
		{
			recortar(quest->pixels, paso[0], 64, quest->w, quest->h, resQ->pixels);
		}
		SDL_BlitSurface(resQ, NULL, screen, &questR);
		
		// Leyendo la entrada
		keys=SDL_GetKeyState(NULL);
		
		// Guardo la posicion anterior
		antX = marioR.x;
		antY = marioR.y;
		
		// Borro lo anterior
		marioRE.x=antX;
		marioRE.y=antY;
		marioRE.w=res->w;
		marioRE.h=res->h;
		SDL_FillRect(screen,&marioRE,SDL_MapRGB(screen->format,0,0,0));

		for (i = 3; i < 24; i++)
		{
			if (resV[i]==1 && mE==10)
			{
				if (resV[i-1]==0)
				{
					mMar = 5;
				}
			}
		}

		if (mMar == 5)
		{

			// Creando una animacion
			hsT2 = SDL_GetTicks();
			if (hsT2 - hsT1 >= 100)
			{
				if (hsp > 16)
				{hspF=1; hsp=0;}
				else {hsp++;}
				hsT1=hsT2;
			}
			if (hsp == 10) mMar2=5;
			if (hspF != 1) 
			{
				recortar(estupe->pixels, hsp, bs->w, estupe->w, estupe->h, bs->pixels);
				SDL_BlitSurface(bs, NULL, screen, &bsR);
			}else
			{
				recortar(hspt->pixels, hsp, hsptrx->w, hspt->w, hspt->h, hsptrx->pixels);
				SDL_BlitSurface(hsptrx, NULL, screen, &hsptRR);
			}
		}


		// Leo para saber donde mover el personaje
		
		if (mMar2 != 5)
		{
			if ((keys[SDLK_LEFT]))
			{
				if (mPe != 5) chequearColisiones(puntosP, 8, marioR.x-10, marioR.y+marioR.h, resP);
				
				if (resP[0]==0)
				{
					marioR.x=marioR.x-(2);
				}else
				{
					marioR.x = marioR.x+5;
					resP[0]=0;
				}
				
				if (marioR.x <= 0) marioR.x = 1000;
			}
			if ((keys[SDLK_RIGHT]))
			{
				if (mPe != 5) chequearColisiones(puntosP, 8, marioR.x+marioR.w, marioR.y+marioR.h, resP);
				if (resP[0]==0)
				{
					marioR.x=marioR.x+(2);
				}else
				{
					marioR.x=marioR.x-(2);
					resP[0]=0;
				}
				if (marioR.x >= 1000) marioR.x = 0;
			}


			// Lo animo
			t4 = SDL_GetTicks();
			marioR.w = res->w;
			marioR.h = res->h;
			
			if (t4 - t3 >= 100)
			{
				if (dec==0)
				{dec=1;}else
				{
					if(dec==1)
					{dec=2;}else
					{
						if(dec==2)
						{dec=0;}
					}
				}
				t3 = t4;
			}
			
				if(keys[SDLK_LEFT] && !keys[SDLK_RIGHT])
				{
					recortar(marioSpriteWL->pixels, dec, 32, marioSpriteWL->w, marioSpriteWL->h, res->pixels);
					blit(res->pixels, marioR.w, marioR.h);
					SDL_BlitSurface(res, NULL, screen, &marioR);
				}else
				{
					if(!keys[SDLK_LEFT] && keys[SDLK_RIGHT])
					{
						recortar(marioSpriteW->pixels, dec, 32, marioSpriteW->w, marioSpriteW->h, res->pixels);
						blit(res->pixels, marioR.w, marioR.h);
						SDL_BlitSurface(res, NULL, screen, &marioR);
						
					}else
					{
						if (mouse.boton == 1)
						{
							if (mouse.x < marioR.x)
							{
								blit(marioStandL->pixels, marioR.w, marioR.h);
								SDL_BlitSurface(marioStandL, NULL, screen, &marioR);
							}else
							{
								blit(marioStandR->pixels, marioR.w, marioR.h);
								SDL_BlitSurface(marioStandR, NULL, screen, &marioR);
							}
						}else
						{
							blit(marioStandR->pixels, marioR.w, marioR.h);
							SDL_BlitSurface(marioStandR, NULL, screen, &marioR);
						}
					}
				}
			}
		else
		{
			SDL_BlitSurface(go, NULL, screen, &goR);
		}
fin:

		while (SDL_PollEvent(&event)) 
		{	
			if (event.type == SDL_QUIT) {done=1;}
					
			if (event.type == SDL_KEYDOWN || event.type == SDL_JOYBUTTONDOWN)
			{
				if (event.key.keysym.sym == SDLK_ESCAPE) {
					done=1;
				}
				
			}
			if (event.type = SDL_MOUSEBUTTONDOWN && event.type != SDL_KEYDOWN)
				{
					if (event.button.button == SDL_BUTTON_RIGHT)
					{
						mouse.boton = 2;
						mouse.x = event.motion.x;
						mouse.y = event.motion.y;
					}else
					{
						if (event.button.button == SDL_BUTTON_LEFT)
						{
							mouse.boton = 1;
							mouse.x = event.motion.x;
							mouse.y = event.motion.y;
						}else
						{
							mouse.boton = -1;
						}
					}
				}
		}

   }

	return 0;
}
