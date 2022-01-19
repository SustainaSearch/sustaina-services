package com.sustainasearch.services.sustainaindex.certification

case class Certification(id: Int,
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

				 // working conditions
				 no_sandblasting: Int, // max 10p
				 CRS_membership: Int, // max 10p
				 minimum_wages: Int, // max 10p
				 safety_rules: Int, // max 10p
)
