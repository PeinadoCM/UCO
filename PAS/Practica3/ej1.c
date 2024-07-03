#include <getopt.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <pwd.h>
#include <grp.h>
#include <string.h>

// Funciones para cada opción
void help(){
    printf("Uso del programa: ./ejercicio1 [opciones]\n");
    printf("La unicas opciones validas son:\n");
    printf("--> --help, junto con cualquiera (se ignoran las demas)\n--> vacio (equivalente a --active --maingroup)\n--> --user <uid o nombre de usuario>\n--> --user <uid o nombre de usuario> --maingroup\n--> --active\n--> --active --maingroup\n--> --group <gid o nombre de grupo>\n--> --allgroups\n");
}

void user(char* user, bool m){
    struct passwd *pw;
    // DEVUELVE LA ESTRUCTURA TRAS RECIBIR user COMO PARÁMETRO
    if ((pw = getpwnam(user)) == NULL){ 
        fprintf(stderr, "Fallo al obtener información de usuario.\n");
        exit(1);
    }
    // Mostramos todos los datos del usuario
    printf("Usuario:\n");
    printf("-->Nombre: %s\n", pw->pw_gecos); // No es lo mismo el nombre de usuario asociado a un login que el propio login
    printf("-->Login: %s\n", pw->pw_name);
    printf("-->Password: %s\n", pw->pw_passwd);
    printf("-->UID: %d\n", pw->pw_uid);
    printf("-->Home: %s\n", pw->pw_dir);
    printf("-->Shell: %s\n", pw->pw_shell);
    printf("-->Número de grupo principal: %d\n", pw->pw_gid);
    // Comprobamos si se ha que hacer la opcion m
    if(m==true){
        struct group *gr;
        // DEVUELVE LA ESTRUCTURA TRAS RECIBIR pw->pw_gid COMO PARÁMETRO
        if((gr = getgrgid(pw->pw_gid)) == NULL){
            fprintf(stderr, "Fallo al obtener información de grupo.\n");
            exit(1);
        }
        printf("\nGrupo:\n");
        printf("-->Nombre del grupo: %s\n", gr->gr_name);
        printf("-->GID: %d\n", gr->gr_gid);
        // Mostramos todos los miembros secundarios
        printf("-->Miembros secundarios: ");
        if(gr->gr_mem[0] == NULL){
            printf("\n");
        }
        else{
            for(char **member = gr->gr_mem; *member != NULL; member++){
                printf("%s ", *member);
            }
            printf("\n");
        }
    }
}

void userid(int uid, bool m){
    struct passwd *pw;
    // DEVUELVE LA ESTRUCTURA TRAS RECIBIR uid COMO PARÁMETRO
    if ((pw = getpwuid(uid)) == NULL){ 
        fprintf(stderr, "Fallo al obtener información de usuario.\n");
        exit(1);
    }
    // Mostramos todos los datos del usuario
    printf("Usuario:\n");
    printf("-->Nombre: %s\n", pw->pw_gecos); // No es lo mismo el nombre de usuario asociado a un login que el propio login
    printf("-->Login: %s\n", pw->pw_name);
    printf("-->Password: %s\n", pw->pw_passwd);
    printf("-->UID: %d\n", pw->pw_uid);
    printf("-->Home: %s\n", pw->pw_dir);
    printf("-->Shell: %s\n", pw->pw_shell);
    printf("-->Número de grupo principal: %d\n", pw->pw_gid);
    // Comprobamos si se ha que hacer la opcion m
    if(m==true){
        struct group *gr;
        // DEVUELVE LA ESTRUCTURA TRAS RECIBIR pw->pw_gid COMO PARÁMETRO
        if((gr = getgrgid(pw->pw_gid)) == NULL){
            fprintf(stderr, "Fallo al obtener información de grupo.\n");
            exit(1);
        }
        printf("\nGrupo:\n");
        printf("-->Nombre del grupo: %s\n", gr->gr_name);
        printf("-->GID: %d\n", gr->gr_gid);
        // Mostramos todos los miembros secundarios
        printf("-->Miembros secundarios: ");
        if(gr->gr_mem[0] == NULL){
            printf("\n");
        }
        else{
            for(char **member = gr->gr_mem; *member != NULL; member++){
                printf("%s ", *member);
            }
            printf("\n");
        }
    }
}

void gr(char* grname){
    struct group *gr;
    // DEVUELVE LA ESTRUCTURA TRAS RECIBIR grname COMO PARÁMETRO
    if((gr = getgrnam(grname)) == NULL){
        fprintf(stderr, "Fallo al obtener información de grupo.\n");
        exit(1);
    }
    printf("Grupo:\n");
    printf("-->Nombre del grupo: %s\n", gr->gr_name);
    printf("-->GID: %d\n", gr->gr_gid);
    // Mostramos todos los miembros secundarios
    printf("-->Miembros secundarios: ");
    if(gr->gr_mem[0] == NULL){
        printf("\n");
    }
    else{
        for(char **member = gr->gr_mem; *member != NULL; member++){
            printf("%s ", *member);
        }
        printf("\n");
    }
    printf("\n");
}

void grid(int grid){
    struct group *gr;
    // DEVUELVE LA ESTRUCTURA TRAS RECIBIR grid COMO PARÁMETRO
    if((gr = getgrgid(grid)) == NULL){
        fprintf(stderr, "Fallo al obtener información de grupo.\n");
        exit(1);
    }
    printf("Grupo:\n");
    printf("-->Nombre del grupo: %s\n", gr->gr_name);
    printf("-->GID: %d\n", gr->gr_gid);
    // Mostramos todos los miembros secundarios
    printf("-->Miembros secundarios: ");
    if(gr->gr_mem[0] == NULL){
        printf("\n");
    }
    else{
        for(char **member = gr->gr_mem; *member != NULL; member++){
            printf("%s ", *member);
        }
        printf("\n");
    }
}

void show_all_groups() {
    FILE *file = fopen("/etc/group", "r");
    if(!file){
        perror("Error al abrir el archivo /etc/group");
        exit(EXIT_FAILURE);
    }

    char line[256];
    while(fgets(line, sizeof(line), file)){
        // La línea tiene el formato group_name:x:group_id:user_list
        //Cogemos solo hasta el primer :
        char *group_name = strtok(line, ":");
        gr(group_name);
    }

    fclose(file);
}

int main(int argc, char **argv) {
    int c;
    system("clear");
    /* Estructura a utilizar por getoptlong */
    static struct option long_options[] = {
        /* Opciones que no van a actuar sobre un flag */
        //  {<nombre largo>, <recibe/no recibe argumento>, NULL, <nombre corto>}
        {"user", required_argument, NULL, 'u'},
        {"group", required_argument, NULL, 'g'},
        {"active", no_argument, NULL, 'a'},
        {"maingroup", no_argument, NULL, 'm'},
        {"allgroups", no_argument, NULL, 's'},
        {"help", no_argument, NULL, 'h'},
        // Necesario para indicar el final de las opciones
        {0, 0, 0, 0}
    };

    /* Estas variables servirán para almacenar el resultado de procesar la línea
     * de comandos */
    bool aflag = false;
    bool sflag = false;
    bool hflag = false;
    char *uvalue = NULL;
    char *gvalue = NULL;
    bool mflag = false;

    /* Deshabilitar la impresión de errores por defecto */
    /* opterr=0; */
    opterr=0;
    while ((c = getopt_long(argc, argv, "ashu:g:m", long_options, NULL)) != -1) {
        //printf("optind: %d, optarg: %s, optopt: %c\n", optind, optarg, optopt);

        switch (c) {
        case 'a':
            // printf("Opción -a\n");
            aflag = true;
            break;

        case 's':
            // printf("Opción -s\n");
            sflag = true;
            break;
        
        case 'h':
            // printf("Opción -h\n");
            hflag = true;
            break;

        case 'u':
            //printf("Opción -u con valor '%s'\n", optarg);
            uvalue = optarg;
            break;

        case 'g':
            // printf("Opción -g con valor '%s'\n", optarg);
            gvalue = optarg;
            break;

        case 'm':
            // printf("Opción -m con valor '%s'\n", optarg);
            mflag = true;
            break;

        case '?':
            /* getopt_long ya imprime su mensaje de error, no es necesario hacer
             * nada */
            /* Si queremos imprimir nuestros propios errores, debemos poner
            opterr=0, y hacer algo como lo que se expone a continuacion. Pruebe
            a hacer sus propios errores. if (optopt == 'c')
                    fprintf (stderr, "La opción %c requiere un argumento.\n",
            optopt);*/
            if (isprint(optopt)) // Se mira si el caracter es imprimible
                fprintf(stderr,
                        "Opción desconocida \"-%c\". Valor de opterr = %d\n",
                        optopt, opterr);
            else // Caracter no imprimible o especial
                fprintf(stderr, "Caracter `\\x%x'. Valor de opterr = %d\n",
                        optopt, opterr);
            return 1; // Finaliza el programa

            break;

        default:
            abort();
        }
    }

    /* Imprimir el resto de argumentos de la línea de comandos que no son
     * opciones con "-" */
    if (optind < argc) {
        printf("Argumentos ARGV que no son opciones: ");
        while (optind < argc)
            printf("%s ", argv[optind++]);
        putchar('\n');
    }
    
    //Opcion -h
    if(hflag==true){
        help();
    }
    //Ninguna opcion
    else if(argc==1){
        user(getenv("USER"), true);
    }
    //Opcion -u
    else if(uvalue!=NULL && aflag==false && sflag==false && gvalue==NULL){
        int cont=0;
        int letras=0;
        char* aux=uvalue;
        while (*aux != '\0'){
        // Comprueba si el carácter es un número
        if(isdigit(*aux)) {
            cont++;
        }
        // Avanza al siguiente carácter
        letras++;
        aux++;
        }
        if(letras==cont){
            userid(atoi(uvalue),mflag);
        }
        else{
            user(uvalue, mflag);
        }
    }
    //Opcion -a
    else if(aflag==true && sflag==false && uvalue==NULL && gvalue==NULL){
        user(getenv("USER"), mflag);
    }
    //Opcion -g
    else if(gvalue!=NULL && aflag==false && sflag==false && uvalue==NULL && mflag==false){
        int cont=0;
        int letras=0;
        char* aux=gvalue;
        while (*aux != '\0'){
        // Comprueba si el carácter es un número
        if(isdigit(*aux)) {
            cont++;
        }
        // Avanza al siguiente carácter
        letras++;
        aux++;
        }
        if(letras==cont){
            grid(atoi(gvalue));
        }
        else{
            gr(gvalue);
        }
    }
    //Opcion -s
    else if(sflag==true && aflag==false && uvalue==NULL && gvalue==NULL && mflag==false){
        show_all_groups();
    }
    //Opcion invalida
    else{
        printf("Error, opciones invalidas\n");
        help();
        return 1;
    }

    //printf("aflag = %d, sflag = %d, hflag = %d, uvalue = %s, gvalue = %s, mflag = %d\n", aflag, sflag, hflag, uvalue, gvalue, mflag);
    exit(0);
}