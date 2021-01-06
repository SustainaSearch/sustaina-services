package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.SustainaIndex
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class SustainaIndexCalculator @Inject()(implicit ec: ExecutionContext) {

  def calculateSustainaIndex(input: SustainaIndexInput): Future[Try[SustainaIndex]] = {
    // TODO: fix weight parameters
	val MaterialWeight = 0.4F
	val CertificationWeight = 0.3F
	val RenewableEnergyWeight = 0.2F
	val CrcWeight = 0.1F
	
	var materialPoints = calculateMaterialPoints(input, MaterialWeight)
	var certificationPoints = calculateCertificationPoints(input, CertificationWeight)
	
	var renewableEnergyPoints = 0.0
	var crcPoints = 0.0
	if (input.item.country.isDefined) {
		crcPoints             = input.item.country.get.crc * CrcWeight
		renewableEnergyPoints = input.item.country.get.renewableEnergy * RenewableEnergyWeight
	}

	// calculate and convert SI points to percentage
	val si = 0.01F * ( materialPoints + certificationPoints + renewableEnergyPoints + crcPoints)

    Future {
	  Success(SustainaIndex(si.toFloat))
    }
  }

  def calculateMaterialPoints(input: SustainaIndexInput, weight: Float): Float = {
	var materialPoints = 0.0F
	for ( material <- input.item.materials ) {
		materialPoints += 0.01F* material.percent * material.materialType.group.score
	} 
	materialPoints * weight
  }

  def calculateCertificationPoints(input: SustainaIndexInput, weight: Float): Float = {
  
	// TODO move constants to certification data
	val CERTIFICATION_MAX_INTERNAL_POINTS = 200.0F
	val CERTIFICATION_TOP_SCORE = 100.0F
	
	var ecological_farming = 0
	var pesitcides_ban = 0
    var chemical_fertilizer_ban = 0
    var rules_for_water_use_and_protection = 0
    var gmo_crops_ban = 0
    var desaturation_ban = 0
    var package_material_requirements = 0
	var health_and_environmental_toxins_use_ban = 0
    var control_of_chemical_use = 0
    var chemical_remnants_banned_in_final_product = 0
    var requirements_on_wear_tear_and_color_percistance = 0
	var follows_ilo_conventions = 0
    var rules_on_workplace_saftey = 0
    var guarantees_minimum_wage = 0

	for ( certification <- input.item.certifications ) {
		ecological_farming = math.max(ecological_farming, certification.ecological_farming)
		pesitcides_ban = math.max(pesitcides_ban, certification.pesitcides_ban)
		chemical_fertilizer_ban = math.max(chemical_fertilizer_ban, certification.chemical_fertilizer_ban)
		rules_for_water_use_and_protection = math.max(rules_for_water_use_and_protection, certification.rules_for_water_use_and_protection)
		gmo_crops_ban = math.max(gmo_crops_ban, certification.gmo_crops_ban)
		desaturation_ban = math.max(desaturation_ban, certification.desaturation_ban)
		package_material_requirements = math.max(package_material_requirements, certification.package_material_requirements)
		health_and_environmental_toxins_use_ban = math.max(health_and_environmental_toxins_use_ban, certification.health_and_environmental_toxins_use_ban)
		control_of_chemical_use = math.max(control_of_chemical_use, certification.control_of_chemical_use)
		chemical_remnants_banned_in_final_product = math.max(chemical_remnants_banned_in_final_product, certification.chemical_remnants_banned_in_final_product)
		requirements_on_wear_tear_and_color_percistance = math.max(requirements_on_wear_tear_and_color_percistance, certification.requirements_on_wear_tear_and_color_percistance)
		follows_ilo_conventions = math.max(follows_ilo_conventions, certification.follows_ilo_conventions)
		rules_on_workplace_saftey = math.max(rules_on_workplace_saftey, certification.rules_on_workplace_saftey)
		guarantees_minimum_wage = math.max(guarantees_minimum_wage, certification.guarantees_minimum_wage)
	} 
	
	var certificationPoints = (ecological_farming +
	pesitcides_ban +
    chemical_fertilizer_ban +
    rules_for_water_use_and_protection +
    gmo_crops_ban +
    desaturation_ban +
    package_material_requirements +
	health_and_environmental_toxins_use_ban +
    control_of_chemical_use +
    chemical_remnants_banned_in_final_product +
    requirements_on_wear_tear_and_color_percistance +
	follows_ilo_conventions +
    rules_on_workplace_saftey +
    guarantees_minimum_wage).toFloat * CERTIFICATION_TOP_SCORE / CERTIFICATION_MAX_INTERNAL_POINTS
  
	certificationPoints.toFloat * weight
  }
}
