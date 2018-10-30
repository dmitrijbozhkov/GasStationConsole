import Route from '@ember/routing/route';

export default class FuelCatalogueBuy extends Route.extend({
}) {
  selectedFuelAmount: number = 0;
  model({ fuel_name } : { fuel_name: string }) {
    let fuels = [
      {
        fuelName: "95",
        price: 9.99,
        fuelLeft: 10000,
        description: "Just regular fuel most people use"
      },
      {
        fuelName: "92",
        price: 3.99,
        fuelLeft: 0,
        description: "A bit worse fuel than 95"
      },
      {
        fuelName: "95+",
        price: 20.99,
        fuelLeft: 5000,
        description: "The newest development in the petrolium industry. Cleans engine while you drive."
      }
    ];
    return fuels.filter((f) => {
      return f.fuelName === fuel_name;
    })[0];
  }
  actions = {
    fuel
  }
}
