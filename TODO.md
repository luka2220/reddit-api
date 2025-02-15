## Task List

- #### Current

* Configure the /r/all endpoint
* /all gets the current top posts, depending on the specified post amount, for /r/all (all subreddits)

- #### Overall

* HIGH: Get bright data integrated with application code.
* Create a dynamoDB instance on AWS
* Create an AWS simple message queue (all DB operations from application code will read from the message queue)
* Connect the message queue with dynamo DB and application code
* TTFB is currently way to high as a result of scrapping and data processing (avg. 3.0s - 7.0s) Find a way to optimize
  and reduce this.

## Routes

- #### Popular - Popular posts - '/r/popular'

* /popular -> Default popular route with country set to global and post amount set the 25
* /popular?countryCode=CA -> 2-letter ISO-3166 country code query param to get posts from certain country. For example
  CA=Canada, US=United States
* /popular?numPosts=30 -> Number of top reddit posts in /r/popular to send in the response. Default number is 25,
  maximum amount is 100;
    - Valid Post Amounts:
    - 25, 50, 75, 100

- #### All - All subreddits - '/r/all'

* /all -> Default all route with post amount set to 25
* /all?numPosts=30 -> Number of top reddit posts in /r/all to send in the response. Default number is 25, maximum amount
  is 100;
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