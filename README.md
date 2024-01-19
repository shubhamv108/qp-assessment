# grocery-booking
Simple application to demonstrate server sent events using Spring Boot.


# Replace
### grocery-booking
### grocerybooking
### GroceryBooking

# Github Secrets
DOCKER_USER
DOCKER_PASSWORD

# Setup
### Local
      make local-setup
      make local-app

# [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/)


# Tech Stack
##### Code Repository
- Github

#### Configuration
- AWS Param Store
##### Secure Config
- AWS KMS - Encryption keys
- AWS Secret manager: Database keys

(Alternative centralized Spring Config Server (git branch support), Vault for secured keys]

#### Deployment
- Github Action (format, build, sonarqube static code analysis, test, test coverage, create image, push image to registry)
- Jfrog artifact code repository (Dependency repository with vulnerability check) (exploring)
- AWS ECR - Image Repository
- Prisma Cloud - Image Scan (exploring)
- AWS Cloudformation (IaC)
- AWS Codepipeline (Env - DEV, QA, Pre-Prod, Prod (full auto on approval in pre prod )) (Stages - Source (AutoTrigger on commit), setup infra, test, deploy, approve for next env (Manual))


#### DNS
- Route 53

#### CDN
- AWS Cloudfront

#### DFS
- AWS S3


#### API Gateway
- AWS API Gateway (Routing, Rate Limiting)


##### Auth
- OAuth2 Google with Role in IAM service for eahc email.  (RBAC Keycloak when allowing self registration)
- AWS Lambda Authorizer


#### VPC
- VPC with private Subnet
- VPC Link (connect AWS Api Gateway to ELB (on VPC with private subnet))


#### Load Balancing
- AWS ELB (ALB) - Round Robin

#### Service Host - Stateless
- AWS ECS (Auto Scaling on CPU (AWS Cloudwatch Alarm on CPU Metric))
  (Alternative Kubernetes with Horizontal Pod Autoscaling (not clear on metrics collection in kubernetes))

#### Programming Language
- Java

#### Framework
- Spring Boot

#### Circuit Breaker
- Hystrix

#### Event Sourcing
- Eventuate (exploring)

#### Transactional outbox with AWS DynamoDB backed Services
- DynamoDB Stream
- Kinesis (Dynamo event stream persisted here for more than 24 hours)
- AWS Lambda (Consumer)
- SNS

#### Test
- JaCoCo - Code Coverage
- Mockito
- Junit
- Docker Compose

#### Static code analysis
- Sonarqube

#### Firewall
- AWS Security group

#### Cache
- AWS Elasticache (Redis)


#### Databases
- Postgres (AWS RDS (Multi AZ with Read Replica), RDS Proxy(ConnectionPool)) - Driver, Driver Onboard, Document, Order, OrderProduct, IAM, UserProfile
- AWS DynamoDB - Blob, Background Verification, Shipment

#### Queue
- AWS SQS

#### InterService Communication
##### Synchronous
- Rest API

##### Asynchronous
###### PubSub
- AWS SNS

#### Logging
- FlunetBit (Sidecar/Daemon Set in Kubernetes)
- AWS Cloudwatch Logs
- AWS Opensearch

#### Metrics & Alarms
##### Push
- FlunetBit (Sidecar/Daemon Set in Kubernetes)
- AWS Cloudwatch Logs
- AWS Cloudwatch Metrics - Dashboard, Alarms
##### Pull
- Prometheus, InfluxDB (when needed)

#### Exception tracking & Error De-Duplication
- Sentry

#### Jump Host
- AWS Session Manager

#### Cron
- AWS Eventbridge

#### Analytic Report
- eventbridge
- sns
- sqs
- Schema - AWS Glue
- job within service or aws emr
- S3

#### ServiceMesh
Istio (With kubernetes, exploring)

# High Level Design
![HIghLevelDesign]()


# Images
#### Generate Google OAuth2 Bearer Token
![Screenshot from 2023-12-31 13-02-34](https://github.com/shubhamv108/craft/assets/16763337/a6f293dc-40d0-4712-bdf7-63839ab0bb75)

