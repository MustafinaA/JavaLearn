package part1.lesson11.task01;

public class NumberSqrtNumber<T, T1> {
    private Long num;
    private Double sqrt;

    public NumberSqrtNumber(Long num, Double sqrt) {
        this.num = num;
        this.sqrt = sqrt;
    }

    public Double getSqrt() {
        return sqrt;
    }

    public Long getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "NumberSqrtNumber{" +
                "num=" + num +
                ", sqrt=" + sqrt +
                '}';
    }
}
