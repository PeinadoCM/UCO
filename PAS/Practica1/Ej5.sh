#!/bin/bash

clear
#Funcion para recorrer recursivamente un directorio
recorrer_directorio() {
        #En caso de que no haya ningun fichero que no sea un directorio cortamos la recursividad
        #Leemos todos los directorios del directorio actual
        for dir in `ls -ap | grep .../`
        do
            #Entramos en el directorio
            cd $dir
            #Llamamos de manera revursiva a recorrer_directorio
            recorrer_directorio
            #Leemos todos los ficheros del directorio actual
            for fich in `ls -ap | grep -v /`
            do
                #Mostramos los datos por pantalla (-e para poder escribir el tabulador)
                echo -e "`basename $fich`\t`realpath $fich`\t`stat -c "%Y" "$fich"`\t`stat -c "%s" "$fich"` bytes \t`stat -c "%A" "$fich"`"
            done
            #Salimos del directorio
            cd ..
        done
}
#Comprobamos que nos han pasado un directorio como argumento
if [ $# -ne 1 ];
then 
    echo "Error, argumentos incorrectos. Uso: ./Ej5.sh <directorio>"
    exit 0
fi
#Comprobamos que existe el directorio
if [ -d $1 ];
then
    #Entramos y lo recorremos recursivamente
    cd $1
    #El sort -k3n es para ordenar las salidas por pantalla en funcion de la columan 3 (k3)
    recorrer_directorio | sort -k3n
else
    echo "El directorio $1 no existe"
    exit 0
fi