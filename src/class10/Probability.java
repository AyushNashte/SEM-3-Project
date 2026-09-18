package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class Probability extends Topic {

    // Concepts specific to this topic
    private static final Concept WHAT_IS_PROBABILITY = new Concept("PR1", "What is Probability");
    private static final Concept SAMPLE_SPACE = new Concept("PR2", "Sample Space and Events");
    private static final Concept PROBABILITY_FORMULA = new Concept("PR3", "Probability Formula");
    private static final Concept COMPLEMENTARY_EVENTS = new Concept("PR4", "Complementary Events");
    private static final Concept PROBABILITY_OF_CARDS_DICE = new Concept("PR5", "Probability with Cards and Dice");
    private static final Concept BASIC_FRACTIONS = new Concept("PR6", "Basic Fractions");
    private static final Concept BASIC_COUNTING = new Concept("PR7", "Basic Counting");
    private static final Concept BASIC_RATIOS = new Concept("PR8", "Basic Ratios");

    public Probability() {
        super("Class 10", "Probability");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "Simplify the fraction 4/8",
                        Arrays.asList("1/2", "1/4", "2/3", "3/4"),
                        0,
                        BASIC_FRACTIONS
                ),
                new Question(
                        "Simplify the fraction 6/9",
                        Arrays.asList("1/2", "2/3", "3/4", "1/3"),
                        1,
                        BASIC_FRACTIONS
                ),
                new Question(
                        "Convert 1/4 to a decimal",
                        Arrays.asList("0.2", "0.25", "0.4", "0.5"),
                        1,
                        BASIC_FRACTIONS
                ),
                new Question(
                        "How many total outcomes are there when rolling a single 6-sided die?",
                        Arrays.asList("4", "5", "6", "12"),
                        2,
                        BASIC_COUNTING
                ),
                new Question(
                        "How many total cards are there in a standard deck of playing cards?",
                        Arrays.asList("48", "50", "52", "54"),
                        2,
                        BASIC_COUNTING
                ),
                new Question(
                        "A coin is tossed once. How many possible outcomes are there?",
                        Arrays.asList("1", "2", "3", "4"),
                        1,
                        BASIC_COUNTING
                ),
                new Question(
                        "Express the ratio 3:12 in simplest form",
                        Arrays.asList("1:2", "1:3", "1:4", "3:4"),
                        2,
                        BASIC_RATIOS
                ),
                new Question(
                        "Express the ratio 5:20 in simplest form",
                        Arrays.asList("1:2", "1:4", "1:5", "1:3"),
                        1,
                        BASIC_RATIOS
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
                        "Probability measures:",
                        Arrays.asList("The exact outcome of an event", "How likely an event is to occur", "The number of times an event has occurred", "The total number of outcomes only"),
                        1,
                        WHAT_IS_PROBABILITY
                ),
                new Question(
                        "The probability of any event always lies between:",
                        Arrays.asList("-1 and 1", "0 and 1", "0 and 100", "1 and 10"),
                        1,
                        WHAT_IS_PROBABILITY
                ),
                new Question(
                        "A probability of 0 means the event is:",
                        Arrays.asList("Certain to happen", "Impossible", "Very likely", "Equally likely"),
                        1,
                        WHAT_IS_PROBABILITY
                ),
                new Question(
                        "The set of all possible outcomes of an experiment is called the:",
                        Arrays.asList("Event", "Sample space", "Trial", "Outcome set"),
                        1,
                        SAMPLE_SPACE
                ),
                new Question(
                        "What is the sample space when tossing a single coin?",
                        Arrays.asList("{Heads}", "{Heads, Tails}", "{1,2,3,4,5,6}", "{Heads, Tails, Edge}"),
                        1,
                        SAMPLE_SPACE
                ),
                new Question(
                        "An 'event' in probability refers to:",
                        Arrays.asList("The total number of outcomes", "One or more outcomes from the sample space", "Only impossible outcomes", "The experiment itself"),
                        1,
                        SAMPLE_SPACE
                ),
                new Question(
                        "What is the formula for the probability of an event E?",
                        Arrays.asList("P(E) = Favorable outcomes x Total outcomes", "P(E) = Favorable outcomes / Total outcomes", "P(E) = Total outcomes / Favorable outcomes", "P(E) = Total outcomes - Favorable outcomes"),
                        1,
                        PROBABILITY_FORMULA
                ),
                new Question(
                        "A bag has 3 red balls and 7 blue balls. What is the probability of drawing a red ball?",
                        Arrays.asList("3/7", "3/10", "7/10", "1/3"),
                        1,
                        PROBABILITY_FORMULA
                ),
                new Question(
                        "A die is rolled once. What is the probability of getting a 4?",
                        Arrays.asList("1/4", "1/6", "1/3", "4/6"),
                        1,
                        PROBABILITY_FORMULA
                ),
                new Question(
                        "The complement of an event E, written E', represents:",
                        Arrays.asList("The event happening twice", "All outcomes where E does NOT happen", "The same outcomes as E", "An impossible event"),
                        1,
                        COMPLEMENTARY_EVENTS
                ),
                new Question(
                        "P(E) + P(E') always equals:",
                        Arrays.asList("0", "0.5", "1", "It depends on the event"),
                        2,
                        COMPLEMENTARY_EVENTS
                ),
                new Question(
                        "If the probability of rain tomorrow is 0.3, what is the probability of no rain?",
                        Arrays.asList("0.3", "0.5", "0.7", "1.3"),
                        2,
                        COMPLEMENTARY_EVENTS
                ),
                new Question(
                        "What is the probability of drawing an Ace from a standard 52-card deck?",
                        Arrays.asList("1/52", "4/52", "1/13", "Both 4/52 and 1/13 are correct"),
                        3,
                        PROBABILITY_OF_CARDS_DICE
                ),
                new Question(
                        "Two dice are rolled. What is the total number of possible outcomes?",
                        Arrays.asList("6", "12", "36", "64"),
                        2,
                        PROBABILITY_OF_CARDS_DICE
                ),
                new Question(
                        "What is the probability of drawing a red card from a standard 52-card deck?",
                        Arrays.asList("1/4", "1/2", "1/13", "13/52"),
                        1,
                        PROBABILITY_OF_CARDS_DICE
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
                        WHAT_IS_PROBABILITY,
                        "Probability measures how likely an event is to happen, expressed as a number between "
                                + "0 (impossible) and 1 (certain). The closer to 1, the more likely the event.",
                        null,
                        Arrays.asList(
                                "The probability of the sun rising tomorrow is essentially 1 (certain)",
                                "The probability of rolling a 7 on a standard die is 0 (impossible)"
                        )
                ),
                new LessonContent(
                        SAMPLE_SPACE,
                        "The sample space is the set of all possible outcomes of an experiment. An event is "
                                + "any specific outcome or group of outcomes we're interested in, taken from that sample space.",
                        null,
                        Arrays.asList(
                                "Rolling a die: sample space = {1,2,3,4,5,6}. The event 'rolling an even number' = {2,4,6}"
                        )
                ),
                new LessonContent(
                        PROBABILITY_FORMULA,
                        "For outcomes that are all equally likely, the probability of an event is the number "
                                + "of favorable outcomes divided by the total number of possible outcomes.",
                        "P(E) = (Number of favorable outcomes) / (Total number of outcomes)",
                        Arrays.asList(
                                "A bag has 3 red and 7 blue balls (10 total). P(red) = 3/10"
                        )
                ),
                new LessonContent(
                        COMPLEMENTARY_EVENTS,
                        "The complement of an event E (written E') is everything that happens when E does NOT "
                                + "occur. Since an event either happens or doesn't, their probabilities always add up to 1.",
                        "P(E) + P(E') = 1,  so  P(E') = 1 - P(E)",
                        Arrays.asList(
                                "If P(rain) = 0.3, then P(no rain) = 1 - 0.3 = 0.7"
                        )
                ),
                new LessonContent(
                        PROBABILITY_OF_CARDS_DICE,
                        "Cards and dice are classic probability examples because their sample spaces are fixed "
                                + "and well known: a standard deck has 52 cards (4 suits of 13 each, including 4 Aces), and a "
                                + "single die has 6 faces.",
                        null,
                        Arrays.asList(
                                "P(drawing an Ace) = 4/52 = 1/13",
                                "P(drawing a red card) = 26/52 = 1/2, since half the deck (hearts + diamonds) is red"
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
    public java.util.Optional<String> getVisualComponentId(Concept concept) {
        if (concept == WHAT_IS_PROBABILITY || concept == PROBABILITY_FORMULA) {
            return java.util.Optional.of("probability-simulator");
        }
        return java.util.Optional.empty();
    }
}