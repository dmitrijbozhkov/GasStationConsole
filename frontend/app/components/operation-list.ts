import Component from '@ember/component';
import { computed } from '@ember/object';

export default class OperationList extends Component.extend({
  positionalParams: ["isAdmin"],
  isFuelByCurrency: computed("model", function() {
    return this.get("model.orderType") === "FUEL_BY_CURRENCY";
  })
}) {
  actions = {
    deleteOperation(this: OperationList, orderId: number) {
      if ((this as any).delete) {
        (this as any).delete(orderId);
      }
    }
  }
};
