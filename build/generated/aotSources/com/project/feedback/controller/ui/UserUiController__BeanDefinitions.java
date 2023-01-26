package com.project.feedback.controller.ui;

import com.project.feedback.service.CourseService;
import com.project.feedback.service.UserService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link UserUiController}
 */
public class UserUiController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'userUiController'.
   */
  private static BeanInstanceSupplier<UserUiController> getUserUiControllerInstanceSupplier() {
    return BeanInstanceSupplier.<UserUiController>forConstructor(UserService.class, CourseService.class)
            .withGenerator((registeredBean, args) -> new UserUiController(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'userUiController'
   */
  public static BeanDefinition getUserUiControllerBeanDefinition() {
    Class<?> beanType = UserUiController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getUserUiControllerInstanceSupplier());
    return beanDefinition;
  }
}
