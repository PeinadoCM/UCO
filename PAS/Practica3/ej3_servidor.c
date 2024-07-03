/*
Servidor que lee de una cola abierta para lectura una cadena de caracteres y la
imprime por pantalla.

Lo hace mientras que el valor de esa cadena sea distinto a la palabra exit.
*/

#include "ej3_common.h"
#include <errno.h>
#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>

// Prototipo de funcionn
void funcionLog(char *);

// Apuntador al fichero de log
FILE *fLog = NULL;

int main(int argc, char **argv) {
    system("clear");
    // Cola del servidor y cliente
    mqd_t mq_server;
    mqd_t mq_client;
    // Atributos de las colas
    struct mq_attr attr;
    struct mq_attr attrclient;
    // Buffer para intercambiar mensajes y guardarlos en el log
    char buffer[MAX_SIZE];
    char msgbuff[2*MAX_SIZE];
    // flag que indica cuando hay que parar. Se escribe palabra exit
    int must_stop = 0;
    // Inicializar los atributos de las colas
    attr.mq_maxmsg = 10;        // Maximo número de mensajes
    attr.mq_msgsize = MAX_SIZE; // Maximo tamaño de un mensaje
    attrclient.mq_maxmsg = 10;        // Maximo número de mensajes
    attrclient.mq_msgsize = MAX_SIZE; // Maximo tamaño de un mensaje
    // Nombre para las colas
    char serverQueue[100];
    char clientQueue[100];
    // Variable para enviar mensajes del tamaño
    int length;

    // Nombre para la cola del servidor. Al concatenar el login sera unica en un sistema compartido.
    sprintf(serverQueue, "%s-%s", SERVER_QUEUE, getenv("USER"));
    sprintf(msgbuff,"[Servidor]: El nombre de la cola es: %s", serverQueue);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Nombre para la cola del cliente. Al concatenar el login sera unica en un sistema compartido.
    sprintf(clientQueue, "%s-%s", CLIENT_QUEUE, getenv("USER"));
    sprintf(msgbuff,"[Servidor]: El nombre de la cola es: %s", clientQueue);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Crear la cola de mensajes del servidor
    mq_server = mq_open(serverQueue, O_CREAT | O_RDONLY, 0644, &attr);

    if (mq_server == (mqd_t)-1) {
        funcionLog("Error al abrir la cola del servidor");
        perror("Error al abrir la cola del servidor");
        exit(-1);
    }
    sprintf(msgbuff,"[Servidor]: El descriptor de la cola del servidor es: %d", (int)mq_server);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Crear la cola de mensajes del cliente
    mq_client = mq_open(clientQueue, O_CREAT | O_WRONLY, 0644, &attrclient);

    if (mq_client == (mqd_t)-1) {
        funcionLog("Error al abrir la cola del cliente");
        perror("Error al abrir la cola del cliente");
        exit(-1);
    }
    sprintf(msgbuff,"[Servidor]: El descriptor de la cola del cliente es: %d", (int)mq_client);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    do {
        // Número de bytes leidos
        ssize_t bytes_read;

        // Recibir el mensaje
        bytes_read = mq_receive(mq_server, buffer, MAX_SIZE, NULL);
        // Comprar que la recepción es correcta (bytes leidos no son negativos)
        if (bytes_read < 0) {
            funcionLog("Error al recibir el mensaje");
            perror("Error al recibir el mensaje");
            exit(-1);
        }
        // Cerrar la cadena
        //buffer[bytes_read] = '\0';
        
        
        // Guardar el mensaje recibido en el log y mostrarlo por pantalla
        sprintf(msgbuff,"Recibido el mensaje: %s", buffer);
        funcionLog(msgbuff);
        printf("%s",msgbuff);
        length=(strlen(buffer)-1);

        // Comprobar el fin del bucle
        if (strncmp(buffer, MSG_STOP, strlen(MSG_STOP)) == 0){
            must_stop = 1;
            printf("\n");
        }
        else{
            //Enviar mensaje del numero de caracteres recibidos, ademas de guardarlo en el log y mostrarlo por pantalla
            sprintf(buffer, "Numero de caracteres recibidos: %d", length);
            sprintf(msgbuff,"Enviando mensaje: %s",buffer);
            printf("%s\n",msgbuff);
            funcionLog(msgbuff);
            if (mq_send(mq_client, buffer, MAX_SIZE, 0) != 0) {
            perror("Error al enviar el mensaje");
            exit(-1);
            }
        }
    }while (!must_stop); // Iterar hasta que llegue el código de salida, es decir, la palabra exit

    // Cerrar la cola del servidor
    if (mq_close(mq_server) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del servidor");
        perror("Error al cerrar la cola del servidor");
        exit(-1);
    }

    // Eliminar la cola del servidor
    if (mq_unlink(serverQueue) == (mqd_t)-1) {
        funcionLog("Error al eliminar la cola del servidor");
        perror("Error al eliminar la cola del servidor");
        exit(-1);
    }

    // Cerrar la cola del cliente
    if (mq_close(mq_client) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del cliente");
        perror("Error al cerrar la cola del cliente");
        exit(-1);
    }

    // Eliminar la cola del cliente
    if (mq_unlink(clientQueue) == (mqd_t)-1) {
        funcionLog("Error al eliminar la cola del cliente");
        perror("Error al eliminar la cola del cliente");
        exit(-1);
    }

    return 0;
}

// Función auxiliar, escritura de un log.
void funcionLog(char *mensaje) {
    int resultado;
    char nombreFichero[100];
    char mensajeAEscribir[300];
    time_t t;

    // Abrir el fichero
    sprintf(nombreFichero, "log-servidor.txt");
    if (fLog == NULL) {
        fLog = fopen(nombreFichero, "at");
        if (fLog == NULL) {
            perror("Error abriendo el fichero de log");
            exit(1);
        }
    }

    // Obtener la hora actual
    t = time(NULL);
    struct tm *p = localtime(&t);
    strftime(mensajeAEscribir, 1000, "[%Y-%m-%d, %H:%M:%S]", p);

    // Vamos a incluir la hora y el mensaje que nos pasan
    sprintf(mensajeAEscribir, "%s ==> %s\n", mensajeAEscribir, mensaje);

    // Escribir finalmente en el fichero
    resultado = fputs(mensajeAEscribir, fLog);
    if (resultado < 0)
        perror("Error escribiendo en el fichero de log");

    fclose(fLog);
    fLog = NULL;
}