package com.sustainasearch.services.sustainaindex.certification

case class Certification(id: Int,
						// process
						chemical_use_restriction: Int,
						//quality
						requirements_on_wear_tear_and_color_percistance: Int,
						// workers rights
						workers_rights: Int,
						// packaging
						requirements_on_packaging: Int)
