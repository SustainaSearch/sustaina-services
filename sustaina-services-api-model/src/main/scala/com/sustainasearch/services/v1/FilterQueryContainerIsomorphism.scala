package com.sustainasearch.services.v1

import com.sustainasearch.searchengine.{BooleanFilterQuery, FilterQuery, RangeFilterQuery, TextFilterQuery}
import com.sustainasearch.services.catalog.products.FilterQueryContainer
import scalaz.Isomorphism.<=>

object FilterQueryContainerIsomorphism {

  val filterQueryContainer = new (FilterQueryContainer <=> FilterQueryContainerApiModel) {
    override def to: FilterQueryContainer => FilterQueryContainerApiModel = { query =>
      FilterQueryContainerApiModel(
        names = query.names.map(NameIsomorphism.name.to),
        filterQuery = filterQuery.to(query.filterQuery)
      )
    }

    override def from: FilterQueryContainerApiModel => FilterQueryContainer = { query =>
      FilterQueryContainer(
        names = query.names.map(NameIsomorphism.name.from),
        filterQuery = filterQuery.from(query.filterQuery)
      )
    }
  }

  private val filterQuery = new (FilterQuery <=> FilterQueryApiModel) {
    override def to: FilterQuery => FilterQueryApiModel = {
      case fq: TextFilterQuery =>
        FilterQueryApiModel(
          text = Some(
            TextFilterQueryApiModel(
              fieldName = fq.fieldName,
              fieldValue = fq.fieldValue.toString
            )
          ),
          boolean = None,
          range = None
        )
      case fq: BooleanFilterQuery =>
        FilterQueryApiModel(
          text = None,
          boolean = Some(
            BooleanFilterQueryApiModel(
              fieldName = fq.fieldName,
              fieldValue = fq.fieldValue
            )
          ),
          range = None
        )
      case fq: RangeFilterQuery =>
        FilterQueryApiModel(
          text = None,
          boolean = None,
          range = Some(
            RangeFilterQueryApiModel(
              fieldName = fq.fieldName,
              from = fq.from,
              to = fq.to
            )
          )
        )
    }

    override def from: FilterQueryApiModel => FilterQuery = {
      case FilterQueryApiModel(Some(genericFilterQuery), None, None) => TextFilterQuery(
        fieldName = genericFilterQuery.fieldName,
        fieldValue = genericFilterQuery.fieldValue
      )
      case FilterQueryApiModel(None, Some(booleanFilterQuery), None) => BooleanFilterQuery(
        fieldName = booleanFilterQuery.fieldName,
        fieldValue = booleanFilterQuery.fieldValue
      )
      case FilterQueryApiModel(None, None, Some(rangeFilterQuery)) => RangeFilterQuery(
        fieldName = rangeFilterQuery.fieldName,
        from = rangeFilterQuery.from,
        to = rangeFilterQuery.to
      )
      case anythingElse => throw new IllegalArgumentException(s"'$anythingElse' is not a supported FilterQuery")
    }

  }
}
