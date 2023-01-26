package com.project.feedback.controller.api;

import com.project.feedback.service.CourseService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CourseApiController}
 */
public class CourseApiController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'courseApiController'.
   */
  private static BeanInstanceSupplier<CourseApiController> getCourseApiControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<CourseApiController>forConstructor(CourseService.class)
            .withGenerator((registeredBean, args) -> new CourseApiController(args.get(0)));
  }

  /**
   * Get the bean definition for 'courseApiController'
   */
  public static BeanDefinition getCourseApiControllerBeanDefinition() {
    Class<?> beanType = CourseApiController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getCourseApiControllerInstanceSupplier());
    return beanDefinition;
  }
}
