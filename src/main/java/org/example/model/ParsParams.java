package org.example.model;

import lombok.Getter;
import org.example.model.common.customException.ValidException;

import java.util.HashMap;

@Getter
public class ParsParams {
    private Double x = null;
    private Double y = null;
    private Float r = null;

    public ParsParams(HashMap<String, String> map) throws ValidException {
        Pars(map);
        validParams();
    }

    private void Pars(HashMap<String, String> map){

        for (String key : map.keySet()) {
            switch (key) {
                case "x" -> x = Double.parseDouble(map.get(key));
                case "y" -> y = Double.parseDouble(map.get(key));
                case "r" -> r = Float.parseFloat(map.get(key));
            }
        }
    }

    public void validParams() throws ValidException{
        if (x == null) throw new ValidException("x is empty");
        if (y == null) throw new ValidException("y is empty");
        if (r == null) throw new ValidException("r is empty");
        if (-4.0 > x || x > 4.0) throw new ValidException("x must be between -4 and 4");
        if (-3.0 > y || y > 3.0) throw new ValidException("y must be between -3 and 3");
        if (1 > r || r > 5) throw new ValidException("r must be between 1 and 5");
    }

}
