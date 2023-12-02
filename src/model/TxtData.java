package model;

import java.io.File;
import java.io.Serializable;

public class TxtData implements Serializable {
    public static final long serialVersionUID = 1L;
    private File data;

    public TxtData(File data){
        this.data = data;
    }
    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
    }

}
