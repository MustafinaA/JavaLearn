package part1.lesson03.task01;

public class Main {
    public static void main(String[] args) {
        MathBox nums = new MathBox(new Number[]{0, 4.56, Math.PI, -1, 10000});
        MathBox otherNums = new MathBox(new Number[]{0, 4.56, Math.PI, -1, 10000});
        System.out.println("summator return     " + nums.summator());
        System.out.println("Is                  " + nums + " equals " + otherNums + "? : " + nums.equals(otherNums));
        System.out.println("HashCodes:          " + nums.hashCode() + " - " + otherNums.hashCode());
        nums.splitter(2);
        System.out.println("splitter return     " + nums);
        System.out.println("Is                  " + nums.toString() + " equals " + otherNums.toString() + "? : " + nums.equals(otherNums));
        System.out.println("HashCodes:          " + nums.hashCode() + " - " + otherNums.hashCode());
        otherNums.delElem(10000);
        System.out.println("after delete return " + otherNums);
    }

}
