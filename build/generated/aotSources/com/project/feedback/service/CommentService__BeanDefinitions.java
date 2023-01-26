package com.project.feedback.service;

import com.project.feedback.repository.CommentRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CommentService}
 */
public class CommentService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'commentService'.
   */
  private static BeanInstanceSupplier<CommentService> getCommentServiceInstanceSupplier() {
    return BeanInstanceSupplier.<CommentService>forConstructor(CommentRepository.class)
            .withGenerator((registeredBean, args) -> new CommentService(args.get(0)));
  }

  /**
   * Get the bean definition for 'commentService'
   */
  public static BeanDefinition getCommentServiceBeanDefinition() {
    Class<?> beanType = CommentService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getCommentServiceInstanceSupplier());
    return beanDefinition;
  }
}
