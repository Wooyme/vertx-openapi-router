package com.github.wooyme.openapi;

import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.vertx.ext.web.RoutingContext;

public class HeaderParameterExtractor extends AbstractSerializableParameterExtractor implements ParameterExtractor {
  @Override
  public Object extract(String name, Parameter parameter, RoutingContext context) {
    HeaderParameter headerParam = (HeaderParameter) parameter;
    if ("array".equals(headerParam.getSchema().getType())) {
      return context.request().headers().getAll(name);
    }
    return this.extract(name, parameter, context.request().headers());
  }
}
