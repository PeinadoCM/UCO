package data.dto.pistas;

/**
 * La clase {@code MaterialDTO} representa un material utilizado en pistas,
 * incluyendo su identificación, uso (interior o exterior), tipo y estado.
 */
public class MaterialDTO {
    
    private int id;
    //uso=true -> Uso de exterior
    //uso=false -> Uso de interior
    private boolean uso;
    private Tipo tipo;
    private Estado estado;

    /**
     * Crea un nuevo objeto {@code MaterialDTO} sin inicializar sus atributos.
     */
    public MaterialDTO(){

    }

    /**
     * Crea un nuevo objeto {@code MaterialDTO} con la información proporcionada.
     * 
     * @param id El identificador del material.
     * @param uso El uso del material (true para exterior, false para interior).
     * @param tipo El tipo de material.
     * @param estado El estado del material.
     */
    public MaterialDTO(int id, boolean uso, Tipo tipo, Estado estado){

        this.id = id;
        this.uso = uso;
        this.tipo = tipo;
        this.estado = estado;
    }

    /**
     * Devuelve el identificador del material.
     *
     * @return El identificador del material.
     */
    public int getId(){

        return id;
    }

    /**
     * Establece el identificador del material.
     *
     * @param id El nuevo identificador del material.
     */
    public void setId(int id){

        this.id = id;
    }

    /**
     * Devuelve el uso del material.
     *
     * @return true si el material es para uso exterior, false si es para uso interior.
     */
    public boolean getUso(){

        return uso;
    }

    /**
     * Establece el uso del material.
     *
     * @param uso El nuevo uso del material (true para exterior, false para interior).
     */
    public void setUso(boolean uso){
        
        this.uso = uso;
    }

    /**
     * Devuelve el tipo del material.
     *
     * @return El tipo del material.
     */
    public Tipo getTipo(){

        return tipo;
    }

    /**
     * Establece el tipo del material.
     *
     * @param tipo El nuevo tipo del material.
     */
    public void setTipo(Tipo tipo){

        this.tipo = tipo;
    }

    /**
     * Devuelve el estado del material.
     *
     * @return El estado del material.
     */
    public Estado getEstado(){

        return estado;
    }

    /**
     * Establece el estado del material.
     *
     * @param estado El nuevo estado del material.
     */
    public void setEstado(Estado estado){

        this.estado = estado;
    }

    /**
     * Devuelve una representación en cadena del material.
     *
     * @return Una cadena que representa la información del material.
     */
    public String toString(){

        return "Material: id = "+id+", uso = "+uso+", tipo = "+tipo+", estado = "+estado+".";
    }

}