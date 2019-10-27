package com.sustainasearch.dynamodb

case class TableName(baseName: String, maybeTablePrefix: Option[TablePrefix] = None) {

  def withTablePrefix(tablePrefix: TablePrefix): TableName = copy(maybeTablePrefix = Some(tablePrefix))

  def value: String = maybeTablePrefix.fold(baseName)(tablePrefix => s"${tablePrefix.value}$baseName")
}
