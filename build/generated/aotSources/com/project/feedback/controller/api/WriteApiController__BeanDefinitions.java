package com.project.feedback.controller.api;

import com.project.feedback.service.BoardService;
import com.project.feedback.service.CommentService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WriteApiController}
 */
public class WriteApiController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'writeApiController'.
   */
  private static BeanInstanceSupplier<WriteApiController> getWriteApiControllerInstanceSupplier() {
    return BeanInstanceSupplier.<WriteApiController>forConstructor(BoardService.class, CommentService.class)
            .withGenerator((registeredBean, args) -> new WriteApiController(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'writeApiController'
   */
  public static BeanDefinition getWriteApiControllerBeanDefinition() {
    Class<?> beanType = WriteApiController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getWriteApiControllerInstanceSupplier());
    return beanDefinition;
  }
}
