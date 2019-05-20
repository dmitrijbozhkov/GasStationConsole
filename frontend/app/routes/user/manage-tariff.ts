import Route from '@ember/routing/route';
import { inject } from '@ember/service';

export default class UserManageTariff extends Route.extend({
  fuelTariffService: inject("fuel-tariff-service"),
  // anything which *must* be merged to prototype here
}) {
  model() {
    return this.get("fuelTariffService").getAllFuelTariffs();
  }
}
