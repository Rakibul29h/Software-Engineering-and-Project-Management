import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static List<String> validate(Object obj) {
        List<String> violations = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            Range r = field.getAnnotation(Range.class);
            if (r == null) continue;
            if (field.getType() != int.class && field.getType() != Integer.class) continue;
            try {
                field.setAccessible(true);
                int value = field.getInt(obj);
                if (value < r.min() || value > r.max()) {
                    violations.add(field.getName() + " - " + r.message() + " (actual: " + value + ")");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return violations;
    }
}
