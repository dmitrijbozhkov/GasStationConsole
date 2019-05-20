import Mirage, { faker } from 'ember-cli-mirage';

const TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXR2aWVpIiwic2NvcGVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTU0MTE3NzczOCwiZXhwIjoxNTQxMTk1NzM4fQ.CtdZkkk343yQnCRyRqOoWSf07N9FTyVa9b50bNpPVFk"

const ROLE_ADMIN = "ROLE_ADMIN";

function ListDTO(contents) {
  this.content = contents;
}

function PageDTO(contents, page, amount, total) {
  this.content = contents;
  this.page = page;
  this.amount = amount;
  this.total = total;
}

function FuelTariff(id, exchangeRate) {
  this.id = id;
  this.exchangeRate = exchangeRate;
}

function FuelStorage(id, fuelAmount) {
  this.id = id;
  this.fuelAmount = fuelAmount;
}

function Fuel(fuelName, fuelTariff, fuelStorage) {
  this.fuelName = fuelName;
  this.tariff = fuelTariff;
  this.storage = fuelStorage;
}

function UserDetails(name, surname, username, role) {
  this.name = name;
  this.surname = surname;
  this.username = username;
  this.role = role;
}

function Order(id, orderType, amount, orderDate, fuel, tariff, user) {
  this.id = id;
  this.orderType = orderType;
  this.orderDate = orderDate;
  this.amount = amount;
  this.fuel = fuel;
  this.tariff = tariff;
  this.user = user;
}

function FuelVolumeOfSales(fuelName, volumeOfSales) {
  this.fuelName = fuelName;
  this.volumeOfSales = volumeOfSales;
}

function FuelsVolumeOfSales(volumes, overal) {
  this.fuelVolumeOfSales = volumes;
  this.overallVolumeOfSales = overal;
}

const user = new UserDetails("Matviei", "Serbull", "pepe@gmail.com", ROLE_ADMIN);

const fuelList = [
  new Fuel("95", new FuelTariff(1, 9.99), new FuelStorage(1, 10000)),
  new Fuel("95+", new FuelTariff(2, 13.99), new FuelStorage(2, 500)),
  new Fuel("92", new FuelTariff(3, 7.99), new FuelStorage(3, 5000))
];

const OrderList = [
  new Order(1, "FUEL_BY_CURRENCY", 50, new Date("27 July 2016 13:30:00 GMT+05:45"), fuelList[0], fuelList[0].tariff, user),
  new Order(2, "CURRENCY_BY_FUEL", 10, new Date("28 July 2016 13:30:00 GMT+05:45"), fuelList[2], fuelList[2].tariff, user)
];

const volumesOfSales = [
  new FuelVolumeOfSales(fuelList[0].fuelName, 500),
  new FuelVolumeOfSales(fuelList[1].fuelName, 600),
  new FuelVolumeOfSales(fuelList[2].fuelName, 900)
];

const USER = {
  name: "Matviei",
  surname: "Serbull",
  username: "matviei",
  password: "pass1234"
}

export default function() {
  this.namespace = "/api";
  // Fuel
  this.get("/fuel/get-all", function() {
    return new ListDTO(fuelList);
  });
  this.post("/fuel/get", function(schema, request) {
    const fuelName = JSON.parse(request.requestBody).fuelName;
    return fuelList.find((fuel) => {
      return fuel.fuelName === fuelName;
    });
  });
  this.post("/fuel/add", function(schema, request) {
    console.log(request);
  });
  this.del("/fuel/remove", function(schema, request) {
    console.log(request);
  });
  this.put("/fuel/update-name", function(schema, request) {
    console.log(request);
  });
  this.put("/fuel/update-name", function(schema, request) {
    console.log(request);
  });
  this.put("/fuel/update-tariff", function(schema, request) {
    console.log(request);
  });
  this.put("/fuel/update-left", function(schema, request) {
    console.log(request);
  });
  // Orders
  this.post("/order/create", function(schema, request) {
    console.log(request);
  });
  this.del("/order/remove/:order_id", function(schema, request) {
    console.log(request);
  });
  this.post("/order/query-user-current", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/query", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/query-date", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/query-fuel", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/query-user", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/query-fuel-date", function(schema, request) {
    let data = JSON.parse(request.requestBody);
    return new PageDTO(OrderList, data.page, data.amount, OrderList.length);
  });
  this.post("/order/volume-of-sales", function(schema, request) {
    return new FuelsVolumeOfSales(volumesOfSales, volumesOfSales.reduce((acc, v) => v.volumeOfSales + acc, 0));
  });
  this.post("/order/volume-of-sales-fuel", function(schema, request) {
    return new FuelsVolumeOfSales(volumesOfSales, volumesOfSales.reduce((acc, v) => v.volumeOfSales + acc, 0));
  });
  // User
  this.post("/user/signin", function(schema, request) {
    const userDetails = JSON.parse(request.requestBody);
    if (userDetails.name === USER.name &&
        userDetails.surname === USER.surname &&
        userDetails.username === USER.username &&
        userDetails.password === USER.password) {
      return new Mirage.Response(200);
    }
    return new Mirage.Response(409,
      {},
      {exceptionType: "EntityAlreadyExistsException", exceptionMessage: "User with name of " + userDetails.name + " already exists"});
  });
  this.post("/user/login", function(schema, request) {
    const userDetails = JSON.parse(request.requestBody);
    return {
      token: TOKEN
    }
  });
  this.post("/user/change-password", function(schema, request) {
    return new Mirage.Response(200);
  });
  this.post("/admin/search-user", function(schema, request) {
    return new PageDTO([user], 1, 5, 1);
  });
  this.post("/admin/set-role", function(schema, request) {
    return new Mirage.Response(200);
  });
  // Fuel tariff
  this.get("/tariff/get-all", function(schema, request) {
    return new ListDTO(fuelList.map(f => f.tariff));
  });
  this.post("/tariff/get/:id", function(schema, request) {
    return new ListDTO(fuelList.find(f => f.tariff.id === request.params.id).tariff);
  });
  this.post("/tariff/add", function(schema, request) {
    return new Mirage.Response(200);
  });
  this.del("/tariff/remove/:id", function(schema, request) {
    return new Mirage.Response(204);
  });
  this.put("/tariff/update", function(schema, request) {
    return new Mirage.Response(204);
  });
  // These comments are here to help you get started. Feel free to delete them.

  /*
    Config (with defaults).

    Note: these only affect routes defined *after* them!
  */

  // this.urlPrefix = '';    // make this `http://localhost:8080`, for example, if your API is on a different server
  // this.namespace = '';    // make this `/api`, for example, if your API is namespaced
  // this.timing = 400;      // delay for each request, automatically set to 0 during testing

  /*
    Shorthand cheatsheet:

    this.get('/posts');
    this.post('/posts');
    this.get('/posts/:id');
    this.put('/posts/:id'); // or this.patch
    this.del('/posts/:id');

    http://www.ember-cli-mirage.com/docs/v0.3.x/shorthands/
  */
}
