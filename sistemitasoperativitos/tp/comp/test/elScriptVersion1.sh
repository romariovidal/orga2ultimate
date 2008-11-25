#!/bin/bash

#echo "Hola puto"
#echo $1, $2, $3

#if(($# == 3))
#then
#	echo "(3) Son $# par'ametros"
#	if (( $3 == "-r" ))
#	then
#		echo "ser'a recursivo"
#	fi
#fi

#echo "Carpeta origen : $1"
#echo "Carpeta destino : $2"

ORIGEN=$1
DESTINO=$2

#echo "Listado de archivos: "

for file in $(ls $ORIGEN)
do
#	echo "Procesando archivo: $file"
	#echo "fecha de modificacion: $(stat -c %Y $file)"
	FORIGEN=$(stat -c %Y $ORIGEN/$file)
	ARCHIVODESTINO="$DESTINO/$file"
	ARCHIVOORIGEN="$ORIGEN/$file"
	#echo "Comparar contra $ARCHIVODESTINO"

	if [ -d "$ARCHIVOORIGEN" ] 
	#ARCHIVOORIGEN es un directorio
	then
		if [ -d "$ARCHIVODESTINO" ] 
		then
			if (( $3 == "-r" ))
			then
#				echo "caso recursivo"
				sh elScriptVersion1.sh $ARCHIVOORIGEN $ARCHIVODESTINO $3
			fi
		else
#			echo "file $ARCHIVOORIGEN"
			cp -r $ARCHIVOORIGEN $DESTINO
		fi
			
	#else
	fi
	
	if [ -e "$ARCHIVODESTINO" ]
	then
		#echo "-- Existe el archivo de destino, verificar"
		FDESTIN=$(stat -c %Y $DESTINO/$file)
		if [ "$FORIGEN" -gt "$FDESTIN" ]
		then
			#echo "---- El origen es mas nuevo, actualizar: $file"			
			cp $ORIGEN/$file $DESTINO
			#else
			#echo "---- El destino es mas nuevo, ignorar"
		fi
	else
		#echo "copio archivo que no esta en el destino: $file"
		cp $ORIGEN/$file $DESTINO
	fi
done 
