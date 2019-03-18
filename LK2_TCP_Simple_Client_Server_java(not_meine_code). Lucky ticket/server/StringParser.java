
public class StringParser {
    private String ticketNum;
    private int sumOfTheFirstNums;
    private int sumOfTheSecondNums;

    public StringParser(String ticketNum){
        this.ticketNum = ticketNum;
        calcSumOfTheFirstNums();
        calcSumOfTheSecondNums();
    }


    private void calcSumOfTheFirstNums(){
        for (int i = 0; i < 3; i++) {
            sumOfTheFirstNums += ticketNum.charAt(i) - '0';
        }
    }
    private void calcSumOfTheSecondNums(){
        for (int i = 3; i < 6; i++) {
            sumOfTheSecondNums += ticketNum.charAt(i) - '0';
        }
    }

    public boolean isSumEquals(){
        return sumOfTheFirstNums == sumOfTheSecondNums;
    }

    public int getSumOfTheFirstNums() {
        return sumOfTheFirstNums;
    }

    public int getSumOfTheSecondNums() {
        return sumOfTheSecondNums;
    }
}
