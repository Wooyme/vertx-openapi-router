package com.github.wooyme.openapi;

import io.swagger.v3.oas.models.parameters.RequestBody;
import io.vertx.core.json.DecodeException;
import io.vertx.ext.web.RoutingContext;

public class BodyParameterExtractor  {
  public Object extract(RequestBody parameter, RoutingContext context) {
    if ("".equals(context.getBodyAsString())) {
      if (parameter.getRequired())
        throw new IllegalArgumentException("Missing body");
      else
        return null;
    }

    try {
      return context.getBodyAsJson();
    } catch (DecodeException e) {
      return context.getBodyAsString();
    }
  }
}
