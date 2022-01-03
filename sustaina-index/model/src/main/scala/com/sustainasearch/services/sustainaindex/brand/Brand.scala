package com.sustainasearch.services.sustainaindex.brand

import com.sustainasearch.services.sustainaindex.country.{Country}

case class Brand(id: Int,

				 // Processes
				 no_perfluorinated_compounds: Int, // max 5p, yes = 5 , other = 0
				 no_added_biocides: Int, // max 5p, yes = 5 , other = 0
				 no_pvc_with_ftalates: Int, // max 5p, yes = 5 , other = 0
				 no_bleach: Int, // max 5p, yes = 5, other = 0, wool, cotton & natural fibers from cellulose
				 no_chloride_treatment: Int, // max 5p, yes = 5, other = 0, material wool
				 no_chloric_gas_bleach: Int, // max 5p, yes = 5, other = 0, cellulosa based textile mass
				 chemical_restriction_lists: Int, // max 10p (Per Tier, No points for tiers out of traceability)
				 water_purification: Int, // max 20p (dyeing , beredning, washing is possible in all tiers)  (if no water-purification is neccessary) endast vikig inom (bomull) fiber - (dyeing & berdning 80%) resten av stegen max 20p // 5p per tier (verifikat), norden = 4p per tier. eu = 3p per tier, australien=2.5p per tier, asien=0p, africa = 0p, north america=2p south america= 0p, unknown = 0
				 tier_traceability: Int, // max 20p - 5p per tier (ONLY POINTS IN BRAND)
				 circularity_points: Int, // 20p max for second_hand clothes, 10p for remakes/upcycled clothes, 0p other
				 
				 //packaging
				 packaging_points: Int, // Max 10p, Minimerar paketering - Hur pongstter vi, verifikat?

				 //quality
				 requirements_on_quality: Int, // max 10p ,  verifikat? 
				 quality_testing: Int, // max 10p

				 // working conditions - TODO? organic solvants
				 no_sandblasting: Int, // max 10p, yes = 10, other = 0, second hand = 10p, 
				 CRS_membership: Int, // max 10p, yes = 10, second hand = 10p, other = 0
				 minimum_wages: Int, // max 10p, second hand = 10p, norden = 8p. eu = 5p australien=4p, N america=3p, S america=0p, asien=0p, afrika=0p, unknown=0p
				 safety_rules: Int, // max 10p, second hand = 10p, norden = 8p. eu = 5p australien=4p, N america=3p, S america=0p, asien=0p, afrika=0p, unknown=0p

				 // Other
				 fibers_country: Country, // fiber, garnspinning stickning/vevnad, vatberedning/finish, couture
				 fibers_renewable_energy: Int, // -1 = no traceability, 0 = use country energy stats, e.g 30 = 30% renewable energy and 70% country stats
				 dyeing_country: Country,
				 dyeing_renewable_energy: Int,
				 spinning_country: Country,
				 spinning_renewable_energy: Int,
				 weaving_country: Country,
				 weaving_renewable_energy: Int,
				 couture_renewable_energy: Int,
				 
				 )
