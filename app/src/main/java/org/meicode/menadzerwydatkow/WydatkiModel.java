package org.meicode.menadzerwydatkow;

import java.util.Date;

public class WydatkiModel {

    private String Nazwa, Kategoria, Kwota;
    private Date Data;
    //private long Kwota;

    private WydatkiModel() {}
    private WydatkiModel(String Nazwa, String Kategoria, Date Data, String Kwota) {
        this.Nazwa = Nazwa;
        this.Kategoria = Kategoria;
        this.Data = Data;
        this.Kwota = Kwota;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public String getKategoria() {
        return Kategoria;
    }

    public void setKategoria(String kategoria) {
        Kategoria = kategoria;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public String getKwota() {
        return Kwota +" PLN";
    }

    public void setKwota(String kwota) {
        Kwota = kwota;
    }


}
