#!/bin/bash

clear
#Comprobamos que se le ha pasado un argumento
if [ $# != 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej5.sh <texto.txt>"
    exit 0
fi
#Comprobamos que el argumento es texto.txt
if [ $1 != "texto.txt" ];
then
    echo "Error, se esperaba un fichero del tipo texto.txt"
    exit 0
fi
echo -e "# \t Count \t Word"
cat $1 | grep -E '[0-9]' | sed -r -e 'y/áéíóúÁÉÍÓÚ\.\:\,/aeiouAEIOU   /' | sed -r -e 'y/ /\n/' | grep -E -v -e '[0-9]' -e '^$' | sort -dr | uniq -ic | nl