package com.project.feedback.controller.api;

import com.project.feedback.service.UserService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link UserApiController}
 */
public class UserApiController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'userApiController'.
   */
  private static BeanInstanceSupplier<UserApiController> getUserApiControllerInstanceSupplier() {
    return BeanInstanceSupplier.<UserApiController>forConstructor(UserService.class)
            .withGenerator((registeredBean, args) -> new UserApiController(args.get(0)));
  }

  /**
   * Get the bean definition for 'userApiController'
   */
  public static BeanDefinition getUserApiControllerBeanDefinition() {
    Class<?> beanType = UserApiController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getUserApiControllerInstanceSupplier());
    return beanDefinition;
  }
}
