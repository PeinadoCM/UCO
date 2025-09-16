#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdlib.h>
#include <string.h>
#include<signal.h>
#include <unistd.h>
#include <time.h>
#include <arpa/inet.h>


#define MSG_SIZE 250
#define MAX_CLIENTS 30

//Falta que no se puedan repartir cartas repetidas


/*
 * El servidor ofrece el servicio de un chat
 */
//Estructura que almacena todos los datos de una partida
struct Mesa{
    //Variables que almacenan a los dos jugadores
    int j1;
    int j2;
    //Variables que almacenan el numero de cartas de un jugador
    int numcartasj1;
    int numcartasj2;
    //Variables donde se almacenan las cartas de cada jugador
    int cartasj1[12][2];
    int cartasj2[12][2];
    //Variables donde se almacenan los puntos de cada jugador
    int puntosj1;
    int puntosj2;
};

//Vector donde se almacenan todas las partidas, con todos los valores inicializados a 0
struct Mesa partidas[10]={0};

void manejador(int signum);
void salirCliente(int socket, fd_set * readfds, int * numClientes, int arrayClientes[]);


//Funcion que asigna un numero aleatorio a una carta
//J=11
//Q=12
//K=13
int numerorand(){return rand()%13+1;}

//Funcion que asigna un palo aleatorio a una carta
//Corazones=1
//Diamantes=2
//Picas=3
//Treboles=4
int palorand(){return rand()%4+1;}

//Funcion que muestra las cartas del jugador
void mostrarcartas(int mesa, int receptor);

//Funcion que reparte las cartas al iniciar una partida
void iniciopartida(int mesa, int j1, int j2);

//Funcionque suma los puntos de las cartas del jugador:
// Si devuelve 0 la suma de puntos NO es mayor que 21,
// Si devuelve 1 la suma de puntos es mayor que 21,
// Si devuelve 2 la suma de puntos es 21;
int sumarcartas(int mesa, int jugador);

//Funcion que resuelve la partida:
// Si devuelve -1 no ha ganado nadie,
// Si devuelve 0 han empatado,
// Si devuelve 1 gana el jugador 1,
// Si devuelve 2 gana el jugador 2,
int terminarpartida(int mesa);

//Funcion que vacia una mesa una vez ha terminado la partida
void vaciarmesa(int mesa);

//Funcion que comprueba que no se repitan cartas repetidas
//Si devuelve 0 todo correcto,
//Si devuelve 1 la ultima carta repartida esta repetida
int revisarcartas(int mesa, int jugador);

int main ( )
{
  
	/*---------------------------------------------------- 
		Descriptor del socket y buffer de datos                
	-----------------------------------------------------*/
	int sd, new_sd;
	struct sockaddr_in sockname, from;
	char buffer[MSG_SIZE];
	socklen_t from_len;
    	fd_set readfds, auxfds;
   	 int salida;
   	 int arrayClientes[MAX_CLIENTS];
    	int numClientes = 0;
   	 //contadores
    	int i,j,k;
	int recibidos;
   	 char identificador[MSG_SIZE];
    
    	int on, ret;

    //Variable donde se almacenan los nombres de cada cliente
    char nomclie[MAX_CLIENTS][50];
    //La inicializamos a la cadena vacia
    for(int i = 0; i < MAX_CLIENTS; i++){
        strcpy(nomclie[i],""); 
    }

    //Variable que almacena el estado de cada cliente:
    //Estado=0 Usuario sin registrar
    //Estado=1 Usuario con nombre introducido pero sin verificar
    //Estado=2 Usuario verificado
    //Estado=3 Usuario buscando partida
    //Estado=4 Usuario en partida
    //Estado=5 Usuario en partida plantado
    int estado[MAX_CLIENTS]={0};
    
    //struct Mesa partidas[10]={0};

    srand(time(NULL));

	/* --------------------------------------------------
		Se abre el socket 
	---------------------------------------------------*/
  	sd = socket (AF_INET, SOCK_STREAM, 0);
	if (sd == -1)
	{
		perror("No se puede abrir el socket cliente\n");
    		exit (1);	
	}
    
    	// Activaremos una propiedad del socket para permitir· que otros
    	// sockets puedan reutilizar cualquier puerto al que nos enlacemos.
    	// Esto permite· en protocolos como el TCP, poder ejecutar un
    	// mismo programa varias veces seguidas y enlazarlo siempre al
   	 // mismo puerto. De lo contrario habrÌa que esperar a que el puerto
    	// quedase disponible (TIME_WAIT en el caso de TCP)
    	on=1;
    	ret = setsockopt( sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));



	sockname.sin_family = AF_INET;
	sockname.sin_port = htons(2060);
	sockname.sin_addr.s_addr =  INADDR_ANY;

	if (bind (sd, (struct sockaddr *) &sockname, sizeof (sockname)) == -1)
	{
		perror("Error en la operación bind");
		exit(1);
	}
	

   	/*---------------------------------------------------------------------
		Del las peticiones que vamos a aceptar sólo necesitamos el 
		tamaño de su estructura, el resto de información (familia, puerto, 
		ip), nos la proporcionará el método que recibe las peticiones.
   	----------------------------------------------------------------------*/
	from_len = sizeof (from);


	if(listen(sd,1) == -1){
		perror("Error en la operación de listen");
		exit(1);
	}

	
    	printf("El servidor está esperando conexiones...\n");	//Inicializar los conjuntos fd_set
    	
	FD_ZERO(&readfds);
    	FD_ZERO(&auxfds);
    	FD_SET(sd,&readfds);
    	FD_SET(0,&readfds);
    
   	
    	//Capturamos la señal SIGINT (Ctrl+c)
    	signal(SIGINT,manejador);
    
	/*-----------------------------------------------------------------------
		El servidor acepta una petición
	------------------------------------------------------------------------ */
	while(1){
            
            //Esperamos recibir mensajes de los clientes (nuevas conexiones o mensajes de los clientes ya conectados)
            
            auxfds = readfds;
            
            salida = select(FD_SETSIZE,&auxfds,NULL,NULL,NULL);
            
            if(salida > 0){
                
                
                for(i=0; i<FD_SETSIZE; i++){
                    
                    //Buscamos el socket por el que se ha establecido la comunicación
                    if(FD_ISSET(i, &auxfds)) {
                        
                        if( i == sd){
                            
                            if((new_sd = accept(sd, (struct sockaddr *)&from, &from_len)) == -1){
                                perror("Error aceptando peticiones");
                            }
                            else
                            {
                                if(numClientes < MAX_CLIENTS){
                                    arrayClientes[numClientes] = new_sd;
                                    numClientes++;
                                    FD_SET(new_sd,&readfds);
                                
                                    strcpy(buffer, "Bienvenido al chat\n");
                                
                                    send(new_sd,buffer,sizeof(buffer),0);

                                    sprintf(buffer,"Comandos aceptados:\n--> REGISTRO -u usuario -p password\n--> USUARIO usuario\n--> PASSWORD password\n--> INICIAR-PARTIDA\n--> PEDIR-CARTA\n--> PLANTARME\n--> SALIR\n");
                                    send(new_sd,buffer,sizeof(buffer),0);
                
                                }
                                else
                                {
                                    bzero(buffer,sizeof(buffer));
                                    strcpy(buffer,"Demasiados clientes conectados\n");
                                    send(new_sd,buffer,sizeof(buffer),0);
                                    close(new_sd);
                                }
                                
                            }
                            
                            
                        }
                        else if (i == 0){
                            //Se ha introducido información de teclado
                            bzero(buffer, sizeof(buffer));
                            fgets(buffer, sizeof(buffer),stdin);
                            
                            //Controlar si se ha introducido "SALIR", cerrando todos los sockets y finalmente saliendo del servidor. (implementar)
                            if(strcmp(buffer,"SALIR\n") == 0){
                             
                                for (j = 0; j < numClientes; j++){
                                    bzero(buffer, sizeof(buffer));
                                    strcpy(buffer,"Desconexión servidor\n"); 
                                    send(arrayClientes[j],buffer , sizeof(buffer),0);
                                    close(arrayClientes[j]);
                                    FD_CLR(arrayClientes[j],&readfds);
                                }
                                    close(sd);
                                    exit(-1);
                                
                                
                            }
                            //Mensajes que se quieran mandar a los clientes (implementar)
                            
                        } 
                        else{
                            bzero(buffer,sizeof(buffer));
                            
                            recibidos = recv(i,buffer,sizeof(buffer),0);
                            
                            if(recibidos > 0){
                                
                                if(strcmp(buffer,"SALIR\n") == 0){
                                    //Se vacia el nombre del cliente
                                    strcpy(nomclie[i],"");
                                    //Se comprueba si estaba en partida para enviar un mensaje al rival
                                    if(estado[i] >= 4){
                                        for(int mesa=0; mesa < 10; mesa++){
                                            if(i == partidas[mesa].j1){
                                                sprintf(buffer, "-Err: Tu rival se ha desconectado\n");
                                                send(partidas[mesa].j2,buffer,sizeof(buffer),0);
                                                estado[partidas[mesa].j2]=2;
                                                vaciarmesa(mesa);
                                            }
                                            else if(i == partidas[mesa].j2){
                                                sprintf(buffer, "-Err: Tu rival se ha desconectado\n");
                                                send(partidas[mesa].j1,buffer,sizeof(buffer),0);
                                                estado[partidas[mesa].j1]=2;
                                                vaciarmesa(mesa);
                                            }
                                        }
                                    }
                                    //Se resetea el estado del cliente
                                    estado[i]=0;
                                    salirCliente(i,&readfds,&numClientes,arrayClientes);
                                    
                                }
                                else if (strncmp(buffer, "USUARIO ", 8) == 0) {
                                    //Se comprueba si el cliente no ha introducido usuario
                                    if(estado[i] == 0){
                                        
                                        char* nombreUsuario = buffer + 8;
                                        int existe=0;
                                        nombreUsuario[strlen(nombreUsuario)-1] = '\0';
                                        //Se comprueba si ya hay un usuario con el nombre introducido
                                        for(int j=0; j < MAX_CLIENTS;j++){
                                            if(strcmp(nomclie[j],nombreUsuario) == 0){
                                                existe=1;
                                                break;
                                            }
                                        }
                                        if(existe == 0){
                                            // Verificar si el usuario existe en el archivo
                                            char linea[100];
                                            FILE* fich=fopen("usuarios.txt","r");
                                            if(fich == NULL){
                                                sprintf(buffer, "-Err: No hay ningun usuario registrado\n");
                                                send(i,buffer,sizeof(buffer),0);
                                            }
                                            else{
                                                // Leer el archivo línea por línea
                                                while(fgets(linea, sizeof(linea), fich)){
                                                    linea[strlen(linea)-1] = '\0';
                                                    // Comparar la línea con el nombre de usuario
                                                    if (strcmp(linea, nombreUsuario) == 0) {
                                                        
                                                        strcpy(nomclie[i],nombreUsuario);
                                                        estado[i]=1;
                                                        break;
                                                    }
                                                    //Saltamos las lineas de las contraseñas
                                                    fgets(linea, sizeof(linea), fich);
                                                }
                                                fclose(fich);
                                                if(estado[i] == 0){
                                                    sprintf(buffer,"-Err: Usuario no encontrado.\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                }
                                                else{
                                                    sprintf(buffer,"+Ok: Usuario encontrado introduzca la contraseña.\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                }
                                            }
                                        }
                                        else{
                                            sprintf(buffer, "-Err: Ya hay una sesion iniciada con este usuario\n");
                                            send(i,buffer,sizeof(buffer),0);
                                        }
                                    }
                                    else if(estado[i] == 1){
                                        sprintf(buffer, "-Err: Ya se ha introducido un usuario, introduzca la contraseña\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 2){
                                        sprintf(buffer, "-Err: Ya se ha verificado un usuario\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] >= 3){
                                        sprintf(buffer, "-Err: Este comando no se puede usar mientras estas en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else if(strncmp(buffer, "PASSWORD ", 9) == 0){
                                    //Se comprueba si el cliente ha introducido un usuario
                                    if(estado[i] == 1){
                                        FILE* fich=fopen("usuarios.txt","r");
                                        if (fich == NULL) {
                                            sprintf(buffer, "-Err: No hay ningun usuario registrado\n");
                                            send(i,buffer,sizeof(buffer),0);
                                        }
                                        else{
                                            char* passw = buffer + 9;
                                            passw[strlen(passw)-1] = '\0';
                                            char lineanombre[100];
                                            char lineapassw[100];
                                            //Se lee el fichero
                                            while (fgets(lineanombre, sizeof(lineanombre), fich)) {
                                                fgets(lineapassw, sizeof(lineapassw), fich);
                                                lineanombre[strlen(lineanombre)-1] = '\0';
                                                lineapassw[strlen(lineapassw)-1] = '\0';
                                                //Se comprueba que la contraseña introducida se corresponde con la contraseña del usuario
                                                if (strcmp(lineapassw, passw) == 0 && strcmp(lineanombre, nomclie[i]) == 0) {
                                                    sprintf(buffer, "+Ok: Usuario verificado\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                    estado[i]=2;
                                                    break;
                                                }
                                                
                                            }
                                            fclose(fich);
                                            if(estado[i] == 1){
                                                sprintf(buffer, "-Err: El usuario no coincide con la contraseña.\n");
                                                send(i,buffer,sizeof(buffer),0);
                                            }
                                        }
                                    }
                                    else if(estado[i] == 0){
                                        sprintf(buffer, "-Err: No se ha introducido ningun usuario\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 2){
                                        sprintf(buffer, "-Err: Ya se ha verificado un usuario\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] >= 3){
                                        sprintf(buffer, "-Err: Este comando no se puede usar mientras estas en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else if(strncmp(buffer, "REGISTRO", 8) == 0){
                                    char usuario[50]="";
                                    char password[50]="";
                                    int existe=0;
                                    sscanf(buffer, "REGISTRO -u %s -p %s", usuario, password);
                                    //Comprobamos que se ha añadido correctamente la instruccion
                                    if(strlen(usuario) != 0 && strlen(password) != 0){
                                        //Comprobamos que no existe ya el usuario introducido
                                        FILE *fich = fopen("usuarios.txt", "r");
                                        if(fich != NULL){
                                            char lineanombre[50];
                                            while (fgets(lineanombre, sizeof(lineanombre), fich)) {
                                                lineanombre[strlen(lineanombre)-1] = '\0';
                                                // Comparar la línea con el nombre de usuario
                                                if (strcmp(lineanombre, usuario) == 0) {
                                                    sprintf(buffer, "-Err: Error ese usuario ya existe\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                    existe=1;
                                                    break;
                                                }
                                                //Saltamos las contraseñas
                                                fgets(lineanombre, sizeof(lineanombre), fich);
                                            }
                                            fclose(fich);
                                        }

                                        //Añadimos el nuevo usuario
                                        if(existe == 0){
                                            fich = fopen("usuarios.txt", "a");
                                            if(fich == NULL){
                                                sprintf(buffer, "-Err: Error al abrir el fichero\n");
                                                send(i,buffer,sizeof(buffer),0);
                                            }
                                            fprintf(fich, "%s\n%s\n", usuario, password);
            
                                            // Cerramos el archivo
                                            fclose(fich);
                                            sprintf(buffer, "+Ok: Usuario y contraseña guardados correctamente.\n");
                                            send(i,buffer,sizeof(buffer),0);
                                        }
                                    }
                                    else{
                                        sprintf(buffer, "-Err: Error orden introducida incorrectamente\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else if(strcmp(buffer,"INICIAR-PARTIDA\n") == 0){
                                    //Se comprueba que el cliente ha verificado su identidad
                                    if(estado[i] == 2){
                                        int auxi=0;
                                        int auxj=0;
                                        estado[i]=3;
                                        //Se busca otro jugador buscando partida
                                        for(int j=0; j<MAX_CLIENTS; j++){
                                            if(estado[j] == 3 && j != i){
                                                //Se busca una mesa vacia
                                                for(int k=0; 10>k; k++){
                                                    if(partidas[k].j1 == 0 && partidas[k].j2 == 0){
                                                        //Se inicia la partida
                                                        estado[i]=4;
                                                        estado[j]=4;
                                                        sprintf(buffer, "+Ok: Empieza la partida\n");
                                                        send(i,buffer,sizeof(buffer),0);
                                                        send(j,buffer,sizeof(buffer),0);
                                                        
                                                        iniciopartida(k,i,j);
                                                        if(sumarcartas(k,i) == 2){
                                                            auxi=1;
                                                        }
                                                        if(sumarcartas(k,j)){
                                                            auxj=1;
                                                        }
                                                        mostrarcartas(k,i);
                                                        mostrarcartas(k,j);
                                                        if(auxi == 1){
                                                            estado[i]=5;
                                                            sprintf(buffer, "+Ok: El valor de tus cartas es 21, ya no se permite pedir mas cartas\n");
                                                            send(i,buffer,sizeof(buffer),0);
                                                        }
                                                        if(auxj == 1){
                                                            estado[j]=5;
                                                            sprintf(buffer, "+Ok: El valor de tus cartas es 21, ya no se permite pedir mas cartas\n");
                                                            send(j,buffer,sizeof(buffer),0);
                                                        }
                                                        break;
                                                    }
                                                }
                                                //Si no se ha entrado en ninguna mesa es porque todas las partidas que se pueden jugar se estan jugando
                                                if(estado[i] == 3){
                                                    sprintf(buffer, "-Err: El maximo de partidas se ha alcanzado espere unos segundos y vuelva a escribir el comando INICIAR-PARTIDA\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                    send(j,buffer,sizeof(buffer),0);
                                                    estado[i]=2;
                                                    estado[j]=2;
                                                    break;
                                                }
                                            }
                                        }
                                        if(estado[i] == 3){
                                            sprintf(buffer, "+Ok: Esperando otro jugador\n");
                                            send(i,buffer,sizeof(buffer),0);
                                        }
                                    }
                                    else if(estado[i] == 0){
                                        sprintf(buffer, "-Err: Tienes que introducir un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 1){
                                        sprintf(buffer, "-Err: Tienes que verificar un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] >= 3){
                                        sprintf(buffer, "-Err: Este comando no se puede usar mientras estas en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else if(strcmp(buffer,"PEDIR-CARTA\n") == 0){
                                    //Se comprueba que el cliente esta jugando la partida
                                    if(estado[i] == 4){
                                        for(int j=0; 10 > j; j++){
                                            //Se comprueba si es el j1 o el j2
                                            if(i == partidas[j].j1){
                                                //Se reparte la nueva carta 
                                                do{
                                                    partidas[j].cartasj1[partidas[j].numcartasj1][0]=palorand();
                                                    partidas[j].cartasj1[partidas[j].numcartasj1][1]=numerorand();
                                                }while(revisarcartas(j,i) == 1);
                                                partidas[j].numcartasj1++;
                                                sumarcartas(j,i);
                                                mostrarcartas(j,i);
                                                //Comprobamos si el jugador se ha pasado de 21 puntos
                                                switch(sumarcartas(j,i)){
                                                    case 1:
                                                        estado[i]=5;
                                                        sprintf(buffer, "-Err: Excedido el valor de 21\n");
                                                        send(i,buffer,sizeof(buffer),0);
                                                        
                                                    break;
                                                    case 2:
                                                        estado[i]=5;
                                                        sprintf(buffer, "+Ok: El valor de tus cartas es 21, ya no se permite pedir mas cartas\n");
                                                        send(i,buffer,sizeof(buffer),0);
                                                    break;
                                                }
                                                //Comprobamos si los dos jugadores han terminado
                                                if(estado[i] == 5 && estado[partidas[j].j2] == 5){
                                                    //Terminamos la partida
                                                    switch(terminarpartida(j)){
                                                        case -1:
                                                            sprintf(buffer, "+Ok: No hay ganadores\n");
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 0:
                                                            sprintf(buffer, "+Ok: Jugador %s y jugador %s habeis empatado la partida\n",nomclie[i],nomclie[partidas[j].j2]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 1:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[i]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 2:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[partidas[j].j2]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                    }
                                                    //Actualizamos los estados y vaciamos la mesa
                                                    estado[i]=2;
                                                    estado[partidas[j].j2]=2;
                                                    vaciarmesa(j);
                                                }
                                                break;
                                            }
                                            else if(i == partidas[j].j2){
                                                //Se reparte la nueva carta
                                                do{
                                                    partidas[j].cartasj2[partidas[j].numcartasj2][0]=palorand();
                                                    partidas[j].cartasj2[partidas[j].numcartasj2][1]=numerorand();
                                                }while(revisarcartas(j,i) == 1);
                                                partidas[j].numcartasj2++;
                                                sumarcartas(j,i);
                                                mostrarcartas(j,i);
                                                //Comprobamos si el jugador se ha pasado de 21 puntos
                                                switch(sumarcartas(j,i)){
                                                    case 1:
                                                        estado[i]=5;
                                                        sprintf(buffer, "-Err: Excedido el valor de 21\n");
                                                        send(i,buffer,sizeof(buffer),0);
                                                    break;
                                                    case 2:
                                                        estado[i]=5;
                                                        sprintf(buffer, "+Ok: El valor de tus cartas es 21, ya no se permite pedir mas cartas\n");
                                                        send(i,buffer,sizeof(buffer),0);
                                                    break;
                                                }
                                                //Comprobamos si los dos jugadores han terminado
                                                if(estado[i] == 5 && estado[partidas[j].j1] == 5){
                                                    //Terminamos la partida
                                                    switch(terminarpartida(j)){
                                                        case -1:
                                                            sprintf(buffer, "+Ok: No hay ganadores\n");
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 0:
                                                            sprintf(buffer, "+Ok: Jugador %s y jugador %s habeis empatado la partida\n",nomclie[i],nomclie[partidas[j].j1]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 1:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[partidas[j].j1]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 2:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[i]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                    }
                                                    //Actualizamos los estados y vaciamos la mesa
                                                    estado[i]=2;
                                                    estado[partidas[j].j1]=2;
                                                    vaciarmesa(j);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    else if(estado[i] == 0){
                                        sprintf(buffer, "-Err: Tienes que introducir un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 1){
                                        sprintf(buffer, "-Err: Tienes que verificar un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 3){
                                        sprintf(buffer, "-Err: Este comando solo se puede usar cuando se ha inciado partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 5){
                                        sprintf(buffer, "-Err: Este comando solo se puede usar mientras estas jugando la partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else if(strcmp(buffer,"PLANTARME\n") == 0){
                                    //Se comprueba que el jugador todavia no se ha plantado en la partida
                                    if(estado[i] == 4){
                                        estado[i]=5;
                                        for(int j=0; 10 > j; j++){
                                            //Comprobamos si el jugador es el j1 o el j2
                                            if(i == partidas[j].j1){
                                                //Comprobamos si el rival se ha plantado
                                                if(estado[partidas[j].j2] == 5){
                                                    //Terminamos la partida
                                                    switch(terminarpartida(j)){
                                                        case -1:
                                                            sprintf(buffer, "+Ok: No hay ganadores\n");
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 0:
                                                            sprintf(buffer, "+Ok: Jugador %s y jugador %s habeis empatado la partida\n",nomclie[i],nomclie[partidas[j].j2]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 1:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[i]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 2:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[partidas[j].j2]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j2,buffer,sizeof(buffer),0);
                                                        break;
                                                    }
                                                    //Actualizamos los estados y vaciamos la mesa
                                                    estado[i]=2;
                                                    estado[partidas[j].j2]=2;
                                                    vaciarmesa(j);
                                                }
                                                else{
                                                    sprintf(buffer, "+Ok: Esperando a que termine el otro jugador\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                }
                                                break;
                                            }
                                            else if(i == partidas[j].j2){
                                                //Comprobamos si el rival se ha plantado
                                                if(estado[partidas[j].j1] == 5){
                                                    //Terminamos la partida
                                                    switch(terminarpartida(j)){
                                                        case -1:
                                                            sprintf(buffer, "+Ok: No hay ganadores\n");
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 0:
                                                            sprintf(buffer, "+Ok: Jugador %s y jugador %s habeis empatado la partida\n",nomclie[i],nomclie[partidas[j].j1]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 1:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[partidas[j].j1]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                        case 2:
                                                            sprintf(buffer, "+Ok: Jugador %s ha ganado la partida\n",nomclie[i]);
                                                            send(i,buffer,sizeof(buffer),0);
                                                            send(partidas[j].j1,buffer,sizeof(buffer),0);
                                                        break;
                                                    }
                                                    //Actualizamos los estados y vaciamos la mesa
                                                    estado[i]=2;
                                                    estado[partidas[j].j1]=2;
                                                    vaciarmesa(j);
                                                }
                                                else{
                                                    sprintf(buffer, "+Ok: Esperando a que termine el otro jugador\n");
                                                    send(i,buffer,sizeof(buffer),0);
                                                }
                                                break;
                                            }
                                        }

                                    }
                                    else if(estado[i] == 0){
                                        sprintf(buffer, "-Err: Tienes que introducir un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 1){
                                        sprintf(buffer, "-Err: Tienes que verificar un usuario antes de entrar en partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 3){
                                        sprintf(buffer, "-Err: Este comando solo se puede usar cuando se ha inciado partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                    else if(estado[i] == 5){
                                        sprintf(buffer, "-Err: Este comando solo se puede usar mientras estas jugando la partida\n");
                                        send(i,buffer,sizeof(buffer),0);
                                    }
                                }
                                else{
                                    sprintf(buffer, "-Err: NO se reconoce el comando introducido\n");
                                    send(i,buffer,sizeof(buffer),0);

                                }
                                                                
                                
                            }
                            //Si el cliente introdujo ctrl+c
                            if(recibidos== 0)
                            {
                                printf("El socket %d, ha introducido ctrl+c\n", i);
                                strcpy(nomclie[i],"");
                                if(estado[i] >= 4){
                                    for(int mesa=0; mesa < 10; mesa++){
                                        if(i == partidas[mesa].j1){
                                            sprintf(buffer, "-Err: Tu rival se ha desconectado\n");
                                            send(partidas[mesa].j2,buffer,sizeof(buffer),0);
                                            estado[partidas[mesa].j2]=2;
                                            vaciarmesa(mesa);
                                        }
                                        else if(i == partidas[mesa].j2){
                                            sprintf(buffer, "-Err: Tu rival se ha desconectado\n");
                                            send(partidas[mesa].j1,buffer,sizeof(buffer),0);
                                            estado[partidas[mesa].j1]=2;
                                            vaciarmesa(mesa);
                                        }
                                    }
                                }
                                estado[i]=0;
                                //Eliminar ese socket
                                salirCliente(i,&readfds,&numClientes,arrayClientes);
                            }
                        }
                    }
                }
            }
		}

	close(sd);
	return 0;
	
}

void salirCliente(int socket, fd_set * readfds, int * numClientes, int arrayClientes[]){
  
    char buffer[250];
    int j;
    
    close(socket);
    FD_CLR(socket,readfds);
    
    //Re-estructurar el array de clientes
    for (j = 0; j < (*numClientes) - 1; j++)
        if (arrayClientes[j] == socket)
            break;
    for (; j < (*numClientes) - 1; j++)
        (arrayClientes[j] = arrayClientes[j+1]);
    
    (*numClientes)--;

}


void manejador (int signum){
    printf("\nSe ha recibido la señal sigint\n");
    signal(SIGINT,manejador);
    
    //Implementar lo que se desee realizar cuando ocurra la excepción de ctrl+c en el servidor
}

void mostrarcartas(int mesa, int receptor){
    char buffer[MSG_SIZE];
    char aux[MSG_SIZE];
    //Comprobamos si es el j1 o el j2
    if(receptor == partidas[mesa].j1){
        //Mostramos las cartas del j1
        sprintf(buffer, "TUS-CARTAS:");
        for(int i=0; partidas[mesa].numcartasj1 > i; i++){

            switch(partidas[mesa].cartasj1[i][0]){
                case 1:
                    sprintf(aux, " [Corazones,%d]",partidas[mesa].cartasj1[i][1]);
                break;
                case 2:
                    sprintf(aux, " [Diamantes,%d]",partidas[mesa].cartasj1[i][1]);
                break;
                case 3:
                    sprintf(aux, " [Picas,%d]",partidas[mesa].cartasj1[i][1]);
                break;
                case 4:
                    sprintf(aux, " [Treboles,%d]",partidas[mesa].cartasj1[i][1]);
                break;
            }
            strcat(buffer,aux);
        }
        strcat(buffer, "\n");
        //Mostramos la primera carta del j2
        switch(partidas[mesa].cartasj2[0][0]){
            case 1:
                sprintf(aux, "OPONENTE: [Corazones,%d]\n",partidas[mesa].cartasj2[0][1]);
            break;
            case 2:
                sprintf(aux, "OPONENTE: [Diamantes,%d]\n",partidas[mesa].cartasj2[0][1]);
            break;
            case 3:
                sprintf(aux, "OPONENTE: [Picas,%d]\n",partidas[mesa].cartasj2[0][1]);
            break;
            case 4:
                sprintf(aux, "OPONENTE: [Treboles,%d]\n",partidas[mesa].cartasj2[0][1]);
            break;
        }
        strcat(buffer,aux);
        //Mostramos la puntuacion del usuario
        sprintf(aux, "TU PUNTUACION ES: %d\n",partidas[mesa].puntosj1);
        strcat(buffer,aux);

    }
    else{
        //Mostramos las cartas del j2
        sprintf(buffer, "TUS-CARTAS:");
        for(int i=0; partidas[mesa].numcartasj2 > i; i++){

            switch(partidas[mesa].cartasj2[i][0]){
                case 1:
                    sprintf(aux, " [Corazones,%d]",partidas[mesa].cartasj2[i][1]);
                break;
                case 2:
                    sprintf(aux, " [Diamantes,%d]",partidas[mesa].cartasj2[i][1]);
                break;
                case 3:
                    sprintf(aux, " [Picas,%d]",partidas[mesa].cartasj2[i][1]);
                break;
                case 4:
                    sprintf(aux, " [Treboles,%d]",partidas[mesa].cartasj2[i][1]);
                break;
            }
            strcat(buffer,aux);
        }
        strcat(buffer, "\n");
        //Mostramos las cartas del j1
        switch(partidas[mesa].cartasj1[0][0]){
            case 1:
                sprintf(aux, "OPONENTE: [Corazones,%d]\n",partidas[mesa].cartasj1[0][1]);
            break;
            case 2:
                sprintf(aux, "OPONENTE: [Diamantes,%d]\n",partidas[mesa].cartasj1[0][1]);
            break;
            case 3:
                sprintf(aux, "OPONENTE: [Picas,%d]\n",partidas[mesa].cartasj1[0][1]);
            break;
            case 4:
                sprintf(aux, "OPONENTE: [Treboles,%d]\n",partidas[mesa].cartasj1[0][1]);
            break;
        }
        strcat(buffer,aux);
        //Mostramos la puntuacion del j2
        sprintf(aux, "TU PUNTUACION ES: %d\n",partidas[mesa].puntosj2);
        strcat(buffer,aux);
    }
    //Enviamos el mensaje
    send(receptor,buffer,sizeof(buffer),0);
}

void iniciopartida(int mesa, int j1, int j2){
    //Guardamos a los dos jugadores
    partidas[mesa].j1=j1;
    partidas[mesa].j2=j2;

    //Repartimos dos cartas a cada jugador
    partidas[mesa].cartasj1[0][0]= palorand();
    partidas[mesa].cartasj1[0][1]= numerorand();
    partidas[mesa].numcartasj1=1;
    do{
        partidas[mesa].cartasj1[1][0]= palorand();
        partidas[mesa].cartasj1[1][1]= numerorand();
    }while(revisarcartas(mesa,j1) == 1);
    partidas[mesa].numcartasj1=2;

    do{
        partidas[mesa].cartasj2[0][0]= palorand();
        partidas[mesa].cartasj2[0][1]= numerorand();
    }while(revisarcartas(mesa,j2) == 1);
    partidas[mesa].numcartasj2=1;

    do{
        partidas[mesa].cartasj2[1][0]= palorand();
        partidas[mesa].cartasj2[1][1]= numerorand();
    }while(revisarcartas(mesa,j2) == 1);
    partidas[mesa].numcartasj2=2;
}

int sumarcartas(int mesa, int jugador){
    int suma=0;
    int ases=0;
    //Se comprueba si somos el j1 o el j2
    if(jugador == partidas[mesa].j1){
        for(int i=0; i < partidas[mesa].numcartasj1; i++){
            //Sumamos la puntuacion de las cartas
            if(partidas[mesa].cartasj1[i][1] >= 10){
                suma+=10;
            }
            else if(partidas[mesa].cartasj1[i][1] == 1){
                ases++;
                suma+=11;
            }
            else{
                suma+=partidas[mesa].cartasj1[i][1];
            }
            //Comprobamos si nos hemos pasado de 21 puntos
            if(suma > 21){
                //Si tenemos algun as cambiamos su puntuacion de 11 a 1 
                if(ases > 0){
                    ases--;
                    suma-=10;
                }
                if(suma > 21){
                    partidas[mesa].puntosj1=suma;
                    return 1;
                }
            }
        }
        //Actualizamos el valor de los puntos
        partidas[mesa].puntosj1=suma;
    }
    else{
        for(int i=0; i < partidas[mesa].numcartasj2; i++){
            //Sumamos la puntuacion de las cartas
            if(partidas[mesa].cartasj2[i][1] >= 10){
                suma+=10;
            }
            else if(partidas[mesa].cartasj2[i][1] == 1){
                ases++;
                suma+=11;
            }
            else{
                suma+=partidas[mesa].cartasj2[i][1];
            }
            //Comprobamos si nos hemos pasado de 21 puntos
            if(suma > 21){
                //Si tenemos algun as cambiamos su puntuacion de 11 a 1 
                if(ases > 0){
                    ases--;
                    suma-=10;
                }
                if(suma > 21){
                    partidas[mesa].puntosj2=suma;
                    return 1;
                }
            }
        }
        //Actualizamos el valor de los puntos
        partidas[mesa].puntosj2=suma;
    }
    //Comprobamos si los puntos es exactamente 21
    if(suma == 21){
        return 2;
    }
    return 0;
}

int terminarpartida(int mesa){
    //Se actualiza la puntuación final
    sumarcartas(mesa,partidas[mesa].j1);
    sumarcartas(mesa,partidas[mesa].j2);
    //Se envia el resultado
    if((partidas[mesa].puntosj1 <= 21 && partidas[mesa].puntosj1 > partidas[mesa].puntosj2) || (partidas[mesa].puntosj1 <= 21 && partidas[mesa].puntosj2 > 21)){
        return 1;
    }
    else if((partidas[mesa].puntosj2 <= 21 && partidas[mesa].puntosj2 > partidas[mesa].puntosj1) || (partidas[mesa].puntosj2 <= 21 && partidas[mesa].puntosj1 > 21)){
        return 2;
    }
    else if(partidas[mesa].puntosj1 > 21 && partidas[mesa].puntosj2 > 21){
        return -1;
    }
    else{
        return 0;
    }
}

void vaciarmesa(int mesa){
    //Reseteamos todos los valores de la mesa en la que se ha terminado la partida
    partidas[mesa].j1=0;
    partidas[mesa].j2=0;
    partidas[mesa].numcartasj1=0;
    partidas[mesa].numcartasj2=0;
    partidas[mesa].puntosj1=0;
    partidas[mesa].puntosj2=0;
}

int revisarcartas(int mesa, int jugador){
    int ultimacarta[2];
    //Comprobamos si es el j1 o el j2
    if(partidas[mesa].j1 == jugador){
        //Se guarada la ultima carta dada
        ultimacarta[0]=partidas[mesa].cartasj1[partidas[mesa].numcartasj1][0];
        ultimacarta[1]=partidas[mesa].cartasj1[partidas[mesa].numcartasj1][1];
        
    }
    else{
        //Se guarda la ultima carta dada
        ultimacarta[0]=partidas[mesa].cartasj2[partidas[mesa].numcartasj2][0];
        ultimacarta[1]=partidas[mesa].cartasj2[partidas[mesa].numcartasj2][1];
    }
    //Se comprueba si esa carta la tiene el j1 
    for(int i=0; partidas[mesa].numcartasj1 > i; i++){
        if(ultimacarta[0] == partidas[mesa].cartasj1[i][0] && ultimacarta[1] == partidas[mesa].cartasj1[i][1]){
            return 1;
        }
    }
    //Se comprueba si esa carta la tiene el j2
    for(int i=0; partidas[mesa].numcartasj2 > i; i++){
        if(ultimacarta[0] == partidas[mesa].cartasj2[i][0] && ultimacarta[1] == partidas[mesa].cartasj2[i][1]){
            return 1;
        }
    }
    return 0;
}