package com.github.wooyme.openapi;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.vertx.ext.web.RoutingContext;

public interface ParameterExtractor {
  Object extract(String name, Parameter parameter, RoutingContext context);

}
