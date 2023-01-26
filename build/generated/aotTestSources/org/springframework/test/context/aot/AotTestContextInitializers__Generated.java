package org.springframework.test.context.aot;

import com.project.feedback.FeedbackApplicationTests__TestContext001_ApplicationContextInitializer;
import java.lang.Class;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Generated mappings for {@link AotTestContextInitializers}.
 */
public class AotTestContextInitializers__Generated {
  public static Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> getContextInitializers(
      ) {
    Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> map = new HashMap<>();
    map.put("com.project.feedback.FeedbackApplicationTests", () -> new FeedbackApplicationTests__TestContext001_ApplicationContextInitializer());
    map.put("com.project.feedback.integrate.IntegrateTest", () -> new FeedbackApplicationTests__TestContext001_ApplicationContextInitializer());
    return map;
  }

  public static Map<String, Class<? extends ApplicationContextInitializer<?>>> getContextInitializerClasses(
      ) {
    Map<String, Class<? extends ApplicationContextInitializer<?>>> map = new HashMap<>();
    map.put("com.project.feedback.FeedbackApplicationTests", FeedbackApplicationTests__TestContext001_ApplicationContextInitializer.class);
    map.put("com.project.feedback.integrate.IntegrateTest", FeedbackApplicationTests__TestContext001_ApplicationContextInitializer.class);
    return map;
  }
}
