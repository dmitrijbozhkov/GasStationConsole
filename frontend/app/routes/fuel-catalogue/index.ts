import Route from '@ember/routing/route';
import { inject } from '@ember/service';

export default class FuelCatalogueIndex extends Route.extend({
  fuelService: inject("fuel-service"),
  // anything which *must* be merged to prototype here
}) {
  model() {
    return this.get("fuelService").getAllFuels();
  }
}
