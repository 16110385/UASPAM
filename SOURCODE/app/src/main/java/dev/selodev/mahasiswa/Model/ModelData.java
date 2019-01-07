package dev.selodev.mahasiswa.Model;

public class ModelData {
    String npm, nama, prodi, fakultas;

    public ModelData(){}

    public ModelData(String npm, String nama, String prodi, String fakultas) {
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
        this.fakultas = fakultas;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }
}
