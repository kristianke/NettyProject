import com.hcns.training.entity.Department;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void streamTest1(){
        String[] bullets = new String[]{"a1","b1","c1","b2","b5","b7","c7"};
        Stream<String> stream = Arrays.stream(bullets);
        long b = stream.filter((s) -> s.contains("b")).count();
        System.out.println(b);

        List<String> strings = Arrays.asList(bullets);
        Stream<String> stream1 = strings.stream();

        Stream<String> bullets1 = Stream.of(bullets);

        Stream<? extends Serializable> a1 = Stream.of("a1", "b1", 3, 5, 7L, true);
//        a1.forEach(System.out::println);

        Stream<Integer> iterate = Stream.iterate(1, (a) -> a*2);
        iterate.limit(20).forEach(System.out::println);
        Stream.generate(()->Math.random()).limit(10).forEach(System.out::println);

    }

    @Test
    public void StreamTest2(){
        Department d1 = new Department(1,"Researching","M1");
        Department d2 = new Department(2,"Marketing","M1");
        List<Department> deps = new ArrayList();
        deps.add(d1);
        deps.add(d2);

        deps.stream().map(Department::getTitle).forEach(System.out::println);
        Stream<Object> x1 = deps.stream().map((x) -> x.getGroup());
    }
}
