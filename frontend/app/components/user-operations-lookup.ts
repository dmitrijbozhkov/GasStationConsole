import Component from '@ember/component';
import { amountPages } from './common-functions';
import { observer } from '@ember/object';
import { inject } from '@ember/service';

export default class UserOperationsLookup extends Component.extend({
  router: inject("router"),
  currentPage: 0,
  amountPages: 0,
  isFirst: true,
  pageChange: observer("currentPage", function(this: UserOperationsLookup) {
    if (this.get("isFirst")) {
      this.set("isFirst", false);
    } else {
      this.get("router").transitionTo("user.my-operations.page", this.get("currentPage"));
    }
  })
}) {
  constructor() {
    super(...arguments);
    this.set("currentPage", this.get("model.page" as any));
    this.set("amountPages", amountPages(this.get("model.total" as any), this.get("model.amount" as any)));
  }
  actions = {
    delete(orderId: number) {
      console.log(orderId);
    }
  }
};