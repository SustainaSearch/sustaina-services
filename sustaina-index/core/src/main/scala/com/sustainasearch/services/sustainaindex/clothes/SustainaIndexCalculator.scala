package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.SustainaIndex
import com.sustainasearch.services.sustainaindex.country.{Country, CountryStorage}

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}
//import play.api

@Singleton
class SustainaIndexCalculator @Inject()(implicit ec: ExecutionContext) {

  def calculateSustainaIndex(input: SustainaIndexInput): Future[Try[SustainaIndex]] = {
    // TODO: fix weight parameters
	// ####### Version 2 values #######
	val MaterialWeight = 0.3F
	val ProcessWeight = 0.4F
	val QualityWeight = 0.1F // removed 0.1 here to have room for energy and crc
	val WorkingConditionWeight = 0.07F
	val PackagingWeight = 0.03F
	val RenewableEnergyWeight = 0.05F
	val CrcWeight = 0.05F
	
	var materialPoints = calculateMaterialPoints(input, MaterialWeight)
	var processPoints = calculateProcessPoints(input, ProcessWeight)
	var qualityPoints = calculateQualityPoints(input, QualityWeight)
	var workingConditionPoints = calculateWorkingConditionPoints(input, WorkingConditionWeight)
	var packagingPoints = calculatePackagingPoints(input, PackagingWeight)
	var renewableEnergyPoints = calculateRenewableEnergyPoints(input, RenewableEnergyWeight)
	var crcPoints = calculateCRCPoints(input, CrcWeight)

	// calculate and convert SI points to percentage
	val si = 0.01F * (materialPoints + renewableEnergyPoints + crcPoints + processPoints + qualityPoints + workingConditionPoints + packagingPoints)

    Future {
	  Success(SustainaIndex(si.toFloat, materialPoints.toFloat, processPoints.toFloat, qualityPoints.toFloat, workingConditionPoints.toFloat, packagingPoints.toFloat, renewableEnergyPoints.toFloat, crcPoints.toFloat))
    }
  }

  // ############# CRC ###############################################

  def calculateCRCPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = 0.0F
	var fibres_points = 0.0F
	var dyeing_points = 0.0F
	var spinning_points = 0.0F
	var weaving_points = 0.0F
	var couture_points = 0.0F
	
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		if (!brand.fibers_country.equals(CountryStorage.NotAvailable.toCountry)) {
			fibres_points = brand.fibers_country.crc
		}
		if (!brand.spinning_country.equals(CountryStorage.NotAvailable.toCountry)) {
			spinning_points = brand.spinning_country.crc
		}
		if (!brand.dyeing_country.equals(CountryStorage.NotAvailable.toCountry)) {
			dyeing_points = brand.dyeing_country.crc
		}
		if (!brand.weaving_country.equals(CountryStorage.NotAvailable.toCountry)) {
			weaving_points = brand.weaving_country.crc
		}
	}

	if (input.item.country.isDefined) {
		couture_points = input.item.country.get.crc
	}
	points = (couture_points + spinning_points + weaving_points + dyeing_points + fibres_points) /5.0F
	points.toFloat * weight
  }

  // ############# Renewable Energy ########################################

  def calculateRenewableEnergyPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = 0.0F
	var fibres_points = 0.0F
	var dyeing_points = 0.0F
	var spinning_points = 0.0F
	var weaving_points = 0.0F
	var couture_points = 0.0F

	if (input.item.country.isDefined) {
		couture_points = input.item.country.get.renewableEnergy 
	}
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		if (!brand.fibers_country.equals(CountryStorage.NotAvailable.toCountry) && brand.fibers_renewable_energy > -1) {
			fibres_points = brand.fibers_renewable_energy + (100.0F - brand.fibers_renewable_energy) * brand.fibers_country.renewableEnergy /100.0F
		}
		if (!brand.spinning_country.equals(CountryStorage.NotAvailable.toCountry) && brand.spinning_renewable_energy > -1) {
			spinning_points = brand.spinning_renewable_energy + (100.0F - brand.spinning_renewable_energy) * brand.spinning_country.renewableEnergy /100.0F
		}
		if (!brand.dyeing_country.equals(CountryStorage.NotAvailable.toCountry) && brand.dyeing_renewable_energy > -1) {
			dyeing_points = brand.dyeing_renewable_energy + (100.0F - brand.dyeing_renewable_energy) * brand.dyeing_country.renewableEnergy /100.0F
		}
		if (!brand.weaving_country.equals(CountryStorage.NotAvailable.toCountry) && brand.weaving_renewable_energy > -1) {
			weaving_points = brand.weaving_renewable_energy + (100.0F - brand.weaving_renewable_energy) * brand.weaving_country.renewableEnergy /100.0F
		}
		if (brand.couture_renewable_energy > -1 ) {
			if (input.item.country.isDefined) {
				couture_points = brand.couture_renewable_energy + (100.0F - brand.couture_renewable_energy) * input.item.country.get.renewableEnergy /100.0F
			} else {
				couture_points = brand.couture_renewable_energy 
			}
			
		}
	}
	
	//points = (couture_points + spinning_points + dyeing_points + weaving_points + fibres_points) / 5.0F
	points = (0.08F*couture_points + 0.05F*spinning_points + 0.82F*dyeing_points + 0.05F*weaving_points) 
	points.toFloat * weight
  }


  // ############# Material ##########################################
  
  def calculateMaterialPoints(input: SustainaIndexInput, weight: Float): Float = {
	var materialPoints = 0.0F
	for ( material <- input.item.materials ) {
		materialPoints += 0.01F* material.percent * material.materialType.group.score
	} 
	materialPoints * weight
  }
 
  // ############# Process ###########################################
  
  def calculateProcessPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = math.max(calculateCertificationProcessPoints(input), calculateBrandProcessPoints(input))
	points.toFloat * weight
  }

  def calculateCertificationProcessPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to certification data
	val PROCESS_MAX_INTERNAL_POINTS = 10.0F
	val PROCESS_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var chemical_use_restriction = 0
		for ( certification <- input.item.certifications ) {
			chemical_use_restriction = math.max(chemical_use_restriction, certification.chemical_use_restriction)
		}
		points = (chemical_use_restriction).toFloat * PROCESS_TOP_SCORE / PROCESS_MAX_INTERNAL_POINTS
	}
	points.toFloat
  }

  def calculateBrandProcessPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to brand data
	val BRAND_PROCESS_MAX_INTERNAL_POINTS = 100.0F
	val BRAND_PROCESS_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = (brand.no_perfluorinated_compounds_used 
				+ brand.no_added_biocides_for_antibacterial_purpose
				+ brand.no_pvc_with_ftalates_used
				+ brand.bleach_ban
				+ brand.chloride_treatment_ban
				+ brand.chloric_gas_bleach_ban
				+ brand.chemical_restriction_lists
				+ brand.water_purification
				+ brand.tier_traceability
				+ brand.circularity_points).toFloat * BRAND_PROCESS_TOP_SCORE / BRAND_PROCESS_MAX_INTERNAL_POINTS
	}
	points.toFloat
  }

  // ############# Quality ###########################################
  
  def calculateQualityPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = math.max(calculateCertificationQualityPoints(input), calculateBrandQualityPoints(input))
	points.toFloat * weight
  }
  
  def calculateCertificationQualityPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to certification data
	val QUALITY_MAX_INTERNAL_POINTS = 10.0F
	val QUALITY_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var requirements_on_wear_tear_and_color_percistance = 0
		for ( certification <- input.item.certifications ) {
			requirements_on_wear_tear_and_color_percistance = math.max(requirements_on_wear_tear_and_color_percistance, certification.requirements_on_wear_tear_and_color_percistance)
		}
		points = (requirements_on_wear_tear_and_color_percistance).toFloat * QUALITY_TOP_SCORE / QUALITY_MAX_INTERNAL_POINTS
	}
	points.toFloat
  }

  def calculateBrandQualityPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to certification data
	val BRAND_QUALITY_MAX_INTERNAL_POINTS = 20.0F
	val BRAND_QUALITY_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = (brand.requirements_on_quality + brand.quality_testing).toFloat * BRAND_QUALITY_TOP_SCORE / BRAND_QUALITY_MAX_INTERNAL_POINTS
	}
	points.toFloat
  }

  // ############# Working Conditions ################################
  
  def calculateWorkingConditionPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = math.max(calculateCertificationWorkingConditionPoints(input), calculateBrandWorkingConditionPoints(input))
	points.toFloat * weight
  }

  def calculateCertificationWorkingConditionPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to certification data
	val WORKING_CONDITIONS_MAX_INTERNAL_POINTS = 30.0F
	val WORKING_CONDITIONS_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var workers_rights = 0
		for ( certification <- input.item.certifications ) {
			workers_rights = math.max(workers_rights, certification.workers_rights)
		}
		points = (workers_rights).toFloat * 
					WORKING_CONDITIONS_TOP_SCORE / WORKING_CONDITIONS_MAX_INTERNAL_POINTS
	} 
	points.toFloat
  }

  def calculateBrandWorkingConditionPoints(input: SustainaIndexInput): Float = {
	// TODO move constants to certification data
	val BRAND_WORKING_CONDITIONS_MAX_INTERNAL_POINTS = 40.0F
	val BRAND_WORKING_CONDITIONS_TOP_SCORE = 100.0F

	var points = 0.0F
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = (brand.no_sandblasting + brand.members_in_CRS_organisation + brand.minimum_wages + brand.safety_rules).toFloat * BRAND_WORKING_CONDITIONS_TOP_SCORE / BRAND_WORKING_CONDITIONS_MAX_INTERNAL_POINTS 
	}
	points.toFloat
  }

  // ############# Packaging #########################################
  
  def calculatePackagingPoints(input: SustainaIndexInput, weight: Float): Float = {
	var points = math.max(calculateCertificationPackagingPoints(input), calculateBrandPackagingPoints(input))
	points.toFloat * weight
  }

  def calculateCertificationPackagingPoints(input: SustainaIndexInput): Float = {
    // TODO move constants to certification data
	val PACKAGING_MAX_INTERNAL_POINTS = 10.0F
	val PACKAGING_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var requirements_on_packaging = 0
		for ( certification <- input.item.certifications ) {
			requirements_on_packaging = math.max(requirements_on_packaging, certification.requirements_on_packaging)
		}
		points = (requirements_on_packaging).toFloat * PACKAGING_TOP_SCORE / PACKAGING_MAX_INTERNAL_POINTS
	}
	points.toFloat
  }

  def calculateBrandPackagingPoints(input: SustainaIndexInput): Float = {
    // TODO move constants to brand data
	val BRAND_PACKAGING_MAX_INTERNAL_POINTS = 10.0F
	val BRAND_PACKAGING_TOP_SCORE = 100.0F

	var points = 0.0F
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = brand.recycled_packaging_percent.toFloat * BRAND_PACKAGING_TOP_SCORE / BRAND_PACKAGING_MAX_INTERNAL_POINTS 
	}
	points.toFloat
  } 

}
