package wtf.scala.e02.lecture;

public class PrefixJava {

    public static void main(String[] args) {
        OperatorFormsExamples.Unary u = new OperatorFormsExamples.Unary(3);

        OperatorFormsExamples.Unary plus = u.unary_$plus();
        OperatorFormsExamples.Unary minus = u.unary_$minus();
        OperatorFormsExamples.Unary bang = u.unary_$bang();
        OperatorFormsExamples.Unary tilde = u.unary_$tilde();
    }
}
