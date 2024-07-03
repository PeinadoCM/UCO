#!/bin/bash

clear
#Comprobamos que se le pasa el numero correcto de parametros
if [ $# -ne 1 ];
then
    echo "Error, argumentos incorrectos. Uso: ./Ej3.sh <directorio>"
    exit 0
fi

#Comprobamos que exista el directorio $1
if [ -d $1 ];
then
    cd $1
    #Recorremos todos los directorios de $1
    for dir in `ls`
    do
        #Comprobamos que existe el directorio .ssh
        if [ -d "$dir/.ssh" ];
        then
            cd "$dir/.ssh"
            #Revisamos los permisos de id_rsa
            permisos=`stat -c "%A" id_rsa`
            if [ $permisos != "-rw-------" ];
            then
                #Si no son correctos revisamos los permisos de .ssh
                cd ..
                permisos=`stat -c "%A" .ssh`
                if [ $permisos != "drwx------" ];
                then
                    #Si no son correctos revisamos los pemisos de $dir
                    cd ..
                    permisos=`stat -c "%A" $dir`
                    if [ $permisos != "drwx------" ];
                    then
                        #Si no son correctos avisamos al usuario y creamos un fichero en su Escritorio
                        echo "El usuario $dir tiene una clave privada de ssh en $1$dir/.ssh/id_rsa que no esta protegida. La clave debe ser accesible únicamente por el propietario"
                        cd "$dir/Desktop"
                        touch "Tu_clave_ssh_es_vulnerable"
                        cd ..
                        cd ..
                    fi
                else
                    cd ..
                fi
            else
                cd ..
                cd ..
            fi
        fi
    done
else
    echo "El directorio $1 no existe"
    exit 0
fi