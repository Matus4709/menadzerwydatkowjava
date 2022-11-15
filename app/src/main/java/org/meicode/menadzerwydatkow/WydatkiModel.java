package org.meicode.menadzerwydatkow;

public class WydatkiModel {

    private String Nazwa, Kategoria, Data;
    private long Kwota;

    private WydatkiModel() {}
    private WydatkiModel(String Nazwa,String Kategoria, String Data, long Kwota) {
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

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public long getKwota() {
        return Kwota;
    }

    public void setKwota(long kwota) {
        Kwota = kwota;
    }


}
