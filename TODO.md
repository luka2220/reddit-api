## Task List

* HIGH: Get bright data integrated with application code.

* Work on returning a custom amount of posts for /popular
* Design a way to continuously render the next page for however many posts requested

## Routes

* /popular -> Default popular route with country set to global
* /popular?countryCode=CA -> 2-letter ISO-3166 country code query param to get posts from certain country. For example
  CA=Canada, US=United States
* /popular?numPosts=30 -> Number of reddit post to send in the response. Default number is 25, maximum amount is 100;
    - Valid Post Amounts:
    - 25, 50, 75, 100

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