package com.sustainasearch.services.sustainaindex.clothes.material

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

@Singleton
class InMemoryMaterialTypeRepository @Inject()(materialGroupRepository: MaterialGroupRepository)(implicit ec: ExecutionContext) extends MaterialTypeRepository {

  override def getMaterialType(id: Int): Future[Try[MaterialType]] = {
    def convertToMaterialType(materialType: MaterialTypeStorage.Val) = {
      materialGroupRepository
        .getMaterialGroup(materialType.group.id)
        .map {
          case Success(materialGroup) => Success(MaterialType(materialType.id, materialGroup))
          case Failure(t) => Failure(t)
        }
    }

    Try(MaterialTypeStorage(id)) match {
      case Success(mt) => convertToMaterialType(mt)
      case Failure(t) => Future.successful(Failure(t))
    }
  }
}

private object MaterialTypeStorage extends Enumeration {

  case class Val(
                  override val id: Int,
                  group: MaterialGroupStorage.Val
                ) extends super.Val(id) {


  }

  implicit def valueToMaterialTypeVal(x: Value): MaterialTypeStorage.Val = x.asInstanceOf[Val]

    val NoMaterialInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group0
  )

  // Cotton
  nextId=100
  val Cotton_NoInfo = Val(
	id = nextId,
    group = MaterialGroupStorage.group1
  )
  val Cotton_Recycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group9
  )
  val Cotton_Ecological = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )
  val Cotton_InChange = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )
  val Cotton_BCI = Val(
    id = nextId,
    group = MaterialGroupStorage.group3
  )

  // Polyester
  nextId=200
  val Polyester_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3
  )
  val Polyester_Biobased = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )
  val Polyester_ChemicallyRecycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )
  val Polyester_MechanicallyRecycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group9
  )
  val Polyester_Recycled = Val( // same as Polyester_ChemicallyRecycled
    id = nextId,
    group = MaterialGroupStorage.group7
  )


  // Polyamid
  nextId=300
  val Polyamid_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group1
  )
  val Polyamid_Biobased = Val(
    id = nextId,
    group = MaterialGroupStorage.group3
  )
  val Polyamid_ChemicallyRecycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )
  val Polyamid_MechanicallyRecycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group9
  )
  val Polyamid_Recycled = Val( // same as Polyamid_ChemicallyRecycled 
    id = nextId,
    group = MaterialGroupStorage.group5
  )


  //Wool
  nextId=400
  val Wool_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group1 
  )
  val Wool_NonChlorine = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )
  val Wool_Ecological = Val(
    id = nextId,
    group = MaterialGroupStorage.group5 
  )
  val Wool_Ecological_NoChlorine = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )
  val Wool_Recycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group9
  )

  //Viscose
  nextId=500
  val Viscose_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group1 
  )
  val Viscose_Bamboo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )
  val Viscose_FSC_or_PEFC = Val(
    id = nextId,
    group = MaterialGroupStorage.group5 
  )
  val Viscose_Recycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )
    val Viscose_Lenzing = Val( // same as val Viscose_FSC_or_PEFC 
    id = nextId,
    group = MaterialGroupStorage.group5 
  )


  //Lyocell
  nextId=600
  val Lyocell_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )
  val Lyocell_Recycled = Val(
    id = nextId,
    group = MaterialGroupStorage.group9
  )
  val Lyocell_FSC_PEFC = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )
  val Lyocell_Upcycled_FSC_PEFC = Val(
    id = nextId,
    group = MaterialGroupStorage.group10
  )

  //Flax
  nextId=700
  val Flax_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )
  val Flax_Ecological = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )

  //Hemp
  nextId=800
  val Hemp_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )
  val Hemp_Ecological = Val(
    id = nextId,
    group = MaterialGroupStorage.group7
  )

  //Silk
  nextId=900
  val Silk_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group3 
  )

  //Modal
  nextId=1000
  val Modal = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )

  //Acrylic
  nextId=1100
  val Acrylic_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group1
  )

  //Elastane
  nextId=1200
  val Elastane_NoInfo = Val(
    id = nextId,
    group = MaterialGroupStorage.group1
  )
  val Elastane_Biobased = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )

  //Bamboo
  nextId=1300
  val Bamboo = Val(
    id = nextId,
    group = MaterialGroupStorage.group5
  )


}