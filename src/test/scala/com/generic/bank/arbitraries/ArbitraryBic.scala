package com.generic.bank.arbitraries

import com.generic.bank.domain.Bic
import org.scalacheck.{ Arbitrary, Gen }

trait ArbitraryBic {

  implicit lazy val arbBic: Arbitrary[Bic] = Arbitrary(
    for {
      bic <- Gen.nonEmptyListOf(Gen.alphaNumChar).map(_.mkString)
    } yield Bic(bic)
  )

}

object ArbitraryBic extends ArbitraryBic
