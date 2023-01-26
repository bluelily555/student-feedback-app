package com.project.feedback.service;

import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link UserService}.
 */
public class UserService__TestContext001_Autowiring {
  /**
   * Apply the autowiring.
   */
  public static UserService apply(RegisteredBean registeredBean, UserService instance) {
    AutowiredFieldValueResolver.forRequiredField("secretKey").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
