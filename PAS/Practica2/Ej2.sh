#!/bin/bash

clear
#Comprobamos que se le ha pasado un argumento
if [ $# != 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej2.sh <fichero_peliculas>"
    exit 0
fi
#Comprobamos que el argumento es peliculas.txt
if [ $1 != "peliculas.txt" ];
then
    echo "Error, se esperaba un fichero del tipo peliculas.txt"
    exit 0
fi
#El primer sed es para eliminar lineas vacias, subrayados y la descripción 
#El segundo sed es para cambiar el formato del texto que queremos mantener
cat $1 | sed '/^$/d; /^=/d; /^ /d' | sed -r -e 's/^> (.+)$/Titulo: \1/; s/\((.+)\).+$/|->Fecha de estreno: \1/; s/Dirigida por (.+)$/|->Director: \1/; s/Reparto: (.+)/|->Reparto: \1/; s/(.+hr .+min)/|->Duración: \1/' | tee peliculas_formateadas.txt
echo "Fichero peliculas_formateadas.txt creado con exito."