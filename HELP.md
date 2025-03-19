
# Getting Started
### Introduction
The following component was build as a Tech Test for Backend JAVA for NEQUI Guatemala
The application was built with this specifications

1. Java 21
2. Maven 3.9.X
3. Docker
4. AWS DynamoDB
5. Terraform
6. Spring boot 3.4.X

## Guides

The following guides illustrate how to build and run the application in a local machine:

## Prerequisites
AWS client
Terraform client
Docker

## Database
Ensure you already has exported the environment variables for execute the commands on AWS  
`export AWS_DEFAULT_REGION=`  
`export AWS_ACCESS_KEY_ID=`  
`export AWS_SECRET_ACCESS_KEY=`  

In the folder /terraform are the files for build the dynamodb in the AWS account of your choice.
The following commands are used to deploy the database:  
		`1. terraform init`  
		`2. terraform validate`  
		`3. terraform plan`  
		`4. terraform apply`  

## Build the component
 Ensure you have the docker installed on the machine where you are going to build and run the component.  
 Find the /Dockerfile and execute the following commands
For build the image run:  
`docker build -t nequi-franchise .`  
For run the container locally:  
` docker run --rm -e AWS_DEFAULT_REGION=<AWS_REGION> -e AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID> -e AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY> -p 8080:8080 nequi-franchise:latest `

## Note
If the component runs on AWS (EC2, EKS, ECS, others), there is no need to add the environment variables, but be sure that the component's IAM Role has the rights for read and write on the table created in the "Database" section of this guide.

## Testing
In this section, are the CURL examples for testing each one of the requirements in localhost, if need to test in another environment, make sure you replace the localhost:8080 by the right domain and port

#### Create a Franchise
    curl --location 'localhost:8080/nequi/franchise' \--header 'Content-Type: application/json' \--data '{    "name":"Motor GM"}'

#### Update a Franchise's name

    curl --location --request PATCH 'localhost:8080/nequi/franchise' \--header 'nq-franchise-id: bbfd7801-79d9-43f5-937c-14ddd8bcc76e' \--header 'Content-Type: application/json' \--data '{    "name":"TitanFit Gym"}'

#### Create a Branch

    curl --location 'localhost:8080/nequi/branch' \--header 'nq-franchise-id: bbfd7801-79d9-43f5-937c-14ddd8bcc76e' \--header 'Content-Type: application/json' \--data '{    "name":"Manizales gym"}'

#### Update a Branch's name

    curl --location --request PATCH 'localhost:8080/nequi/branch' \--header 'nq-branch-id: bbfd7801-79d9-43f5-937c-14ddd8bcc76e#54244981-9f9c-45b5-b799-375f07fd8962' \--header 'Content-Type: application/json' \--data '{    "name":"Muscular gym"}'

#### Create a Product for a Branch

    curl --location 'localhost:8080/nequi/product' \--header 'nq-branch-id: 1536cfa2-b5b7-4c6b-a136-8921546f9ddf#d8c40934-4fa5-4762-82e5-3ccb8b48dab8' \--header 'Content-Type: application/json' \--data '{    "name":"Fit cross medium",    "stock": 18}'

#### Update a Product

    curl --location --request PATCH 'localhost:8080/nequi/product' \--header 'nq-product-id: bbfd7801-79d9-43f5-937c-14ddd8bcc76e#81a787f3-16ee-4cc8-9f48-e34114a5ff75#e1e3e97f-a603-4649-8119-a2c685ce5cbf' \--header 'Content-Type: application/json' \--data '{    "name":"Fit cross medium update",    "stock": 25}'

#### Delete a Product

    curl --location --request DELETE 'localhost:8080/nequi/product' \--header 'nq-product-id: 1a7f7eae-90ad-4769-a847-92304fd768a0#4da31e02-b267-4590-9f1f-d5ca9966c849#19306bf9-2873-4a7f-b9f3-c5b879b7a18c'

#### Get Max Stock for Product in a Franchise

    curl --location 'localhost:8080/nequi/product' \--header 'nq-franchise-id: bbfd7801-79d9-43f5-937c-14ddd8bcc76e'
