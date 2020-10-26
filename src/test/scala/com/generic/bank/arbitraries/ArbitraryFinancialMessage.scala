package com.generic.bank.arbitraries

import com.generic.bank.domain.FinancialMessage.{ Amount, ReceiverBic, SenderBic }
import com.generic.bank.domain.{ Bic, FinancialMessage }
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{ Arbitrary, Gen }

trait ArbitraryFinancialMessage {
  import ArbitraryBic._

  implicit lazy val arbSenderBic: Arbitrary[SenderBic] = Arbitrary(
    for {
      bic <- arbitrary[Bic]
    } yield SenderBic(bic)
  )

  implicit lazy val arbReceiverBic: Arbitrary[ReceiverBic] = Arbitrary(
    for {
      bic <- arbitrary[Bic]
    } yield ReceiverBic(bic)
  )

  implicit lazy val arbAmount: Arbitrary[Amount] = Arbitrary(
    for {
      value <- arbitrary[Double].map(Amount.Value)
      currency <- Gen.oneOf(Amount.Currency.values)
    } yield Amount(value, currency)
  )

  implicit lazy val arbFinancialMessage: Arbitrary[FinancialMessage] = Arbitrary(
    for {
      sender <- arbitrary[SenderBic]
      receiver <- arbitrary[ReceiverBic]
      amount <- arbitrary[Amount]
      isFraud <- arbitrary[Option[Boolean]]
    } yield FinancialMessage(sender, receiver, amount, isFraud)
  )

}

object ArbitraryFinancialMessage extends ArbitraryFinancialMessage
