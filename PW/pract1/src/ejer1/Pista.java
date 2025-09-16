package ejer1;

import java.util.ArrayList;
import java.text.ParseException;


/**
 * La clase {@code Pista} representa una pista deportiva, incluyendo su nombre,
 * estado, tipo, tamaño, número máximo de jugadores y lista de materiales asociados.
 */
public class Pista {
    
    private String nombre;
    //estado=true -> pista disponible
    //estado=false -> pista no disponible
    private boolean estado;
    //tipo=true -> pista de exterior
    //tipo=false -> pista de interior
    private boolean tipo;
    private TipoPista tamaño;
    private int maxjugadores;
    private ArrayList<Material> listamateriales;
    
    /**
     * Crea un nuevo objeto {@code Pista} sin inicializar sus atributos.
     */
    public Pista() {
    	
    }
    
    /**
     * Crea un nuevo objeto {@code Pista} con la información proporcionada.
     * 
     * @param nombre El nombre de la pista.
     * @param estado El estado de la pista (true para disponible, false para no disponible).
     * @param tipo El tipo de pista (true para exterior, false para interior).
     * @param tamaño El tamaño de la pista.
     * @param maxjugadores El número máximo de jugadores en la pista.
     */
    public Pista(String nombre, boolean estado, boolean tipo, TipoPista tamaño, int maxjugadores) {
		
    	this.nombre=nombre;
    	this.estado=estado;
    	this.tipo=tipo;
    	this.tamaño=tamaño;
    	this.maxjugadores=maxjugadores;
    	this.listamateriales = new ArrayList<Material>();
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
    public ArrayList<Material> getListaMateriales(){
    	return listamateriales;
    }
    
    /**
     * Establece la lista de materiales asociados a la pista.
     *
     * @param listamateriales La nueva lista de materiales.
     */
    public void setListaMateriales(ArrayList<Material> listamateriales) {
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
    public ArrayList<Material> consultarMaterialesDisponibles(){
    	ArrayList<Material> disponibles = new ArrayList<Material>();
    	for(Material material: listamateriales) {
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
    public void asociarMaterialAPista(Material material) {
    	int numpelotas=0, numcanastas=0, numconos=0;
    	if(material.getUso() != false && tipo != true) {
    		for(Material mat: listamateriales) {
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

    /**
     * Crea un objeto {@code Pista} a partir de una línea de texto con formato específico.
     *
     * @param linea La línea de texto que contiene la información de la pista.
     * @return Un objeto {@code Pista} con la información extraída de la línea de texto.
     * @throws ParseException Si hay un error al parsear la información.
     */
	public static Pista fromTexto(String linea)throws ParseException {
       
        String[] partes = linea.split(",");
        Pista pista = new Pista();
        pista.nombre = partes[0]; 
        pista.estado = Boolean.parseBoolean(partes[1]);
        pista.tipo = Boolean.parseBoolean(partes[2]);
        pista.tamaño = TipoPista.valueOf(partes[3]);
		pista.maxjugadores = Integer.parseInt(partes[4]);
		
		if(partes[5].equals("[]")){
			pista.listamateriales = new ArrayList<>();
		}
		else {
			pista.listamateriales = new ArrayList<>();
			for(int aux=5; aux < partes.length; aux+=4) {
				Material material = new Material();
				
				String[] VariablesMaterial=partes[aux].split("= ");
				material.setId(Integer.parseInt(VariablesMaterial[1]));
				VariablesMaterial=partes[aux+1].split("= ");
				material.setUso(Boolean.parseBoolean(VariablesMaterial[1]));
				VariablesMaterial=partes[aux+2].split("= ");
				material.setTipo(Tipo.valueOf(VariablesMaterial[1]));
				VariablesMaterial=partes[aux+3].split("= ");
				if(aux+4 == partes.length) {
					material.setEstado(Estado.valueOf(VariablesMaterial[1].replace(".]", ""))); 
				}
				else {
					material.setEstado(Estado.valueOf(VariablesMaterial[1].replace(".", "")));
				}
				pista.listamateriales.add(material);
			}
		}

        return pista;
    }
}
