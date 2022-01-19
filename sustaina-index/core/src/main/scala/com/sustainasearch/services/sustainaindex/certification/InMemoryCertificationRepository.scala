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

object CertificationStorage extends Enumeration {

  protected case class Val(
				 override val id: Int,
				 // Processes
				 no_perfluorinated_compounds: Int, // max 5p, yes = 5 , other = 0
				 no_added_biocides: Int, // max 5p, yes = 5 , other = 0
				 no_pvc_with_ftalates: Int, // max 5p, yes = 5 , other = 0
				 no_bleach: Int, // max 5p, yes = 5, other = 0, wool, cotton & natural fibers from cellulose
				 no_chloride_treatment: Int, // max 5p, yes = 5, other = 0, material wool
				 no_chloric_gas_bleach: Int, // max 5p, yes = 5, other = 0, cellulosa based textile mass
				 chemical_restriction_lists: Int, // max 10p 
				 water_purification: Int, // max 20p (dyeing , beredning, washing is possible in all tiers)  (if no water-purification is neccessary) endast vikig inom (bomull) fiber - (dyeing & berdning 80%) resten av stegen max 20p // 5p per tier (verifikat), norden = 4p per tier. eu = 3p per tier, australien=2.5p per tier, asien=0p, africa = 0p, north america=2p south america= 0p, unknown = 0
				 tier_traceability: Int, // max 20p - 5p per tier
				 circularity_points: Int, // 20p max for second_hand clothes, 10p for remakes/upcycled clothes, 0p other

				 //packaging
				 packaging_points: Int, // Max 10p, Minimerar paketering - Hur pongstter vi, verifikat?

				 //quality
				 requirements_on_quality: Int, // max 10p
				 requirements_quality_testing: Int, // max 10p

				 // working conditions (TODO organic solvants)
				 no_sandblasting: Int, // max 10p
				 CRS_membership: Int, // max 10p
				 minimum_wages: Int, // max 10p
				 safety_rules: Int, // max 10p
			) extends super.Val(id) {

    def toCertification: Certification = Certification(
		id = id,
				 // Processes
				 no_perfluorinated_compounds = no_perfluorinated_compounds,
				 no_added_biocides = no_added_biocides,
				 no_pvc_with_ftalates = no_pvc_with_ftalates,
				 no_bleach = no_bleach,
				 no_chloride_treatment = no_chloride_treatment, 
				 no_chloric_gas_bleach = no_chloric_gas_bleach,
				 chemical_restriction_lists = chemical_restriction_lists,
				 water_purification = water_purification,
				 tier_traceability = tier_traceability,
				 circularity_points = circularity_points,
				 
				 //packaging
				 packaging_points = packaging_points,

				 //quality
				 requirements_on_quality = requirements_on_quality,
				 requirements_quality_testing = requirements_quality_testing,

				 // working conditions
				 no_sandblasting = no_sandblasting,
				 CRS_membership = CRS_membership,
				 minimum_wages = minimum_wages,
				 safety_rules = safety_rules,
    )

  }

  implicit def valueToCertificationVal(x: Value): CertificationStorage.Val = x.asInstanceOf[Val]
  //                                [---    Process                      --][p ][Quality][working cond]
  val BlueSign    = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0,  0,  0,  0, 10, 10,  0)
  val EUEcoLabel  = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0, 10, 10,  0, 10, 10,  0)
  val BraMiljoval = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0, 10, 10,  0, 10, 10,  0)
  val GOTS        = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0, 10, 10,  0, 10, 10,  0)
  val OekoTex100  = Val(nextId,       0,  0,  0,  0,  0,  0,  5,  0, 20,  0,  0,  0,  0,  0, 10, 10,  0)
  val OekoTexStep = Val(nextId,       0,  0,  0,  0,  0,  0,  5,  0, 20,  0,  0,  0,  0,  0,  0,  0,  0)
  val Svanen      = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0, 10, 10,  0, 10, 10,  0)
  val MadeInGreen = Val(nextId,       0,  0,  0,  0,  0,  0, 10,  0, 20,  0,  0,  0,  0,  0, 10, 10,  0)
  val FairTrade   = Val(nextId,       0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 10, 10, 10, 10)

  // val BlueSign    = Val(nextId,      10,  0, 10, 0)
  // val EUEcoLabel  = Val(nextId,      10, 10, 10, 0)
  // val BraMiljoval = Val(nextId,      10, 10, 10, 0)
  // val GOTS        = Val(nextId,      10, 10, 10, 0)
  // val OekoTex100  = Val(nextId,       5,  0,  0, 0)
  // val OekoTexStep = Val(nextId,       5,  0, 10, 0)
  // val Svanen      = Val(nextId,      10, 10, 10, 0)
  // val MadeInGreen = Val(nextId,      10,  0, 10, 0)
  // val FairTrade   = Val(nextId,       0,  0, 10, 0)
}