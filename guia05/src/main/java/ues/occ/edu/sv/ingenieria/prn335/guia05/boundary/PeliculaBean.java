/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia05.boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Clasificacion;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Director;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Genero;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Pelicula;
import ues.occ.edu.sv.ingenieria.prn335.guia05.control.AbstractFacade;
import ues.occ.edu.sv.ingenieria.prn335.guia05.control.ClasificacionFacade;
import ues.occ.edu.sv.ingenieria.prn335.guia05.control.DirectorFacade;
import ues.occ.edu.sv.ingenieria.prn335.guia05.control.GeneroFacade;
import ues.occ.edu.sv.ingenieria.prn335.guia05.control.PeliculaFacade;

/**
 *
 * @author sandoval
 */
@Named(value = "peliculaBean")
@ViewScoped
public class PeliculaBean extends BackingBean<Pelicula> implements Serializable {

    /**
     * Creates a new instance of PeliculaBean
     */
    public PeliculaBean() {
    }
    @Inject
    PeliculaFacade facadePelicula;
    @Inject
    GeneroFacade facadeGenero;
    @Inject
    DirectorFacade facadeDirector;
    @Inject
    ClasificacionFacade facadeClasificacion;
    List<Genero> allGenero;
    List<Director> allDirector;
    List<Clasificacion> allClasificacion;
    @PostConstruct
    @Override
    public void iniciar() {
        this.allGenero = facadeGenero.findAll();
        this.allDirector = facadeDirector.findAll();
        this.allClasificacion = facadeClasificacion.findAll();
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyDataModel<Pelicula> Modelo() {
        return super.Modelo(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pelicula getRegistro() {
        if (registro == null) {
            registro = new Pelicula();
        }
        return super.getRegistro();
    }

    @Override
    public Object clavePorDatos(Pelicula object) {
        if (object != null) {
            return object.getIdPelicula();
        }
        return null;
    }

    @Override
    public Pelicula datosPorClave(String rowKey) {
        if (rowKey != null && !rowKey.isEmpty()) {
            try {
                Integer search = new Integer(rowKey);
                for (Pelicula tu : this.List) {
                    if (tu.getIdPelicula().compareTo(search) == 0) {
                        return tu;
                    }
                }
            } catch (NumberFormatException e) {

                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected AbstractFacade<Pelicula> getFacade() {
        return facadePelicula;
    }

    @Override
    public void btnEditarHandler(ActionEvent ae) {
        List<Genero> generoRegistro = facadePelicula.find(registro.getIdPelicula()).getGeneroList();
        if (registro != null) {
            System.out.println("Entro");
            if (generoRegistro.size() > registro.getGeneroList().size()) {
                for (int i = 0; i < generoRegistro.size(); i++) {
                    if (registro.getGeneroList().contains(generoRegistro.get(i)) == false) {
                        generoRegistro.get(i).getPeliculaList().remove(registro);
                        facadeGenero.edit(generoRegistro.get(i));
                        System.out.println("Borro Generos");
                    }

                }
            } else {
                for (int i = 0; i < registro.getGeneroList().size(); i++) {
                    if (generoRegistro.contains(registro.getGeneroList().get(i)) == false) {
                        registro.getGeneroList().get(i).getPeliculaList().add(registro);
                        facadeGenero.edit(registro.getGeneroList().get(i));
                        System.out.println("Agrego Generos");
                    }
                }
            }
        }
        getFacade().edit(registro);
        iniciar();
        this.estado = EstadoCrudBean.VISTA;
    }

    @Override
    public void btnAgregarHandler(ActionEvent ae) {
        estado = EstadoCrudBean.VISTA;
        if (registro != null || registro.getGeneroList() != null) {
            List<Genero> generoRegistro = registro.getGeneroList();
            registro.setGeneroList(Collections.EMPTY_LIST);
            facadePelicula.create(registro);
            registro.setGeneroList(generoRegistro);
            this.btnEditarHandler(ae);
        }
    }

    public List<Genero> generoFindByNombreLike(String nombre) {
        return facadeGenero.generoFindByNombreLike(nombre);
    }

    public String getRegistroGenero(List<Genero> list) {
        String registroGenero;
        if (list != null || list != Collections.EMPTY_LIST) {
            registroGenero = list.get(0).getNombre();
            for (int i = 1; i < list.size(); i++) {
                registroGenero = registroGenero + ", " + list.get(i).getNombre();

            }
            return registroGenero;
        } else {
            return registroGenero = "Sin especificar";
        }
    }

    public List<Genero> getAllGenero() {
        return allGenero;
    }

    public void setAllGenero(List<Genero> allGenero) {
        this.allGenero = allGenero;
    }

    public List<Director> getAllDirector() {
        return allDirector;
    }

    public void setAllDirector(List<Director> allDirector) {
        this.allDirector = allDirector;
    }

    public List<Clasificacion> getAllClasificacion() {
        return allClasificacion;
    }

    public void setAllClasificacion(List<Clasificacion> allClasificacion) {
        this.allClasificacion = allClasificacion;
    }

}
