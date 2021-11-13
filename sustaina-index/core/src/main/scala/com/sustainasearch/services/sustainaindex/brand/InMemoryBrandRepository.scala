package com.sustainasearch.services.sustainaindex.brand

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

import com.sustainasearch.services.sustainaindex.country.{Country, CountryStorage}

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

				 // Processes
				 no_perfluorinated_compounds_used: Int,
				 no_added_biocides_for_antibacterial_purpose: Int,
				 no_pvc_with_ftalates_used: Int,
				 bleach_ban: Int,
				 chloride_treatment_ban: Int,
				 chloric_gas_bleach_ban: Int,
				 chemical_restriction_lists: Int,
				 water_purification: Int,
				 tier_traceability: Int,
				 circularity_points: Int,

				 //packaging
				 recycled_packaging_percent: Int,

				 //quality
				 requirements_on_quality: Int,
				 quality_testing: Int,

				 // working conditions?
				 no_sandblasting: Int,
				 members_in_CRS_organisation: Int,
				 minimum_wages: Int,
				 safety_rules: Int,

				 // Other
				 fibers_country: Country,
				 fibers_renewable_energy: Int,
				 dyeing_country: Country,
				 dyeing_renewable_energy: Int,
				 spinning_country: Country,
				 spinning_renewable_energy: Int,
				 weaving_country: Country,
				 weaving_renewable_energy: Int,
				 couture_renewable_energy: Int,

  ) extends super.Val(id) {

    def toBrand: Brand = Brand(id, 
				 // Processes
				 no_perfluorinated_compounds_used,
				 no_added_biocides_for_antibacterial_purpose,
				 no_pvc_with_ftalates_used,
				 bleach_ban,
				 chloride_treatment_ban,
				 chloric_gas_bleach_ban,
				 chemical_restriction_lists,
				 water_purification,
				 tier_traceability,
				 circularity_points,

				 //packaging
				 recycled_packaging_percent,

				 //quality
				 requirements_on_quality,
				 quality_testing,

				 // working conditions?
				 no_sandblasting,
				 members_in_CRS_organisation,
				 minimum_wages,
				 safety_rules,

				 // Other
				 fibers_country,
				 fibers_renewable_energy,
				 dyeing_country,
				 dyeing_renewable_energy,
				 spinning_country,
				 spinning_renewable_energy,
				 weaving_country,
				 weaving_renewable_energy,
				 couture_renewable_energy,
    )
  }

  implicit def valueToBrandVal(x: Value): BrandStorage.Val = x.asInstanceOf[Val]

  val NoBrand = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 0,
				 no_added_biocides_for_antibacterial_purpose = 0,
				 no_pvc_with_ftalates_used = 0,
				 bleach_ban = 0,
				 chloride_treatment_ban = 0,
				 chloric_gas_bleach_ban = 0,
				 chemical_restriction_lists = 0,
				 water_purification = 0,
				 tier_traceability = 0,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 0,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 0,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 0,
				 safety_rules = 0,

				 // Other
				 fibers_country = CountryStorage.NotAvailable.toCountry,
				 fibers_renewable_energy = -1,
				 dyeing_country =  CountryStorage.NotAvailable.toCountry,
				 dyeing_renewable_energy = -1,
				 spinning_country = CountryStorage.NotAvailable.toCountry,
				 spinning_renewable_energy = -1,
				 weaving_country = CountryStorage.NotAvailable.toCountry,
				 weaving_renewable_energy = -1,
				 couture_renewable_energy = -1,

  )
  
  val PerfectTestBrand1 = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5,
				 no_added_biocides_for_antibacterial_purpose = 5,
				 no_pvc_with_ftalates_used = 5,
				 bleach_ban = 5,
				 chloride_treatment_ban = 5,
				 chloric_gas_bleach_ban = 5,
				 chemical_restriction_lists = 10,
				 water_purification = 20,
				 tier_traceability = 20,
				 circularity_points = 20,

				 //packaging
				 recycled_packaging_percent = 10,

				 //quality
				 requirements_on_quality = 10,
				 quality_testing = 10,

				 // working conditions?
				 no_sandblasting = 10,
				 members_in_CRS_organisation = 10,
				 minimum_wages = 10,
				 safety_rules = 10,

				 // Other
				 fibers_country = CountryStorage.PerfectScoreCountry.toCountry,
				 fibers_renewable_energy = 100,
				 dyeing_country =  CountryStorage.PerfectScoreCountry.toCountry,
				 dyeing_renewable_energy = 100,
				 spinning_country = CountryStorage.PerfectScoreCountry.toCountry,
				 spinning_renewable_energy = 100,
				 weaving_country = CountryStorage.PerfectScoreCountry.toCountry,
				 weaving_renewable_energy = 100,
				 couture_renewable_energy = 100,
  )
 
  val LinnesAndWolf = Val(
  				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 0,
				 no_added_biocides_for_antibacterial_purpose = 0,
				 no_pvc_with_ftalates_used = 0,
				 bleach_ban = 0,
				 chloride_treatment_ban = 0,
				 chloric_gas_bleach_ban = 0,
				 chemical_restriction_lists = 0,
				 water_purification = 0,
				 tier_traceability = 0,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 0,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 0,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 0,
				 safety_rules = 0,

				 // Other
				 fibers_country = CountryStorage.NotAvailable.toCountry,
				 fibers_renewable_energy = -1,
				 dyeing_country =  CountryStorage.NotAvailable.toCountry,
				 dyeing_renewable_energy = -1,
				 spinning_country = CountryStorage.NotAvailable.toCountry,
				 spinning_renewable_energy = -1,
				 weaving_country = CountryStorage.NotAvailable.toCountry,
				 weaving_renewable_energy = -1,
				 couture_renewable_energy = -1,

  )
 
  val ANewSweden = Val(
  				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5,
				 no_added_biocides_for_antibacterial_purpose = 5,
				 no_pvc_with_ftalates_used = 5,
				 bleach_ban = 5,
				 chloride_treatment_ban = 5,
				 chloric_gas_bleach_ban = 5,
				 chemical_restriction_lists = 0,
				 water_purification = 10,
				 tier_traceability = 20,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 5,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 10,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 10,
				 safety_rules = 10,

				 // Other
				 fibers_country = CountryStorage.Sweden.toCountry,
				 fibers_renewable_energy = 0,
				 dyeing_country =  CountryStorage.Sweden.toCountry,
				 dyeing_renewable_energy = 0,
				 spinning_country = CountryStorage.Italy.toCountry,
				 spinning_renewable_energy = 0,
				 weaving_country = CountryStorage.Sweden.toCountry,
				 weaving_renewable_energy = 0,
				 couture_renewable_energy = 0,
  )

  val HM = Val(
  				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 0,
				 no_added_biocides_for_antibacterial_purpose = 0,
				 no_pvc_with_ftalates_used = 0,
				 bleach_ban = 0,
				 chloride_treatment_ban = 0,
				 chloric_gas_bleach_ban = 0,
				 chemical_restriction_lists = 0,
				 water_purification = 0,
				 tier_traceability = 0,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 0,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 0,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 0,
				 safety_rules = 0,

				 // Other
				 fibers_country = CountryStorage.NotAvailable.toCountry,
				 fibers_renewable_energy = -1,
				 dyeing_country =  CountryStorage.NotAvailable.toCountry,
				 dyeing_renewable_energy = -1,
				 spinning_country = CountryStorage.NotAvailable.toCountry,
				 spinning_renewable_energy = -1,
				 weaving_country = CountryStorage.NotAvailable.toCountry,
				 weaving_renewable_energy = -1,
				 couture_renewable_energy = -1,

  )
  val HouseOfDagmar = Val(
  				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 0,
				 no_added_biocides_for_antibacterial_purpose = 0,
				 no_pvc_with_ftalates_used = 0,
				 bleach_ban = 0,
				 chloride_treatment_ban = 0,
				 chloric_gas_bleach_ban = 0,
				 chemical_restriction_lists = 0,
				 water_purification = 0,
				 tier_traceability = 0,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 0,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 0,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 0,
				 safety_rules = 0,

				 // Other
				 fibers_country = CountryStorage.NotAvailable.toCountry,
				 fibers_renewable_energy = -1,
				 dyeing_country =  CountryStorage.NotAvailable.toCountry,
				 dyeing_renewable_energy = -1,
				 spinning_country = CountryStorage.NotAvailable.toCountry,
				 spinning_renewable_energy = -1,
				 weaving_country = CountryStorage.NotAvailable.toCountry,
				 weaving_renewable_energy = -1,
				 couture_renewable_energy = -1,
		 )

  val VeroModa = Val(
  				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 0,
				 no_added_biocides_for_antibacterial_purpose = 0,
				 no_pvc_with_ftalates_used = 0,
				 bleach_ban = 0,
				 chloride_treatment_ban = 0,
				 chloric_gas_bleach_ban = 0,
				 chemical_restriction_lists = 0,
				 water_purification = 0,
				 tier_traceability = 0,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 0,

				 //quality
				 requirements_on_quality = 0,
				 quality_testing = 0,

				 // working conditions?
				 no_sandblasting = 0,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 0,
				 safety_rules = 0,

				 // Other
				 fibers_country = CountryStorage.NotAvailable.toCountry,
				 fibers_renewable_energy = -1,
				 dyeing_country =  CountryStorage.NotAvailable.toCountry,
				 dyeing_renewable_energy = -1,
				 spinning_country = CountryStorage.NotAvailable.toCountry,
				 spinning_renewable_energy = -1,
				 weaving_country = CountryStorage.NotAvailable.toCountry,
				 weaving_renewable_energy = -1,
				 couture_renewable_energy = -1,

  )

  val TheWiman_standard = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5,
				 no_added_biocides_for_antibacterial_purpose = 5,
				 no_pvc_with_ftalates_used = 5,
				 bleach_ban = 5,
				 chloride_treatment_ban = 5,
				 chloric_gas_bleach_ban = 5,
				 chemical_restriction_lists = 0,// Inger?
				 water_purification = 12, // 
				 tier_traceability = 20,
				 circularity_points = 0,

				 //packaging
				 recycled_packaging_percent = 5,

				 //quality
				 requirements_on_quality = 5,
				 quality_testing = 5,

				 // working conditions?
				 no_sandblasting = 10,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 5,
				 safety_rules = 5,

				 // Other
				 fibers_country = CountryStorage.Sweden.toCountry,
				 fibers_renewable_energy = 100,
				 dyeing_country =  CountryStorage.Portugal.toCountry,
				 dyeing_renewable_energy = 0,
				 spinning_country = CountryStorage.Austria.toCountry,
				 spinning_renewable_energy = 0,
				 weaving_country = CountryStorage.Portugal.toCountry,
				 weaving_renewable_energy = 0,
				 couture_renewable_energy = 30,

  )

  val TheWiman_upcycled = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5,
				 no_added_biocides_for_antibacterial_purpose = 5,
				 no_pvc_with_ftalates_used = 5,
				 bleach_ban = 5,
				 chloride_treatment_ban = 5,
				 chloric_gas_bleach_ban = 5,
				 chemical_restriction_lists = 10,
				 water_purification = 20,
				 tier_traceability = 20,
				 circularity_points = 10, 

				 //packaging
				 recycled_packaging_percent = 5,

				 //quality
				 requirements_on_quality = 5,
				 quality_testing = 5,

				 // working conditions?
				 no_sandblasting = 10,
				 members_in_CRS_organisation = 0,
				 minimum_wages = 8,
				 safety_rules = 8,

				 // Other
				 fibers_country = CountryStorage.PerfectScoreCountry.toCountry,
				 fibers_renewable_energy = 100,
				 dyeing_country =  CountryStorage.PerfectScoreCountry.toCountry,
				 dyeing_renewable_energy = 100,
				 spinning_country = CountryStorage.PerfectScoreCountry.toCountry,
				 spinning_renewable_energy = 100,
				 weaving_country = CountryStorage.PerfectScoreCountry.toCountry,
				 weaving_renewable_energy = 100,
				 couture_renewable_energy = 0,
  )

  val TheWiman_preloved = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5,
				 no_added_biocides_for_antibacterial_purpose = 5,
				 no_pvc_with_ftalates_used = 5,
				 bleach_ban = 5,
				 chloride_treatment_ban = 5,
				 chloric_gas_bleach_ban = 5,
				 chemical_restriction_lists = 10, 
				 water_purification = 20, 
				 tier_traceability = 20,
				 circularity_points = 20, 

				 //packaging
				 recycled_packaging_percent = 5,

				 //quality
				 requirements_on_quality = 5,
				 quality_testing = 5,

				 // working conditions?
				 no_sandblasting = 10,
				 members_in_CRS_organisation = 10,
				 minimum_wages = 10,
				 safety_rules = 10,

				 // Other
				 fibers_country = CountryStorage.PerfectScoreCountry.toCountry,
				 fibers_renewable_energy = 100,
				 dyeing_country =  CountryStorage.PerfectScoreCountry.toCountry,
				 dyeing_renewable_energy = 100,
				 spinning_country = CountryStorage.PerfectScoreCountry.toCountry,
				 spinning_renewable_energy = 100,
				 weaving_country = CountryStorage.PerfectScoreCountry.toCountry,
				 weaving_renewable_energy = 100,
				 couture_renewable_energy = 100,
  )

    val MalinWinberg = Val(
				id = nextId,

				// Processes
				 no_perfluorinated_compounds_used = 5, // no
				 no_added_biocides_for_antibacterial_purpose = 5, // no
				 no_pvc_with_ftalates_used = 5, // no
				 bleach_ban = 0, // Todo: no answer,  no question
				 chloride_treatment_ban = 5, // full pott pga GOTS certifiering av plagg
				 chloric_gas_bleach_ban = 5, // full pott pga GOTS certifiering av plagg
				 chemical_restriction_lists = 10, // full pott pga GOTS certifiering av plagg
				 water_purification = 20, // full pott pga GOTS certifiering av plagg
				 tier_traceability = 20, // Vet alla tiers via underproducent
				 circularity_points = 0, // Nyproduktion

				 //packaging
				 recycled_packaging_percent = 5,// Todo: chansat

				 //quality
				 requirements_on_quality = 10,// full pott pga GOTS certifiering av plagg
				 quality_testing = 10,// // full pott pga GOTS certifiering av plagg

				 // working conditions?
				 no_sandblasting = 10, // no
				 members_in_CRS_organisation = 10, // yes
				 minimum_wages = 10, // full pott pga GOTS certifiering av plagg
				 safety_rules = 10, // full pott pga GOTS certifiering av plagg

				 // Other
				 fibers_country = CountryStorage.India.toCountry, // unknown
				 fibers_renewable_energy = 0, // 0 = use 100% of country energy mix
				 dyeing_country =  CountryStorage.India.toCountry,
				 dyeing_renewable_energy = 0, // 0 = use 100% of country energy mix
				 spinning_country = CountryStorage.India.toCountry,
				 spinning_renewable_energy = 0, // 0 = use 100% of country energy mix
				 weaving_country = CountryStorage.Bangladesh.toCountry,
				 weaving_renewable_energy = 1,// 0 = use 100% of country energy mix
				 couture_renewable_energy = 1, // 0 = use 100% of country (in product data) energy mix 
  )

}
