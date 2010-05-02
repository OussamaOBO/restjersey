package model.dao;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.hibernate.criterion.*;

/**
 *
 * @author João Sávio Ceregatti Longo - joaosavio@gmail.com
 */


public class CriterionConfiguration {
    private Map mapAllEq;

    public List<Criterion> getListCriterion() {
        Criterion c;
        List<Criterion> listCriterion = new ArrayList<Criterion>();
        if (mapAllEq != null) {
            c = Restrictions.allEq(mapAllEq);
            listCriterion.add(c);
        }

        return listCriterion;
    }

    

    public Map getMapAllEq() {
        return mapAllEq;
    }

    public void setMapAllEq(Map mapAllEq) {
        this.mapAllEq = mapAllEq;
    }

    



}
