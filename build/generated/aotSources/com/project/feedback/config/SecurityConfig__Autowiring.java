package com.project.feedback.config;

import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link SecurityConfig}.
 */
public class SecurityConfig__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static SecurityConfig apply(RegisteredBean registeredBean, SecurityConfig instance) {
    AutowiredFieldValueResolver.forRequiredField("secretkey").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
