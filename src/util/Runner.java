package util;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Runner {
    static boolean runSamples = true;
    static String[] sampleAnswers1 = new String[]        {"142", "8",    "4361",   "13",  "35",  "288",   "6440", "2",  "114", "8",  "374",      "21",     "405", "136", "1320", "46", "102", null, null, null, null, null, null, null};
    static String[] sampleAnswers2 = new String[]        {"281", "2286", "467835", "30",  "46",  "71503", "5905", "6",  "2"  , "10", "82000210", "525152", "400", "64",  "145",  "51", "94", null, null, null, null, null, null, null};
    static boolean[] hasDifferentSamples = new boolean[] {true,  false,  false,    false, false, false,    false, true, false, true, false,      false,    false, false, false,  false,false,false,false,false,false,false,false,false};

    public static void main(String[] args) throws Exception {
        String[] days = new String[] {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16", "17"};
        boolean[] parts = new boolean[] {true, false};

        for (String day : days) {
            Class<?> cls = Class.forName("days.Day" + day);
            boolean exclude = (boolean) cls.getMethod("exclude").invoke(cls.getDeclaredConstructor().newInstance());
            if (exclude) {
                System.out.println("Excluding day" + day + "\n");
                continue;
            }
            for (boolean part : parts) {
                boolean samplePassed = runSamples(cls, day, part);
                if (runSamples && !samplePassed) {
                    continue;
                }
                Reader reader = new Reader("src/data/day" + day + ".txt");
                ArrayList<String> inputs = reader.readAsStrings();
                Method m = cls.getMethod("timeAndLogResult", String.class, boolean.class, ArrayList.class);
                m.invoke(cls.getDeclaredConstructor().newInstance(), day, part, inputs);
                System.out.println("");
            }
        }
    }

    private static boolean runSamples(Class<?> cls, String day, boolean part1) throws Exception {
        if (!runSamples) {
            return true;
        }
        System.out.print("Sample ");
        int index = Integer.parseInt(day) - 1;
        Reader reader = new Reader("src/test/day" + day + "sample" + ((hasDifferentSamples[index] && !part1)? "2" : "") + ".txt");
        ArrayList<String> inputs = reader.readAsStrings();
        Method m = cls.getMethod("timeAndLogResult", String.class, boolean.class, ArrayList.class);
        String result = (String) m.invoke(cls.getDeclaredConstructor().newInstance(), day, part1, inputs);
        String sampleAnswer = (part1) ? sampleAnswers1[index] : sampleAnswers2[index];
        if (sampleAnswer == null) {
            return true;
        }
        boolean passed =  result.equals(sampleAnswer);
        if (!passed) {
            System.out.println("\nSample failed! Expected: " + sampleAnswer + " Actual: " + result + "\n");
        }
        return passed;
    }
}
