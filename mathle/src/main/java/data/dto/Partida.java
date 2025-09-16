package data.dto;

public class Partida {
    int idProblema;
    float puntuacion;
    int duracion;
    int intentos;

    public Partida(int idProblema, float puntuacion, int duracion, int intentos) {
        this.idProblema = idProblema;
        this.puntuacion = puntuacion;
        this.duracion = duracion;
        this.intentos = intentos;
    }

    public Partida(){}

    public int getIdProblema() {
        return idProblema;
    }

    public void setIdProblema(int idProblema) {
        this.idProblema = idProblema;
    }
    
    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
}
