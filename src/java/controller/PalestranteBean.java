/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PalestranteDao;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.Palestrante;

/**
 *
 * @author Ricardo
 */
@ManagedBean
@RequestScoped
public class PalestranteBean {

    @PostConstruct
    public void init() {
        palestrante = new Palestrante();
    }

    private Palestrante palestrante;

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }

    public String salvar() throws Exception {
        PalestranteDao.getInstance().create(palestrante);
        return "index";
    }

}