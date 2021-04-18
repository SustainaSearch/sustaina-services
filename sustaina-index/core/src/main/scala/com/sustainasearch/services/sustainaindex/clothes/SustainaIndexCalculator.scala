package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.SustainaIndex
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
	val WorkingConditionWeight = 0.05F
	val PackagingWeight = 0.05F
	val RenewableEnergyWeight = 0.05F
	val CrcWeight = 0.05F
	
	var materialPoints = calculateMaterialPoints(input, MaterialWeight)
	var processPoints = calculateProcessPoints(input, ProcessWeight)
	var qualityPoints = calculateQualityPoints(input, QualityWeight)
	var workingConditionPoints = calculateWorkingConditionPoints(input, WorkingConditionWeight)
	var packagingPoints = calculatePackagingPoints(input, PackagingWeight)
	var renewableEnergyPoints = 0.0
	var crcPoints = 0.0

	if (input.item.country.isDefined) {
		crcPoints             = input.item.country.get.crc * CrcWeight
		renewableEnergyPoints = input.item.country.get.renewableEnergy * RenewableEnergyWeight
	}

	// calculate and convert SI points to percentage
	val si = 0.01F * (materialPoints + renewableEnergyPoints + crcPoints + processPoints + qualityPoints + workingConditionPoints + packagingPoints)

    Future {
	  Success(SustainaIndex(si.toFloat, materialPoints.toFloat, processPoints.toFloat, qualityPoints.toFloat, workingConditionPoints.toFloat, packagingPoints.toFloat, renewableEnergyPoints.toFloat, crcPoints.toFloat))
    }
  }

  def calculateMaterialPoints(input: SustainaIndexInput, weight: Float): Float = {
	var materialPoints = 0.0F
	for ( material <- input.item.materials ) {
		materialPoints += 0.01F* material.percent * material.materialType.group.score
	} 
	materialPoints * weight
  }
 
  def calculateProcessPoints(input: SustainaIndexInput, weight: Float): Float = {
	// TODO move constants to certification data
	val PROCESS_MAX_INTERNAL_POINTS = 10.0F
	val PROCESS_TOP_SCORE = 100.0F
	val BRAND_PROCESS_MAX_INTERNAL_POINTS = 30.0F
	val BRAND_PROCESS_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var chemical_use_restriction = 0
		for ( certification <- input.item.certifications ) {
			chemical_use_restriction = math.max(chemical_use_restriction, certification.chemical_use_restriction)
		}
		points = (chemical_use_restriction).toFloat * PROCESS_TOP_SCORE / PROCESS_MAX_INTERNAL_POINTS
	} else if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = ((if (brand.no_perfluorinated_compounds_used) 10 else 0) + (if (brand.no_added_biocides_for_anibacterial_purpose) 10 else 0) + (if (brand.no_pvc_with_ftalates_used) 10 else 0)).toFloat * BRAND_PROCESS_TOP_SCORE / BRAND_PROCESS_MAX_INTERNAL_POINTS
	}
	points.toFloat * weight
  }

  def calculateQualityPoints(input: SustainaIndexInput, weight: Float): Float = {
	// TODO move constants to certification data
	val QUALITY_MAX_INTERNAL_POINTS = 10.0F
	val QUALITY_TOP_SCORE = 100.0F
	val BRAND_QUALITY_MAX_INTERNAL_POINTS = 10.0F
	val BRAND_QUALITY_TOP_SCORE = 100.0F
	
	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var requirements_on_wear_tear_and_color_percistance = 0
		for ( certification <- input.item.certifications ) {
			requirements_on_wear_tear_and_color_percistance = math.max(requirements_on_wear_tear_and_color_percistance, certification.requirements_on_wear_tear_and_color_percistance)
		}
		points = (requirements_on_wear_tear_and_color_percistance).toFloat * QUALITY_TOP_SCORE / QUALITY_MAX_INTERNAL_POINTS
	} else if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = ((0)).toFloat * BRAND_QUALITY_TOP_SCORE / BRAND_QUALITY_MAX_INTERNAL_POINTS // TODO brand quality points question
	}
	
	points.toFloat * weight
  }

  def calculateWorkingConditionPoints(input: SustainaIndexInput, weight: Float): Float = {
	// TODO move constants to certification data
	val WORKING_CONDITIONS_MAX_INTERNAL_POINTS = 30.0F
	val WORKING_CONDITIONS_TOP_SCORE = 100.0F
	val BRAND_WORKING_CONDITIONS_MAX_INTERNAL_POINTS = 20.0F
	val BRAND_WORKING_CONDITIONS_TOP_SCORE = 100.0F

	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var workers_rights = 0
		for ( certification <- input.item.certifications ) {
			workers_rights = math.max(workers_rights, certification.workers_rights)
		}
		points = (workers_rights).toFloat * 
					WORKING_CONDITIONS_TOP_SCORE / WORKING_CONDITIONS_MAX_INTERNAL_POINTS
	} else if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = ((if (brand.no_sandblasting) 10 else 0) + (if (brand.members_in_CRS_organisation) 10 else 0)).toFloat * BRAND_WORKING_CONDITIONS_TOP_SCORE / BRAND_WORKING_CONDITIONS_MAX_INTERNAL_POINTS 
	}
	points.toFloat * weight
  }

  def calculatePackagingPoints(input: SustainaIndexInput, weight: Float): Float = {
    // TODO move constants to certification data
	val PACKAGING_MAX_INTERNAL_POINTS = 10.0F
	val PACKAGING_TOP_SCORE = 100.0F
	val BRAND_PACKAGING_MAX_INTERNAL_POINTS = 10.0F
	val BRAND_PACKAGING_TOP_SCORE = 100.0F

	var points = 0.0F
	if ( input.item.certifications.length > 0 ) {
		var requirements_on_packaging = 0
		for ( certification <- input.item.certifications ) {
			requirements_on_packaging = math.max(requirements_on_packaging, certification.requirements_on_packaging)
		}
		points = (requirements_on_packaging).toFloat * PACKAGING_TOP_SCORE / PACKAGING_MAX_INTERNAL_POINTS
	} else if (input.item.brand.isDefined) {
		var brand = input.item.brand.get
		points = (if (brand.recycled_packaging) 10 else 0).toFloat * BRAND_PACKAGING_TOP_SCORE / BRAND_PACKAGING_MAX_INTERNAL_POINTS 
	}
	points.toFloat * weight
  }

}
