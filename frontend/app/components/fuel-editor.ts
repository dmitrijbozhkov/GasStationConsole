import Component from '@ember/component';
import { inject } from '@ember/service';
import { observer } from '@ember/object';
import FuelService, { Fuel } from '../services/fuel-service';

enum ModalOperation {
  AddOperation = "Adding new Fuel",
  EditOperation = "Edit fuel"
}

export default class FuelEditor extends Component.extend({
  tariffService: inject("fuel-tariff-service"),
  fuelService: inject("fuel-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  modalOperation: null as unknown as ModalOperation,
  fuelTariffs: [],
  operationFuelName: "",
  operationFuelAmount: 0,
  displayTariffs: [],
  oldFuelSnapshot: {fuelName: "", fuelAmount: 0, tariffId: 0},
  fuelAmountValidator: observer("operationFuelAmount", function(this: FuelEditor) {
    if (this.get("operationFuelAmount") > 10000) {
      this.set("operationFuelAmount", 10000);
    }
    if ( this.get("operationFuelAmount") < 0) {
      this.set("operationFuelAmount", 0);
    }
  }),
  // delete modal
  isDeleteOpen: false,
  fuelToRemove: null as unknown as Fuel
}) {
  constructor() {
    super(...arguments);
    this.get("tariffService")
    .getAllFuelTariffs()
    .then((tariffs) => {
      this.set("fuelTariffs", tariffs.content);
    });
  }
  tariffsToDisplay(tariffChosenId: number) {
    return this.get("fuelTariffs").map((tariff: any) => { 
      if (tariffChosenId === tariff.id) {
        return {id: tariff.id, exchangeRate: tariff.exchangeRate, isChosen: true};
      } else {
        return {id: tariff.id, exchangeRate: tariff.exchangeRate, isChosen: false};
      }
    });
  }
  checkForm() {
    if (!this.get("operationFuelName")) {
      this.notify.error("Please provide valid fuel name", "Validation error");
      return false;
    }
    if (!this.get("operationFuelAmount")) {
      this.notify.error("Please provide valid fuel amount", "Validation error");
      return false;
    }
    return true;
  }
  createFuel() {
    if (!this.checkForm()) {
      return;
    }
    this.set("isLoading", true);
    this.get("fuelService")
    .addFuel(this.get("operationFuelName"), (this.get("displayTariffs") as any).find((t: any) => t.isChosen).id, this.get("operationFuelAmount"))
    .then((response) => {
      this.updateModel();
      this.notify.success("Fuel successfuly added", "Operation successful");
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  editFuel() {
    if (!this.checkForm()) {
      return;
    }
    this.set("isLoading", true);
    let editOperations = [];
    if ((this.get("displayTariffs") as any).find((t: any) => t.isChosen).id != this.get("oldFuelSnapshot").tariffId) {
      editOperations.push(this.get("fuelService").updateTariff(this.get("oldFuelSnapshot").fuelName, (this.get("displayTariffs") as any).find((t: any) => t.isChosen).id));
    }
    if (this.get("operationFuelAmount") != this.get("oldFuelSnapshot").fuelAmount) {
      editOperations.push(this.get("fuelService").updateLeft(this.get("oldFuelSnapshot").fuelName, this.get("operationFuelAmount")));
    }
    if (this.get("operationFuelName") != this.get("oldFuelSnapshot").fuelName) {
      editOperations.push(this.get("fuelService").updateName(this.get("oldFuelSnapshot").fuelName, this.get("operationFuelName")));
    }
    Promise.all(editOperations)
    .then((result) => {
      this.updateModel();
      this.notify.success("Fuel successfuly edited", "Operation successful");
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  updateModel() {
    this.get("fuelService")
    .getAllFuels()
    .then((result) => {
      this.set("isLoading", false);
      this.set("model" as any, result)
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  actions = {
    addFuel(this: FuelEditor) {
      this.set("modalOperation", ModalOperation.AddOperation);
      this.set("operationFuelName", "");
      this.set("operationFuelAmount", 0);
      this.set("displayTariffs", this.tariffsToDisplay((this.get("fuelTariffs")[0] as any).id));
    },
    editFuel(this: FuelEditor, fuel: Fuel) {
      this.set("modalOperation", ModalOperation.EditOperation);
      this.set("operationFuelName", fuel.fuelName);
      this.set("operationFuelAmount", fuel.storage.fuelAmount);
      this.set("displayTariffs", this.tariffsToDisplay(fuel.tariff.id));
      let temp = this.get("oldFuelSnapshot");
      temp.fuelName = fuel.fuelName;
      temp.fuelAmount = fuel.storage.fuelAmount;
      temp.tariffId = fuel.tariff.id;
    },
    removeFuel(this: FuelEditor, fuel: Fuel) {
      this.set("fuelToRemove", fuel);
      this.set("isDeleteOpen", true);
    },
    tariffChange(this: FuelEditor) {
      this.set("displayTariffs", []);
      this.set("displayTariffs", this.tariffsToDisplay(parseInt((event as any).target.value)));
    },
    closeModal(this: FuelEditor) {
      this.set("modalOperation", null);
    },
    save(this: FuelEditor) {
      let operation = this.get("modalOperation");
      this.set("modalOperation", null);
      switch(operation){
        case ModalOperation.AddOperation:
        this.createFuel()
        break;
        case ModalOperation.EditOperation:
        this.editFuel()
        break;
      }
    },
    closeDeleteModal(this: FuelEditor) {
      this.set("isDeleteOpen", false);
    },
    deleteFuel(this: FuelEditor) {
      this.set("isDeleteOpen", false);
      this.set("isLoading", true);
      this.get("fuelService")
      .removeFuel(this.get("fuelToRemove").fuelName)
      .then((result) => {
        this.updateModel();
      })
      .catch((error) => {
        this.set("isLoading", false);
        this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
      });
    }
  }
};
