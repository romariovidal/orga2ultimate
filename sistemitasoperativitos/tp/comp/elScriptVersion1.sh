#!/bin/bash

echo "Hola puto"


if(($# == 3))
then
	echo "(3) Son $# par'ametros"
	if (( $3 == "-r" ))
	then
		echo "ser'a recursivo"
	fi
fi

echo "Carpeta origen : $1"
echo "Carpeta destino : $2"

ORIGEN="/home/tomas/comp"
DESTINO="/home/tomas/local"

echo "Listado de archivos: "

for file in $(ls $ORIGEN)
do
	echo "Procesando archivo: $file"
	#echo "fecha de modificacion: $(stat -c %Y $file)"
	FORIGEN=$(stat -c %Y $file)
	ARCHIVODESTINO="$DESTINO/$file"
	#echo "Comparar contra $ARCHIVODESTINO"

	#if [ -d "$ARCHIVODESTINO" ] para verificar si es directorio
	if [ -e "$ARCHIVODESTINO" ]
	then
		echo "-- Existe el archivo de destino, verificar"
		FDESTIN=$(stat -c %Y $DESTINO/$file)
		if [ "$FORIGEN" -gt "$FDESTIN" ]
		then
			echo "---- El origen es m'as nuevo, actualizar"
		else
			echo "---- El destino es m'as nuevo, ignorar"
		fi
	else
		echo "-- Copiar como nuevo"
	fi
done 
