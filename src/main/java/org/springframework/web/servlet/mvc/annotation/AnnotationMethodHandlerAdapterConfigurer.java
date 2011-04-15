package org.springframework.web.servlet.mvc.annotation;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.Map;

public class AnnotationMethodHandlerAdapterConfigurer {
  @Autowired
  private AnnotationMethodHandlerAdapter adapter;

  private WebBindingInitializer webBindingInitializer;
  private HttpMessageConverter<?>[] messageConverters;
  private PathMatcher pathMatcher;
  private UrlPathHelper urlPathHelper;
  private MethodNameResolver methodNameResolver;
  private WebArgumentResolver[] customArgumentResolvers;
  private ModelAndViewResolver[] customModelAndViewResolvers;

  private boolean replaceMessageConverters = false;

  public void init() {
    if (webBindingInitializer != null) {
      adapter.setWebBindingInitializer(webBindingInitializer);
    }

    if (messageConverters != null) {
      if (replaceMessageConverters) {
        adapter.setMessageConverters(messageConverters);
      } else {
        adapter.setMessageConverters(mergeMessageConverters());
      }
    }

    if (pathMatcher != null) {
      adapter.setPathMatcher(pathMatcher);
    }

    if (urlPathHelper != null) {
      adapter.setUrlPathHelper(urlPathHelper);
    }

    if (methodNameResolver != null) {
      adapter.setMethodNameResolver(methodNameResolver);
    }

    if (customArgumentResolvers != null) {
      adapter.setCustomArgumentResolvers(customArgumentResolvers);
    }

    if (customModelAndViewResolvers != null) {
      adapter.setCustomModelAndViewResolvers(customModelAndViewResolvers);
    }
  }

  private HttpMessageConverter<?>[] mergeMessageConverters() {
    return (HttpMessageConverter<?>[])
                  ArrayUtils.addAll(messageConverters, adapter.getMessageConverters());
  }

  public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
    this.webBindingInitializer = webBindingInitializer;
  }

  public void setPathMatcher(PathMatcher pathMatcher) {
    this.pathMatcher = pathMatcher;
  }

  public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
    this.urlPathHelper = urlPathHelper;
  }

  public void setMethodNameResolver(MethodNameResolver methodNameResolver) {
    this.methodNameResolver = methodNameResolver;
  }

  public void setCustomArgumentResolver(WebArgumentResolver argumentResolver) {
    this.customArgumentResolvers = new WebArgumentResolver[] {argumentResolver};
  }

  public void setCustomArgumentResolvers(WebArgumentResolver[] argumentResolvers) {
    this.customArgumentResolvers = argumentResolvers;
  }

  public void setCustomModelAndViewResolver(ModelAndViewResolver customModelAndViewResolver) {
    this.customModelAndViewResolvers = new ModelAndViewResolver[] {customModelAndViewResolver};
  }

  public void setCustomModelAndViewResolvers(ModelAndViewResolver[] customModelAndViewResolvers) {
    this.customModelAndViewResolvers = customModelAndViewResolvers;
  }

  public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
    this.messageConverters = messageConverters;
  }

  public void setReplaceMessageConverters(boolean replaceMessageConverters) {
    this.replaceMessageConverters = replaceMessageConverters;
  }
}