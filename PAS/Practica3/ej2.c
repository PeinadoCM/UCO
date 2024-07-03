#include <errno.h> //Control de errores
#include <stdio.h>
#include <stdlib.h>
#include <string.h> //Para la funcion strerror(), que permite describir el valor de errno como cadena.
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

int main() {
    system("clear");
    // Para realizar el fork
    pid_t rf;
    int flag, status;
    // Para controlar los valores devueltos por las funciones (control de errores)
    int resultado;
    // Lo que vamos a leer y escribir de la tubería
    float num;
    float sum=0;
    // Descriptores de los dos extremos de la tubería
    int fileDes[2];

    // Creamos la tubería
    resultado = pipe(fileDes);
    if (resultado == -1) {
        printf("\nERROR al crear la tubería.\n");
        exit(1);
    }

    rf = fork();
    switch (rf) {
    case -1:
        printf("No se ha podido crear el proceso hijo...\n");
        exit(EXIT_FAILURE);
    case 0:
        printf("[HIJO]: Mi PID es %d y mi PPID es %d\n", getpid(), getppid());
        
        // Cerramos uno de los extremos de la tuberia, ya que solo vamos a recibir informacion
        close(fileDes[1]);
        // Recibimos un mensaje a través de la cola
        resultado = read(fileDes[0], &sum, sizeof(float));

        if (resultado != sizeof(int)) {
            printf("\n[HIJO]: ERROR al leer de la tubería...\n");
            exit(EXIT_FAILURE);
        }
        // Imprimimos el campo que viene en la tubería
        printf("[HIJO]: Leo el número aleatorio %f de la tubería.\n", sum);
        // Cerrar el extremo que he usado
        printf("[HIJO]: Tubería cerrada ...\n");
        close(fileDes[0]);
        break;

    default:
        printf("[PADRE]: Mi PID es %d y el PID de mi hijo es %d \n", getpid(), rf);

        // Cerramos uno de los extremos ya que solo vamos a enviar informacion
        close(fileDes[0]);

        srand48(time(NULL)); // Semilla de los números flotantes aleatorios establecida a la hora actual

        for (int i = 0; i < 2; i++) {
            // Rellenamos los campos del mensaje que vamos a enviar
            num = drand48() * 100; // Número aleatorio flotante entre 0 y 100
            sum += num;
            printf("[PADRE]: Sumo el número aleatorio %f\n", num);
        }
            printf("[PADRE]: Escribo el número aleatorio %f en la tuberia\n", sum);
            // Mandamos el mensaje
            resultado = write(fileDes[1], &sum, sizeof(float));

            if (resultado != sizeof(int)) {
                printf("\n[PADRE]: ERROR al escribir en la tubería...\n");
                exit(EXIT_FAILURE);
            }

        // Cerrar el extremo que he usado
        close(fileDes[1]);
        printf("[PADRE]: Tubería cerrada...\n");

        /*Espera del padre a los hijos*/
        while ((flag = wait(&status)) > 0) {
            if (WIFEXITED(status)) {
                printf("Proceso Padre, Hijo con PID %ld finalizado, status = %d\n", (long int)flag, WEXITSTATUS(status));
            } else if (WIFSIGNALED(status)) { // Para seniales como las de finalizar o matar
                printf("Proceso Padre, Hijo con PID %ld finalizado al recibir la señal %d\n", (long int)flag, WTERMSIG(status));
            }
        }
        if (flag == (pid_t)-1 && errno == ECHILD) {
            printf("Proceso Padre %d, no hay mas hijos que esperar. Valor de errno = %d, definido como: %s\n", getpid(), errno, strerror(errno));
        } else {
            printf("Error en la invocacion de wait o waitpid. Valor de errno = %d, definido como: %s\n", errno, strerror(errno));
            exit(EXIT_FAILURE);
        }
    }
    exit(EXIT_SUCCESS);
}