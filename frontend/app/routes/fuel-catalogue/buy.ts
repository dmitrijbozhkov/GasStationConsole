import Route from '@ember/routing/route';
import authenticatedBuyerMixin from 'frontend/mixins/authenticated-buyer-mixin';
import { inject } from '@ember/service';

// @ts-ignore
export default class FuelCatalogueBuy extends Route.extend(authenticatedBuyerMixin, {
  fuelService: inject("fuel-service"),
}) {
  model({ fuel_id } : { fuel_id: string }) {
    return this.get("fuelService").getFuel(fuel_id);
  }
}
