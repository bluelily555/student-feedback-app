package com.project.feedback.controller.ui;

import com.project.feedback.service.BoardService;
import com.project.feedback.service.CodeService;
import com.project.feedback.service.CommentService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WriteController}
 */
public class WriteController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'writeController'.
   */
  private static BeanInstanceSupplier<WriteController> getWriteControllerInstanceSupplier() {
    return BeanInstanceSupplier.<WriteController>forConstructor(BoardService.class, CommentService.class, CodeService.class)
            .withGenerator((registeredBean, args) -> new WriteController(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'writeController'
   */
  public static BeanDefinition getWriteControllerBeanDefinition() {
    Class<?> beanType = WriteController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getWriteControllerInstanceSupplier());
    return beanDefinition;
  }
}
