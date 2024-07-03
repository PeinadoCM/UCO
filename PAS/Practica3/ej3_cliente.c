/*
Cliente que envia por una cola abierta para escritura una cadena de caracteres
recogida por teclado, mientras que el valor de esa cadena sea distinto a la palabra exit
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
#include <signal.h>

// Prototipo de funcion
void funcionLog(char *);

// Apuntador al fichero de log
FILE *fLog = NULL;

// Cola del servidor creadas como variables globales para que se puedan cerrar con las senales
mqd_t mq_server;
mqd_t mq_client;

// Funcion para terminar la ejecución en caso de recibir las señales
void terminar(int sign){
    //Variables necesarias
    char buffer[MAX_SIZE];
    char msgbuff[2*MAX_SIZE];

    // Guardar en el log porque ha terminado el programa
    sprintf(msgbuff,"Finalizacion del programa tras recibir la senal %i",sign);
    funcionLog(msgbuff);

    // Enviar señal de fin
    strcpy(buffer,MSG_STOP);

    if (mq_send(mq_server, buffer, MAX_SIZE, 0) != 0) {
        funcionLog("Error al enviar el mensaje");
        perror("Error al enviar el mensaje");
        exit(-1);
    }

    // Guardar en el log el mensaje enviado
    sprintf(msgbuff,"Enviando mensaje: %s",buffer);
    funcionLog(msgbuff);

    // Cerramos la cola del servidor
    if (mq_close(mq_server) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del servidor");
        perror("Error al cerrar la cola del servidor");
        exit(-1);
    }

    // Cerramos la cola del cliente
    if (mq_close(mq_client) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del cliente");
        perror("Error al cerrar la cola del cliente");
        exit(-1);
    }

    // Comprobamos si esta abierto el log, en caso de que lo este lo cerramos
    if(fLog != NULL){
        fclose(fLog);
    }

    exit(0);
}

int main(int argc, char **argv) {
    system("clear");

    // Asignamos el comportamineto de las señales
    if(signal(SIGINT,terminar) == SIG_ERR){
        funcionLog("Error al asiganr el comportamiento de la señal SIGINT");
        perror("Error al asiganr el comportamiento de la señal SIGINT");
        exit(-1);
    }

    if(signal(SIGTERM,terminar) == SIG_ERR){
        funcionLog("Error al asiganr el comportamiento de la señal SIGTERM");
        perror("Error al asiganr el comportamiento de la señal SIGTERM");
        exit(-1);
    }

    // Buffer para intercambiar mensajes y guardar en el log
    char buffer[MAX_SIZE];
    char msgbuff[2*MAX_SIZE];
    // Nombre para las colas
    char serverQueue[100];
    char clientQueue[100];

    // Nombre para la cola del servidor. Al concatenar el login sera unica en un sistema compartido.
    sprintf(serverQueue, "%s-%s", SERVER_QUEUE, getenv("USER"));
    sprintf(msgbuff,"[Cliente]: El nombre de la cola es: %s", serverQueue);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Nombre para la cola del cliente. Al concatenar el login sera unica en un sistema compartido.
    sprintf(clientQueue, "%s-%s", CLIENT_QUEUE, getenv("USER"));
    sprintf(msgbuff,"[Cliente]: El nombre de la cola es: %s", clientQueue);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Abrimos la cola del servidor
    mq_server = mq_open(serverQueue, O_WRONLY);

    if (mq_server == (mqd_t)-1) {
        funcionLog("Error al abrir la cola del servidor");
        perror("Error al abrir la cola del servidor");
        exit(-1);
    }
    sprintf(msgbuff,"[Cliente]: El descriptor de la cola del servidor es: %d", (int)mq_server);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    // Abrimos la cola del cliente
    mq_client = mq_open(clientQueue, O_RDONLY);

    if (mq_client == (mqd_t)-1) {
        funcionLog("Error al abrir la cola del cliente");
        perror("Error al abrir la cola del cliente");
        exit(-1);
    }
    sprintf(msgbuff,"[Cliente]: El descriptor de la cola del cliente es: %d", (int)mq_client);
    printf("%s\n",msgbuff);
    funcionLog(msgbuff);

    printf("Mandando mensajes al servidor (escribir \"%s\" para parar):\n", MSG_STOP);
    do {
        printf("> ");

        // Leer por terminal 
        fgets(buffer, MAX_SIZE, stdin);

        // Enviar y comprobar si el mensaje se manda
        if (mq_send(mq_server, buffer, MAX_SIZE, 0) != 0) {
            funcionLog("Error al enviar el mensaje");
            perror("Error al enviar el mensaje");
            exit(-1);
        }

        // Guardar mensaje en el log
        sprintf(msgbuff,"Enviando mensaje: %s",buffer);
        funcionLog(msgbuff);

        // Comprobar si el mensaje enviado es MSG_STOP
        if(strncmp(buffer, MSG_STOP, strlen(MSG_STOP)) != 0){
            ssize_t bytes_read;
            // Recibir el mensaje
            bytes_read = mq_receive(mq_client, buffer, MAX_SIZE, NULL);
            // Comprar que la recepción es correcta (bytes leidos no son negativos)
            if (bytes_read < 0) {
                funcionLog("Error al recibir el mensaje");
                perror("Error al recibir el mensaje");
                exit(-1);
            }

            //buffer[bytes_read] = '\0';

            //Mostrar por pantalla el mensaje y guardarlo en el log
            sprintf(msgbuff,"Recibido el mensaje: %s", buffer);
            printf("%s\n",msgbuff);
            funcionLog(msgbuff);
        }
        // Iterar hasta escribir el código de salida
    } while (strncmp(buffer, MSG_STOP, strlen(MSG_STOP)));

    // Cerrar la cola del servidor
    if (mq_close(mq_server) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del servidor");
        perror("Error al cerrar la cola del servidor");
        exit(-1);
    }

    // Cerrar la cola del cliente
    if (mq_close(mq_client) == (mqd_t)-1) {
        funcionLog("Error al cerrar la cola del cliente");
        perror("Error al cerrar la cola del cliente");
        exit(-1);
    }

    return 0;
}

/* Función auxiliar, escritura de un log.
No se usa en este ejemplo, pero le puede servir para algun
ejercicio resumen */
void funcionLog(char *mensaje) {
    int resultado;
    char nombreFichero[100];
    char mensajeAEscribir[300];
    time_t t;

    // Abrir el fichero
    sprintf(nombreFichero, "log-cliente.txt");
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
