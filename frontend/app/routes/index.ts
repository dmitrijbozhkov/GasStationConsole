import Route from '@ember/routing/route';

export default class Index extends Route.extend({
  // anything which *must* be merged to prototype here
}) {
  model() {
    return [
      {
        fuelName: "95",
        price: 9.99,
        fuelLeft: 10000,
        maxFuel: 10000,
        description: "Just regular fuel most people use"
      },
      {
        fuelName: "92",
        price: 3.99,
        fuelLeft: 0,
        maxFuel: 10000,
        description: "A bit worse fuel than 95"
      },
      {
        fuelName: "95+",
        price: 20.99,
        fuelLeft: 5000,
        maxFuel: 10000,
        description: "THe newest development in the petrolium industry. Cleans engine while you drive."
      }
    ]
  }
}
