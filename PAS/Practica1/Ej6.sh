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
            #Creamos el fichero html en todos los directorios
            crear_html
            #Salimos del directorio
            cd ..
        done
}

crear_html() {
    #Escribimos el comienzo de la lista
    echo -e "<ul>" > "index.html"
    #Recorremos todos los elementos en el directorio
    for arch in `ls`
    do
        #No mostramos el index.html en el index.html
        if [ "$arch" != "index.html" ];
        then
            #Comprobamos si es un fichero y lo añadimos al index
            if [ -f "$arch" ];
            then
                echo -e "\t <li>$arch</li>" >> "index.html"
            fi
            #Comprobamos si es un directorio y lo añadimos al index
            if [ -d "$arch" ];
            then
                echo -e "\t <li><a href=\"$PWD/$arch/.html\">$arch</a></li>" >> "index.html"
            fi
        fi
    done
    #Cerramos la lista y damos el mensaje de creacion
    echo -e "</ul>" >> "index.html"
    echo "Se ha creado el fichero "$PWD"/index.html en el directorio $PWD"
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
    recorrer_directorio
    #Creamos el html en $1
    crear_html
else
    echo "El directorio $1 no existe"
    exit 0
fi