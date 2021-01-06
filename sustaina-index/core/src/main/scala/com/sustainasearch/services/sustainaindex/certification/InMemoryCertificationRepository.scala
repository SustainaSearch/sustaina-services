package com.sustainasearch.services.sustainaindex.certification

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryCertificationRepository @Inject()(implicit ec: ExecutionContext) extends CertificationRepository {

  override def getCertification(id: Int): Future[Try[Certification]] = {
    Future {
      Try(CertificationStorage(id)).map(_.toCertification)
    }
  }

}

private object CertificationStorage extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            // environmental
						ecological_farming: Int,
						pesitcides_ban: Int,
						chemical_fertilizer_ban: Int,
						rules_for_water_use_and_protection: Int,
						gmo_crops_ban: Int,
						desaturation_ban: Int, //blekning
						package_material_requirements: Int,
						// health
						health_and_environmental_toxins_use_ban: Int,
						control_of_chemical_use: Int,
						chemical_remnants_banned_in_final_product: Int,
						//quality
						requirements_on_wear_tear_and_color_percistance: Int,
						// workers rights
						follows_ilo_conventions: Int,
						rules_on_workplace_saftey: Int,
						guarantees_minimum_wage: Int
                          ) extends super.Val(id) {

    def toCertification: Certification = Certification(
		id = id,
		ecological_farming = ecological_farming,
		pesitcides_ban = pesitcides_ban,
		chemical_fertilizer_ban = chemical_fertilizer_ban,
		rules_for_water_use_and_protection = rules_for_water_use_and_protection,
		gmo_crops_ban = gmo_crops_ban,
		desaturation_ban = desaturation_ban,
		package_material_requirements = package_material_requirements,
		health_and_environmental_toxins_use_ban = health_and_environmental_toxins_use_ban,
		control_of_chemical_use = control_of_chemical_use,
		chemical_remnants_banned_in_final_product = chemical_remnants_banned_in_final_product,
		requirements_on_wear_tear_and_color_percistance = requirements_on_wear_tear_and_color_percistance,
		follows_ilo_conventions = follows_ilo_conventions,
		rules_on_workplace_saftey = rules_on_workplace_saftey,
		guarantees_minimum_wage = guarantees_minimum_wage
    )

  }

  implicit def valueToCertificationVal(x: Value): CertificationStorage.Val = x.asInstanceOf[Val]

  val BlueSign    = Val(nextId,      10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10) // 140 TODO not evaluated properly (21 SI points with weight 0.3)
  val EUEcoLabel  = Val(nextId,       5,  5,  0, 10, 10,  5,  0,  5, 10,  5, 10,  5,  5,  0) // 75 (11,25 SI points with weight 0.3)
  val BraMiljoval = Val(nextId,      10,  5,  5, 10, 10,  5, 10,  5, 10, 10, 10, 10, 10, 10) // 120 (18 SI points with weight 0.3)
  val GOTS        = Val(nextId,      10, 10, 10, 10, 10,  5, 10,  5, 10, 10, 10, 10, 10, 10) // 130 (19,5 SI points with weight 0.3)
  val OekoTex100  = Val(nextId,       0,  0,  0,  0,  0,  0,  5,  5,  5, 10, 10,  0,  0,  0) // 35 (5,25 SI points with weight 0.3)
  val OekoTexStep = Val(nextId,       5,  5,  0, 10,  0,  5,  5, 10,  5, 10, 10, 10, 10, 10)
  val Svanen      = Val(nextId,      10,  5, 10, 10,  0,  5,  5,  5, 10, 10, 10, 10, 10,  0)

}