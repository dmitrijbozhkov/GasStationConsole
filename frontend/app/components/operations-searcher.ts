import Component from '@ember/component';
import { observer, computed } from '@ember/object';
import { inject } from '@ember/service';
import { IPageDTO } from "../services/service-commons";
import { Order } from "../services/fuel-order-service";
import { amountPages } from "./common-functions";

export default class OperationsSearcher extends Component.extend({
  orderService: inject("fuel-order-service"),
  fuelService: inject("fuel-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  currentPage: 0,
  amountPages: 0,
  operations: [],
  // form fuel
  searchFuel: false,
  fuels: [],
  chosenFuel: "placeholder",
  isFuelChooserNotTouched: true,
  // form date
  searchDates: false,
  dateBefore: null as unknown as Date,
  dateAfter: null as unknown as Date,
  dateBeforeObj: computed("dateBefore", function() {
    return new Date(this.get("dateBefore"));
  }),
  dateAfterObj: computed("dateAfter", function() {
    return new Date(this.get("dateAfter"));
  }),
  // form operation
  allowPagination: false,
  pageChange: observer("currentPage", function (this: OperationsSearcher) {
    if (this.get("allowPagination")) {
      this.getOrders(this.get("currentPage"));
    }
  })
}) {
  constructor() {
    super(...arguments);
    this.get("fuelService")
    .getAllFuels()
    .then((result) => {
      this.set("fuels", result.content);
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  getOrders(page: number) {
    this.set("isLoading", true);
    let query;
    if (this.get("searchFuel") && this.get("searchDates")) {
      query = this.get("orderService")
      .queryDateFuel(this.get("dateBeforeObj"), this.get("dateAfterObj"), this.get("chosenFuel"), page);
    } else if (this.get("searchFuel")) {
      query = this.get("orderService")
      .queryFuel(this.get("chosenFuel"), page);
    } else if (this.get("searchDates")) {
      query = this.get("orderService")
      .queryDate(this.get("dateBeforeObj"), this.get("dateAfterObj"), page);
    } else {
      query = this.get("orderService")
      .query(page);
    }
    query
    .then((result: IPageDTO<Order>) => {
      this.set("isLoading", false);
      this.set("operations", result.content);
      this.set("currentPage", result.page);
      this.set("amountPages", amountPages(result.total, result.amount));
      this.set("allowPagination", true);
    })
    .catch((error: any) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  actions = {
    delete(this: OperationsSearcher, orderId: number) {
      this.set("allowPagination", false);
      this.set("isLoading", true);
      this.get("orderService")
      .removeOrder(orderId)
      .then((result) => {
        this.set("isLoading", false);
        this.get("notify").success("Order removed");
        this.getOrders(this.get("currentPage"));
      })
      .catch((error) => {
        this.set("isLoading", false);
        this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
      });
    },
    fuelChange(this: OperationsSearcher) {
      this.set("isFuelChooserNotTouched", false);
      if ((event as any).target.value != "placeholder") {
        this.set("chosenFuel", (event as any).target.value);
      }
    },
    submitSearch(this: OperationsSearcher) {
      if (this.get("searchDates")) {
        if (!this.get("dateBeforeObj") || !this.get("dateAfterObj")) {
          this.notify.error("Both dates should be chosen", "Validation error");
          return;
        }
        if (this.get("dateBeforeObj") > this.get("dateAfterObj")) {
          this.notify.error("Date before cannot be bigger, than after", "Validation error");
          return;
        }
      }
      if (this.get("searchFuel")) {
        if (this.get("chosenFuel") === "placeholder") {
          this.notify.error("Please choose fuel", "Validation error");
          return;
        }
      }
      this.set("allowPagination", false);
      this.getOrders(1);
    }
  }
};
