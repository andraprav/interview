# Captain's Log - Technical Decisions / Assumptions / README

## Implementation details

### JsonUtil

    - add jackson json library to easily deserialize the message into multiple types
    - return a Left(Error.Illegal(message)) in case there are some unrecognized fields during the deserializations

### Message

    - created Message trait which can be deserialized into multiple case classes depending on messageType field
    - MT103 and MT202 are subtypes of Message

### MessageParser

    - read json into Message object useing JsonUtil
    - in case read returns a left -> resend it
    - in case read returns a right -> process it
        - extract currency and amount
        - in case the currency is not found -> return an error (as left)
        - extract sender and receiver
        - create FinancialMessage

### FraudClient

    - call FraudApi
    - map results from external call to internal objects

### OutgoingStream

    - putting everything together
    - parse every file from incoming stream source
        - in case parsing returned an error -> forward it
        - in case it returned a financialMessage:
            - call FraudClient
            - enrich the actual message with isFraud or forward error

### Main

    - create an incoming stream
    - create an outgoing stream based on the incoming one
    - create a sink which will ignore errors and println rights

### Future work 
    - use extractor objects
    - terminate stream
    - write test for outgoingStream