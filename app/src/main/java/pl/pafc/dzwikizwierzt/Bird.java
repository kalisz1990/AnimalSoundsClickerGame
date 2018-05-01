package pl.pafc.dzwikizwierzt;

public class Bird {

    private int     birdNumbers = 0;
    private boolean birdLocked = true;

    public boolean isBirdLocked() {
        return birdLocked;
    }

    public void setBirdLocked(boolean birdLocked) {
        this.birdLocked = birdLocked;
    }

    public int getBirdNumbers() {
        return birdNumbers;
    }

    public void setBirdNumbers(int birdNumbers) {
        this.birdNumbers = birdNumbers;
    }
}
