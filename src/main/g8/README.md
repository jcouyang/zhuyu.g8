# $name$

![Cats Friendly Badge](https://typelevel.org/cats/img/cats-badge-tiny.png)

## Getting Started

### Prerequisites

- docker
- sbt

SQS up
```
docker-compose up -d sqs
```

SQS Queue Prep
```
ops/bin/setup
```

## Up and Running
SQS Queue Purge
```
aws --endpoint-url=http://localhost:9324 sqs purge-queue --queue-url "http://localhost:9324/queue/$app_queue_name$"
```

SQS Add message
```
aws --endpoint-url=http://localhost:9324 sqs send-message --queue-url "http://localhost:9324/queue/$app_queue_name$" --message-body '{"SomethingHappened": {"what": "hehe"}}'
```

#### From Docker

it's recommend for anyone just want to browse around not actively developing something
```
docker-compose run app
```

#### From sbt

for active developer
```
env APP_ENV=Local sbt run
```

## Unit tests
from docker
```
docker-compose run --rm test
```

or if database is already up:
```
sbt test
```

## Contribute

```
src/main/scala/$organization;format="pacakged"$/$name;format="word,lower"$
├── effects                            <- Side Effects DSL (Kleisli[IO, Input, Output])
│   ├── Logger.scala
│   └── SQS.scala
├── impl                       <- Side Effect Implementation
│   ├── Config.scala
│   └── SQS.scala
├── jobs
│   ├── OnSomethingHappened.scala      <- Generic Job seeker
│   └── package.scala                  <- Index of Jobs (3)
├── Main.scala
├── models
│   └── Events.scala                   <- Data Type (1)
```

1. add new Data Type A
2. define trait B that has implicit `Job[A, Has]`
3. add B into index of jobs

## Infrastructure

Infrastructure just needed to create once, so if anything bad happened we need to manually setup again:

login into preprod or prod account

### SQS
```
ops/bin/setup
```

### Alerts
```
ops/bin/cloudformation <preprod|prod> $name;format="normalize"$
```
