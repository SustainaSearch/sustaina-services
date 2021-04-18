package com.sustainasearch.services.sustainaindex.brand

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryBrandRepository @Inject()(implicit ec: ExecutionContext) extends BrandRepository {

  override def getBrand(id: Int): Future[Try[Brand]] = {
    Future {
      Try(BrandStorage(id)).map(_.toBrand)
    }
  }

}

private object BrandStorage extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            bleach_ban: Boolean, // wool, cotton & natural fibers from cellulose
				 chloride_treatment_ban: Boolean, // wool
				 mulesing_ban: Boolean, // wool
				 fibers_from_sustainable_forests: Boolean,
				 fibers_from_closed_systems: Boolean, // i.e. lyocell
				 chloric_gas_bleach_ban: Boolean, // cellulosa based textile mass
				 no_acrylic_fibers_in_product: Boolean,
				 no_perfluorinated_compounds_used: Boolean,
				 no_added_biocides_for_anibacterial_purpose: Boolean,
				 no_pvc_with_ftalates_used: Boolean,
				 no_sandblasting: Boolean,
				 recycled_packaging: Boolean,
				 members_in_CRS_organisation: Boolean
                          ) extends super.Val(id) {

    def toBrand: Brand = Brand(id, 
				 bleach_ban, 
				 chloride_treatment_ban,
				 mulesing_ban,
				 fibers_from_sustainable_forests,
				 fibers_from_closed_systems,
				 chloric_gas_bleach_ban,
				 no_acrylic_fibers_in_product,
				 no_perfluorinated_compounds_used,
				 no_added_biocides_for_anibacterial_purpose,
				 no_pvc_with_ftalates_used,
				 no_sandblasting,
				 recycled_packaging,
				 members_in_CRS_organisation)
  }

  implicit def valueToBrandVal(x: Value): BrandStorage.Val = x.asInstanceOf[Val]

  val TestBrand1 = Val(
  id = nextId,
				 bleach_ban = true, 
				 chloride_treatment_ban = true,
				 mulesing_ban = true,
				 fibers_from_sustainable_forests = true,
				 fibers_from_closed_systems = true,
				 chloric_gas_bleach_ban = true,
				 no_acrylic_fibers_in_product = true,
				 no_perfluorinated_compounds_used = true,
				 no_added_biocides_for_anibacterial_purpose = true,
				 no_pvc_with_ftalates_used = true,
				 no_sandblasting = true,
				 recycled_packaging = true,
				 members_in_CRS_organisation = true
  )

  val TestBrand2 = Val(
    id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = true
  )
  val LinnesAndWolf = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
  val ANewSweden = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
  val HM = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
  val HouseOfDagmar = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
  val VeroModa = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = false,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = false,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = false,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
  val TheWiman = Val(
  id = nextId,
				 bleach_ban = false, 
				 chloride_treatment_ban = false,
				 mulesing_ban = true,
				 fibers_from_sustainable_forests = false,
				 fibers_from_closed_systems = true,
				 chloric_gas_bleach_ban = false,
				 no_acrylic_fibers_in_product = false,
				 no_perfluorinated_compounds_used = false,
				 no_added_biocides_for_anibacterial_purpose = false,
				 no_pvc_with_ftalates_used = false,
				 no_sandblasting = true,
				 recycled_packaging = false,
				 members_in_CRS_organisation = false
  )
}