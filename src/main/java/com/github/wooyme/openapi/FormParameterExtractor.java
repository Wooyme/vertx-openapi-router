package com.github.wooyme.openapi;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

public class FormParameterExtractor extends AbstractSerializableParameterExtractor implements ParameterExtractor {
  @Override
  public Object extract(String name, Parameter parameter, RoutingContext context) {
    if ("file".equals(parameter.getSchema().getType())) {
      for (FileUpload file : context.fileUploads()) {
        if (file.name().equals(name)) {
          return file.uploadedFileName();
        }
      }
      if(parameter.getRequired())
        throw new IllegalArgumentException("Missing required parameter: " + name);
      return null;
    } else
      return this.extract(name, parameter, context.request().formAttributes());
  }
}
