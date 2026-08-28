package class7;
import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class 7 - Fractions & Decimals
 * Framed around food-sharing, money, and measurements so operations feel
 * concrete rather than abstract symbol pushing.
 */
public class FractionsAndDecimals extends Topic {

    private static final Concept FRACTION_BASICS = new Concept("FD1", "Understanding Fractions");
    private static final Concept FRACTION_OPS = new Concept("FD2", "Operations on Fractions");
    private static final Concept DECIMAL_BASICS = new Concept("FD3", "Understanding Decimals");
    private static final Concept DECIMAL_OPS = new Concept("FD4", "Operations on Decimals");
    private static final Concept CONVERSION = new Concept("FD5", "Converting Fractions & Decimals");

    public FractionsAndDecimals() {
        super("Class 7", "Fractions & Decimals");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "If a pizza is cut into 4 equal pieces and you eat 1 piece, what fraction did you eat?",
                        Arrays.asList("1/2", "1/3", "1/4", "1/5"),
                        2,
                        FRACTION_BASICS
                ),
                new Question(
                        "What is 3/4 + 1/4?",
                        Arrays.asList("1", "4/8", "3/8", "1/4"),
                        0,
                        FRACTION_OPS
                ),
                new Question(
                        "Which is bigger, 0.5 or 0.25?",
                        Arrays.asList("0.25", "0.5", "They are equal", "Cannot say"),
                        1,
                        DECIMAL_BASICS
                ),
                new Question(
                        "What is 2.5 + 1.5?",
                        Arrays.asList("3.0", "4.0", "3.5", "4.5"),
                        1,
                        DECIMAL_OPS
                ),
                new Question(
                        "What is 10 x 3?",
                        Arrays.asList("13", "30", "33", "300"),
                        1,
                        FRACTION_BASICS
                ),
                new Question(
                        "How many equal parts make a 'whole'?",
                        Arrays.asList("It depends on how it's divided", "Always 2", "Always 4", "Always 10"),
                        0,
                        FRACTION_BASICS
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
                        "A chocolate bar has 8 pieces. Aman eats 3 pieces. What fraction of the bar is left?",
                        Arrays.asList("3/8", "5/8", "8/3", "3/5"),
                        1,
                        FRACTION_BASICS
                ),
                new Question(
                        "Which fraction is equivalent to 2/3?",
                        Arrays.asList("3/2", "4/6", "2/6", "6/2"),
                        1,
                        FRACTION_BASICS
                ),
                new Question(
                        "Two friends share a 3/5 kg bag of candy equally. How much does each get?",
                        Arrays.asList("3/10 kg", "3/5 kg", "6/5 kg", "1/5 kg"),
                        0,
                        FRACTION_OPS
                ),
                new Question(
                        "What is 2/3 + 1/6?",
                        Arrays.asList("3/9", "5/6", "1/2", "3/6"),
                        1,
                        FRACTION_OPS
                ),
                new Question(
                        "What is 3/4 x 2/5?",
                        Arrays.asList("6/20 (=3/10)", "5/9", "6/9", "3/20"),
                        0,
                        FRACTION_OPS
                ),
                new Question(
                        "What is 1/2 ÷ 1/4?",
                        Arrays.asList("1/8", "2", "1/2", "4"),
                        1,
                        FRACTION_OPS
                ),
                new Question(
                        "A book costs ₹125.75. What does 'point 75' represent?",
                        Arrays.asList("75 rupees", "75 paise as a fraction of a rupee", "7.5 rupees", "750 paise"),
                        1,
                        DECIMAL_BASICS
                ),
                new Question(
                        "Which decimal is the same as the fraction 3/10?",
                        Arrays.asList("0.3", "0.03", "3.0", "0.13"),
                        0,
                        CONVERSION
                ),
                new Question(
                        "Convert 1/4 into a decimal.",
                        Arrays.asList("0.4", "0.25", "0.14", "1.4"),
                        1,
                        CONVERSION
                ),
                new Question(
                        "A runner completes a race in 12.35 seconds and another in 12.5 seconds. Who is faster?",
                        Arrays.asList("12.35 sec runner", "12.5 sec runner", "Both equal", "Cannot say"),
                        0,
                        DECIMAL_OPS
                ),
                new Question(
                        "What is 5.6 - 2.35?",
                        Arrays.asList("3.25", "3.35", "2.25", "3.15"),
                        0,
                        DECIMAL_OPS
                ),
                new Question(
                        "What is 1.2 x 0.5?",
                        Arrays.asList("0.6", "6.0", "0.06", "1.7"),
                        0,
                        DECIMAL_OPS
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
                        FRACTION_BASICS,
                        "A fraction represents equal parts of a whole. The bottom number (denominator) "
                                + "tells you how many equal parts the whole is divided into, and the top number "
                                + "(numerator) tells you how many of those parts you have.",
                        "Fraction = Numerator / Denominator",
                        Arrays.asList(
                                "A dosa cut into 4 equal pieces: eating 3 pieces = 3/4 of the dosa",
                                "5/8 and 10/16 represent the same amount (equivalent fractions)"
                        )
                ),
                new LessonContent(
                        FRACTION_OPS,
                        "To add or subtract fractions, first make the denominators the same (common "
                                + "denominator), then add/subtract only the numerators. To multiply fractions, "
                                + "multiply numerators together and denominators together. To divide by a "
                                + "fraction, multiply by its reciprocal (flip it).",
                        "a/b + c/d = (ad + bc)/bd   |   a/b ÷ c/d = a/b x d/c",
                        Arrays.asList(
                                "1/3 + 1/6 = 2/6 + 1/6 = 3/6 = 1/2",
                                "2/5 x 3/4 = 6/20 = 3/10",
                                "3/4 ÷ 1/2 = 3/4 x 2/1 = 6/4 = 3/2"
                        )
                ),
                new LessonContent(
                        DECIMAL_BASICS,
                        "A decimal is another way to write a fraction whose denominator is 10, 100, 1000, "
                                + "and so on. The digits after the decimal point represent tenths, hundredths, "
                                + "thousandths, and so on.",
                        null,
                        Arrays.asList(
                                "In ₹45.60, the '6' is in the tenths place and the '0' is in the hundredths place",
                                "0.75 means 75 hundredths"
                        )
                ),
                new LessonContent(
                        DECIMAL_OPS,
                        "To add or subtract decimals, line up the decimal points and add zeros if needed so "
                                + "every number has the same number of digits after the point. To multiply "
                                + "decimals, multiply as whole numbers first, then place the decimal point by "
                                + "counting the total decimal places in both numbers.",
                        null,
                        Arrays.asList(
                                "12.50 + 3.75 = 16.25",
                                "2.3 x 1.5: multiply 23 x 15 = 345, then place decimal (2 total places) -> 3.45"
                        )
                ),
                new LessonContent(
                        CONVERSION,
                        "To convert a fraction to a decimal, divide the numerator by the denominator. To "
                                + "convert a decimal to a fraction, write the digits after the point over the "
                                + "matching power of 10, then simplify.",
                        null,
                        Arrays.asList(
                                "3/4 -> 3 ÷ 4 = 0.75",
                                "0.6 -> 6/10 = 3/5 (simplified)"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts in Fractions & Decimals:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}