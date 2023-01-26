package com.project.feedback.service;

import com.project.feedback.repository.UserRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Bean definitions for {@link UserService}
 */
public class UserService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'userService'.
   */
  private static BeanInstanceSupplier<UserService> getUserServiceInstanceSupplier() {
    return BeanInstanceSupplier.<UserService>forConstructor(UserRepository.class, BCryptPasswordEncoder.class, FindService.class)
            .withGenerator((registeredBean, args) -> new UserService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'userService'
   */
  public static BeanDefinition getUserServiceBeanDefinition() {
    Class<?> beanType = UserService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    InstanceSupplier<UserService> instanceSupplier = getUserServiceInstanceSupplier();
    instanceSupplier = instanceSupplier.andThen(UserService__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
