package class7;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class 7 - Integers
 * Author: Class 7 content owner
 *
 * Concepts are taught through everyday situations (temperature, money,
 * elevators, sea level) so integers feel like something students already
 * use, not just numbers with a minus sign.
 */
public class Integers extends Topic {

    private static final Concept NUMBER_LINE = new Concept("INT1", "Integers on a Number Line");
    private static final Concept ADD_SUB = new Concept("INT2", "Addition & Subtraction of Integers");
    private static final Concept MULT_DIV = new Concept("INT3", "Multiplication & Division of Integers");
    private static final Concept PROPERTIES = new Concept("INT4", "Properties of Integers");
    private static final Concept WORD_PROBLEMS = new Concept("INT5", "Integers in Real Life");

    public Integers() {
        super("Class 7", "Integers");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is 8 + 5?",
                        Arrays.asList("12", "13", "14", "15"),
                        1,
                        ADD_SUB
                ),
                new Question(
                        "What is 15 - 9?",
                        Arrays.asList("4", "5", "6", "7"),
                        2,
                        ADD_SUB
                ),
                new Question(
                        "On a number line, which number is to the right of 3?",
                        Arrays.asList("1", "2", "3", "5"),
                        3,
                        NUMBER_LINE
                ),
                new Question(
                        "What is 6 x 7?",
                        Arrays.asList("36", "42", "48", "49"),
                        1,
                        MULT_DIV
                ),
                new Question(
                        "What is 20 divided by 4?",
                        Arrays.asList("4", "5", "6", "8"),
                        1,
                        MULT_DIV
                ),
                new Question(
                        "Which is the smallest whole number?",
                        Arrays.asList("1", "-1", "0", "10"),
                        2,
                        NUMBER_LINE
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "The temperature in Shimla is -3°C and in Mumbai is 28°C. On a number line, which city's temperature is further to the left?",
                        Arrays.asList("Mumbai", "Shimla", "Both are equal", "Cannot say"),
                        1,
                        NUMBER_LINE
                ),
                new Question(
                        "Which of these integers is the greatest: -8, -2, 0, -15?",
                        Arrays.asList("-8", "-2", "0", "-15"),
                        2,
                        NUMBER_LINE
                ),
                new Question(
                        "A submarine is at -120 m (120 m below sea level) and rises 45 m. What is its new position?",
                        Arrays.asList("-165 m", "-75 m", "75 m", "165 m"),
                        1,
                        ADD_SUB
                ),
                new Question(
                        "Riya's bank balance is ₹500. She withdraws ₹700. What is her new balance (as an integer)?",
                        Arrays.asList("₹200", "-₹200", "₹1200", "-₹1200"),
                        1,
                        ADD_SUB
                ),
                new Question(
                        "What is (-7) + (-5)?",
                        Arrays.asList("-12", "-2", "2", "12"),
                        0,
                        ADD_SUB
                ),
                new Question(
                        "What is (-9) - (-4)?",
                        Arrays.asList("-13", "-5", "5", "13"),
                        1,
                        ADD_SUB
                ),
                new Question(
                        "A cricket team's run rate change is (-2) per over for 6 overs. What is the total change?",
                        Arrays.asList("-12", "-8", "8", "12"),
                        0,
                        MULT_DIV
                ),
                new Question(
                        "What is (-6) x (-3)?",
                        Arrays.asList("-18", "-9", "9", "18"),
                        3,
                        MULT_DIV
                ),
                new Question(
                        "What is (-24) ÷ 6?",
                        Arrays.asList("-4", "4", "-18", "18"),
                        0,
                        MULT_DIV
                ),
                new Question(
                        "Which property does (-4) + 7 = 7 + (-4) show?",
                        Arrays.asList("Associative Property", "Commutative Property", "Closure Property", "Identity Property"),
                        1,
                        PROPERTIES
                ),
                new Question(
                        "Adding any two integers always gives an integer. Which property is this?",
                        Arrays.asList("Closure Property", "Commutative Property", "Distributive Property", "None"),
                        0,
                        PROPERTIES
                ),
                new Question(
                        "An elevator starts at the 3rd floor, goes down 5 floors, then up 2 floors. Which floor is it on now?",
                        Arrays.asList("-2", "Ground floor (0)", "2", "5"),
                        1,
                        WORD_PROBLEMS
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
                        NUMBER_LINE,
                        "Integers are whole numbers that can be positive, negative, or zero. On a number "
                                + "line, numbers increase as you move right and decrease as you move left. "
                                + "Negative numbers are always smaller than positive numbers and zero.",
                        null,
                        Arrays.asList(
                                "-5°C is colder than 2°C because -5 is to the left of 2 on the number line",
                                "-10 < -3 < 0 < 4"
                        )
                ),
                new LessonContent(
                        ADD_SUB,
                        "To add integers with the same sign, add their values and keep the sign. To add "
                                + "integers with different signs, subtract the smaller value from the bigger "
                                + "one and keep the sign of the bigger value. Subtracting an integer is the "
                                + "same as adding its opposite.",
                        "a - b = a + (-b)",
                        Arrays.asList(
                                "(-8) + (-3) = -11 (same sign, add and keep the sign)",
                                "(-8) + 3 = -5 (different signs, subtract and keep sign of bigger value)",
                                "5 - (-2) = 5 + 2 = 7"
                        )
                ),
                new LessonContent(
                        MULT_DIV,
                        "When multiplying or dividing two integers: same signs give a positive answer, "
                                + "different signs give a negative answer.",
                        "(+ x +)=+ , (- x -)=+ , (+ x -)=- , (- x -) for division follows the same rule",
                        Arrays.asList(
                                "(-4) x (-5) = 20 (same signs -> positive)",
                                "(-4) x 5 = -20 (different signs -> negative)",
                                "(-30) ÷ (-6) = 5"
                        )
                ),
                new LessonContent(
                        PROPERTIES,
                        "Integers follow useful properties: Closure (adding/multiplying two integers always "
                                + "gives an integer), Commutative (order doesn't matter for + and x), and "
                                + "Associative (grouping doesn't matter for + and x). Subtraction and division "
                                + "are NOT commutative.",
                        "a + b = b + a  |  (a+b)+c = a+(b+c)",
                        Arrays.asList(
                                "3 + (-7) = -4 and (-7) + 3 = -4, so addition is commutative",
                                "5 - 2 = 3 but 2 - 5 = -3, so subtraction is NOT commutative"
                        )
                ),
                new LessonContent(
                        WORD_PROBLEMS,
                        "Integers show up everywhere: temperature above/below zero, money earned/spent, "
                                + "floors above/below ground, and heights above/below sea level. The trick is "
                                + "to decide what counts as positive and what counts as negative first, then "
                                + "translate the situation into an integer sum.",
                        null,
                        Arrays.asList(
                                "Spending ₹250 from ₹1000 pocket money: 1000 + (-250) = 750",
                                "A kite at height 40 m loses 15 m of height: 40 + (-15) = 25 m",
                                "A lift goes down 4 floors from floor 6: 6 + (-4) = floor 2"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts in Integers:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}