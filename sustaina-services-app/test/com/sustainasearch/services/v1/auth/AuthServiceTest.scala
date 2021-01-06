package com.sustainasearch.services.v1.auth

import java.time.Clock

import com.sustainasearch.services.sustainaindex.Tenant
import com.sustainasearch.services.sustainaindex.tenant.{InMemoryTenantRepository, TenantService}
import org.scalatest.{Matchers, WordSpec}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader}
import play.api.Configuration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

class AuthServiceTest extends WordSpec with Matchers {
  val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlJ1v9nmszUwqwiP0wBpUEhdd0aESvMfOjCQHl0ENUKp9abTuY2TMSvBQ6KSnFccK1bQoVeLerNAMtxSrw+NvTxBVBRQnxhIgFu+1V3uMCzRdVfpmdUax0Wx+W604zIJEyo5xsxkxg+kYnDzu3ak6jT1gBRDJMcW6uhpozCwDKmJ6yvnZPLBnMsRNc4XgAjuicwZU7AXAT5ey4n53gE1y0Fy6rNAPNWjZ4ARDuv5NnJpMO/T4GKa6VCAtYX3aQfr47ExxXqgNK/WTi3SQy482aBRGZzlV0ObU2tYELF54vIRpg+dUSllN1zj9v0n46iS2fXGh/R5E/3fp1YlFRsQ22QIDAQAB"
  val privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCUnW/2eazNTCrC\nI/TAGlQSF13RoRK8x86MJAeXQQ1Qqn1ptO5jZMxK8FDopKcVxwrVtChV4t6s0Ay3\nFKvD429PEFUFFCfGEiAW77VXe4wLNF1V+mZ1RrHRbH5brTjMgkTKjnGzGTGD6Ric\nPO7dqTqNPWAFEMkxxbq6GmjMLAMqYnrK+dk8sGcyxE1zheACO6JzBlTsBcBPl7Li\nfneATXLQXLqs0A81aNngBEO6/k2cmkw79PgYprpUIC1hfdpB+vjsTHFeqA0r9ZOL\ndJDLjzZoFEZnOVXQ5tTa1gQsXni8hGmD51RKWU3XOP2/SfjqJLZ9caH9HkT/d+nV\niUVGxDbZAgMBAAECggEAK7c8DhawnBtBoKYPAss7265/7IAjEOD9gv++M+Hw1r+v\n8H5GeXpXFdwnEKgOdjt8lmxOSSVZNyBj/R7Zf9/RCELXn4zUPlFqmxScFUXEZi9b\nHUVxCiJngCEX8kO8J3xSW/sWuwY4KINSt/K8mPuEu7NIIXVUmY1+ZewK07RGohqt\n/Me4rk6dam5e7bC8VLSbG8Q3BQMjb0sBkGM3BZH0XRupEinHZ62HtKlBWEpnUqWK\nt3fDAIsE0EyD/w7NniNBCC6g8BePdA0uRQKs/on+7JOyQ2qYX8Ep/SMe7NDzVSQR\n51b48GMR6EWfwjEF0Merw2W7Tp32Df8/c4P/i3bpYQKBgQDgfZOwDoZCmXJfJjbD\ngPnRqYVUeqLQ+cDK3KPkX8L0ubd7ZwUxk7dcUHuHtPEC48t9AHi3izzacZOGzHJ7\nR5LBht/LcSWgZUgdRaH0sOY/S21WLQA1YGbIG+7c31H3jP8JQ/yis5WUClNzP8Pl\n7LZBp/jcUPmc0yeqGv1D5P24fQKBgQCpeX2Q+Lzbd/3as2fIvvec4ucwf3yw49Ea\n6uLlPIVeGPt7Kj1dyO9NCQcZ3J+ixt9dM8AahCTvv+YtKUIgUEKRLB12KmAkwPxP\njCoRWFD7svCguOWnwHK/S9y3EH8aj7LARHkmuLussGfCHVolWD4jhfYlkk+sV+fa\njmMQcqkijQKBgQDRvoKui+OFdjkWDW49W9QNwIWCWNKmzbMD1wKJ7a8JWDvGYIrJ\nt2oqJkhEkxpbyNnnAPnJA57nuhZMa9jKtiS4DHwsaJrvMbIfJ2Aabu2xVZfiXElF\nlbxyWybh1wWOdX4T+iGhIokuuDcgBwPRX0kqLvalYkLV87Ori2v2c41bQQKBgQCF\nHHJ+spALxXRzGaSzrSLpa+LwWcIDYfy0qrbqHJ4YKYEk3Sl0B0XF+QhFzN7pyFBa\ncTI64X3tfYl6AUT6AnA2fDLrxB7d32KNzGaiVv7Mo2CSrIddOjAmhpxmgSOEJkfc\n8itCOajW23uzoMBUQroTxr1uBzxi/mySYnH20kh8ZQKBgQCxHMM2pVmp1a1NOPzN\n/r9sXz9C2Le++LRuFZasEu2jKMPLThp/SxkKiz9mBdcYqRUIwBQAD+FCBI5WT/h2\nBm/C+bkH5Uuo8SZSy6SP9X5X3WzzuMEjEMf7Qh2sGMRKfDmtuVBZj73zNdxyzJxN\nvzZtZkmdndUx79g4sVzoS3KyRQ=="
  val config = Configuration.from(Map(
    "jwt.publicKey" -> publicKey
  ))
  implicit val clock = Clock.systemUTC()
  val underTest = new AuthService(
    new ConfigPublicKeyProvider(config),
    new TenantService(new InMemoryTenantRepository)
  )

  classOf[AuthService].getSimpleName should {

    "return tenant if JWT token is valid" in {
      val claim = JwtClaim("""{"tid":"1", "thost":"host_1"}""")
        .issuedNow
        .startsNow
        .expiresIn(10)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
      val result = underTest.validate(s"Bearer $token")
      result shouldBe Success(Tenant("1", "host_1"))
    }

    "return a failure if token has expired" in {
      val claim = JwtClaim("""{"tid":"1", "thost":"host_1"}""")
        .issuedNow
        .startsNow
        .expiresIn(-1)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
      val result = underTest.validate(s"Bearer $token")
      result.isFailure shouldBe true
    }

    "return a failure if private key is invalid" in {
      val invalidPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPgxUZ/YSuNkTh\nEtG/c/xkQCHoMZaidYDDdxPp6lK/HpBW5I+LeT67yb5VqHFo7flf5km0ZkH3nNKd\nxRW7Ke+I2zl5GPB97CdjVCshYDYEViEgQoPsoG0gSwLroYPzds14xYxYBMXlA2FY\nPhA3mJLbAVZx1HJcvCcsX28uzYNtCwZe7MZrk8SfoG8K4zpVQ33GlCvuSTxom280\nZgYniTNyKBITkn/g4YadrbnQUIfa4Uqcay5GShfmLa/xssNXDr8/o4AFZs9+Mq+c\niXWn5/TFDgMBy2G3nsqnStBDJD2QBh9BsaqA/2oTmwI+pJeAAd7NC99b7K268NlJ\n5JJsjLGZAgMBAAECggEAa/aKLmZJpaHQCZ7VynkgO+7/UyXTE97gAqyXf5c3ru34\nWlZSIvQQnMd1+AzjXLQCsgGWGy/+mqAQ1n4JDifLOyLpRs/bRHAfZZtRYyd1G6gL\n/a/sfVxnHC8yQD/e+465nwzGuwTEN7hmSDNQkfKnMDj+Zl4Ttu4ZbklRZF1ZbPMF\nI1Akn4O0ojiyL04QPNQWGNlobY3M3P2HwLPdWef2E5J0ber0CvpUwZ+6SbBucmt7\n4mW05lzR1OVfEIdg7oAe4sykD+P6NahZbVf2gl+yX6YC4Hfv3MsBBw9i9IndimE8\nhaj6KcbP1eSIL6y+zq61QpuzvM6XvY05fBSnW794cQKBgQDhaad7AIEZrW2osrIo\nW1TRLEbE1ZBFkYbCSFG0TZeZJGyOZLbgnbcuOdXJNSacBDpMYDWBauXGFSNcxhs7\nIJ9QRZ8MJf1T8GWZSU5UoYbaRidxijhTx6KAOdLdOUw9tii6AC0Zp0sa8M2BjQww\nwFtj1DhT3JD1Ljx4elBJlKECdQKBgQCi/GCp+n7EZkcLMgq+EBIqH6vXH1YgBE8/\nM2S+h3IRS3Cb7KEymLfla6fRPJyoFsyi5DTD1+Ny6LoXp900zYczxLBbhy7RjqZX\nWdVqjBuoHbp6G/itksxsUBEv1iDpEh7IoNZE1HxyQOlVXg+FwVxymvwQgL4xCxUI\nvxkP1Y3GFQKBgCqS4/kNXVTWNrH4HkIVDe376NngZEFffQ5Qzm25gfqAsKgsSYYf\nA6wNbF12VnZ9Le2i9jgdUoyZDXbGS/pc7PoiTCnlOvWRn/7Vo5/Eak3rliORsZXR\nTNXfFf6GhenZ79NBmUMRH9RUytu/IiBLFHfEa2cbb51/QBiZMXTUR5zJAoGADoOC\n6CASYdLpyKlwI3Sy6WVgnAuCU9+OXwZolj7hfYDGSlxMDoFi/t8TVqKbIKzHFEpW\nGIqFCpqG18xu+mDDBW0id0QWezrEvUqWoqJPZC0hifYrsuk7ovmOTkA3NgoTC7Km\n4gI6AsKdBKkXoMWTQfFjOOT+Csf924WI6cwOj/ECgYEAjZ0acIxLQM3i2FrELNHN\nbD0KwdaLvpWjl1JUYCfXVHmWciLcMabNYvEpR7pVZwDurCdjxgYCjTvj6EEgbN0+\nRX6xEbIjN7gdzxSlBPj8Uvg7DbvksknYrGO+kjqQPs8jB86jgISsKZhTMbPIJC82\nOCsPms9CQY4nnQ/ttvPbghY="
      val claim = JwtClaim("""{"tid":"1", "thost":"host_1"}""")
        .issuedNow
        .startsNow
        .expiresIn(10)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, invalidPrivateKey)
      val result = underTest.validate(s"Bearer $token")
      result.isFailure shouldBe true
    }

    "return a failure if tenant ID is missing in the claim" in {
      val claim = JwtClaim("""{"thost":"host_1"}""")
        .issuedNow
        .startsNow
        .expiresIn(10)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
      val result = underTest.validate(s"Bearer $token")
      result.isFailure shouldBe true
    }

    "return a failure if tenant host is missing in the claim" in {
      val claim = JwtClaim("""{"tid":"1"}""")
        .issuedNow
        .startsNow
        .expiresIn(10)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
      val result = underTest.validate(s"Bearer $token")
      result.isFailure shouldBe true
    }

    "return a failure if tenant is invalid in the claim" in {
      val claim = JwtClaim("""{"tid":"1", "thost":"invalid"}""")
        .issuedNow
        .startsNow
        .expiresIn(10)
      val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
      val result = underTest.validate(s"Bearer $token")
      result.isFailure shouldBe true
    }

  }
}
