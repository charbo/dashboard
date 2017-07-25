package org.dashboard.server;


import java.io.Serializable;
import java.util.List;

public class DataSet implements Serializable{
    private String label;
    private List<Data> datas;

    public DataSet() {
    }

    public DataSet(String label, List<Data> datas) {
        this.label = label;
        this.datas = datas;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSet dataSet = (DataSet) o;

        return getLabel().equals(dataSet.getLabel());
    }

    @Override
    public int hashCode() {
        return getLabel().hashCode();
    }
}
