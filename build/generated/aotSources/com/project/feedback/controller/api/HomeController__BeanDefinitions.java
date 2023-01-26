package com.project.feedback.controller.api;

import com.project.feedback.service.FindService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HomeController}
 */
public class HomeController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'homeController'.
   */
  private static BeanInstanceSupplier<HomeController> getHomeControllerInstanceSupplier() {
    return BeanInstanceSupplier.<HomeController>forConstructor(FindService.class)
            .withGenerator((registeredBean, args) -> new HomeController(args.get(0)));
  }

  /**
   * Get the bean definition for 'homeController'
   */
  public static BeanDefinition getHomeControllerBeanDefinition() {
    Class<?> beanType = HomeController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getHomeControllerInstanceSupplier());
    return beanDefinition;
  }
}
