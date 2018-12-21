import action.interfaceActionMatch.ChangeSizeActionFulsh;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static java.security.AccessController.doPrivileged;

abstract class Person {
    public String Name ;
    public int Age ;
    abstract void aaa(String aa);
    abstract public void bbb(String aa);
}
public class Lamabe
{

    public static List<Person> PersonsList()
    {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 7; i++)
        {
        }
        return persons;
    }
    @Test
    public void reduceList() {

        List<Student> list = getStudents();

        //使用Reduce 将所有的所有的成绩进行加和
        Optional<Score> totalScore = list.stream()
                .map(Student::getScore)
                .reduce((x, y) -> x.add(y));

        System.out.println(totalScore.get().getPoint());
    }
    @Test
    public void testIterate() {

        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        nums.stream().
                map(n -> n * n).
                collect(Collectors.toList()).forEach(System.out::println);
    }

    private int reduceList(int i) {
        return i;
    }

    private List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student stu = new Student();

            Score score = new Score();
            score.setPoint(80);
            score.setCourseName("English");

            stu.setId(i);
            stu.setScore(score);

            list.add(stu);
        }
        return list;
    }
    public static void main(String[] args)
    {


        FileFilter java11 = (f) -> {
            return f.getName().endsWith("*.java");
        };

        FileFilter java = (File f) -> f.getName().endsWith("*.java");

        String user = doPrivileged((PrivilegedAction<String>) () -> System.getProperty("user.name"));

        ChangeSizeActionFulsh changeSizeActionFulsh = () -> {
            System.out.println("ssss");
        };
        changeSizeActionFulsh.flushComponent();
        new Thread(() -> {
            System.err.println("sdasd");
        }).start();
    }
}
//学生
class Student {

    private Integer id;

    //课程分数
    private Score score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

//课程分数
class Score {

    //分数
    private Integer point;

    //课程名称
    private String courseName;

    public Integer getPoint() {
        return point;
    }

    public Score add(Score other) {

        this.point += other.getPoint();

        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
