
# CampusBoot – Custom Annotations Framework

**Student Name:** Md. Rakibul Islam  
**Student ID:** IT-24029

---

## Project Overview

CampusBoot is a simplified Java-based framework developed to demonstrate how custom annotations and Java Reflection can be used to build core features similar to Spring Boot.

The project explores annotation-based component management, dependency injection, configuration value injection, validation, and request routing.

## Terminal Output

The following screenshot shows the output generated after running the CampusBoot application.

![CampusBoot Terminal Output](https://github.com/user-attachments/assets/fbe1ee0b-6cdf-492e-bd69-5710b68dab6b)

---

# Part E: Reflection Questions

This section covers important concepts related to Java annotation retention, comparisons between CampusBoot and Spring Boot, and the advantages and risks of Java Reflection.

## 1. What Would Happen If You Forgot `@Retention(RetentionPolicy.RUNTIME)`?

Without `@Retention(RetentionPolicy.RUNTIME)`, Java uses the default retention policy, which is `RetentionPolicy.CLASS`.

This means annotations are stored in the compiled `.class` files but are not available to the JVM through runtime reflection.

As a result:

- Calls like `getAnnotation(Component.class)` would return `null`.
- The Container would skip every component class.
- No configuration values would be injected.
- The Validator would not detect fields annotated with `@Range`.
- The Router would fail to recognize registered routes and return `404 Not Found` for every path.

**Conclusion:** The framework would compile successfully but fail to function correctly at runtime because its annotations would not be accessible through reflection.

## 2. Mapping CampusBoot to Spring Boot

CampusBoot implements simplified versions of several annotations and features found in Spring Boot.

### CampusBoot vs. Spring Boot

| CampusBoot | Spring Boot Equivalent |
|---|---|
| `@Component` | `@Component`, `@Service`, `@Controller` |
| `@Value` | `@Value("${key}")` |
| `@Range` | `@Min`, `@Max` (Bean Validation) |
| `@Route` | `@GetMapping`, `@RequestMapping` |

### What Does Real Spring Boot Do That CampusBoot Does Not?

Spring Boot provides many advanced features that are not implemented in CampusBoot.

1. **Dependency Injection:** Supports constructor injection and `@Autowired` for connecting application components.
2. **Bean Scopes:** Supports different bean scopes, such as singleton and prototype.
3. **Automatic Classpath Scanning:** Automatically discovers and registers application components.
4. **Embedded Web Server:** Provides embedded servers, such as Tomcat, to run web applications.
5. **Parameter Binding:** Automatically maps HTTP request parameters to controller method arguments.
6. **AOP Proxies:** Supports features such as `@Transactional` through Aspect-Oriented Programming (AOP).

**Conclusion:** CampusBoot demonstrates the fundamental concepts behind Spring Boot, while Spring Boot provides a complete application framework with many additional features.

## 3. Why Is `setAccessible(true)` Both Powerful and Risky?

Java Reflection provides `setAccessible(true)` to suppress normal Java access checks when permitted by the runtime environment.

### Why Is It Powerful?

- Allows reflection to access private fields and methods.
- Makes dependency injection into private fields possible without requiring setter methods.
- Enables frameworks to interact with classes without modifying their source code.
- Provides flexibility when developing annotation-based frameworks.

### Why Is It Risky?

- **Breaks Encapsulation:** Bypasses normal access restrictions.
- **Modifies Internal State:** Can change private data and potentially violate class invariants.
- **Creates Hidden Dependencies:** Frameworks may become dependent on private implementation details.
- **Reduces Maintainability:** Changes to private fields or methods can break framework behavior.
- **Introduces Security Concerns:** Unrestricted access can expose sensitive internal data.
- **Java Module Restrictions:** Java 9 and later may prevent reflective access to certain members, resulting in an `InaccessibleObjectException`.

**Conclusion:** `setAccessible(true)` is useful in framework development because it provides powerful access through reflection. However, it should be used carefully because bypassing encapsulation can make applications harder to maintain and less secure.

---

# Overall Summary

Through the CampusBoot project, we learned three important concepts:

### 1. Annotation Retention

Runtime retention is essential for frameworks that inspect annotations using Java Reflection. Without `RetentionPolicy.RUNTIME`, annotations cannot be accessed through runtime reflection.

### 2. Spring Boot Architecture

CampusBoot demonstrates simplified versions of real Spring Boot features, including dependency injection, configuration management, validation, and request routing.

### 3. Java Reflection

Reflection makes dynamic framework behavior possible. However, accessing private members requires careful consideration of encapsulation, security, and maintainability.

## Learning Outcomes

After completing this project, we gained practical knowledge of:

- Creating and using custom Java annotations.
- Understanding annotation retention policies.
- Using Java Reflection to inspect classes, fields, and methods.
- Implementing basic dependency injection.
- Injecting configuration values into application components.
- Performing annotation-based field validation.
- Building a simple annotation-based routing mechanism.
- Understanding the similarities and differences between custom frameworks and Spring Boot.

---

## Final Remarks

The CampusBoot project helped us understand how modern Java frameworks use annotations and reflection to simplify application development.

By implementing a simplified framework from scratch, we gained a better understanding of the underlying mechanisms that power annotation-driven frameworks such as Spring Boot.

---

**Submitted By:** Md. Rakibul Islam  
**Student ID:** IT-24029  
**Project:** CampusBoot – Custom Annotations Framework
