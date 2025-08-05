# Fridge inventory management - A sample Spring AI Server Client setup

- This is a simple fridge order placing system(similation of your production system thats already running) based on Spring Modulith's events, when the inventory goes below the requested consumption quantity sends an event to place an order. 
- This is to demonstrate the AI chatbot and MCP Spring AI chat using SSE

## Run

Run the application using the application classes in the following order,
- InventoryApplication - runs on 8080
- McpServerApplication - runs on 8081
- McpClientApplication - runs on 8082

## AI Chat

Use the following swagger endpoint - http://localhost:8082/swagger-ui/index.html

- ```AI Chat Conroller``` - for general purpose chat
- ```AI Memory Controller``` - for general purpose chat with memory repository
- ```MCP Chat Controller``` - for the Fridge system 

## SQL

```sql
select p.id, p.name, p.price, i.availability, i.id as inventoryid
from products p left join inventory i on i.pid = p.id;

select * from products;

select * from orders o inner join orders_line_item l on o.id = l.oid ;
```

## Prompts

- ```add apple juice to the products at 1.99```
  - this will will add the apple juice to the products table, check the product table
- ```get the product id of apple juice```
  - get the product id to be used in the next prompt - which returns 7
- ```add product 7 with the availability 15```
  - add the product to the inventory with availbilty 15 items, now check the inventory and product table
- ```what is the inventory id of the product 7```
  - this inventory id is needed for the next prompt to consume
- ```consume 10 of the inventory item 7```
  - this would consume the items and return 5 available 
- ```consume 10 of the inventory item 7```
  - this would consume the items, and place the new order for 10 items, and the availble should be reduced to '-5' in the current inventory - check the order table
