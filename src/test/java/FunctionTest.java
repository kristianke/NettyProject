import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionTest {

    final int a = 3;
    FunctionTest(){
        super();
    }
    {int b = a+2;}
    public static void main(String[] args) {
        FunctionTest functionTest = new FunctionTest();
        System.out.println(functionTest.a);
    }

    @Test
    public void functionQuote(){
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "YOU";
            }
        };
        Supplier<String> supplier1 = ()->{return "ARE";};
        Supplier<String> supplier2 = ()->"MY";

        Supplier<String> supplier3 = supplier::get;

        System.out.println(supplier.get() + supplier1.get() + supplier2.get());
    }

    @Test
    public void functionQuote1(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        Comparator<Integer> comparator1 = (x,y)->Integer.compare(x,y);
        Comparator<Integer> comparator2 = Integer::compareTo;
        System.out.println(comparator.compare(1,2));
        System.out.println(comparator1.compare(2,1));
        System.out.println(comparator2.compare(3,3));
    }

    @Test
    public void functionQuote2(){
        BiPredicate<String,String> biPredicate1 = (x,y) -> x.equals(y);
        boolean test = biPredicate1.test("x", "y");
        System.out.println(test);
        BiPredicate<String,String> biPredicate = String::equals;
        boolean test1 = biPredicate.test("you", "you");
        System.out.println(test1);
    }

    @Test
    public void ConstructorQuote(){
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return new String();
            }
        };
        Supplier<String> supplier1 = String::new;
        String s = supplier.get();
        String s1 = supplier1.get();
        System.out.println(s+s1);

        Function<Integer,Integer> function = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer*integer;
            }
        };
        Function<String,Integer> function1 = Integer::new;
        System.out.println(function.apply(2));
        System.out.println(function1.apply("3"));

    }
}
