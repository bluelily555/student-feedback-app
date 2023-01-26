package org.springframework.boot.autoconfigure.jdbc;

import java.lang.Class;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataSourceJmxConfiguration}
 */
public class DataSourceJmxConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'dataSourceJmxConfiguration'
   */
  public static BeanDefinition getDataSourceJmxConfigurationBeanDefinition() {
    Class<?> beanType = DataSourceJmxConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(DataSourceJmxConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link DataSourceJmxConfiguration.Hikari}
   */
  public static class Hikari__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.jdbc.DataSourceJmxConfiguration$Hikari'.
     */
    private static BeanInstanceSupplier<DataSourceJmxConfiguration.Hikari> getHikariInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<DataSourceJmxConfiguration.Hikari>forConstructor(DataSource.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> new DataSourceJmxConfiguration.Hikari(args.get(0), args.get(1)));
    }

    /**
     * Get the bean definition for 'hikari'
     */
    public static BeanDefinition getHikariBeanDefinition() {
      Class<?> beanType = DataSourceJmxConfiguration.Hikari.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getHikariInstanceSupplier());
      return beanDefinition;
    }
  }
}
