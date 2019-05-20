import Service from '@ember/service';
import { inject } from '@ember/service';
import { Fuel } from "./fuel-service";
import { FuelTariff } from "./fuel-tariff-service";
import { UserDetails } from "./user-service";
import { IPageDTO, AMOUNT_PAGE } from "./service-commons";

export enum OrderType {
  FUEL_BY_CURRENCY = "FUEL_BY_CURRENCY",
  CURRENCY_BY_FUEL = "CURRENCY_BY_FUEL"
}

export interface Order {
  id: number;
  orderType: OrderType;
  orderDate: Date;
  amount: number;
  fuel: Fuel;
  tariff: FuelTariff;
  user: UserDetails
}

export interface FuelVolumeOfSales {
  fuelName: string;
  volumeOfSales: number;
}

export interface FuelsVolumeOfSales {
  fuelVolumeOfSales: Array<FuelVolumeOfSales>;
  overallVolumeOfSales: number;
}

export default class FuelOrderService extends Service.extend({
  makeAuthRequest: inject("make-auth-request"),
  namespace: "/api/order"
}) {
  createOrder(fuelName: string, amount: number, orderType: string) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/create",
      dataType: "json",
      data: JSON.stringify({
        fuelName: fuelName,
        amount: amount,
        orderType: orderType,
        orderDate: new Date()
      })
    });
  }
  removeOrder(orderId: number) {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "DELETE",
      url: this.get("namespace") + "/remove/" + orderId,
      dataType: "json"
    });
  }
  queryCurrentUser(page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query-user-current",
      dataType: "json",
      data: JSON.stringify({
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  query(page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query",
      dataType: "json",
      data: JSON.stringify({
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  queryFuel(fuelName: string, page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query-fuel",
      dataType: "json",
      data: JSON.stringify({
        fuelName: fuelName,
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  queryUser(username: string, page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query-user",
      dataType: "json",
      data: JSON.stringify({
        username: username,
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  queryDate(before: Date, after: Date, page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query-date",
      dataType: "json",
      data: JSON.stringify({
        before: before,
        after: after,
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  queryDateFuel(before: Date, after: Date, fuelName: string, page: number): Promise<IPageDTO<Order>> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/query-fuel-date",
      dataType: "json",
      data: JSON.stringify({
        fuelName: fuelName,
        before: before,
        after: after,
        page: page,
        amount: AMOUNT_PAGE
      })
    });
  }
  getVolumeOfSales(before: Date, after: Date): Promise<FuelsVolumeOfSales> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/volume-of-sales",
      dataType: "json",
      data: JSON.stringify({
        before: before,
        after: after
      })
    });
  }
  getFuelVolumeOfSales(before: Date, after: Date, fuelNames: Array<string>): Promise<FuelsVolumeOfSales> {
    return this.get("makeAuthRequest").ajaxRequest({
      type: "POST",
      url: this.get("namespace") + "/volume-of-sales-fuel",
      dataType: "json",
      data: JSON.stringify({
        fuelNames: fuelNames,
        before: before,
        after: after
      })
    });
  }
}

// DO NOT DELETE: this is how TypeScript knows how to look up your services.
declare module '@ember/service' {
  interface Registry {
    'fuel-order-service': FuelOrderService;
  }
}
