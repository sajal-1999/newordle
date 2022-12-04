package com.newordle.newordle.controllers;

import com.newordle.newordle.services.NewordleService;
import org.springframework.aot.beans.factory.BeanDefinitionRegistrar;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public final class NewordleApplicationTestsContextInitializer {
  public static void registerController(DefaultListableBeanFactory beanFactory) {
    BeanDefinitionRegistrar.of("controller", Controller.class)
        .instanceSupplier((instanceContext) -> {
          Controller bean = new Controller();
          instanceContext.field("service", NewordleService.class)
              .invoke(beanFactory, (attributes) -> bean.service = attributes.get(0));
                  return bean;
                }).register(beanFactory);
          }
        }
