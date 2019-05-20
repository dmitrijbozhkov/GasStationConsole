import Component from '@ember/component';
import { observer, computed } from '@ember/object';
import { inject } from '@ember/service';
import { Fuel } from "../services/fuel-service";

export default class StatisticsForm extends Component.extend({
  orderService: inject("fuel-order-service"),
  fuelService: inject("fuel-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  // form fuel
  searchFuel: false,
  fuels: [],
  chosenFuel: "placeholder",
  isFuelChooserNotTouched: true,
  // form date
  dateBefore: null as unknown as Date,
  dateAfter: null as unknown as Date,
  dateBeforeObj: computed("dateBefore", function() {
    return new Date(this.get("dateBefore"));
  }),
  dateAfterObj: computed("dateAfter", function() {
    return new Date(this.get("dateAfter"));
  }),
  // chosen
  chosenFuels: [],
  // results
  overallVolumeOfSales: 0,
  fuelsVolumeOfSales: [],
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
  findFuelIndex(fuelList: Fuel[], fuelName: string) {
    for (let i = 0; i < fuelList.length; i += 1) {
      if (fuelList[i].fuelName === fuelName) {
        return i;
      }
    }
    return -1;
  }
  moveFuelToChosen(index: number) {
    let chosenFuels = this.get("chosenFuels") as Fuel[];
    let fuels = this.get("fuels") as Fuel[];
    let nextFuels = [] as Fuel[];
    let nextChosenFuels = chosenFuels.map(i => i);
    for (let i = 0; i < fuels.length; i += 1) {
      if (i === index) {
        nextChosenFuels.push(fuels[i]);
      } else {
        nextFuels.push(fuels[i]);
      }
    }
    this.set("chosenFuels", nextChosenFuels);
    this.set("fuels", nextFuels);
  }
  moveChosenToFuel(index: number) {
    let chosenFuels = this.get("chosenFuels") as Fuel[];
    let fuels = this.get("fuels") as Fuel[];
    let nextFuels = fuels.map(i => i);
    let nextChosenFuels = [];
    for (let i = 0; i < chosenFuels.length; i += 1) {
      if (i === index) {
        nextFuels.push(chosenFuels[i]);
      } else {
        nextChosenFuels.push(chosenFuels[i]);
      }
    }
    this.set("chosenFuels", nextChosenFuels);
    this.set("fuels", nextFuels);
  }
  getVolumeOfSales() {
    this.get("orderService")
    .getVolumeOfSales(this.get("dateBeforeObj"), this.get("dateAfterObj"))
    .then((results) => {
      this.set("overallVolumeOfSales", results.overallVolumeOfSales);
      this.set("fuelsVolumeOfSales", results.fuelVolumeOfSales);
      this.set("isLoading", false);
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  getFuelsVolumeOfSales() {
    this.get("orderService")
    .getFuelVolumeOfSales(this.get("dateBeforeObj"), this.get("dateAfterObj"), this.get("chosenFuels").map((f: any) => f.fuelName))
    .then((results) => {
      this.set("overallVolumeOfSales", results.overallVolumeOfSales);
      this.set("fuelsVolumeOfSales", results.fuelVolumeOfSales);
      this.set("isLoading", false);
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  actions = {
    fuelChange(this: StatisticsForm) {
      let index: number;
      this.set("isFuelChooserNotTouched", false);
      if ((event as any).target.value != "placeholder") {
        index = this.findFuelIndex(this.get("fuels"), (event as any).target.value);
        this.moveFuelToChosen(index);
      }
    },
    submitSearch(this: StatisticsForm) {
      if (!this.get("dateBeforeObj") || !this.get("dateAfterObj")) {
        this.notify.error("Both dates should be chosen", "Validation error");
        return;
      }
      if (this.get("dateBeforeObj") > this.get("dateAfterObj")) {
        this.notify.error("Date before cannot be bigger, than after", "Validation error");
        return;
      }
      if (this.get("searchFuel") && this.get("chosenFuels").length === 0) {
        this.notify.error("Please choose at least one fuel", "Validation error");
        return;
      }
      this.set("isLoading", true);
      if (this.get("searchFuel")) {
        this.getFuelsVolumeOfSales()
      } else {
        this.getVolumeOfSales();
      }
      },
      removeChosenFuel(this: StatisticsForm, fuelName: string) {
        let index = this.findFuelIndex(this.get("chosenFuels"), fuelName);
        this.moveChosenToFuel(index);
      }
    }
  }
};
