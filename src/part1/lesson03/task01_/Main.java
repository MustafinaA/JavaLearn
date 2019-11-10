package part1.lesson03.task01_;

public class Main {
    public static void main(String[] args) {
        MathBox<Integer> inums = new MathBox<>(new Integer[]{1, 2, 3});

        System.out.println(inums.getClass());
        Integer iSum = inums.summator(new Arithmetic<>() {
            @Override
            public Integer zero() {
                return 0;
            }

            @Override
            public Integer add(Integer a, Integer b) {
                return (a + b);
            }
        });
        inums.splitter(2);
        System.out.println(inums.toString());

        MathBox<Double> dnums = new MathBox<>(new Double[]{1.0, 2.2, 3.0});
        Double dSum = dnums.summator(new Arithmetic<Double>() {
            @Override
            public Double zero() {
                return 0.0;
            }

            @Override
            public Double add(Double a, Double b) {
                return a+b;
            }
        });
        System.out.println(dSum);
        System.out.println(iSum/0.1);

    }

}
