import Component from '@ember/component';
import { observer } from '@ember/object';
import { inject } from '@ember/service';
import { amountPages } from "./common-functions";

export default class UserOperationsSearcher extends Component.extend({
  orderService: inject("fuel-order-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  chosenUser: null,
  currentPage: 0,
  amountPages: 0,
  operations: [],
  userChoose: observer("chosenUser", function (this: UserOperationsSearcher) {
    if (this.get("chosenUser")) {
      this.getUserOrders(1);
    }
  })
}) {
  getUserOrders(page: number) {
    this.set("isLoading", true);
    this.get("orderService")
    .queryUser(this.get("chosenUser" as any).username, page)
    .then((result) => {
      this.set("isLoading", false);
      this.set("operations", result.content);
      this.set("currentPage", result.page);
      this.set("amountPages", amountPages(result.total, result.amount));
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  actions = {
    delete(this: UserOperationsSearcher, orderId: number) {
      this.set("isLoading", true);
      this.get("orderService")
      .removeOrder(orderId)
      .then((result) => {
        this.set("isLoading", false);
        this.get("notify").success("Order removed");
        this.getUserOrders(this.get("currentPage"));
      })
      .catch((error) => {
        this.set("isLoading", false);
        this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
      });
    }
  }
};
