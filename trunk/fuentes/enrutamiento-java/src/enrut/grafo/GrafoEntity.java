/*
 * GrafoEntity.java
 *
 * Created on 2 de noviembre de 2007, 10:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package enrut.grafo;

import java.util.Set;

/**
 *
 * @author ghuttemann
 */
public class GrafoEntity {
    private Long idgrafo;
    private String descrip;
    private String autor;
    private Set<Arista> aristas;
    
    /** Creates a new instance of GrafoEntity */
    public GrafoEntity() {
    }

    public Long getIdgrafo() {
        return idgrafo;
    }

    public void setIdgrafo(Long idgrafo) {
        this.idgrafo = idgrafo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Set<Arista> getAristas() {
        return aristas;
    }

    public void setAristas(Set<Arista> aristas) {
        this.aristas = aristas;
    }
}
