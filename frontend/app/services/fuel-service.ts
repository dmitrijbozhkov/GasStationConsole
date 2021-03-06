import Service from '@ember/service';
import { inject } from '@ember/service';
import { IListDTO, fetchAjax } from "./service-commons";

export interface FuelTariff {
  id: number;
  exchangeRate: number;
}

export interface Storage {
  id: number;
  fuelAmount: number;
}

export interface Fuel {
  fuelName: string;
  tariff: FuelTariff;
  storage: Storage;
}

export default class FuelService extends Service.extend({
  makeAuthRequest: inject("make-auth-request"),
  namespace: "/api/fuel"
}) {
  getAllFuels(): Promise<IListDTO<Fuel>> {
    return fetchAjax({
      type: "GET",
      url: this.get("namespace") + "/get-all",
      dataType: "json"
    })
  }
  getFuel(fuelName: string): Promise<Fuel> {
    return fetchAjax({
      type: "POST",
      url: this.get("namespace") + "/get",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: fuelName
      })
    });
  }
  addFuel(fuelName: string, tariffId: number, fuelLeft: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/add",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: fuelName,
        tariffId: tariffId,
        fuelLeft: fuelLeft
      })
    });
  }
  removeFuel(fuelName: string) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "DELETE",
      url: this.get("namespace") + "/remove",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: fuelName
      })
    });
  }
  updateName(oldFuelName: string, nextFuelName: string) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "PUT",
      url: this.get("namespace") + "/update-name",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: oldFuelName,
        nextFuelName: nextFuelName
      })
    });
  }
  updateTariff(fuelName: string, tariffId: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "PUT",
      url: this.get("namespace") + "/update-tariff",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: fuelName,
        tariffId: tariffId
      })
    });
  }
  updateLeft(fuelName: string, fuelLeft: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "PUT",
      url: this.get("namespace") + "/update-left",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        fuelName: fuelName,
        fuelLeft: fuelLeft
      })
    });
  }
}

// DO NOT DELETE: this is how TypeScript knows how to look up your services.
declare module '@ember/service' {
  interface Registry {
    'fuel-service': FuelService;
  }
}
