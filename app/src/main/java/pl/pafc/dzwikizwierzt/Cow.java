package pl.pafc.dzwikizwierzt;

public class Cow {

    private int     cowNumbers = 0;
    private boolean cowInGlassesUnlkocked = false;

    public boolean isCowInGlassesUnlkocked() {
        return cowInGlassesUnlkocked;
    }

    public void setCowInGlassesUlkocked(boolean cowInGlassesUnlkocked) {
        this.cowInGlassesUnlkocked = cowInGlassesUnlkocked;
    }

    public void setCowNumbers(int cowNumbers) {
        this.cowNumbers = cowNumbers;
    }

    public int getCowNumbers() {
        return cowNumbers;
    }




}
