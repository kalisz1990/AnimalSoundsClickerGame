package pl.pafc.dzwikizwierzt;

public class Bird {

    private int     birdNumbers = 0;
    private boolean birdUnlocked = false;

    public boolean isBirdUnlocked() {
        return birdUnlocked;
    }

    public void setBirdUnlocked(boolean birdUnlocked) {
        this.birdUnlocked = birdUnlocked;
    }

    public int getBirdNumbers() {
        return birdNumbers;
    }

    public void setBirdNumbers(int birdNumbers) {
        this.birdNumbers = birdNumbers;
    }
}
