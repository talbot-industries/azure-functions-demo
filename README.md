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


### Testing in the cloud

* Deploy to Azure
  * `mvn azure-functions:deploy` 

Test in the cloud by browsing to https://functions-20230220104431490.azurewebsites.net/api/httpexample?name=foobar


### Deploying a Cosmos Mongo DB in Azure

#### Pre-requisites

* Terraform
  * `brew install terraform`

#### Deploy Cosmos

Database definition in the source repo at [terraform.tf](terraform.tf)

* `az login`
* `terraform init`
* `terraform apply`