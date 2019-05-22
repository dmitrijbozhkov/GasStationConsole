import Component from '@ember/component';
import { computed } from '@ember/object';
import { inject } from '@ember/service';

const ROUND_ZEROES = 100;
const MAX_ORDER_VOLUME = 100;
export default class BuyFuelForm extends Component.extend({
  notify: inject("toast" as any) as any,
  router: inject("router"),
  fuelOrderService: inject("fuel-order-service"),
  positionalParams: ["model"],
  selectedOrderType: "FUEL_BY_CURRENCY",
  maxFuelOrder: 0,
  selectedValueAmount: 0,
  isLoading: false,
  isFuelByCurrency: computed("selectedOrderType", function() {
    return this.get("selectedOrderType") === "FUEL_BY_CURRENCY";
  }),
  fuelByCurrencyMoney: computed("selectedValueAmount", function() {
    return Math.round(this.get("selectedValueAmount") * this.get("model.tariff.exchangeRate") * ROUND_ZEROES) / ROUND_ZEROES;
  }),
  currencyByFuelAmount: computed("selectedValueAmount", function() {
    return Math.round(this.get("selectedValueAmount") / this.get("model.tariff.exchangeRate") * ROUND_ZEROES) / ROUND_ZEROES;
  }),
  currencyByFuelMaxAmount: 0,
  operationDate: null as unknown as Date,
  operationDateObj: computed("operationDate", function() {
    return new Date(this.get("operationDate"));
  }),
}) {
  constructor() {
    super(...arguments);
    let maxFuel = this.get("fuel.storage.fuelAmount" as any) < MAX_ORDER_VOLUME ? this.get("fuel.storage.fuelAmount" as any) : MAX_ORDER_VOLUME;
    this.set("maxFuelOrder", maxFuel);
    this.set("currencyByFuelMaxAmount", maxFuel);
  }
  actions = {
    orderTypeSelected(this: BuyFuelForm, event: any) {
      this.set("selectedOrderType", event.target.value);
      this.set("selectedValueAmount", 0);
      if (this.get("selectedOrderType") === "FUEL_BY_CURRENCY") {
        if (this.get("fuel.storage.fuelAmount" as any) < MAX_ORDER_VOLUME) {
          this.set("maxFuelOrder", 10000);
        } else {
          this.set("maxFuelOrder", MAX_ORDER_VOLUME);
        }
      }
      if (this.get("selectedOrderType") === "CURRENCY_BY_FUEL") {
        if (this.get("fuel.storage.fuelAmount" as any) < MAX_ORDER_VOLUME) {
          this.set("maxFuelOrder", 10000 * this.get("model.tariff.exchangeRate" as any));
        } else {
          this.set("maxFuelOrder", MAX_ORDER_VOLUME * this.get("model.tariff.exchangeRate" as any));
        }
      }
    },
    fuelMoved(this: BuyFuelForm, event: any) {
      let selectedFuel = Number(event.target.value);
      if (this.get("selectedOrderType") === "FUEL_BY_CURRENCY") {
        if (selectedFuel > this.get("maxFuelOrder")) {
          this.set("selectedValueAmount", this.get("maxFuelOrder"));
          event.target.value = this.get("maxFuelOrder");
        } else {
          this.set("selectedValueAmount", selectedFuel);
        }
      }
      if (this.get("selectedOrderType") === "CURRENCY_BY_FUEL") {
        if (selectedFuel > this.get("maxFuelOrder")) {
          this.set("selectedValueAmount", this.get("maxFuelOrder"));
          event.target.value = this.get("maxFuelOrder");
        } else {
          this.set("selectedValueAmount", selectedFuel);
        }
      }
    },
    buyFuel(this: BuyFuelForm) {
      if (this.get("selectedValueAmount") === 0) {
        this.get("notify").error("Please choose some amount of fuel to order", "Validation error");
        return;
      }
      this.set("isLoading", true);
      this.get("fuelOrderService")
      .createOrder(this.get("model.fuelName" as any), this.get("selectedValueAmount"), this.get("selectedOrderType"), this.get("operationDateObj"))
      .then(() => {
        this.set("isLoading", false);
        this.get("notify").success("Thank you for the purchase", "Transaction successful");
        this.get("router").transitionTo("fuel-catalogue");
      })
      .catch((error) => {
        console.log(error);
        this.set("isLoading", false);
        this.get("notify").error(error.responseJSON.exceptionMessage, "An error occured");
      });
    }
  }
};
