#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <arpa/inet.h>
#include <sys/time.h>

/* --------------------------------------------------------------------------------------
 
 Env�a un n�mero aleatorio al servidor, quien el devuelve el n�mero incrementado

---------------------------------------------------------------------------------------- */
 
int main (){
  
	/*---------------------------------------------------- 
		Descriptor del socket y buffer para datos 
	-----------------------------------------------------*/
	int Socket_Cliente;
	char Datos[1024];
   
   	/* -----------------------------------------------------
   		Informacion del Servidor
   	-----------------------------------------------------*/
   	struct sockaddr_in Servidor;  
   	socklen_t Longitud_Servidor;
  
  
   	/* --------------------------------------------------
		Se abre el socket cliente
	---------------------------------------------------*/
	Socket_Cliente = socket (AF_INET, SOCK_DGRAM, 0);
	if (Socket_Cliente == -1){
		printf ("No se puede abrir el socket cliente\n");
			exit (-1);	
	}

	/*---------------------------------------------------------------------
		Necesitamos una estructura con la informacion del Servidor
		para poder solicitarle un servicio.
	----------------------------------------------------------------------*/
	Servidor.sin_family = AF_INET;
	Servidor.sin_port = htons(2000);
	Servidor.sin_addr.s_addr =  inet_addr("127.0.0.1");
	Longitud_Servidor = sizeof(Servidor);

	/* --------------------------------------------------
		Variables del select
	---------------------------------------------------*/
	struct timeval timeout;
	fd_set lectura;
	int salida;
	int intentos=0;

	printf("Escriba el mensaje que quiere enviar al servidor:\n");
	scanf("%s",Datos);

	/*-----------------------------------------------------------------------
		Se envia mensaje al Servidor
	-----------------------------------------------------------------------*/
	int enviado = sendto (Socket_Cliente, &Datos, sizeof(Datos), 0,
		(struct sockaddr *) &Servidor, Longitud_Servidor);

	if (enviado < 0){
		printf("Error al solicitar el servicio\n");
	}

		while (intentos < 3){
			// Inicializar el conjunto fd_set y el tiempo de espera para cada intento
			FD_ZERO(&lectura);
			FD_SET(Socket_Cliente, &lectura);

			timeout.tv_sec = 5;
			timeout.tv_usec = 0;

			// Usamos select para esperar respuesta del servidor
			salida = select(Socket_Cliente + 1, &lectura, NULL, NULL, &timeout);
			
			if (salida == -1){
				perror("Error en select");
				close(Socket_Cliente);
				exit(-1);
			}
			else if (salida == 0){
				// Timeout, no se recibió respuesta
				printf("Timeout\n");
				intentos++;
			}
			else{
				// El socket está listo para leer (hay datos disponibles)
				int recibido = recvfrom(Socket_Cliente, Datos, sizeof(Datos), 0,
						(struct sockaddr *) &Servidor, &Longitud_Servidor);
				
				if (recibido > 0){
					printf("Leido: %s\n", Datos);
					break;  // Salir del bucle si se recibe respuesta
				}
				else{
					printf("Error al leer del servidor\n");
				}
			}

			if (intentos == 3){
				printf("Error: No se recibió respuesta del servidor después de 3 intentos.\n");
			}
	}
 
	close(Socket_Cliente);

	return 0;
}