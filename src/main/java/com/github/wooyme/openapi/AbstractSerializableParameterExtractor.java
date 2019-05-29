package com.github.wooyme.openapi;


import io.swagger.v3.oas.models.parameters.Parameter;
import io.vertx.core.MultiMap;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractSerializableParameterExtractor {
  public Object extract(String name, Parameter parameter, MultiMap params) {
    if (!params.contains(name)) {
      if (parameter.getRequired()) {
        throw new IllegalArgumentException("Missing required parameter: " + name);
      } else if (parameter.getSchema().getDefault()!=null){
        return parameter.getSchema().getDefault();
      } else {
        return null;
      }
    }

    if ((parameter.getAllowEmptyValue() == null
      || !parameter.getAllowEmptyValue())
      && StringUtils.isEmpty(params.get(name))) {
      throw new IllegalArgumentException(
        "Empty value is not authorized for parameter: " + name);
    }

    if ("array".equals(parameter.getSchema().getType())) {
      if ("multi".equals(parameter.getSchema().getFormat())) {
        return params.getAll(name);
      } else {
        List<String> resultParams = this.splitArrayParam(parameter,
          params.get(name));
        if (resultParams != null) {
          return resultParams;
        }
      }
    }
    return params.get(name);
  }

  private List<String> splitArrayParam(Parameter param, String paramAsString) {
    String regex = getArrayRegex(param);
    try {
      return Arrays.asList(URLDecoder.decode(paramAsString, "UTF-8").split(regex));
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

  private String getArrayRegex(Parameter param) {
    String regex;
    if (param.getSchema().getFormat() == null) {
      regex = Pattern.quote(",");
    } else {
      switch (param.getSchema().getFormat()) {
        case "ssv":
          regex = Pattern.quote(" ");
          break;
        case "csv":
          regex = Pattern.quote(",");
          break;
        case "tsv":
          regex = Pattern.quote("\t");
          break;
        case "pipes":
          regex = Pattern.quote("|");
          break;
        default:
          regex = Pattern.quote(",");
          break;
      }
    }
    return regex;
  }
}
