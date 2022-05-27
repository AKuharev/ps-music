# ps-music
Simple REST API with Spring Boot for getting info about singer from different sources.

## Getting started

For running application from the root of the project run: `mvn spring-boot:run`

**Api:**
`GET /musify/music-artist/details/{mbid}`

List of mbids for test:
- Coldplay: cc197bad-dc9c-440d-a5b5-d52ba2e14234
- Michael Jackson: f27ec8db-af05-4f36-916e-3d57f91ecf5e
- Lana Del Ray: b7539c32-53e7-4908-bda3-81449c367da6

## Libraries

- Spring Boot 2.7.0
- Project Reactor 3.4.18
- Netty 4.1.77.Final
- Lombok, Mapstruct

## Decisions

I'm using spring webflux because:
> Spring WebFlux is a good fit for highly concurrent applications, applications that need to be able to process a large number of requests with as few resources as possible, for applications that need scalability or for applications that need to stream request data in a live manner.

## Problems

My application can handle a lot of requests (how much I can't say) but when I tried to test performance
with using jmeter (you can find configuration under src/test/jmeter) I found that provided external services can't handle many requests.
I've tried to play with retry and delays, and it helped a bit but not so much.

### Last performance test results

**Setup:**
- Number of users: 10
- Ramp-Up period: 60 seconds
- Iterations: 10

**Results:**
|Label              |# Samples|Average|Min|Max  |Std. Dev.|Error %|Throughput|Received KB/sec|Sent KB/sec|Avg. Bytes|
|-------------------|---------|-------|---|-----|---------|-------|----------|---------------|-----------|----------|
|Get Coldplay       |100      |5782   |19 |10432|2171,18  |10,000%|,45305    |1,43           |0,08       |3236,2    |
|Get Michael Jackson|100      |5787   |19 |10318|2187,66  |9,000% |,45445    |1,56           |0,08       |3506,2    |
|Get Lana Del Ray   |100      |5480   |20 |10045|1995,51  |9,000% |,45516    |1,49           |0,08       |3357,9    |
|TOTAL              |300      |5683   |19 |10432|2124,76  |9,333% |1,29349   |4,25           |0,23       |3366,8    |

