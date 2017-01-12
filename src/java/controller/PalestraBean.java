/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PalestranteDao;
import dao.PalestraDao;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import model.Palestra;
import model.Palestrante;

/**
 *
 * @author Ricardo
 */
@ManagedBean
@RequestScoped
public class PalestraBean {
    private Palestra palestra;
    private List<SelectItem> palestrantes;
    private List<Palestra> palestras;
    
    public String cadastrarPalestra() {
        PalestraDao.getInstance().create(palestra);
        this.palestras = PalestraDao.getInstance().findPalestraEntities();
        return "index";
    }

    public String getAllPalestrantes() {
        this.palestrantes = this.getPalestrantesList();
        return "index";
    }

    public PalestraBean() {
        this.palestra = new Palestra();
    }



    public List<SelectItem> getPalestrantesList() {
        if (this.palestrantes == null) {
            this.palestrantes = new ArrayList<>();

            List<Palestrante> palestres  = PalestranteDao.getInstance().findPalestranteEntities();
            SelectItem item;

            for (Palestrante palestrante : palestres) {
                item = new SelectItem(palestrante, palestrante.getNome());
                this.palestrantes.add(item);
            }
        }
        return palestrantes;
    }

    public Palestra getPalestra() {
        return palestra;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public List<SelectItem> getPalestrantes() {
        return palestrantes;
    }

    public void setPalestrantes(List<SelectItem> palestrantes) {
        this.palestrantes = palestrantes;
    }

    public List<Palestra> getPalestras() {
        return palestras;
    }

    public void setPalestras(List<Palestra> palestras) {
        this.palestras = palestras;
    }



}
