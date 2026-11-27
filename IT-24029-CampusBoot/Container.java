import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Container {

    private final Map<String, Object> beans = new LinkedHashMap<>();

    public void scan(Class<?>... classes) {
        for (Class<?> cls : classes) {
            Component comp = cls.getAnnotation(Component.class);
            if (comp == null) {
                System.out.println("[Container] Skipped: " + cls.getSimpleName() + " (no @Component)");
                continue;
            }
            String name = comp.value();
            if (name.isEmpty()) {
                String simple = cls.getSimpleName();
                name = Character.toLowerCase(simple.charAt(0)) + simple.substring(1);
            }
            try {
                var ctor = cls.getDeclaredConstructor();
                ctor.setAccessible(true);
                beans.put(name, ctor.newInstance());
                System.out.println("[Container] Registered: " + name + " (" + cls.getSimpleName() + ")");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Cannot create bean for " + cls.getName(), e);
            }
        }
    }

    public void injectValues(Map<String, String> config) {
        for (Object bean : beans.values()) {
            Class<?> cls = bean.getClass();
            for (Field field : cls.getDeclaredFields()) {
                Value v = field.getAnnotation(Value.class);
                if (v == null) continue;
                String key = v.value();
                String raw = config.get(key);
                if (raw == null) {
                    System.out.println("[Container] Skipped: no config for key " + key);
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type == int.class || type == Integer.class) {
                        field.set(bean, Integer.parseInt(raw.trim()));
                    } else if (type == String.class) {
                        field.set(bean, raw);
                    } else {
                        System.out.println("[Container] Skipped: unsupported type "
                                + type.getSimpleName() + " for " + cls.getSimpleName() + "." + field.getName());
                        continue;
                    }
                    System.out.println("[Container] Injected: " + key + " -> "
                            + cls.getSimpleName() + "." + field.getName());
                } catch (IllegalAccessException | NumberFormatException e) {
                    System.out.println("[Container] Skipped: cannot inject " + key
                            + " into " + cls.getSimpleName() + "." + field.getName()
                            + " (" + e.getMessage() + ")");
                }
            }
        }
    }

    public Object getBean(String name) {
        return beans.get(name);
    }

    Map<String, Object> getBeans() {
        return beans;
    }
}
