import Route from '@ember/routing/route';
import { inject } from '@ember/service';

export default class UserMyOperationsPage extends Route.extend({
  fuelOrderService: inject("fuel-order-service")
}) {
  model({ page_number } : { page_number: string }) {
    return this.get("fuelOrderService").queryCurrentUser(parseInt(page_number));
  }
}
