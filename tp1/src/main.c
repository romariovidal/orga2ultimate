#include <SDL.h>

extern void generarFondo (char *screen, int ws, int hs, char *piso, int wp, int wh);
extern int checkcolision(int paredx, int paredy, int paredxw, int paredyh, int marioRx, int marioRy, int marioRxw, int marioRyh);
extern int recortar(char *imagen, int paso, int wP, int w, int h, char *res);
extern int salta(int *saltando, int *bajando, int*posY, int *velocidad, int *crecerVelocidad, int piso);
extern int blit(char *image, int w, int h);
extern int apagar(char *image, int w, int h, int *cont);

int main(int argc, char *argv[]) 
{

	SDL_Surface *screen, *res, *resCoin, *resQ, *piso, *coin, *pipe, *marioSpriteW, *marioSpriteWL, *marioStand, *marioJump, *quest, *cloud, *peach, *victoria, *marioJumpL;
	SDL_Rect marioR, marioRE, areaCoin, areaCoinE, questR, pared, cloudR, peachR, victoriaR, pipeR;
	
	SDL_Event event;
	
	int done=0, t3=0, t4=0;
	int i=0, j=0, c=0, f = 0, k=0, wP=0;
	int antX, antY, antPX, antPY, dec=0;
	int paso[1], t1[1], t2[1];
	int saltando[1], velocidad[1], bajando[1], crecerVelocidad[1], marioRy[1];
	int saltaMoneda[1], bajaMoneda[1], monedaY[1], velocidadMoneda[1], crecerVelMon[1], cont[1];
	
	
	
	char *rojo = 0;
	char *verde = 0;
	char *azul = 0;

	char *rojoS = 0;
	char *verdeS = 0;
	char *azulS = 0;
	
	Uint8 *keys;

	atexit(SDL_Quit);

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
	res = SDL_LoadBMP("imagenes/marioStand.bmp");
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

	marioStand = SDL_LoadBMP("imagenes/marioStand.bmp");
    if ( marioStand == NULL ) {
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
	marioR.x=0;
	marioR.y=screen->h-piso->h-res->h;
	marioR.h = res->h;
	marioR.w = res->w;

	// La moneda
	areaCoin.x=510;
	areaCoin.y=screen->h-300;
	areaCoin.w=resCoin->w;
	areaCoin.h=resCoin->h;

	// La quest
	questR.x=500;
	questR.y=screen->h-300;
	questR.w=64;
	questR.h=64;

	// Una pequeña pared
	pared.y=screen->h-100;
	pared.x=700;
	pared.h=piso->h;
	pared.w=piso->w;

	// Una nube
	cloudR.y = 50;
	cloudR.x = 300;
	cloudR.w = cloud->w;
	cloudR.h = cloud->h;

	// Ultimo cartel
	victoriaR.x=0;
	victoriaR.y=0;
	victoriaR.w=victoria->w;
	victoriaR.h=victoria->h;
	
	// Variables para medir el tiempo
	t1[0] = SDL_GetTicks();
	t3=t1[0];

	// Pipe
	pipeR.y=screen->h-114;
	pipeR.x=700;
	
	// Comenzamos el Ciclo del Juego
	while(done == 0)
	{
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
		blit(pipe->pixels, 64, 64);
		SDL_BlitSurface(pipe, NULL, screen, &pipeR);
		
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
		SDL_FillRect(screen,&areaCoinE,SDL_MapRGB(screen->format,0,150,255));
				
		// La hago saltar si mario pega contra el bloque
		salta(saltaMoneda, bajaMoneda, monedaY, velocidadMoneda, crecerVelMon, screen->h-300);
		areaCoin.y = monedaY[0];
		// La animo
		
		recortar(coin->pixels, paso[0], 32, coin->w, coin->h, resCoin->pixels);
		
		if (bajaMoneda[0]==1) 
			printf("Me llamo con los siguiente valores: %d, %d, %d, %d \n",resCoin->pixels, areaCoin.w, areaCoin.h, *cont);
		if (bajaMoneda[0]==1) apagar(resCoin->pixels, areaCoin.w, areaCoin.h, cont);
		blit(resCoin->pixels, areaCoin.w, areaCoin.h);
		SDL_BlitSurface(resCoin, NULL, screen, &areaCoin);
		
		recortar(quest->pixels, paso[0], 64, quest->w, quest->h, resQ->pixels);
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
		SDL_FillRect(screen,&marioRE,SDL_MapRGB(screen->format,0,150,255));
		

		// Leo para saber donde mover el personaje
		
		if ((keys[SDLK_UP]) && (marioR.y > 0) && saltando[0]==0 && bajando[0]==0)
		{
			saltando[0]=1;
		}

		if (checkcolision(questR.x, questR.y, questR.x+questR.w, questR.y+questR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)==1)
		{
			saltando[0]=0;
			if (saltaMoneda[0]!=1 && bajaMoneda[0]!=1) 
			{
				saltaMoneda[0]=1;
				cont[0]=255;
			}
			bajando[0]=1;
		}

		if (checkcolision(pipeR.x, pipeR.y, pipeR.x+pipeR.w, pipeR.y+pipeR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)==1)
		{
			bajando[0]=0;
			marioR.y-=5;
		}
		
		marioRy[0]=marioR.y;
		
		salta(saltando, bajando, marioRy, velocidad, crecerVelocidad, screen->h-114);
		
		if(marioR.y==0) f=1;
		marioR.y=marioRy[0];
		
		if ((keys[SDLK_LEFT]))
		{
			if (checkcolision(pipeR.x, pipeR.y, pipeR.x+pipeR.w, pipeR.y+pipeR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1
				&& (checkcolision(peachR.x, peachR.y, peachR.x+peachR.w, peachR.y+peachR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1 || f==0)
					&& checkcolision(questR.x, questR.y, questR.x+questR.w, questR.y+questR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1)
			{
				if (saltando[0]==1 || bajando[0]==1)
				{ marioR.x = marioR.x-(2); }
				marioR.x=marioR.x-(1);
				if (saltando[0]!=1)
				{
					if (bajando[0]!=1)
					{
						if (marioR.y!=screen->h-piso->h-marioR.h && 
							checkcolision(	pipeR.x, 
											pipeR.y,
											pipeR.x+pipeR.w, 
											pipeR.y+pipeR.h, 
											marioR.x, 
											marioR.y+15, 
											marioR.x+marioR.w, 
											marioR.y+marioR.h+15)!=1)
						{
							marioR.y=screen->h-piso->h-marioR.h;
						}
					}
				}
			}else
			{
				marioR.x=marioR.x+(2);
			}
			
			if (marioR.x <= 0) marioR.x = 1000;
		}
		if ((keys[SDLK_RIGHT]))
		{
			if (checkcolision(pipeR.x, pipeR.y, pipeR.x+pipeR.w, pipeR.y+pipeR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1
				&& (checkcolision(peachR.x, peachR.y, peachR.x+peachR.w, peachR.y+peachR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1 || f==0)
					&& checkcolision(questR.x, questR.y, questR.x+questR.w, questR.y+questR.h, marioR.x, marioR.y, marioR.x+marioR.w, marioR.y+marioR.h)!=1)
			{
				if (saltando[0]==1 || bajando[0]==1)
				{ marioR.x = marioR.x+(2); }
				marioR.x=marioR.x+(1);
				if (saltando[0]!=1)
				{
					if (bajando[0]!=1)
					{
						if (marioR.y!=screen->h-piso->h-marioR.h 
							&& checkcolision(	pipeR.x, 
												pipeR.y, 
												pipeR.x+pipeR.w, 
												pipeR.y+pipeR.h, 
												marioR.x, 
												marioR.y+15, 
												marioR.x+marioR.w, 
												marioR.y+marioR.h+15)!=1)
						{
							marioR.y=screen->h-piso->h-marioR.h;
						}
					}
				}
			}else
			{
				marioR.x=marioR.x-(2);
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
		
		if (keys[SDLK_UP])
		{
			if (keys[SDLK_LEFT])
			{
				blit(marioJumpL->pixels, marioR.w, marioR.h);
				SDL_BlitSurface(marioJumpL, NULL, screen, &marioR);
			}else
			{
				blit(marioJump->pixels, marioR.w, marioR.h);
				SDL_BlitSurface(marioJump, NULL, screen, &marioR);
			}
		}else
		{
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
					blit(marioStand->pixels, marioR.w, marioR.h);
					SDL_BlitSurface(marioStand, NULL, screen, &marioR);
				}
			}
		}
		
fin:

		while (SDL_PollEvent(&event)) 
		{	
			if (event.type == SDL_QUIT) {done=1;}
					
			if (event.type == SDL_KEYDOWN || event.type == SDL_JOYBUTTONDOWN) {
						
				if (event.key.keysym.sym == SDLK_ESCAPE) {
					done=1;
				} 
			}
		}

   }

	return 0;
}
