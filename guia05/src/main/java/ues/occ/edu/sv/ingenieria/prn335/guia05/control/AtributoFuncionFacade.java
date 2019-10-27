/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia05.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.AtributoFuncion;

/**
 *
 * @author sandoval
 */
@Stateless
public class AtributoFuncionFacade extends AbstractFacade<AtributoFuncion> {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtributoFuncionFacade() {
        super(AtributoFuncion.class);
    }
    
}
