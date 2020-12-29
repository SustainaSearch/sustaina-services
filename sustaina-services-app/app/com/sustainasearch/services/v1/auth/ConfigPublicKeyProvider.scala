package com.sustainasearch.services.v1.auth

import java.security.{KeyFactory, PublicKey}
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class ConfigPublicKeyProvider @Inject()(config: Configuration) extends PublicKeyProvider {

  override def getJwtPublicKey: PublicKey = {
    val encodedPublicKey = config.get[String]("jwt.publicKey")
    val publicBytes = Base64.getDecoder.decode(encodedPublicKey)
    val keySpec = new X509EncodedKeySpec(publicBytes)
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePublic(keySpec)
  }

}
