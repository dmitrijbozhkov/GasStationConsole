import Component from '@ember/component';
import { inject } from '@ember/service';
import { observer } from '@ember/object';
import { FuelTariff } from "../services/fuel-tariff-service";

enum ModalOperation {
  AddOperation = "Adding new Fuel tariff",
  EditOperation = "Edit fuel tariff"
}

export default class TariffEditor extends Component.extend({
  tariffService: inject("fuel-tariff-service"),
  notify: inject("toast" as any) as any,
  isLoading: false,
  modalOperation: null as unknown as ModalOperation,
  operationExchangeRate: 0,
  validateExchangeRate: observer("operationExchangeRate", function(this: TariffEditor) {
    if (!this.get("operationExchangeRate")) {
      this.set("operationExchangeRate", 0);
    }
  }),
  oldTariffSnapshot: {id: 0, exchangeRate: 0},
  // delete
  isDeleteOpen: false,
  tariffToDelete: null as unknown as FuelTariff
}) {
  updateModel() {
    this.get("tariffService")
    .getAllFuelTariffs()
    .then((tariffs) => {
      this.set("model" as any, tariffs);
      this.set("isLoading", false);
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  createTariff() {
    this.get("tariffService")
    .addFuelTariff(this.get("operationExchangeRate"))
    .then((result) => {
      this.updateModel();
      this.notify.success("Fuel tariff successfuly added", "Operation successful");
    })
    .catch((error) => {
      console.log(error);
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  editTariff() {
    if (this.get("operationExchangeRate") === this.get("oldTariffSnapshot").exchangeRate) {
      this.set("isLoading", false);
      this.notify.error("Please change exchange rate to edit", "Validation Error");
      return;
    }
    this.get("tariffService")
    .updateFuelTariff(this.get("oldTariffSnapshot").id, this.get("operationExchangeRate"))
    .then((result) => {
      this.updateModel();
      this.notify.success("Fuel tariff successfuly edited", "Operation successful");
    })
    .catch((error) => {
      this.set("isLoading", false);
      this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
    });
  }
  actions = {
    addTariff(this: TariffEditor) {
      this.set("modalOperation", ModalOperation.AddOperation);
      this.set("operationExchangeRate", 0);
    },
    editTariff(this: TariffEditor, tariff: FuelTariff) {
      this.set("modalOperation", ModalOperation.EditOperation);
      this.set("operationExchangeRate", tariff.exchangeRate);
      console.log(tariff);
      this.set("oldTariffSnapshot", {id: tariff.id, exchangeRate: tariff.exchangeRate});
    },
    removeTariff(this: TariffEditor, tariff: FuelTariff) {
      this.set("isDeleteOpen", true);
      this.set("tariffToDelete", tariff);
    },
    save(this: TariffEditor) {
      let operation = this.get("modalOperation");
      this.set("modalOperation", null);
      this.set("isLoading", true);
      switch(operation){
        case ModalOperation.AddOperation:
        this.createTariff()
        break;
        case ModalOperation.EditOperation:
        this.editTariff()
        break;
      }
    },
    closeModal(this: TariffEditor) {
      this.set("modalOperation", null);
    },
    confirmDelete(this: TariffEditor) {
      this.set("isLoading", true);
      this.get("tariffService")
      .removeFuelTariff(this.get("oldTariffSnapshot").id)
      .then((response) => {
        this.set("isDeleteOpen", null);
        this.notify.success("Fuel tariff successfuly removed", "Operation successful");
        this.updateModel();
      })
      .catch((error) => {
        this.set("isLoading", false);
        this.notify.error(error.responseJSON.exceptionMessage, "Error getting user operations");
      });
    },
    closeDeleteModal(this: TariffEditor) {
      this.set("isDeleteOpen", false);
    }
  }
};
