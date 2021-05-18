package com.sustainasearch.services.sustainaindex.brand

import com.sustainasearch.services.sustainaindex.country.{Country}

case class Brand(id: Int,

				 // Processes
				 no_perfluorinated_compounds_used: Int, // yes = 5 , other = 0
				 no_added_biocides_for_antibacterial_purpose: Int, // yes = 5 , other = 0
				 no_pvc_with_ftalates_used: Int, // yes = 5 , other = 0
				 bleach_ban: Int, // yes = 5, other = 0, wool, cotton & natural fibers from cellulose
				 chloride_treatment_ban: Int, // yes = 5, other = 0, material wool
				 chloric_gas_bleach_ban: Int, // yes = 5, other = 0, cellulosa based textile mass
				 chemical_restriction_lists: Int, // Per Tier, (No points for tiers out of traceability)
				 water_purification: Int, // (dyeing , beredning, washing is possible in all tiers) max 10p (if no water-purification is neccessary) endast vikig inom (bomull) fiber - (dyeing & berdning 80%) resten av stegen max 20p // 5p per tier (verifikat), norden = 4p per tier. eu = 3p per tier, australien=2.5p per tier, asien=0p, africa = 0p, north america=2p south america= 0p, unknown = 0
				 tier_traceability: Int, // max 20p - 5p per tier
				 circularity_points: Int, // 20p max for second_hand clothes, 10p for remakes/upcycled clothes, 0p other
				 
				 //packaging
				 recycled_packaging_percent: Int, // Minimerar paketering - Hur pongstter vi, verifikat?

				 //quality
				 requirements_on_quality: Int, // Points?,  verifikat? 
				 quality_testing: Int, //

				 // working conditions
				 no_sandblasting: Int, // yes = 10, other = 0
				 //organic solvants
				 members_in_CRS_organisation: Int, // yes = 10, other = 0
				 minimum_wages: Int, // norden = 8p. eu = 5p australien=4p, N america=3p, S america=0p, asien=0p, afrika=0p, unknown=0p
				 safety_rules: Int, // norden = 8p. eu = 5p australien=4p, N america=3p, S america=0p, asien=0p, afrika=0p, unknown=0p

				 // Other
				 fibers_country: Country, // fiber, garnspinning stickning/vevnad, vatberedning/finish, couture
				 fibers_renewable_energy: Int,
				 dyeing_country: Country,
				 dyeing_renewable_energy: Int,
				 spinning_country: Country,
				 spinning_renewable_energy: Int,
				 weaving_country: Country,
				 weaving_renewable_energy: Int,
				 couture_renewable_energy: Int,
				 
				 )
