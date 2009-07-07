#!/bin/bash

#echo "Por favor ingrese su nombre: "
#read -s NOMBRE

#echo "Su nombre es $NOMBRE"

function ingresarDir {
	echo "Por favor ingrese el directorio Origen "
	read -s ORIGEN

	if ([ ! -d $ORIGEN ]);then
		echo "No es un directorio, mala suerte, la próxima será."
		exit -1
	fi

	echo "Muchas gracias. Ahora ingrese el directorio Destino"
	read -s DESTINO

	if ([ ! -d $DESTINO ]);then
		echo "No es un directorio, mala suerte, ya habías pegado uno. La próxima será."
		exit -1
	fi
}


if ([ $# -ne 3 ] && [ $# -ne 2 ] && [ $# -ne 4 ] ); then
	echo "Es necesario introducir 2, 3 'o 4 parámetros: $0 parametro1 directorioOrigen directorioDestino"
	exit -1
fi

case $# in
	4 ) 
		if ( ([ $1 != "-r" ] && [ $2 != "-s" ] ) ||
			( ([ $2 != "-r" ] && [ $1 != "-s" ] ) ) || 
			[ ! -d $3 ] || [ ! -d $4 ] ); then
			echo "Si introduce 4 parámetros deben ser: $0 parametro1 parametro2 directorioOrigen directorioDestino"
			echo "Las opciones para los parámetros 1 y 2 son '-r' y '-s'"
			exit -1
		fi
		echo "Son parámetros válidos (4)."
		cp -u -i -r $2/* $3  #capaz hay que agregar un *
		;;
	3 )
		if ( ( [ $1 != "-r" ] && [ $1 != '-s' ] ) || [ ! -d $2 ] || [ ! -d $3 ] ); then
			echo "Si introduce 3 parámetros deben ser: $0 parametro1 directorioOrigen directorioDestino"
			echo "Las opciones para el parámetro 1 son '-r' y '-s'"
			exit -1
		fi
		echo "Son parámetros válidos (3)."
		if( [$1 != '-r'] ); then 
			cp -u -i $2/* $3  #capaz hay que agregar un *
		else
			cp -u -r $2/* $3  #capaz hay que agregar un *
		fi
		;;
 	2 )
		if ([ ! -d $1 ] || [ ! -d $2 ] ); then
			if ( ( [ $1 != "-r" ] && [ $2 != '-s' ] ) ) ||
				( ( [ $2 != "-r" ] && [ $1 != '-s' ] ) ); then
				echo "Si introduce 2 parámetros deben ser: $0 directorioOrigen directorioDestino, $0 -r -s, o $0 -s -r"
				exit -1
			else
				echo "Son parámetros válidos (2)."
				ingresarDir
				cp -u -r -i $ORIGEN/* $DESTINO #capaz hay que agregar un *		
			fi
		else 
			echo "Son parámetros válidos (2)."
			cp -u $1/* $2 #capaz hay que agregar un *
		fi
		;;
	1 ) 
		if ( [ $1 != "-r" ] && [ $1 != '-s' ] ); then
			echo "Las opciones para un parámetro son '-r' y '-s'"
			exit -1
		fi
		echo "Es un parámetro válido (1)."
		ingresarDir
		if( [$1 != '-r'] ); then 
			cp -u -i $ORIGEN/* $DESTINO  #capaz hay que agregar un *
		else
			cp -u -r $ORIGEN/* $DESTINO  #capaz hay que agregar un *
		fi
		;;
	0 )
		echo "Ejecutado sin parámetros (0)."
		ingresarDir
		cp -u $ORIGEN/* $DESTINO  #capaz hay que agregar un *		
esac

exit 0
