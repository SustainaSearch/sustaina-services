package com.sustainasearch.services.v1.auth

import java.security.PublicKey

trait PublicKeyProvider {

  def getJwtPublicKey: PublicKey

}
