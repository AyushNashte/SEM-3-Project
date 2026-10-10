package class7;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Integers extends Topic {

    // ---- Lesson concepts ----
    private static final Concept NUMBER_LINE   = new Concept("INT1", "Integers on the Number Line");
    private static final Concept ABSOLUTE      = new Concept("INT2", "Absolute Value and Opposites");
    private static final Concept ADDITION      = new Concept("INT3", "Addition of Integers");
    private static final Concept SUBTRACTION   = new Concept("INT4", "Subtraction of Integers");
    private static final Concept MULT_DIV      = new Concept("INT5", "Multiplication and Division of Integers");

    // ---- Prerequisite concepts (Class 6 knowledge) ----
    private static final Concept WHOLE_OPS     = new Concept("INT6", "Whole Number Operations");
    private static final Concept COMPARING     = new Concept("INT7", "Comparing and Ordering Numbers");
    private static final Concept LINE_BASICS   = new Concept("INT8", "Number Line Basics");
    private static final Concept OPPOSITES     = new Concept("INT9", "Opposite Situations (gain/loss)");

    public Integers() {
        super("Class 7", "Integers");
    }

    // ==========================================================
    // 1. PREREQUISITE TEST (2 questions per prerequisite concept)
    // ==========================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("What is 15 - 9?",
                        Arrays.asList("4", "6", "24", "5"), 1, WHOLE_OPS),
                new Question("What is 7 x 8?",
                        Arrays.asList("54", "56", "64", "48"), 1, WHOLE_OPS),

                new Question("Which number is greater: 45 or 54?",
                        Arrays.asList("45", "54", "They are equal", "Cannot say"), 1, COMPARING),
                new Question("Which list is in ascending (smallest to largest) order?",
                        Arrays.asList("21, 12, 8, 5", "5, 8, 12, 21", "8, 5, 12, 21", "5, 12, 8, 21"), 1, COMPARING),

                new Question("On a number line, you start at 3 and move 4 steps to the right. Where do you land?",
                        Arrays.asList("7", "-1", "1", "12"), 0, LINE_BASICS),
                new Question("On a number line, a point to the left of 5 is:",
                        Arrays.asList("Smaller than 5", "Greater than 5", "Equal to 5", "Always 0"), 0, LINE_BASICS),

                new Question("If +Rs 100 means you gained Rs 100, what does losing Rs 100 look like?",
                        Arrays.asList("+Rs 100", "-Rs 100", "Rs 0", "Rs 200"), 1, OPPOSITES),
                new Question("You walk 3 steps right and then 3 steps left. Where are you?",
                        Arrays.asList("Back at the start", "3 steps right of start", "6 steps right of start", "3 steps left of start"), 0, OPPOSITES)
        );
        return new Test(questions);
    }

    // ==========================================================
    // 2. LESSON TEST (3 questions per concept)
    // ==========================================================
    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    // Full bank kept separate so the retest can filter it by concept
    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                // ---- INT1: Number line ----
                new Question("Which of these integers is the smallest: -7, -2, 0, 3?",
                        Arrays.asList("-2", "-7", "0", "3"), 1, NUMBER_LINE),
                new Question("Which statement is correct?",
                        Arrays.asList("-3 > -1", "-5 < -2", "0 < -4", "-8 > -6"), 1, NUMBER_LINE),
                new Question("Arrange in ascending order: 2, -5, 0, -1",
                        Arrays.asList("-5, -1, 0, 2", "-1, -5, 0, 2", "2, 0, -1, -5", "0, -1, -5, 2"), 0, NUMBER_LINE),

                // ---- INT2: Absolute value and opposites ----
                new Question("What is the absolute value of -9?",
                        Arrays.asList("-9", "9", "0", "1/9"), 1, ABSOLUTE),
                new Question("What is the opposite (additive inverse) of 14?",
                        Arrays.asList("14", "1/14", "-14", "0"), 2, ABSOLUTE),
                // Trap: |-6| is +6, so the answer is 10, not -2
                new Question("Evaluate: |-6| + |4|",
                        Arrays.asList("10", "2", "-2", "-10"), 0, ABSOLUTE),

                // ---- INT3: Addition ----
                new Question("(-7) + (-5) = ?",
                        Arrays.asList("-2", "2", "-12", "12"), 2, ADDITION),
                // Trap: the sign of the answer follows the number with the bigger absolute value
                new Question("(-8) + 5 = ?",
                        Arrays.asList("13", "-13", "3", "-3"), 3, ADDITION),
                new Question("What is the sum of 12 and -12?",
                        Arrays.asList("24", "0", "-24", "1"), 1, ADDITION),

                // ---- INT4: Subtraction ----
                new Question("5 - 8 = ?",
                        Arrays.asList("3", "-3", "13", "-13"), 1, SUBTRACTION),
                new Question("4 - (-6) = ?",
                        Arrays.asList("-2", "2", "-10", "10"), 3, SUBTRACTION),
                new Question("(-3) - (-7) = ?",
                        Arrays.asList("-10", "-4", "4", "10"), 2, SUBTRACTION),

                // ---- INT5: Multiplication and division ----
                new Question("(-4) x (-5) = ?",
                        Arrays.asList("-20", "20", "-9", "9"), 1, MULT_DIV),
                new Question("(-24) / 6 = ?",
                        Arrays.asList("4", "-4", "18", "-18"), 1, MULT_DIV),
                // Trap: three negatives multiply to a negative answer
                new Question("(-2) x (-3) x (-4) = ?",
                        Arrays.asList("24", "-24", "9", "-9"), 1, MULT_DIV)
        );
    }

    // ==========================================================
    // 3. RETEST (only the weak concepts)
    // ==========================================================
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

    // ==========================================================
    // 4. LESSON CONTENT (shown in LessonScreen)
    // ==========================================================
    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        NUMBER_LINE,
                        "Integers are the numbers ... -3, -2, -1, 0, 1, 2, 3 ... They include positive numbers, "
                                + "negative numbers and zero. On a number line, zero sits in the middle, positives go to the "
                                + "right and negatives go to the left. The further RIGHT a number is, the GREATER it is. "
                                + "So every negative number is smaller than zero and smaller than every positive number.",
                        "Right = greater, Left = smaller",
                        Arrays.asList(
                                "-1 > -5, because -1 is to the right of -5",
                                "-3 < 2, because -3 is to the left of 2",
                                "Ascending order of 4, -2, 0, -7 is: -7, -2, 0, 4"
                        )
                ),
                new LessonContent(
                        ABSOLUTE,
                        "The absolute value of an integer is its distance from zero on the number line. Distance is "
                                + "never negative, so the absolute value is always positive or zero. It is written with bars: |x|. "
                                + "Two integers that are the same distance from zero but on opposite sides are called opposites. "
                                + "An integer plus its opposite always makes zero.",
                        "|-a| = a,   |a| = a,   a + (-a) = 0",
                        Arrays.asList(
                                "|-9| = 9 and |5| = 5",
                                "The opposite of -8 is 8, and (-8) + 8 = 0",
                                "|-6| + |4| = 6 + 4 = 10"
                        )
                ),
                new LessonContent(
                        ADDITION,
                        "Adding two integers with the SAME sign: add their absolute values and keep the common sign. "
                                + "Adding two integers with DIFFERENT signs: subtract the smaller absolute value from the larger "
                                + "one and keep the sign of the integer with the larger absolute value. "
                                + "Think of it as moving on the number line: adding a positive moves right, adding a negative moves left.",
                        "Same signs: add and keep sign.  Different signs: subtract and keep the sign of the bigger one.",
                        Arrays.asList(
                                "(-6) + (-3) = -(6 + 3) = -9",
                                "(-10) + 4: 10 - 4 = 6, and 10 is bigger and negative, so the answer is -6",
                                "8 + (-3): 8 - 3 = 5, and 8 is bigger and positive, so the answer is 5",
                                "(-7) + 7 = 0"
                        )
                ),
                new LessonContent(
                        SUBTRACTION,
                        "Subtracting an integer is the same as adding its opposite. Change the subtraction sign to "
                                + "addition and change the sign of the number being subtracted. Then use the rules of addition. "
                                + "That is why subtracting a negative number gives a bigger answer.",
                        "a - b = a + (-b)",
                        Arrays.asList(
                                "3 - 7 = 3 + (-7) = -4",
                                "5 - (-2) = 5 + 2 = 7",
                                "(-4) - (-9) = (-4) + 9 = 5",
                                "(-6) - 3 = (-6) + (-3) = -9"
                        )
                ),
                new LessonContent(
                        MULT_DIV,
                        "To multiply or divide integers, first multiply or divide the absolute values, then decide the sign. "
                                + "Same signs give a POSITIVE answer. Different signs give a NEGATIVE answer. "
                                + "With several negative numbers in a product: an EVEN number of negatives gives positive, "
                                + "an ODD number of negatives gives negative.",
                        "(+)(+) = +,  (-)(-) = +,  (+)(-) = -,  (-)(+) = -   (same rule for division)",
                        Arrays.asList(
                                "(-3) x (-4) = 12",
                                "(-5) x 4 = -20",
                                "(-18) / (-3) = 6",
                                "20 / (-5) = -4",
                                "(-2) x (-3) x (-4) = 6 x (-4) = -24 (three negatives, so the answer is negative)"
                        )
                )
        );
    }

    // ==========================================================
    // 5. REVIEW (console hook, same as other topics)
    // ==========================================================
    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}