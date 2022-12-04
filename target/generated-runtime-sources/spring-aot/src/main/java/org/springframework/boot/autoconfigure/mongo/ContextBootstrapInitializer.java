package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.MongoClientSettings;
import org.springframework.aot.beans.factory.BeanDefinitionRegistrar;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;

public final class ContextBootstrapInitializer {
  public static void registerMongoAutoConfiguration_MongoClientSettingsConfiguration(
      DefaultListableBeanFactory beanFactory) {
    BeanDefinitionRegistrar.of("org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration$MongoClientSettingsConfiguration", MongoAutoConfiguration.MongoClientSettingsConfiguration.class)
        .instanceSupplier(MongoAutoConfiguration.MongoClientSettingsConfiguration::new).register(beanFactory);
  }

  public static void registerMongoClientSettingsConfiguration_mongoClientSettings(
      DefaultListableBeanFactory beanFactory) {
    BeanDefinitionRegistrar.of("mongoClientSettings", MongoClientSettings.class).withFactoryMethod(MongoAutoConfiguration.MongoClientSettingsConfiguration.class, "mongoClientSettings")
        .instanceSupplier(() -> beanFactory.getBean(MongoAutoConfiguration.MongoClientSettingsConfiguration.class).mongoClientSettings()).register(beanFactory);
  }

  public static void registerMongoClientSettingsConfiguration_mongoPropertiesCustomizer(
      DefaultListableBeanFactory beanFactory) {
    BeanDefinitionRegistrar.of("mongoPropertiesCustomizer", MongoPropertiesClientSettingsBuilderCustomizer.class).withFactoryMethod(MongoAutoConfiguration.MongoClientSettingsConfiguration.class, "mongoPropertiesCustomizer", MongoProperties.class, Environment.class)
        .instanceSupplier((instanceContext) -> instanceContext.create(beanFactory, (attributes) -> beanFactory.getBean(MongoAutoConfiguration.MongoClientSettingsConfiguration.class).mongoPropertiesCustomizer(attributes.get(0), attributes.get(1)))).register(beanFactory);
  }
}
