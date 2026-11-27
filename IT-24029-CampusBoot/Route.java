import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Maps a URL path to a method.
 * The spec names the element path(); the unmodified starter code writes
 * @Route("/hello"), which Java only allows for an element called value().
 * So both are declared: use either @Route(path = "/x") or @Route("/x").
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String path() default "";
    String value() default "";
}
