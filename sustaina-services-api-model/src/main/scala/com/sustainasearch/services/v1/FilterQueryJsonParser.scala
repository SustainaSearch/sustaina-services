package com.sustainasearch.services.v1

import com.sustainasearch.searchengine.{BooleanFilterQuery, FilterQuery, RangeFilterQuery, TextFilterQuery}
import play.api.libs.json.{JsError, JsSuccess, Json}

object FilterQueryJsonParser {

  def fromJson: String => FilterQuery = { query =>
    val normalizedQuery = query
      .replaceAll(" ", "")
      .replaceAll("\n", "")
    Json.parse(normalizedQuery).validate[FilterQueryApiModel] match {
      case s: JsSuccess[FilterQueryApiModel] => {
        s.get match {
          case FilterQueryApiModel(Some(text), None, None) => TextFilterQuery(
            fieldName = text.fieldName,
            fieldValue = text.fieldValue
          )
          case FilterQueryApiModel(None, Some(boolean), None) => BooleanFilterQuery(
            fieldName = boolean.fieldName,
            fieldValue = boolean.fieldValue
          )
          case FilterQueryApiModel(None, None, Some(range)) => RangeFilterQuery(
            fieldName = range.fieldName,
            from = range.from,
            to = range.to
          )
          case _ => throw new IllegalArgumentException(s"'$query' is not a supported FilterQuery")
        }
      }
      case e: JsError => throw new IllegalArgumentException(s"'$query' is not a supported FilterQuery: ${e.errors}")
    }
  }

}
