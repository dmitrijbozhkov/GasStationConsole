import Component from '@ember/component';
import { inject as service } from '@ember/service';
import { computed } from '@ember/object';

enum SETTINGS_MENUS {
  MyOperations = "user.my-operations",
  ChangePassword = "user.change-password",
  ManageUserAuthorities = "user.manage-authorities",
  SearchUserOperations = "user.search-user-operations",
  SearchOperations = "user.search-operations",
  ManageFuel = "user.manage-fuel",
  ManageTariff = "user.manage-tariff",
  Statistics = "user.statistics",
} 

export default class UserNavigation extends Component.extend({
  positionalParams: ["model"],
  isMyOperationsActive: false,
  isChangePasswordActive: false,
  isManageUserAuthoritiesActive: false,
  isSearchUserOperationsActive: false,
  isSearchOperationsActive: false,
  isManageFuelTanksActive: false,
  isManageFuelTariffsActive: false,
  isStatisticsActive: false,
  isAdmin: computed("model.userRole", function() {
    return this.get("model.userRole") === "ROLE_ADMIN";
  })
}) {
  router = service("router")
  resetActiveAndTransition(target: string) {
    this.set("isMyOperationsActive", false);
    this.set("isChangePasswordActive", false);
    this.set("isManageUserAuthoritiesActive", false);
    this.set("isSearchUserOperationsActive", false);
    this.set("isSearchOperationsActive", false);
    this.set("isManageFuelTanksActive", false);
    this.set("isManageFuelTariffsActive", false);
    this.set("isStatisticsActive", false);
    this.get("router").transitionTo(target);
  }
  didReceiveAttrs(this: UserNavigation) {
    this.set("isMyOperationsActive", true);
  }
  actions = {
    transitionToMenu(this: UserNavigation, target: string) {
      this.resetActiveAndTransition(target);
      switch(target) {
        case SETTINGS_MENUS.MyOperations:
        this.set("isMyOperationsActive", true);
        break;
        case SETTINGS_MENUS.ChangePassword:
        this.set("isChangePasswordActive", true);
        break;
        case SETTINGS_MENUS.ManageUserAuthorities:
        this.set("isManageUserAuthoritiesActive", true);
        break;
        case SETTINGS_MENUS.SearchUserOperations:
        this.set("isSearchUserOperationsActive", true);
        break;
        case SETTINGS_MENUS.SearchOperations:
        this.set("isSearchOperationsActive", true);
        break;
        case SETTINGS_MENUS.ManageFuel:
        this.set("isManageFuelTanksActive", true);
        break;
        case SETTINGS_MENUS.ManageTariff:
        this.set("isManageFuelTariffsActive", true);
        break;
        case SETTINGS_MENUS.Statistics:
        this.set("isStatisticsActive", true);
        break;
      }
    }
  }
};
