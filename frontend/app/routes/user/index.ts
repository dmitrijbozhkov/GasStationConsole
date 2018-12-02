import Route from '@ember/routing/route';

export default class UserIndex extends Route.extend({
  // anything which *must* be merged to prototype here
}) {
  beforeModel() {
    this.transitionTo("user.my-operations");
  }
}
