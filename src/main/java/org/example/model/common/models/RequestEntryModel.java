package org.example.model.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class RequestEntryModel {
    Double x;
    Double y;
    Float r;
    private boolean result;
    private long time;
    private Date date;

    public String getXYR(){
        return x + "," + y + "," + r;
    }
}
