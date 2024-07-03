#!/bin/bash

clear
#Comprobamos que el numero de parametros sea valido
if [ $# -lt 1 -o $# -gt 2 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej4.sh <Longitud de la cadena> [tipo de cadena] "
    exit 0
fi

#Comprobamos que la longitud de la cadena sea como minimo 1
if [ $1 -lt 1 ];
then 
    echo "Error, la cadena tiene que tener como longitud minima 1"
    exit 0
fi

tipo="$2"

#Comprobamos si solo han pasado un parametro por linea de comandos o si el segundo esta mal escrito
if [ $# -eq 1 -o "$tipo" != "alfa" -a "$tipo" != "num" -a "$tipo" != "alfanum" ];
then
    #Pedimos el tipo de cadena
    while [ "$tipo" != "alfa" -a "$tipo" != "num" -a "$tipo" != "alfanum" ];
    do
        read -p "Introduzca el tipo de cadena (alfa, num o alfanum)" tipo
        if [ "$tipo" != "alfa" -a "$tipo" != "num" -a "$tipo" != "alfanum" ];
        then
            echo "El tipo de cadena no es correcto"
        fi
    done
fi

#Creamos la cadena dependiendo del tipo
case $tipo in
"alfa")
    cad="`cat /dev/urandom | tr -dc 'a-zA-Z' | head -c $1`";;
"num")
    cad="`cat /dev/urandom | tr -dc '0-9' | head -c $1`";;
"alfanum")
    cad="`cat /dev/urandom | tr -dc 'a-zA-Z0-9' | head -c $1`";;
esac
echo "$cad"
