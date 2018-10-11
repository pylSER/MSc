public class State {
    public State(int year, double currentMoney) {
        this.year = year;
        this.currentMoney = currentMoney;
    }

    private int year;
    private double currentMoney;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    @Override
    public String toString() {
        return "<"+year+", "+currentMoney+">";
    }
}
