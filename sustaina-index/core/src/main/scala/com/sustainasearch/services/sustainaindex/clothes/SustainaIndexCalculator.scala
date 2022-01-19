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
	
	//Todo: move weight constants
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
	val PROCESS_MAX_INTERNAL_POINTS = 100.0F
	val PROCESS_TOP_SCORE = 100.0F
	
	var points = 0.0F

	var cert_no_perfluorinated_compounds = 0.0F
	var cert_no_added_biocides = 0.0F
	var cert_no_pvc_with_ftalates = 0.0F
	var cert_no_bleach = 0.0F
	var cert_no_chloride_treatment = 0.0F
	var cert_no_chloric_gas_bleach = 0.0F
	var cert_chemical_restriction_lists = 0.0F
	var cert_water_purification = 0.0F
	var cert_tier_traceability = 0.0F
	var cert_circularity_points = 0.0F

	var brand_no_perfluorinated_compounds = 0.0F
	var brand_no_added_biocides = 0.0F
	var brand_no_pvc_with_ftalates = 0.0F
	var brand_no_bleach = 0.0F
	var brand_no_chloride_treatment = 0.0F
	var brand_no_chloric_gas_bleach = 0.0F
	var brand_chemical_restriction_lists = 0.0F
	var brand_water_purification = 0.0F
	var brand_tier_traceability = 0.0F
	var brand_circularity_points = 0.0F

	// get certification points
	if ( input.item.certifications.length > 0 ) {
		for ( certification <- input.item.certifications ) {
			cert_no_perfluorinated_compounds = math.max(cert_no_perfluorinated_compounds, certification.no_perfluorinated_compounds)
			cert_no_added_biocides           = math.max(cert_no_added_biocides,           certification.no_added_biocides)
			cert_no_pvc_with_ftalates        = math.max(cert_no_pvc_with_ftalates,        certification.no_pvc_with_ftalates)
			cert_no_chloride_treatment       = math.max(cert_no_chloride_treatment,       certification.no_chloride_treatment)
			cert_no_chloric_gas_bleach       = math.max(cert_no_chloric_gas_bleach,       certification.no_chloric_gas_bleach)
			cert_no_bleach                   = math.max(cert_no_bleach,                   certification.no_bleach)
			cert_chemical_restriction_lists  = math.max(cert_chemical_restriction_lists,  certification.chemical_restriction_lists)
			cert_water_purification          = math.max(cert_water_purification,          certification.water_purification)
			cert_tier_traceability           = math.max(cert_tier_traceability,           certification.tier_traceability)
			cert_circularity_points          = math.max(cert_circularity_points,          certification.circularity_points)
		}
	}

	// get brand points
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		brand_no_perfluorinated_compounds = brand.no_perfluorinated_compounds 
		brand_no_added_biocides           = brand.no_added_biocides
		brand_no_pvc_with_ftalates        = brand.no_pvc_with_ftalates
		brand_no_chloride_treatment       = brand.no_chloride_treatment
		brand_no_chloric_gas_bleach       = brand.no_chloric_gas_bleach
		brand_no_bleach                   = brand.no_bleach
		brand_chemical_restriction_lists  = brand.chemical_restriction_lists
		brand_water_purification          = brand.water_purification
		brand_tier_traceability           = brand.tier_traceability
		brand_circularity_points          = brand.circularity_points
	}

	// calculate points, use max from brand/certification in each category
	points = (math.max(brand_no_perfluorinated_compounds, cert_no_perfluorinated_compounds)
			+ math.max(brand_no_added_biocides,           cert_no_added_biocides)
			+ math.max(brand_no_pvc_with_ftalates,        cert_no_pvc_with_ftalates)
			+ math.max(brand_no_chloride_treatment,       cert_no_chloride_treatment)
			+ math.max(brand_no_chloric_gas_bleach,       cert_no_chloric_gas_bleach)
			+ math.max(brand_no_bleach,                   cert_no_bleach)
			+ math.max(brand_chemical_restriction_lists,  cert_chemical_restriction_lists)
			+ math.max(brand_water_purification,          cert_water_purification)
			+ math.max(brand_tier_traceability,           cert_tier_traceability)
			+ math.max(brand_circularity_points,          cert_circularity_points)
			).toFloat * PROCESS_TOP_SCORE / PROCESS_MAX_INTERNAL_POINTS

	points.toFloat * weight
  }

  // ############# Quality ###########################################
  
  def calculateQualityPoints(input: SustainaIndexInput, weight: Float): Float = {
	val QUALITY_MAX_INTERNAL_POINTS = 20.0F
	val QUALITY_TOP_SCORE = 100.0F
	
	var points = 0.0F

	var cert_requirements_on_quality = 0.0F
	var cert_requirements_quality_testing = 0.0F

	var brand_requirements_on_quality = 0.0F
	var brand_quality_testing = 0.0F

	// get certification points
	if ( input.item.certifications.length > 0 ) {
		for ( certification <- input.item.certifications ) {
			cert_requirements_on_quality      = math.max(cert_requirements_on_quality,      certification.requirements_on_quality)
			cert_requirements_quality_testing = math.max(cert_requirements_quality_testing, certification.requirements_quality_testing)
		}
	}

	// get brand points
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		brand_requirements_on_quality = brand.requirements_on_quality
		brand_quality_testing         = brand.quality_testing
	}

	// calculate points, use max from brand/certification in each category
	points = (math.max(brand_requirements_on_quality, cert_requirements_on_quality)
			+ math.max(brand_quality_testing,         cert_requirements_quality_testing)
			 ).toFloat * QUALITY_TOP_SCORE / QUALITY_MAX_INTERNAL_POINTS
	
	points.toFloat * weight
  }

  // ############# Working Conditions ################################
  
  def calculateWorkingConditionPoints(input: SustainaIndexInput, weight: Float): Float = {
	
	val WORKING_CONDITIONS_MAX_INTERNAL_POINTS = 40.0F
	val WORKING_CONDITIONS_TOP_SCORE = 100.0F

	var cert_no_sandblasting = 0.0F
	var cert_CRS_membership = 0.0F
	var cert_minimum_wages = 0.0F
	var cert_safety_rules = 0.0F

	var brand_no_sandblasting = 0.0F
	var brand_CRS_membership = 0.0F
	var brand_minimum_wages = 0.0F
	var brand_safety_rules = 0.0F

	var points = 0.0F
	
	// get certification points
	if ( input.item.certifications.length > 0 ) {
		for ( certification <- input.item.certifications ) {
			cert_no_sandblasting = math.max(cert_no_sandblasting, certification.no_sandblasting)
			cert_CRS_membership  = math.max(cert_CRS_membership, certification.CRS_membership)
			cert_minimum_wages   = math.max(cert_minimum_wages, certification.minimum_wages)
			cert_safety_rules    = math.max(cert_safety_rules, certification.safety_rules)
		}
	}

	// get brand points
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		brand_no_sandblasting = brand.no_sandblasting
		brand_CRS_membership  = brand.CRS_membership
		brand_minimum_wages   = brand.minimum_wages
		brand_safety_rules    = brand.safety_rules
	}

	points = (math.max(cert_no_sandblasting, brand_no_sandblasting) + 
			  math.max(cert_CRS_membership,  brand_CRS_membership) + 
			  math.max(cert_minimum_wages,   brand_minimum_wages) + 
			  math.max(cert_safety_rules,    brand_safety_rules)
			 ).toFloat * WORKING_CONDITIONS_TOP_SCORE / WORKING_CONDITIONS_MAX_INTERNAL_POINTS 
	

	points.toFloat * weight
  }

  // ############# Packaging #########################################
  
  def calculatePackagingPoints(input: SustainaIndexInput, weight: Float): Float = {
    // TODO move constants
	val PACKAGING_MAX_INTERNAL_POINTS = 10.0F
	val PACKAGING_TOP_SCORE = 100.0F

	var cert_packaging_points = 0.0F
	var brand_packaging_points = 0.0F

	var points = 0.0F

	// get certification points
	if ( input.item.certifications.length > 0 ) {
		for ( certification <- input.item.certifications ) {
			cert_packaging_points = math.max(cert_packaging_points, certification.packaging_points)
		}
	}

	// get brand points
	if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		brand_packaging_points = brand.packaging_points
	}

	points = (math.max(cert_packaging_points, brand_packaging_points)).toFloat * PACKAGING_TOP_SCORE / PACKAGING_MAX_INTERNAL_POINTS 
	points.toFloat * weight
  }

}
