#!/bin/bash

clear
#Comprobamos que se le ha pasado un argumento
if [ $# != 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej1.sh <fichero_peliculas>"
    exit 0
fi
#Comprobamos que el argumento es peliculas.txt
if [ $1 != "peliculas.txt" ];
then
    echo "Error, se esperaba un fichero del tipo peliculas.txt"
    exit 0
fi

echo "1) Titulo de las peliculas que tengan una longitud de 4 palabras:"

cat $1 | grep -E '^> .+ .+ .+ .+$'

echo -e "\n2) Duraciones superiores a 1h 45min"

cat $1 | grep -E -e '^1hr 4[6-9]min' -e '^1hr 5.min' -e '^[2-9]hr ..min' -e '^..+hr ..min'

echo -e "\n3) Numero de peliculas por pais"

#El sort hay que ponerlo porque sino al haber peliculas del mismo pais que no son adyacentes las cuenta por separado
cat $1 | grep -E -o '\-.+\-' | sort | uniq -c

echo -e "\n4) Lineas que contengan d, l o t , una vocal , y misma letra"

cat $1 | grep -E -o -e  '[a-zA-Z]*d[aeiou]d[a-z]*' -e '[a-zA-Z]*l[aeiou]l[a-z]*' -e '[a-zA-Z]*t[aeiou]t[a-z]*'

echo -e "\n5) Lineas que acaben con 3 puntos y no empiecen por espacios"

cat $1 | grep -E '^[a-zA-Z].+\.\.\.$'