#!/bin/bash

clear
#Comprobamos que se le pasa un parametro por linea de comandos
if [ $# != 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej1.sh <directorio>"
    exit 0
fi

#Comprobamos si el directorio $1 existe
if [ -d $1 ];
then

    sesiones=0
    aux=0
    suma=0
    cd $1

    #Comprobamos que todos los ficheros tengan el mismo numero de lineas
    for x in `ls`
    do
        #Mostramos solo el numero de lineas del fichero (primer parametro de la linea devuelta por wc)
        sesiones=`wc -l $x | awk '{print $1}'`

        #Igualamos para tener un valor de referencia
        if [ $aux == 0 ];
        then
            aux=$sesiones
        #Si algun fichero tiene un numero distinto de lineas da un error
        elif [ $aux != $sesiones ];
        then
            echo "Error, el fichero $x tiene $sesiones lineas, mientras que los otros tienen $aux lineas"
            exit 0
        fi
    done

    #Recorremos todas las sesiones
    for i in `seq $sesiones`
    do
        #Recorremos todos los ficheros
        for x in `ls`
        do
            #Leemos la linea $i del fichero $x
            aux=`cat $x | head -n $i | tail -n 1`
            #Si es 1 sumamos al contador de asistencia de la sesion
            if [ $aux == 1 ];
            then
                let suma=suma+1
            fi
        done
        #Mostramos por pantalla 
        echo "Asistieron $suma personas a la sesion $i"
        suma=0
    done

else
    echo "El directorio $1 no existe"
    exit 0
fi

