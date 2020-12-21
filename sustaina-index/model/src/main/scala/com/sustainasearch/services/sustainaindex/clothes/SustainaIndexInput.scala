package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.Tenant

/**
  *
  * @param tenant Tenant
  * @param item   Item
  */
case class SustainaIndexInput(tenant: Tenant,
                              item: Item)