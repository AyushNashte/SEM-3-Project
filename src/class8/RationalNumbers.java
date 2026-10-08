package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RationalNumbers extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_RATIONAL = new Concept("RN1", "What is a Rational Number");
    private static final Concept STANDARD_FORM = new Concept("RN2", "Standard Form of a Rational Number");
    private static final Concept COMPARING = new Concept("RN3", "Comparing and Ordering Rational Numbers");
    private static final Concept ADD_SUBTRACT = new Concept("RN4", "Addition and Subtraction of Rational Numbers");
    private static final Concept MULTIPLY_DIVIDE = new Concept("RN5", "Multiplication and Division of Rational Numbers");

    // Prerequisite concepts
    private static final Concept FRACTION_BASICS = new Concept("RN6", "Fractions: Simplifying and Comparing");
    private static final Concept INTEGER_OPERATIONS = new Concept("RN7", "Operations on Integers");
    private static final Concept HCF_LCM = new Concept("RN8", "HCF and LCM");

    public RationalNumbers() {
        super("Class 8", "Rational Numbers");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "Simplify the fraction 12/18",
                        Arrays.asList("1/2", "2/3", "3/4", "4/6"),
                        1,
                        FRACTION_BASICS
                ),
                new Question(
                        "Which fraction is equal to 3/5?",
                        Arrays.asList("6/10", "6/15", "9/20", "5/3"),
                        0,
                        FRACTION_BASICS
                ),
                new Question(
                        "Which is greater: 3/4 or 2/3?",
                        Arrays.asList("3/4", "2/3", "They are equal", "Cannot be compared"),
                        0,
                        FRACTION_BASICS
                ),
                new Question(
                        "What is (-7) + 10?",
                        Arrays.asList("-17", "-3", "3", "17"),
                        2,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-6) x (-4)?",
                        Arrays.asList("-24", "-10", "10", "24"),
                        3,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-12) ÷ 3?",
                        Arrays.asList("4", "-4", "-9", "36"),
                        1,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is the HCF of 12 and 18?",
                        Arrays.asList("2", "3", "6", "36"),
                        2,
                        HCF_LCM
                ),
                new Question(
                        "What is the LCM of 4 and 6?",
                        Arrays.asList("10", "12", "24", "2"),
                        1,
                        HCF_LCM
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    // Full bank kept separate so the retest can filter it by concept
    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "A rational number is a number that can be written as p/q where:",
                        Arrays.asList("p and q are integers and q ≠ 0", "p and q are integers and p ≠ 0", "p and q are any real numbers", "q is positive and p is negative"),
                        0,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "Which of these is NOT a rational number?",
                        Arrays.asList("3/7", "-5", "0", "5/0"),
                        3,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "Is every integer a rational number?",
                        Arrays.asList("Yes, because n = n/1", "No, only fractions are rational", "Only positive integers are", "Only zero is"),
                        0,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "What is the standard form of 6/-8?",
                        Arrays.asList("6/-8", "-3/4", "3/-4", "-6/8"),
                        1,
                        STANDARD_FORM
                ),
                new Question(
                        "A rational number is in standard form when its denominator is positive and:",
                        Arrays.asList("the numerator and denominator have no common factor other than 1", "the numerator is also positive", "the numerator is larger than the denominator", "the denominator is 1"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Write 15/-35 in standard form.",
                        Arrays.asList("-3/7", "3/7", "-5/7", "-1/7"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Which is greater: -1/2 or -1/3?",
                        Arrays.asList("-1/2", "-1/3", "They are equal", "Cannot be compared"),
                        1,
                        COMPARING
                ),
                new Question(
                        "Arrange in ascending order: 1/2, -3/4, 0",
                        Arrays.asList("-3/4, 0, 1/2", "0, 1/2, -3/4", "1/2, 0, -3/4", "-3/4, 1/2, 0"),
                        0,
                        COMPARING
                ),
                new Question(
                        "On the number line, a negative rational number lies:",
                        Arrays.asList("to the left of zero", "to the right of zero", "at zero", "above zero"),
                        0,
                        COMPARING
                ),
                new Question(
                        "Find 1/3 + 1/6.",
                        Arrays.asList("2/9", "1/2", "1/3", "2/3"),
                        1,
                        ADD_SUBTRACT
                ),
                new Question(
                        "Find 3/4 - 5/6.",
                        Arrays.asList("-1/12", "1/12", "-2/10", "1/2"),
                        0,
                        ADD_SUBTRACT
                ),
                new Question(
                        "What is the additive inverse of -5/7?",
                        Arrays.asList("5/7", "-7/5", "7/5", "0"),
                        0,
                        ADD_SUBTRACT
                ),
                new Question(
                        "Find (2/3) x (-9/4).",
                        Arrays.asList("-3/2", "3/2", "-11/12", "-2/3"),
                        0,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "What is the reciprocal (multiplicative inverse) of -3/8?",
                        Arrays.asList("-8/3", "8/3", "3/8", "-3/8"),
                        0,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "Find (3/5) ÷ (9/10).",
                        Arrays.asList("2/3", "27/50", "3/2", "1/3"),
                        0,
                        MULTIPLY_DIVIDE
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return new Test(getLessonQuestionBank());
        }
        return new Test(filtered);
    }

    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        WHAT_IS_RATIONAL,
                        "A rational number is any number that can be written as a fraction p/q, where p and q "
                                + "are integers and q is not zero. Every integer and every fraction is a rational number.",
                        "Rational number = p/q, where q ≠ 0",
                        Arrays.asList(
                                "5 = 5/1, 0 = 0/1 and -3/4 are all rational numbers",
                                "5/0 is not defined, so it is not a rational number"
                        )
                ),
                new LessonContent(
                        STANDARD_FORM,
                        "A rational number is in standard form when its denominator is positive and its numerator "
                                + "and denominator have no common factor except 1. To get there, move any negative sign "
                                + "to the numerator, then divide both parts by their HCF.",
                        "Standard form: denominator > 0 and HCF(p, q) = 1",
                        Arrays.asList(
                                "6/-8 -> -6/8 -> divide by 2 -> -3/4",
                                "15/-35 -> -15/35 -> divide by 5 -> -3/7"
                        )
                ),
                new LessonContent(
                        COMPARING,
                        "To compare rational numbers, give them the same positive denominator (use the LCM) and "
                                + "compare the numerators. On the number line the greater number lies to the right, and "
                                + "negative numbers lie to the left of zero.",
                        "a/b < c/d  when  a x d < c x b  (for b, d > 0)",
                        Arrays.asList(
                                "Compare -1/2 and -1/3: LCM = 6, so -3/6 and -2/6. Since -2 > -3, -1/3 > -1/2"
                        )
                ),
                new LessonContent(
                        ADD_SUBTRACT,
                        "To add or subtract rational numbers, first write them with a common denominator, then "
                                + "add or subtract the numerators. The additive inverse of p/q is -p/q, because "
                                + "their sum is zero.",
                        "a/b + c/d = (ad + bc) / bd",
                        Arrays.asList(
                                "1/3 + 1/6 = 2/6 + 1/6 = 3/6 = 1/2",
                                "3/4 - 5/6 = 9/12 - 10/12 = -1/12"
                        )
                ),
                new LessonContent(
                        MULTIPLY_DIVIDE,
                        "To multiply rational numbers, multiply the numerators and multiply the denominators. "
                                + "To divide, multiply by the reciprocal (multiplicative inverse) of the divisor.",
                        "a/b x c/d = ac/bd     a/b ÷ c/d = a/b x d/c",
                        Arrays.asList(
                                "(2/3) x (-9/4) = -18/12 = -3/2",
                                "(3/5) ÷ (9/10) = (3/5) x (10/9) = 30/45 = 2/3",
                                "The reciprocal of -3/8 is -8/3"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}