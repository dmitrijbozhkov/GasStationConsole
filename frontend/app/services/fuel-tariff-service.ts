import Service from '@ember/service';
import { inject } from '@ember/service';
import { IListDTO, fetchAjax } from "./service-commons"

export interface FuelTariff {
  id: number;
  exchangeRate: number;
}

export default class FuelTariffService extends Service.extend({
  makeAuthRequest: inject("make-auth-request"),
  namespace: "/api/tariff"
}) {
  getAllFuelTariffs(): Promise<IListDTO<FuelTariff>> {
    return fetchAjax({
      type: "GET",
      url: this.get("namespace") + "/get-all",
      dataType: "json"
    });
  }
  getFuelTariff(tariffId: number): Promise<FuelTariff> {
    return fetchAjax({
      type: "POST",
      url: this.get("namespace") + "/get/" + tariffId,
      dataType: "json"
    });
  }
  addFuelTariff(exchangeRate: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/add",
      dataType: "json",
      data: JSON.stringify({
        exchangeRate: exchangeRate
      })
    });
  }
  removeFuelTariff(tariffId: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "DELETE",
      url: this.get("namespace") + "/remove/" + tariffId,
      dataType: "json"
    });
  }
  updateFuelTariff(tariffId: number, exchangeRate: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "PUT",
      url: this.get("namespace") + "/update",
      dataType: "json",
      data: JSON.stringify({
        tariffId: tariffId,
        exchangeRate: exchangeRate
      })
    });
  }
}

// DO NOT DELETE: this is how TypeScript knows how to look up your services.
declare module '@ember/service' {
  interface Registry {
    'fuel-tariff-service': FuelTariffService;
  }
}
