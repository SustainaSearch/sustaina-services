package com.sustainasearch.services.sustainaindex.certification

case class Certification(id: Int,
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
						guarantees_minimum_wage: Int)
