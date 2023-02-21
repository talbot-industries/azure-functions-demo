# azure-functions-demo
Demo of Azure Functions invoked by HTTP and cron events

Based on https://learn.microsoft.com/en-us/azure/azure-functions/create-first-function-cli-java

### Pre-requisites

* Maven
  * `brew install maven`
* Azure Command Line Tools
  * `brew install azure-cli`
* Azure Functions Core Tools
  * `brew tap azure/functions`
  * `brew install azure-functions-core-tools@4`
* Azure Subscription & Logged In
  * `az login`


### Function code

The [Java function in the repo source](src/main/java/com/talbot_industries/azure/Functions.java#L17-L24) returns a string based on the query string `name` parameter


### Testing locally

* Build
  * `mvn clean package`
* Run Locally
  * `mvn azure-functions:run`

Test locally by browsing to http://localhost:7071/api/HttpExample?name=foobar

Also browsing at http://localhost:7071/api/usageCount and  http://localhost:7071/api/incrementUsage

### Testing in the cloud

* Deploy to Azure
  * `mvn azure-functions:deploy` 

Test in the cloud by browsing to https://functions-20230220104431490.azurewebsites.net/api/httpexample?name=foobar

Also browsing at https://functions-20230220104431490.azurewebsites.net/api/usageCount and https://functions-20230220104431490.azurewebsites.net/api/incrementUsage

### Deploying a Cosmos Mongo DB in Azure

#### Pre-requisites

* Terraform
  * `brew install terraform`
* Mongo Shell
  * `brew install mongosh`

#### Deploy Cosmos

Database definition in the source repo at [terraform.tf](terraform.tf)

* `az login`
* `terraform init`
* `terraform apply`

#### Connect to CosmosDB using Mongo Shell

* `mongosh --host tfex-cosmos-db-38517.mongo.cosmos.azure.com --port 10255 --tls -u tfex-cosmos-db-38517 -p <password>`

Inside the Mongo Shell, create a database, and a collection:

* `use exampleDb`
* `db.createCollection('exampleCollection')`
* `show dbs`
* `show collections`
* `db.exampleCollection.find()`