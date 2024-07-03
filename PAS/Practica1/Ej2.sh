#!/bin/bash

clear
#Comprobamos el numero de argumentos por linea de comandos
if [ $# != 4 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej2.sh <directorio_origen> <directorio_destino> <compresion> <sobreescribir>"
    exit 0
fi

#Comprobamos si existe el directorio que queremos copiar
if [ -d $1 ];
then
    #Comprobamos si existe el directorio donde vamos a destianr la copia
    if [ -d $2 ];
    then
        #Comprobamos que el tercer parametro es un 0 o un 1
        if [ $3 == 0 -o $3 == 1 ];
        then
            #Comprobamos que el cuarto parametro es un 0 o un 1
            if [ $4 == 0 -o $4 == 1 ];
            then
                #Creamos el nombre del tar
                case $3 in
                0)
                    #Usamos basename para coger solamente el nombre del directorio a comprimir
                    nombre=""`basename $1`"_"$USER"_`date +"%Y%m%d"`.tar";;
                1)
                    nombre=""`basename $1`"_"$USER"_`date +"%Y%m%d"`.tar.gz";;
                esac
                #Comprobamos si el fichero ya existe
                if [ -f "$2$nombre" ];
                then
                    echo "Ya se ha realizado esta copia hoy ($2$nombre)"
                    #Comprobamos si tenemos que reemplazarlo o no
                    if [ $4 == 0 ];
                    then 
                        echo "No se sobreescribirá la copia"
                    else
                        tar -cf "$2$nombre" $1
                        echo "Copia realizada en $2$nombre"
                    fi
                else
                    tar -cf "$2$nombre" $1
                    echo "Copia realizada en $2$nombre"
                fi

            else
                echo "El cuarto parametro por linea de comandos tiene que ser 0 o 1"
                exit 0
            fi
        else
            echo "El tercer parametro por linea de comandos tiene que ser 0 o 1"
            exit 0
        fi
    else
        echo "El directorio $2 no existe"
        exit 0
    fi
else
    echo "El directorio $1 no existe"
    exit 0
fi