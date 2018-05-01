package pl.pafc.dzwikizwierzt;

public class Cat {

    private int     catNumbers = 0;
    private boolean catLocked  = true;

    Cow cow = new Cow();


    public int getCatNumbers() {
        return catNumbers;
    }

    public void setCatNumbers(int catNumbers) {
        this.catNumbers = catNumbers;
    }

    public boolean isCatlocked() {
        return catLocked;
    }

    public void setCatLocked(boolean catLocked) {
        this.catLocked = catLocked;
    }

}
