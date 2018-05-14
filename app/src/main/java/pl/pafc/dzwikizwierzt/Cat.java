package pl.pafc.dzwikizwierzt;

public class Cat {

    private int     catNumbers = 0;
    private boolean catLocked  = true;
    private boolean catInHatUnlocked = false;

    Cow cow = new Cow();

    public boolean isCatInHatUnlocked() {
        return catInHatUnlocked;
    }

    public void setCatInHatUnlocked(boolean catInHatUnlocked) {
        this.catInHatUnlocked = catInHatUnlocked;
    }


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
