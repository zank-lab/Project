package pl.edu.pwr.s241843.blutacz;



public class ListviewData {
    private String parametr;
    private String wartosc;

    public ListviewData(String parametr, String wartosc) {
        this.parametr = parametr;
        this.wartosc = wartosc;
    }

    public String getWartosc() {
        return wartosc;
    }

    public void setWartosc(String wartosc) {
        this.wartosc = wartosc;
    }

    public String getParametr() {
        return parametr;
    }

    public void setParametr(String parametr) {
        this.parametr = parametr;
    }
}
