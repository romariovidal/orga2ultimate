#!/bin/sh

echo "\nLista para la matriz"

for i in `seq 1 8`
do
	for j in `seq 1 8`
	do
		#echo "\t\tlaMatriz[$i][$j] = new Integer(text_tiene$i$j.getText());"
		echo "\t\tlaMatriz.asignar($i,$j,new Integer(text_tiene$i$j.getText()));"
	done
done


