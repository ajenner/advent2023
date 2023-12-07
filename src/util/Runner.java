package util;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Runner {

    public static void main(String[] args) throws Exception {
        String[] days = new String[] {"1","2","3","4","5","6","7"};
        boolean[] parts = new boolean[] {true, false};

        for (String day : days) {
            Class<?> cls = Class.forName("days.Day" + day);
            boolean exclude = (boolean) cls.getMethod("exclude").invoke(cls.getDeclaredConstructor().newInstance());
            if (exclude) {
                System.out.println("Excluding day" + day + "\n");
                continue;
            }
            for (boolean part : parts) {
                Reader reader = new Reader("src/data/day" + day + ".txt");
                ArrayList<String> inputs = reader.readAsStrings();
                Method m = cls.getMethod("timeAndLogResult", String.class, boolean.class, ArrayList.class);
                m.invoke(cls.getDeclaredConstructor().newInstance(), day, part, inputs);
            }
            System.out.println("");
        }
    }
}
