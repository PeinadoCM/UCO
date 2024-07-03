#!/bin/bash

clear

echo "1) Ficheros ocultos de /home/$USER"

cd "/home/$USER"
for x in `ls -a | awk '{print length($0), $0}' | sort -n | awk '{print $2}'`
do
    echo "$x"
done

echo "2) Listado de los procesos ejecutados por el usuario $USER"
#ps -u te muestra los procesos del usuario y -o te muestra solo algunas columnas
#El primer sed es para eliminar la primera fila y el segundo es para mostrar la informacion
ps -u $USER -o pid,start,cmd | sed -r -e '/PID/d;' | sed -r -e 's/ *([0-9]+) *([0-9]+:[0-9]+):[0-9]+ *(.+)$/PID: "\1"   Hora: "\2"    Ejecutable: "\3"/' | sort -k6dr