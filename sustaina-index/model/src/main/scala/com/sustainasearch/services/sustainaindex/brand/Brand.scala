package com.sustainasearch.services.sustainaindex.brand

case class Brand(id: Int,
				 //Fibers
                 bleach_ban: Boolean, // wool, cotton & natural fibers from cellulose
				 chloride_treatment_ban: Boolean, // wool
				 mulesing_ban: Boolean, // wool
				 //vicose & biobased fibers
				 fibers_from_sustainable_forests: Boolean,
				 fibers_from_closed_systems: Boolean, // i.e. lyocell
				 chloric_gas_bleach_ban: Boolean, // cellulosa based textile mass
				 //all fibers
				 no_acrylic_fibers_in_product: Boolean,

				 // Processes
				 no_perfluorinated_compounds_used: Boolean,
				 no_added_biocides_for_anibacterial_purpose: Boolean,
				 no_pvc_with_ftalates_used: Boolean,
				 no_sandblasting: Boolean,
				 
				 //packaging
				 recycled_packaging: Boolean,

				 //Process and working conditions?
				 members_in_CRS_organisation: Boolean

				 //TODO quality questions

				 // TODO working conditions?
				 )