package data.dto.pistas;

import java.util.ArrayList;	


/**
 * La clase {@code PistaDTO} representa una pista deportiva, incluyendo su nombre,
 * estado, tipo, tamaño, número máximo de jugadores y lista de materiales asociados.
 */
public class PistaDTO {
    
    private String nombre;
    //estado=true -> pista disponible
    //estado=false -> pista no disponible
    private boolean estado;
    //tipo=true -> pista de exterior
    //tipo=false -> pista de interior
    private boolean tipo;
    private TipoPista tamaño;
    private int maxjugadores;
    private ArrayList<MaterialDTO> listamateriales;
    
    /**
     * Crea un nuevo objeto {@code PistaDTO} sin inicializar sus atributos.
     */
    public PistaDTO() {
    	
    }
    
    /**
     * Crea un nuevo objeto {@code PistaDTO} con la información proporcionada.
     * 
     * @param nombre El nombre de la pista.
     * @param estado El estado de la pista (true para disponible, false para no disponible).
     * @param tipo El tipo de pista (true para exterior, false para interior).
     * @param tamaño El tamaño de la pista.
     * @param maxjugadores El número máximo de jugadores en la pista.
     */
    public PistaDTO(String nombre, boolean estado, boolean tipo, TipoPista tamaño, int maxjugadores) {
		
    	this.nombre=nombre;
    	this.estado=estado;
    	this.tipo=tipo;
    	this.tamaño=tamaño;
    	this.maxjugadores=maxjugadores;
    	this.listamateriales = new ArrayList<MaterialDTO>();
    }

    public PistaDTO(String nombre, boolean estado, boolean tipo, TipoPista tamaño, int maxjugadores, ArrayList<MaterialDTO> materiales) {
		
    	this.nombre=nombre;
    	this.estado=estado;
    	this.tipo=tipo;
    	this.tamaño=tamaño;
    	this.maxjugadores=maxjugadores;
    	this.listamateriales = materiales;
    }
    
    /**
     * Devuelve el nombre de la pista.
     *
     * @return El nombre de la pista.
     */
    public String getNombre() {
    	return nombre;
    }
    
    /**
     * Establece el nombre de la pista.
     *
     * @param nombre El nuevo nombre de la pista.
     */
    public void setNombre(String nombre) {
    	this.nombre=nombre;
    }
    
    /**
     * Devuelve el estado de la pista.
     *
     * @return true si la pista está disponible, false si no.
     */
    public boolean getEstado() {
    	return estado;
    }
    
    /**
     * Establece el estado de la pista.
     *
     * @param estado El nuevo estado de la pista.
     */
    public void setEstado(boolean estado) {
    	this.estado=estado;
    }
    
    /**
     * Devuelve el tipo de la pista.
     *
     * @return true si es de exterior, false si es de interior.
     */
    public boolean getTipo() {
    	return tipo;
    }
    
    /**
     * Establece el tipo de la pista.
     *
     * @param tipo El nuevo tipo de la pista.
     */
    public void setTipo(boolean tipo) {
    	this.tipo=tipo;
    }
    
    /**
     * Devuelve el tamaño de la pista.
     *
     * @return El tamaño de la pista.
     */
    public TipoPista getTamaño() {
    	return tamaño;
    }
    
    /**
     * Establece el tamaño de la pista.
     *
     * @param tamaño El nuevo tamaño de la pista.
     */
    public void setTamaño(TipoPista tamaño) {
    	this.tamaño=tamaño;
    }
    
    /**
     * Devuelve el número máximo de jugadores en la pista.
     *
     * @return El número máximo de jugadores.
     */
    public int getMaxJugadores() {
    	return maxjugadores;
    }
    
    /**
     * Establece el número máximo de jugadores en la pista.
     *
     * @param maxjugadores El nuevo número máximo de jugadores.
     */
    public void setMaxJugadores(int maxjugadores) {
    	this.maxjugadores=maxjugadores;
    }
    
    /**
     * Devuelve la lista de materiales asociados a la pista.
     *
     * @return La lista de materiales.
     */
    public ArrayList<MaterialDTO> getListaMateriales(){
    	return listamateriales;
    }
    
    /**
     * Establece la lista de materiales asociados a la pista.
     *
     * @param listamateriales La nueva lista de materiales.
     */
    public void setListaMateriales(ArrayList<MaterialDTO> listamateriales) {
    	this.listamateriales=listamateriales;
    }
    
    /**
     * Devuelve una representación en cadena de la pista.
     *
     * @return Una cadena que representa la información de la pista.
     */
    public String toString() {
    	return "Pista: nombre= "+nombre+", estado= "+estado+", tipo= "+tipo+", tamaño= "+tamaño+", maxjugadores= "+maxjugadores+", lista de materiales= "+listamateriales;
    }
    
    /**
     * Consulta los materiales disponibles en la pista.
     *
     * @return Una lista de materiales que están disponibles.
     */
    public ArrayList<MaterialDTO> consultarMaterialesDisponibles(){
    	ArrayList<MaterialDTO> disponibles = new ArrayList<MaterialDTO>();
    	for(MaterialDTO material: listamateriales) {
    		if(material.getEstado() == Estado.DISPONIBLE) {
    			disponibles.add(material);
    		}
    	}
    	return disponibles;
    }
    
    /**
     * Asocia un material a la pista, asegurando que se cumplan las restricciones
     * sobre la cantidad de materiales permitidos.
     *
     * @param material El material a asociar a la pista.
     */
    public void asociarMaterialAPista(MaterialDTO material) {
    	int numpelotas=0, numcanastas=0, numconos=0;
    	if(material.getUso() != false && tipo != true) {
    		for(MaterialDTO mat: listamateriales) {
    			if(mat.getTipo() == Tipo.CANASTAS) {
    				numcanastas++;
    			}
    			if(mat.getTipo() == Tipo.CONOS) {
    				numconos++;
    			}
    			if(mat.getTipo() == Tipo.PELOTAS) {
    				numpelotas++;
    			}
    		}
    		if(material.getTipo() == Tipo.CANASTAS && numcanastas < 2) {
    			listamateriales.add(material);
    		}
    		if(material.getTipo() == Tipo.PELOTAS && numpelotas < 12) {
    			listamateriales.add(material);
    		}
    		if(material.getTipo() == Tipo.CONOS && numconos < 20) {
    			listamateriales.add(material);
    		}
    	}

    }

}
