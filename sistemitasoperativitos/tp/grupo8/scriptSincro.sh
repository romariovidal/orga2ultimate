#!/bin/bash

if ([ $# -ne 3 ] && [ $# -ne 2 ]); then
	echo "Es necesario introducir 2 o 3 parámetros: $0 parametro1 directorioOrigen directorioDestino"
	exit -1
fi

case $# in
	3 )
		if ([ $1 != "-r" ] || [ ! -d $2 ] || [ ! -d $3 ] ); then
			echo "Si introduce 3 parámetros deben ser: $0 parametro1 directorioOrigen directorioDestino"
			echo "La única opción para el parámetro 1 es '-r'"
			exit -1
		fi
		echo "son 3 y bien los params."
		#cp -u -r $2 $3  #capaz hay que agregar un *
		;;
 	2 )
		if ([ ! -d $1 ] || [ ! -d $2 ] ); then
			echo "Si introduce 2 parámetros deben ser: $0 directorioOrigen directorioDestino"
			exit -1
		fi
		echo "son 2 y bien los params."
		#cp -u $2 $3 #capaz hay que agregar un *
		;;
esac
	

exit 0
