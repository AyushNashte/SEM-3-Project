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

    @Override
    public java.util.Optional<String> getGameId() {
        return java.util.Optional.of("integer-hopper");
    }

    public static class FractionsAndDecimals extends Topic {

        // ---- Lesson concepts ----
        private static final Concept EQUIVALENT   = new Concept("FD1", "Equivalent and Comparing Fractions");
        private static final Concept ADD_SUB      = new Concept("FD2", "Adding and Subtracting Fractions");
        private static final Concept MULT_DIV     = new Concept("FD3", "Multiplying and Dividing Fractions");
        private static final Concept DEC_PLACE    = new Concept("FD4", "Decimal Place Value and Conversion");
        private static final Concept DEC_OPS      = new Concept("FD5", "Operations on Decimals");

        // ---- Prerequisite concepts (Class 6 knowledge) ----
        private static final Concept FRACTION_MEANING = new Concept("FD6", "Meaning of a Fraction");
        private static final Concept FACTORS          = new Concept("FD7", "Factors and Multiples");
        private static final Concept PLACE_VALUE      = new Concept("FD8", "Place Value in Decimals");
        private static final Concept WHOLE_OPS        = new Concept("FD9", "Whole Number Operations");

        public FractionsAndDecimals() {
            super("Class 7", "Fractions & Decimals");
        }

        // ==========================================================
        // 1. PREREQUISITE TEST
        // ==========================================================
        @Override
        protected Test getPrerequisiteTest() {
            List<Question> questions = Arrays.asList(
                    new Question("A pizza is cut into 8 equal slices and you eat 3. What fraction did you eat?",
                            Arrays.asList("8/3", "3/8", "5/8", "3/5"), 1, FRACTION_MEANING),
                    new Question("In the fraction 5/9, the denominator is:",
                            Arrays.asList("9", "5", "14", "4"), 0, FRACTION_MEANING),

                    new Question("Which number is a factor of 12?",
                            Arrays.asList("5", "7", "4", "9"), 2, FACTORS),
                    new Question("Which of these is a multiple of 6?",
                            Arrays.asList("16", "20", "14", "18"), 3, FACTORS),

                    new Question("In 3.47, what is the place value of the digit 4?",
                            Arrays.asList("4 ones", "4 hundredths", "4 tenths", "4 tens"), 2, PLACE_VALUE),
                    new Question("Which is greater: 0.5 or 0.05?",
                            Arrays.asList("0.5", "0.05", "They are equal", "Cannot say"), 0, PLACE_VALUE),

                    new Question("What is 24 / 6?",
                            Arrays.asList("4", "3", "5", "6"), 0, WHOLE_OPS),
                    new Question("What is 12 x 5?",
                            Arrays.asList("50", "65", "72", "60"), 3, WHOLE_OPS)
            );
            return new Test(questions);
        }

        // ==========================================================
        // 2. LESSON TEST (3 per concept)
        // ==========================================================
        @Override
        protected Test getLessonTest() {
            return new Test(getLessonQuestionBank());
        }

        private List<Question> getLessonQuestionBank() {
            return Arrays.asList(
                    // ---- FD1: Equivalent and comparing ----
                    new Question("Which fraction is equivalent to 2/3?",
                            Arrays.asList("3/4", "4/6", "2/6", "4/9"), 1, EQUIVALENT),
                    new Question("Which is greater: 3/4 or 2/3?",
                            Arrays.asList("2/3", "3/4", "They are equal", "Cannot say"), 1, EQUIVALENT),
                    new Question("Arrange in ascending order: 1/2, 3/5, 2/5",
                            Arrays.asList("1/2, 2/5, 3/5", "3/5, 1/2, 2/5", "2/5, 1/2, 3/5", "2/5, 3/5, 1/2"), 2, EQUIVALENT),

                    // ---- FD2: Add and subtract ----
                    // Trap: 1/2 + 1/3 is NOT 2/5 (never add denominators)
                    new Question("1/2 + 1/3 = ?",
                            Arrays.asList("2/5", "5/6", "1/5", "2/6"), 1, ADD_SUB),
                    new Question("3/4 - 1/2 = ?",
                            Arrays.asList("1/2", "2/2", "1/4", "4/2"), 2, ADD_SUB),
                    new Question("2 1/2 + 1 1/4 = ?",
                            Arrays.asList("3 1/4", "4 1/4", "3 2/6", "3 3/4"), 3, ADD_SUB),

                    // ---- FD3: Multiply and divide ----
                    new Question("2/3 x 3/5 = ?",
                            Arrays.asList("5/8", "6/8", "2/5", "5/15"), 2, MULT_DIV),
                    // Trap: dividing by a fraction means multiplying by its reciprocal
                    new Question("3/4 / 3/8 = ?",
                            Arrays.asList("1/2", "9/32", "4", "2"), 3, MULT_DIV),
                    new Question("The reciprocal of 5/7 is:",
                            Arrays.asList("-5/7", "5/7", "1/35", "7/5"), 3, MULT_DIV),

                    // ---- FD4: Decimal place value and conversion ----
                    new Question("0.75 as a fraction in simplest form is:",
                            Arrays.asList("7/5", "1/75", "3/4", "75/10"), 2, DEC_PLACE),
                    new Question("1/4 as a decimal is:",
                            Arrays.asList("0.14", "0.4", "0.75", "0.25"), 3, DEC_PLACE),
                    new Question("In 6.382, the digit 8 is in the ___ place.",
                            Arrays.asList("tenths", "hundredths", "thousandths", "ones"), 1, DEC_PLACE),

                    // ---- FD5: Operations on decimals ----
                    new Question("2.5 + 3.75 = ?",
                            Arrays.asList("5.75", "6.25", "6.00", "6.30"), 1, DEC_OPS),
                    new Question("5.4 - 2.85 = ?",
                            Arrays.asList("3.55", "2.45", "3.45", "2.55"), 3, DEC_OPS),
                    // Trap: 6 x 4 = 24, but two decimal places give 0.24, not 2.4
                    new Question("0.6 x 0.4 = ?",
                            Arrays.asList("2.4", "1.0", "0.24", "0.024"), 2, DEC_OPS)
            );
        }

        // ==========================================================
        // 3. RETEST
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
        // 4. LESSON CONTENT
        // ==========================================================
        @Override
        protected void teachLesson() {
            getLessonContentBank().forEach(LessonContent::display);
        }

        @Override
        protected List<LessonContent> getLessonContentBank() {
            return Arrays.asList(
                    new LessonContent(
                            EQUIVALENT,
                            "Equivalent fractions look different but have the same value. You get one by multiplying or "
                                    + "dividing the numerator AND denominator by the same non-zero number. To compare fractions, "
                                    + "give them the same denominator (the LCD): with equal denominators, the bigger numerator "
                                    + "is the bigger fraction.",
                            "a/b = (a x k)/(b x k)",
                            Arrays.asList(
                                    "2/3 = 4/6 = 6/9 (multiplying top and bottom by 2, then 3)",
                                    "Compare 3/4 and 2/3: 3/4 = 9/12 and 2/3 = 8/12, so 3/4 is greater",
                                    "Order 1/2, 3/5, 2/5: with denominator 10 they are 5/10, 6/10, 4/10, so 2/5 < 1/2 < 3/5"
                            )
                    ),
                    new LessonContent(
                            ADD_SUB,
                            "Fractions with the SAME denominator: add or subtract the numerators and keep the denominator. "
                                    + "Fractions with DIFFERENT denominators: first make the denominators equal (use the LCM), "
                                    + "then add or subtract. Never add the denominators. For mixed numbers, handle the whole "
                                    + "parts and the fraction parts separately.",
                            "a/c + b/c = (a + b)/c",
                            Arrays.asList(
                                    "5/8 + 1/8 = 6/8 = 3/4",
                                    "1/2 + 1/3 = 3/6 + 2/6 = 5/6",
                                    "3/4 - 1/2 = 3/4 - 2/4 = 1/4",
                                    "2 1/2 + 1 1/4: wholes 2 + 1 = 3, fractions 2/4 + 1/4 = 3/4, so the answer is 3 3/4"
                            )
                    ),
                    new LessonContent(
                            MULT_DIV,
                            "To multiply fractions, multiply the numerators together and the denominators together, then "
                                    + "simplify. To divide by a fraction, multiply by its reciprocal (flip the fraction). "
                                    + "The reciprocal of a/b is b/a.",
                            "a/b x c/d = (a x c)/(b x d)      a/b / c/d = a/b x d/c",
                            Arrays.asList(
                                    "2/3 x 3/5 = 6/15 = 2/5",
                                    "3/4 x 2/9 = 6/36 = 1/6",
                                    "3/4 / 3/8 = 3/4 x 8/3 = 24/12 = 2",
                                    "The reciprocal of 5/7 is 7/5"
                            )
                    ),
                    new LessonContent(
                            DEC_PLACE,
                            "In a decimal, the places to the right of the point are tenths, hundredths, thousandths and so on. "
                                    + "To change a decimal to a fraction, write it over 10, 100 or 1000 (one zero per decimal "
                                    + "place) and simplify. To change a fraction to a decimal, divide the numerator by the "
                                    + "denominator, or make the denominator 10, 100 or 1000.",
                            "0.d = d/10,   0.dd = dd/100,   0.ddd = ddd/1000",
                            Arrays.asList(
                                    "0.75 = 75/100 = 3/4",
                                    "0.5 = 5/10 = 1/2",
                                    "1/4 = 25/100 = 0.25",
                                    "3/5 = 6/10 = 0.6",
                                    "In 6.382: 6 ones, 3 tenths, 8 hundredths, 2 thousandths"
                            )
                    ),
                    new LessonContent(
                            DEC_OPS,
                            "To add or subtract decimals, line up the decimal points and add zeros if needed. To multiply, "
                                    + "ignore the points, multiply as whole numbers, then put the point so the answer has as many "
                                    + "decimal places as both numbers together. To divide by a decimal, move the point the same "
                                    + "number of places in both numbers until the divisor is a whole number.",
                            "Add/subtract: line up the points.   Multiply: count the decimal places.",
                            Arrays.asList(
                                    "2.5 + 3.75 = 2.50 + 3.75 = 6.25",
                                    "5.4 - 2.85 = 5.40 - 2.85 = 2.55",
                                    "0.6 x 0.4: 6 x 4 = 24, two decimal places, so 0.24",
                                    "1.2 x 0.3: 12 x 3 = 36, two decimal places, so 0.36",
                                    "3.6 / 0.6 = 36 / 6 = 6"
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

        @Override
        public java.util.Optional<String> getGameId() {
            return java.util.Optional.of("fraction-fisher");
        }
    }
}