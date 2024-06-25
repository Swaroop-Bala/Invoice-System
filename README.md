# Invoice-System
This repo holds the source code of a simple Invoice System, which does few basic features. This Component has been developed using Spring-Boot and MSSQL as Database

There are 4 services embeded within this repo

## Services

### GET /invoices

This Service would fetch all the invoices available within the system.

### POST /invoices

This service would add new invoices to the system. The service accepts amount and due_date as the paramter within the JSON request body. 
We could add only one invoice at a time.
 
### POST /invoices/{invoiceid}/payments

This service would process the payments for respective invoice. The service accepts invoice id within the path param and payment amount within the request body, 
and payments would be processed for the respective invoice.

## POST /invoices/process-overdue

This service would process the overdue invoices and apply late fees for the due Invoices. The service accepts late fee and overdue days within the request body.

## Tech Stack

- **Spring-Boot**
- **JPA-Hibernate**
- **TomCat Server**
- **MS SQL**
- **Log4j**

## Business Logic

-When a new invoice is added the invoice status should be defaulted to pending.
-When a payment is made against an invoice fully, the status should move to Paid from Pending
-When a payment is made against an invoice partially, the status should move to Paid from Pending, also a new invoice should be generated with the remaining amount, 
  with invoice status as pending, with the same due date.
  
-When a process-ovedue service is called, it should calculate the invoices which are pending and due from the current date.
          a. if the due invoices do not have any payments against it, then cancel the existing invoice and generate a new invoice with today's date and due amount carrying from old invoice and adding the late fee.




## Good To have features
 - **Spring- Security** - Adding a authentication and authorization layer to validate the users, before service call.
 - **log4j** - We have already implemented Log4j framework. But we can categorized it even more to have a better logging.
 - **Better Validations and User Friendly responses** - To validate the request body and provide back better user friendly messages
 - **Docker** - Docker integration would help us for easy deployments
