Feature: user can create a short url and bookmarked it

  Scenario: user should generate short URLs which will expire after a standard default timespan and bookmarked it.
    When user request with following params
      | longUrl                                                   | expiryDate | title     | description | bookmarked |
      | https://engineering-stream-hackathon.github.io/challenge/ | 2020-12-30 | hackathon | hackathon   | true       |

    Then successfully store response on the database
      | id | longUrl                                                   | expiryDate | title     | description | bookmarked |
      | 1  | https://engineering-stream-hackathon.github.io/challenge/ | 2020-12-30 | hackathon | hackathon   | true       |

    And  the client receives status code of 201




