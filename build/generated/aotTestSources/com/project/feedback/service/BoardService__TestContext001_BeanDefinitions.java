package com.project.feedback.service;

import com.project.feedback.repository.BoardRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link BoardService}
 */
public class BoardService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'boardService'.
   */
  private static BeanInstanceSupplier<BoardService> getBoardServiceInstanceSupplier() {
    return BeanInstanceSupplier.<BoardService>forConstructor(BoardRepository.class)
            .withGenerator((registeredBean, args) -> new BoardService(args.get(0)));
  }

  /**
   * Get the bean definition for 'boardService'
   */
  public static BeanDefinition getBoardServiceBeanDefinition() {
    Class<?> beanType = BoardService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getBoardServiceInstanceSupplier());
    return beanDefinition;
  }
}
