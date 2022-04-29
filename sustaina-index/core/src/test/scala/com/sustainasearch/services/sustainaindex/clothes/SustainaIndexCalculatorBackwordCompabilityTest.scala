package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.Tenant
import com.sustainasearch.services.sustainaindex.certification.{CertificationStorage, Certification}
import com.sustainasearch.services.sustainaindex.clothes.material.{Material, MaterialGroup, MaterialType, MaterialTypeStorage}
import com.sustainasearch.services.sustainaindex.country.{Country, CountryStorage}
import com.sustainasearch.services.sustainaindex.brand.{Brand, BrandStorage}
import com.sustainasearch.services.sustainaindex.SustainaIndex


import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import scala.util.{Success, Try}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SustainaIndexCalculatorBackwordCompatibilityTest extends WordSpec with Matchers with ScalaFutures {
  val underTest = new SustainaIndexCalculator()

  classOf[SustainaIndexCalculator].getSimpleName should {

    "calculate sustaina index for TheWiman-standard" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.Portugal.toCountry),
          certifications = Seq.empty,
          materials = Seq(Material(MaterialTypeStorage.Lyocell_FSC_PEFC.toMaterialType, 100.0F)),
          brand = Option(BrandStorage.TheWiman_standard.toBrand)
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
		0.643734872341156F,
		23.700000762939453F,
		24.80000114440918F,
		5F, 
		3.5F,
		1.5F,
		1.4634829759597778F,
		4.409999847412109F))
      }
    }

    "calculate sustaina index for TheWiman-upcycled" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.Sweden.toCountry),
          certifications = Seq.empty,
          materials = Seq(Material(MaterialTypeStorage.Lyocell_Upcycled_FSC_PEFC.toMaterialType, 100.0F)),
          brand = Option(BrandStorage.TheWiman_upcycled.toBrand)
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
            0.8471300601959229F,
            27.900001525878906F,
            36F,
            5F,
            4.550000190734863F,
            1.5F,
            4.813000202178955F,
            4.950000286102295F))
      }
    }

    "calculate sustaina index for TheWiman-preloved" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.PerfectScoreCountry.toCountry),
          certifications = Seq.empty,
          materials = Seq(Material(MaterialTypeStorage.SecondHand_YoungerThan3.toMaterialType, 100.0F)),
          brand = Option(BrandStorage.TheWiman_preloved.toBrand)
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
            0.9139999747276306F,
            27.900001525878906F,
            40F,
            5F,
            7F,
            1.5F,
            5F,
            5F))
      }
    }

    "calculate sustaina index for MalinWinberg Hoodie" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.Bangladesh.toCountry),
          certifications = Seq(CertificationStorage.Svanen.toCertification),
          materials = Seq(Material(MaterialTypeStorage.Cotton_GOTS.toMaterialType, 80.0F),
                          Material(MaterialTypeStorage.Polyester_Recycled.toMaterialType, 20.0F)),
          brand = Option(BrandStorage.MalinWinberg.toBrand)
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
                0.7016661167144775F,
                23.700000762939453F,
                26F,
                10F,
                7F,
                1.5F,
                0.1416100114583969F,
                1.8250000476837158F))
      }
    }

    "calculate sustaina index for Fairly" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.India.toCountry),
          certifications = Seq.empty,
          materials = Seq(Material(MaterialTypeStorage.Hemp_Ecological.toMaterialType, 45.0F),
                          Material(MaterialTypeStorage.Flax_Ecological.toMaterialType, 20.0F),
                          Material(MaterialTypeStorage.Lyocell_NoInfo.toMaterialType, 35.0F)),
          brand = Option(BrandStorage.Fairly.toBrand)
        )
      )
      
    val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
                0.44339317083358765F,
                20.760000228881836F,
                12F,
                5F,
                2.799999952316284F,
                2.0999999046325684F,
                0.2903200089931488F,
                1.3889999389648438F))
      }
    }
    "calculate sustaina index for TheWiman_slowCollectionPart2" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(CountryStorage.Sweden.toCountry),
          certifications = Seq.empty,
          materials = Seq(Material(MaterialTypeStorage.Wool_Recycled.toMaterialType, 80.0F),
                          Material(MaterialTypeStorage.Polyamid_Recycled.toMaterialType, 20.0F)),
          brand = Option(BrandStorage.TheWiman_slowCollectionPart2.toBrand)
        )
      )
      
    val eventualResult = underTest.calculateSustainaIndex(input)
        whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(
                0.7014749050140381F,
                24.53999900817871F,
                26F,
                5F,
                3.5F,
                3F,
                4.522492408752441F,
                3.5849997997283936F))
      }
    }

  }

}
