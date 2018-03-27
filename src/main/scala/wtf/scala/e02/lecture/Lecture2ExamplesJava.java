package wtf.scala.e02.lecture;

public class Lecture2ExamplesJava {

    public void exceptions() {
        Integer x = null;
        try {
            x = new Integer("1");
        } catch (NumberFormatException e){
            e.printStackTrace();
        } finally {
            if (x != null) {
                System.out.println("x = " + x);
            } else {
                System.out.println("Unable to initialize x");
            }
        }
    }

    public void OOP() {
        OOPExamples.Foo f = new OOPExamples.Foo(1,2,3);

        f.c();
    }

}
