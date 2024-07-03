#!/bin/bash

clear
#Comprobamos que se le ha pasado un argumento
if [ $# != 2 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej3.sh <archivo_con_IPs><timeout>"
    exit 0
fi
#Comprobamos que el argumento es ips.txt
if [ $1 != "ips.txt" ];
then
    echo "Error, se esperaba un fichero del tipo ips.txt"
    exit 0
fi

#Comprobamos que el tiempo sea un numero
if [ $2 -lt 1 ];
then
    echo "Error, el timeout tiene que ser como minimo 1"
    exit 0
fi

#Guardamos el tiempo en ms
tim=""$2"000"

#Recorremos el fichero ips.txt
for ip in `cat $1`
do
    #Guardamos en $aux el tiempo que tarda en hacer la primera conexion 
    aux=`traceroute -w $2 -m 1 -q 1 $ip | grep -E -o '[0-9]+.[0-9]+ *ms$' | grep -E -o '[0-9]+.[0-9]+'` 

    #Comprobamos si $aux es un valor valido, en caso contrario sabemos que la conexion ha tardado mas que el tiempo limite
    if [ "$aux" == "" ];
    then
        echo "Error: No se recibe respuesta para $ip en $2 segundos"
    else 
        #Multiplicamos lo que tarda en hacer un hop por 30
        aux=`echo "$aux * 30" | bc`
        #Guardamos en num solamente la parte entera
        num=`grep -E -o '^[0-9]+' <<< "$aux"`
        if [ "$num" -ge "$tim" ];
        then
            echo "Error: No se recibe respuesta para $ip en $2 segundos"
        else
            echo "IP $ip ha tardado $aux ms en el primer salto" 
        fi
    fi
#Ordenamos la salida seun la quinta columna (ms) que es un numero en orden inverso
done | sort -k5nr
