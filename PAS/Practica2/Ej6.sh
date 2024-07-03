#!/bin/bash

clear
#Comprobamos que se pasa un argumento
if [ $# != 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej6.sh </bin/bash>"
    exit 0
fi
#Guardamos el numero de usuarios que hay con esa shell
aux=`cat "/etc/passwd" | grep -E ":$1$" | wc -l`

#Recorremos todos los usuarios uno a uno
for i in `seq $aux`
do
    echo "========="
    #Mostramos los datos del fichero /etc/passwd
    #Con el primer sed se muestra solamente el elemento numero $i 
    cat "/etc/passwd" | grep -E "$1$" | sed -n ""$i"p" | sed -r -e 's/^(.+):x:([0-9]+):([0-9]+).+/Logname: \1\n->UID: \2/'
    #Mostramos los datos del fichero /etc/group
    #Dentro del grep metemos todo el codigo del fichero /etc/passwd pero con algunas modificaciones para buscar los datos de ese grupo
    cat "/etc/group" | grep -E ".+:`cat "/etc/passwd" | grep -E "$1$" | sed -n ""$i"p" | sed -r -e 's/^.+:x:[0-9]+:([0-9]+).+/\1/'`:$" | sed -r -e 's/^(.+):x:([0-9]+).+/->Grupo: \1\n->GID: \2/'
    echo -e "->Shell por defecto: $1\n"
done