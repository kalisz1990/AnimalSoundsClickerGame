package pl.pafc.dzwikizwierzt;

public class Cow {

    private int     cowNumbers = 0 ;
    private boolean cowInGlassesUnlocked = false;
    private boolean cowPurpleUnlocked = false;

    public boolean isCowInGlassesUnlocked() {
        return cowInGlassesUnlocked;
    }

    public void setCowInGlassesUlkocked(boolean cowInGlassesUnlkocked) {
        this.cowInGlassesUnlocked = cowInGlassesUnlkocked;
    }

    public boolean isCowPurpleUnlocked() {
        return cowPurpleUnlocked;
    }

    public void setCowPurpleUnlocked(boolean cowPurpleUnlocked) {
        this.cowPurpleUnlocked = cowPurpleUnlocked;
    }

    public void setCowNumbers(int cowNumbers) {
        this.cowNumbers = cowNumbers;
    }

    public int getCowNumbers() {
        return cowNumbers;
    }




}
