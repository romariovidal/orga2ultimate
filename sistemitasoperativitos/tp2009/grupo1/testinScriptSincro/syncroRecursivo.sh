#!/bin/bash

IFS=$'\n' #esto es para los espacios del ls

#clear

function ingresarDir {
	echo "Por favor ingrese el directorio Origen "
	read ORIGEN

	if ([ ! -d $ORIGEN ]);then
		echo "No es un directorio, mala suerte, la próxima será."
		exit -1
	fi

	echo "Muchas gracias. Ahora ingrese el directorio Destino"
	read DESTINO

	if ([ ! -d $DESTINO ]);then
		echo "No es un directorio, mala suerte, ya habías pegado uno. La próxima será."
		exit -1
	fi
}


function leerParametros {
	#echo -e " 1: $1 \n 2: $2 \n 3: $3 \n 4: $4 \n 5: $5"
	if ([ $# -ne 3 ] && [ $# -ne 2 ] && [ $# -ne 4 ] && [ $# -ne 1 ] && [ $# -ne 0 ] ); then
		echo "Es necesario introducir 0, 1, 2, 3 'o 4 parámetros: $0 [parametro1] [parametro2] directorioOrigen directorioDestino"
		exit -1
	fi

	case $# in
		4 ) 
			if ( !(([ $1 == "-r" ] && [ $2 == "-s" ] ) ||
				( ([ $2 == "-r" ] && [ $1 == "-s" ] ) )) || 
				[ ! -d $3 ] || [ ! -d $4 ] ); then
				echo "Si introduce 4 parámetros deben ser: $0 parametro1 parametro2 directorioOrigen directorioDestino"
				echo "Las opciones para los parámetros 1 y 2 son '-r' y '-s'"
				exit -1
			fi
			echo "Son parámetros válidos (4)."
				ORIGEN=$3
				DESTINO=$4
				PARAMRRR="si"
				PARAMSSS="si"
			;;
		3 )
			if ( ( [ $1 != '-r' ] && [ $1 != '-s' ] ) || [ ! -d $2 ] || [ ! -d $3 ] ); then
				echo "Si introduce 3 parámetros deben ser: $0 parametro1 directorioOrigen directorioDestino"
				echo "Las opciones para el parámetro 1 son '-r' y '-s'"
				exit -1
			fi
			echo "Son parámetros válidos (3)."
				ORIGEN=$2
				DESTINO=$3
			if( [ $1 != '-r' ] ); then 
				PARAMRRR="no"
				PARAMSSS="si"
			else
				PARAMRRR="si"
				PARAMSSS="no"
			fi
			;;
	 	2 )
			if ([ ! -d $1 ] || [ ! -d $2 ] ); then
				if ( !( ( [ $1 == "-r" ] && [ $2 == "-s" ] ) ) ||
					( ( [ $2 == "-r" ] && [ $1 == "-s" ] ) )); then
					echo "Si introduce 2 parámetros deben ser: $0 directorioOrigen directorioDestino, $0 -r -s, o $0 -s -r"
					echo "Usted ingresó $1 y $2"
					exit -1
				else
					echo "Son parámetros válidos (2)."
					PARAMRRR="si"
					PARAMSSS="si"
					ingresarDir
				fi
			else 
				echo "Son parámetros válidos (2)."
				PARAMRRR="no"
				PARAMSSS="no"
				ORIGEN="$1"
				DESTINO="$2"
			fi
			;;
		1 ) 
			if ( [ $1 != "-r" ] && [ $1 != '-s' ] ); then
				echo "Las opciones para un parámetro son '-r' y '-s'"
				exit -1
			fi
			echo "Es un parámetro válido (1)."
			ingresarDir
			if( [ $1 != '-r' ] ); then 
				PARAMRRR="no"
				PARAMSSS="si"
			else
				PARAMRRR="si"
				PARAMSSS="no"
			fi
			;;
		0 )
			echo "Ejecutado sin parámetros (0)."
			ingresarDir
	esac
}

leerParametros $1 $2 $3 $4 $5

#echo -e " 1: $1 \n 2: $2 \n 3: $3 \n 4: $4 \n 5: $5"

echo "Directorio Origen: $ORIGEN"
echo "Directorio Destino: $DESTINO"
echo "Modificador -r presente: $PARAMRRR";
echo "Modificador -s presente: $PARAMSSS";

#echo "Ingrese su nombre"
#read NOMBRE
#echo "HOLA $NOMBRE"

#ORIGEN=$1
#DESTINO=$2


for file in $(ls -1 $ORIGEN)
do
	echo -e "\t\t\t\tProcesando archivo: \"$file\""
	
	FORIGEN=$(stat -c %Y $ORIGEN/$file)
	ARCHIVODESTINO="$DESTINO/$file"
	ARCHIVOORIGEN="$ORIGEN/$file"
	echo -e "\t\t\t\tComparar contra $ARCHIVODESTINO"


	if ([ -d "$ARCHIVOORIGEN" ]); then 
		#ARCHIVOORIGEN es un directorio
		if ([ $PARAMRRR = "si" ]); then
			if ([ $PARAMSSS = "si" ]); then 
				echo -n "Desea sincronizar este directorio << $ARCHIVOORIGEN >>" 
				echo "(presione 's' si sí, cualquier otra cosa si no)"
				read RESPUESTA
			else
				RESPUESTA="s"
			fi
		
			if ([ "$RESPUESTA" = "s" ]); then 
				if ([ -d "$ARCHIVODESTINO" ]); then #chequeo si existe el directorio destino
					if ([ $PARAMSSS = "si" ]); then
						./syncroRecursivo.sh -r -s $ARCHIVOORIGEN $ARCHIVODESTINO
					else 
						./syncroRecursivo.sh -r $ARCHIVOORIGEN $ARCHIVODESTINO
					fi	
				else
					echo -e "\t\t\t\tfile \"$ARCHIVOORIGEN\" aqui se copia toda la carpeta porque no esta"
					cp -r $ARCHIVOORIGEN $DESTINO
				fi
			fi					
		fi
	else
		if ([ -f "$ARCHIVODESTINO" ]); then
			echo -e "\t\t\t\t-- Existe el archivo de destino: \"$ARCHIVODESTINO\", verificar"
			FDESTIN=$(stat -c %Y $DESTINO/$file)
			if ([ "$FORIGEN" -gt "$FDESTIN" ]); then
				echo -e "\t\t\t\t---- El origen es mas nuevo, actualizar: \"$file\""			
				cp -r $ORIGEN/$file $DESTINO
			fi
		else
			echo -e "\t\t\t\tcopio archivo que no esta en el destino: \"$file\""
			cp $ORIGEN/$file $DESTINO
		fi
	fi
done 	
