## Task List

## Routes

* /popular -> Default popular route with country set to global
* /popular?countryCode=CA -> 2-letter ISO-3166 country code query param to get posts from certain country. For example
  CA=Canada, US=United States

## Types

Post: An individual reddit post data

* id: string
* rank: number
* title: string
* url: string
* author: string
* score: int
* comments: int
* subreddit: string

## Countries

Available countries to search for popular posts:

* United States
* Argentina
* Australia
* Bulgaria
* Chile
* Canada
* Colombia
* Croatia
* Czech Republic
* Finland
* France
* Germany
* Greece
* Hungary
* Iceland
* India
* Ireland
* Italy
* Japan
* Malaysia
* Mexico
* New Zealand
* Philippines
* Poland
* Portugal
* Puerto Rico
* Romania
* Serbia
* Singapore
* Spain
* Sweden
* Taiwan
* Thailand
* Turkey
* United Kingdom