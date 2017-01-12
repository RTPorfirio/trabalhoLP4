/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Palestrante;
import dao.PalestranteDao;

@FacesConverter(forClass = Palestrante.class)
public class PalestranteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.equals("")) {
            Integer id = Integer.valueOf(value);
            return PalestranteDao.getInstance().findPalestrante(id);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Palestrante palestrante = (Palestrante) value;
            return palestrante.getId().toString();
        }
        return null;
    }

}
