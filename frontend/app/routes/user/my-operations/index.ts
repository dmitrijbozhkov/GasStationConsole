import Route from '@ember/routing/route';
import { inject } from '@ember/service';

export default class UserMyOperationsIndex extends Route.extend({
  router: inject("router")
}) {
  beforeModel(transition: any) {
    this.get("router").transitionTo("user.my-operations.page", 1);
  }
}
