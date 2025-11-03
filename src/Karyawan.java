public class Karyawan {

    private int id;
    private String nama;
    private String jabatan;
    private double gaji;

    public Karyawan(int id, String nama, String jabatan, double gaji) {
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.gaji = gaji;
    }

    public int getId() {return id;}
    public String getNama() {return nama;}
    public String getJabatan() {return jabatan;}
    public double getGaji() {return gaji;}

    public void setNama(String nama) {this.nama = nama;}
    public void setJabatan(String jabatan) {this.jabatan = jabatan;}
    public void setGaji(double gaji) {this.gaji = gaji;}

}
