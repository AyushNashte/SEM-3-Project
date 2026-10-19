package common.gui;

import class7.Integers;
import common.Topic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClassCatalog {

    public static class Entry {
        public final String emoji;
        public final String name;
        private final Consumer<AppLauncher> opener;

        Entry(String emoji, String name, Consumer<AppLauncher> opener) {
            this.emoji = emoji;
            this.name = name;
            this.opener = opener;
        }

        public void open(AppLauncher launcher) {
            opener.accept(launcher);
        }
    }

    private static Entry topic(String emoji, String name, Supplier<Topic> supplier) {
        return new Entry(emoji, name, launcher -> launcher.selectTopic(supplier.get()));
    }

    public static Map<String, List<Entry>> all() {
        Map<String, List<Entry>> classes = new LinkedHashMap<>();

        List<Entry> list7 = new ArrayList<>();
        list7.add(topic("+/-", "Integers", class7.Integers::new));
        list7.add(topic("a:b", "Ratio & Proportion", class7.RatioAndProportion::new));
        classes.put("Class 7", list7);

        List<Entry> list8 = new ArrayList<>();
        list8.add(topic("a(b+c)", "Factorisation", class8.Factorisation::new));
        list8.add(topic("x=?", "Linear Equations", class8.LinearEquations::new));
        classes.put("Class 8", list8);

        List<Entry> list9 = new ArrayList<>();
        list9.add(topic("π", "Real Numbers", class9.RealNumbers::new));
        list9.add(topic("x²", "Polynomials", class9.Polynomials::new));
        classes.put("Class 9", list9);

        List<Entry> list10 = new ArrayList<>();
        list10.add(topic("a+d", "Arithmetic Progression", class10.ArithmeticProgression::new));
        list10.add(topic("Δ~Δ"  , "Similarity", class10.Similarity::new));
        classes.put("Class 10", list10);

        return classes;
    }

    public static String accent(String className) {
        switch (className) {
            case "Class 7":
                return "#f59e0b";
            case "Class 8":
                return "#10b981";
            case "Class 9":
                return "#3b82f6";
            default:
                return "#ec4899";
        }
    }
}